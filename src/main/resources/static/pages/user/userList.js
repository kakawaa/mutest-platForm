layui.extend({optimizeSelectOption: '../../../layui/plugin/optimizeSelectOption/optimizeSelectOption'})
    .use(['table', 'layer', 'form', 'laypage', 'laydate'], function () {
        var table = layui.table //表格
            , layer = layui.layer //弹层
            , form = layui.form; //form表单
        //执行实例
        table.render({
            elem: '#users_table'
            , id: 'usersReload'//重载数据表格
            , url: '/users/userList'
            , request: {
                pageName: 'pageNum', //页码的参数名称，默认：page
                limitName: 'pageSize' //每页数据量的参数名，默认：limit
            }
            , toolbar: '#users_headerBar' //开启头工具栏，此处default：显示默认图标，可以自定义模板，详见文档
            , title: '用户列表'
            , page: true //开启分页
            , limit: 10
            , limits: [1, 5, 10, 20, 50, 100] //每页默认显示的数量
            // ,totalRow: true //开启合计行
            , cols:
                [[
                    {field: 'number', title: '序号', width: 65, align: 'center', type: 'numbers'}
                    , {field: 'nickname', title: '姓名', width: '8.5%', align: 'center'}
                    , {field: 'username', title: '登录名', width: '9.5%', align: 'center'}
                    , {field: 'department', title: '部门', width: '9%', align: 'center'}
                    , {field: 'role', title: '角色', width: '9%', align: 'center'}
                    , {
                        field: 'sex',
                        title: '性别',
                        width: '6%',
                        align: 'center',
                        templet: function (d) {
                            if (d.sex == 1) {
                                return "男";
                            } else {
                                return "女";
                            }
                        }
                    }
                    , {
                        field: 'status', title: '状态', width: '7%', align: 'center', templet: function (d) {
                            if (d.status == 0) {
                                return "无效";
                            } else if (d.status == 1) {
                                return "正常";
                            } else {
                                return "锁定";
                            }
                        }
                    }
                    , {field: 'phone', title: '手机号', width: '10%', align: 'center'}
                    , {field: 'description', title: '备注', align: 'center'}
                    , {
                        field: 'createTime',
                        title: '创建时间',
                        align: 'center',
                        hide: true
                    }
                    , {
                        field: 'updateTime',
                        title: '修改时间',
                        align: 'center',
                        hide: true
                    }
                    , {
                        fixed: 'right',
                        title: '操作',
                        toolbar: '#user_lineBar',
                        width: 115,
                        align: 'center'
                    }
                ]]
        });

        //监听头部工具事件
        table.on('toolbar(usersBar)', function (obj) {
            var checkStatus = table.checkStatus(obj.config.id);
            var data = checkStatus.data;
            switch (obj.event) {
                case 'refresh':
                    tableReload('usersReload', '', 'application/x-www-form-urlencoded', '/users/userList', 'get');
                    break;
                case 'add':
                    data = {};
                    data.action = 'addUser';
                    data.type = 'post';
                    // data.creator = currentUser()
                    // data.creator = currentUser().nickname;
                    selectInit('/dicts/departmentList', $("#departmentAdd"), '请选择部门');
                    selectInit('/roles/roleNicknameList', $("#roleAdd"), '请选择角色');
                    $("#username").removeAttr("readonly");
                    $("#password-div").removeAttr("style");
                    open_form("#add_div", data, '添加用户', 'url', '100%', '100%');
                    break;
            }
        });

        // 监听行工具事件
        table.on('tool(usersBar)', function (obj) {
            var data = obj.data;
            var layEvent = obj.event;
            var userId = data.id;

            switch (layEvent) {
                case 'edit':
                    data.action = 'updateUser';
                    data.type = 'put';
                    $("#username").attr("readonly", "readonly");
                    $("#password-div").attr("style", "display:none");
                    selectInit('/dicts/departmentList', $("#departmentAdd"), '请选择部门');
                    selectInit('/roles/roleNicknameList', $("#roleAdd"), '请选择角色');
                    $("#departmentAdd").empty().append(new Option(data.department));
                    $("#roleAdd").empty().append(new Option(data.role));

                    open_form("#add_div", data, '编辑用户', 'url', '100%', '100%');
                    break;
                case 'delete':
                    layer.confirm('确认要删除该用户？', {
                        skin: 'layui-layer-molv'
                        , closeBtn: 1
                        , btn: ['确定', '取消']
                        , yes: function () {
                            $.ajax({
                                type: 'get',
                                url: '/users/deleteUserById?userId=' + userId,
                                contentType: "application/json; charset=utf-8",
                                success: function (result) {
                                    layer.msg(result.msg, {
                                        icon: 1,
                                        time: 1000
                                    });
                                }, error: function (e) {
                                    console.log(e, 'error');
                                }
                            });
                        },
                        btn2: function () {
                            layer.msg('好的,暂时不执行', {
                                icon: 1,
                                time: 1000
                            });
                        }
                    });
                    break;
            }
        });

        // 监听保存提交事件
        form.on('submit(form_submit)', function (data) {
            var uri = data.field.action;
            var type = data.field.type;
            // console.log(data);
            // var admin = data.field.admin;
            // var user = data.field.user;
            //
            // var roleIds = [];
            // if (admin === "on")
            //     roleIds.push(1);
            // if (user === "on")
            //     roleIds.push(2);
            //
            // data.field.roleIds = roleIds;
            $.ajax({
                type: type,
                url: "/users/" + uri,
                contentType: "application/json; charset=utf-8",
                data: JSON.stringify(data.field),
                dataType: "json",
                success: function (result) {
                    if (result.code == "0") {
                        tableReload('usersReload', '', 'application/x-www-form-urlencoded', '/users/userList', 'get');
                        layer.close(index);
                        layer.msg(result.msg, {icon: 1, time: 1000});
                    } else {
                        layer.alert(result.msg, {icon: 2}, function () {
                            layer.close(index);
                        });
                    }
                }
            });
            return false;
        });

        //监听返回按钮点击事件
        form.on('submit(cancel_button)', function () {
            // var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
            // parent.layer.close(index); //再执行关闭
            layer.close(index);
            // location.href = "userList.html";
            return false;
        });

        // 监听搜索按钮提交事件
        form.on('submit(search)', function (data) {
            var formData = data.field;
            var count = checkForm("search_form");
            if (count != 0) {
                //数据表格重载
                tableReload('usersReload', formData, "application/json; charset=utf-8", '/users/searchUser', 'post');
            } else {
                parent.layer.msg('请先选择查询条件！', {icon: 2, time: 1500});
            }
            return false;
        });
    });