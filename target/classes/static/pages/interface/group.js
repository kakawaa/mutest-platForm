layui.use(['transfer', 'table', 'layer', 'form', 'util'], function () {
    var table = layui.table //表格
        , layer = layui.layer //弹层
        , form = layui.form
        , transfer = layui.transfer
        , util = layui.util;

    //执行实例
    table.render({
        elem: '#group_table'
        , id: 'groupReload'
        // , skin: 'line' //行边框风格
        , even: true //开启隔行背景
        , url: '/interface/groupList'
        , request: {
            pageName: 'pageNum',
            limitName: 'pageSize'
        }
        , toolbar: '#group_headerBar'
        , title:
            '分组列表'
        , page:
            true
        , limit: 10
        , limits: [1, 5, 10, 20, 50, 100]
        , cols:
            [[{field: 'number', title: '序号', width: 60, align: 'center', type: 'numbers'}
                , {field: 'id', title: '组ID', width: 60, align: 'center', hide: true}
                , {field: 'groupName', title: '组名称', width: '15%', align: 'center'}
                , {field: 'groupLeader', title: '负责人', width: '15%', align: 'center'}
                , {field: 'description', title: '组说明', align: 'center'}
                , {
                    field: 'createTime',
                    title: '创建时间',
                    align: 'center',
                    width: 240,
                    hide: true
                }
                , {
                    field: 'modifyTime',
                    title: '修改时间',
                    align: 'center',
                    width: '20%',
                }
                , {
                    fixed: 'right',
                    title: '操作',
                    toolbar: '#group_lineBar',
                    width: 218,
                    align: 'center'
                }
            ]]
    });

    // 监听头部工具事件
    table.on('toolbar(groupBar)', function (obj) {
        var checkStatus = table.checkStatus(obj.config.id);
        var data = checkStatus.data; //获取选中的数据

        switch (obj.event) {
            case 'refresh':
                tableReload('groupReload', '', 'application/x-www-form-urlencoded', '/interface/groupList', 'get');
                break;
            case 'add':
                data = {};
                data.action = 'addGroup';
                data.type = 'POST';

                open_form("#add_div", data, '添加分组', 'url', '617px', '317px');
                break;
        }
    });

    // 监听行工具事件
    table.on('tool(groupBar)', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;
        var groupId = data.id;

        switch (layEvent) {
            case 'edit':
                data.action = 'updateGroup';
                data.type = 'put';
                open_form("#add_div", data, '编辑分组', 'url', '617px', '317px');
                break;
            case 'userDetail':
                index = layer.open({
                    type: 1,
                    title: data.groupName + '用户信息详情',
                    // area: ['auto', 'auto'],//类型：String/Array，默认：'auto'  只有在宽高都定义的时候才不会自适应
                    area: ['630px', '412px'],
                    fix: false, //不固定
                    maxmin: true,//开启最大化最小化按钮
                    shadeClose: true,//点击阴影处可关闭
                    shade: 0.4,//背景灰度
                    skin: 'layui-layer-lan',
                    content: $("#userDetail"),
                    success: function () {
                        $.ajax({
                            type: 'get',
                            url: "/interface/groupUserList?groupId=" + groupId,
                            contentType: "application/json; charset=utf-8",
                            data: JSON.stringify(data.field),
                            dataType: "json",
                            success: function (result) {
                                console.log("user:", result.data.sysUsers);
                                if (result.code === "0") {
                                    //定义标题及数据源
                                    transfer.render({
                                        elem: '#userDetail'
                                        , title: ['全部成员', '本组成员']  //自定义标题
                                        // , data: result.data.sysUsers
                                        , data: result.data.sysUsers
                                        , parseData: function (res) {
                                            return {
                                                "value": res.id //数据值
                                                , "title": res.nickname //数据标题
                                            }
                                        }
                                        , value: result.data.groupUserIds
                                        , showSearch: true
                                        , onchange: function (obj, index) {
                                            layer.confirm('确认修改组成员？', {
                                                skin: 'layui-layer-molv'
                                                , closeBtn: 1
                                                , btn: ['确定', '取消']
                                                , yes: function () {
                                                    $.ajax({
                                                        type: 'post',
                                                        url: '/interface/updateGroupUsers',
                                                        contentType: "application/json; charset=utf-8",
                                                        data: JSON.stringify({
                                                            "groupId": groupId,
                                                            "index": index,
                                                            "userList": obj
                                                        }),
                                                        success: function (result) {
                                                            if (result.code === "0") {
                                                                layer.msg(result.msg, {
                                                                    icon: 1,
                                                                    time: 1000
                                                                });
                                                            } else {
                                                                layer.alert(result.msg, {icon: 2});
                                                            }
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
                                        }
                                        , width: 218 //定义宽度
                                        , height: 340 //定义高度
                                    });
                                } else {
                                    layer.alert(result.msg, {icon: 2}, function () {
                                        layer.close(index);
                                    });
                                }
                            }
                        });
                    }
                });

                break;
            case 'projectDetail':
                index = layer.open({
                    type: 1,
                    title: data.groupName + '项目信息详情',
                    area: ['630px', '412px'],
                    fix: false,
                    maxmin: true,
                    shadeClose: true,
                    shade: 0.4,
                    skin: 'layui-layer-lan',
                    content: $("#projectDetail"),
                    success: function () {
                        $.ajax({
                            type: 'get',
                            url: "/interface/groupProjectList?groupId=" + groupId,
                            contentType: "application/json; charset=utf-8",
                            data: JSON.stringify(data.field),
                            dataType: "json",
                            success: function (result) {
                                console.log("project:", result.data.projectList);
                                if (result.code === "0") {
                                    //定义标题及数据源
                                    transfer.render({
                                        elem: '#projectDetail'
                                        , title: ['全部项目', '本组项目']  //自定义标题
                                        // , data: result.data.sysUsers
                                        , data: result.data.projectList
                                        , parseData: function (res) {
                                            return {
                                                "value": res.id //数据值
                                                , "title": res.projectName //数据标题
                                            }
                                        }
                                        , value: result.data.groupProjectIds
                                        , showSearch: true
                                        , onchange: function (obj, index) {
                                            layer.confirm('确认修改组项目？', {
                                                skin: 'layui-layer-molv'
                                                , closeBtn: 1
                                                , btn: ['确定', '取消']
                                                , yes: function () {
                                                    $.ajax({
                                                        type: 'post',
                                                        url: '/interface/updateGroupProject',
                                                        contentType: "application/json; charset=utf-8",
                                                        data: JSON.stringify({
                                                            "groupId": groupId,
                                                            "index": index,
                                                            "projectList": obj
                                                        }),
                                                        success: function (result) {
                                                            if (result.code === "0") {
                                                                layer.msg(result.msg, {
                                                                    icon: 1,
                                                                    time: 1000
                                                                });
                                                            } else {
                                                                layer.alert(result.msg, {icon: 2});
                                                            }
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
                                        }
                                        , width: 218 //定义宽度
                                        , height: 340 //定义高度
                                    });
                                } else {
                                    layer.alert(result.msg, {icon: 2}, function () {
                                        layer.close(index);
                                    });
                                }
                            }
                        });
                    }
                });

                break;
            case 'delete':

                break;
        }
    });

    // 监听提交
    form.on('submit(update_submit)', function (data) {
        var uri = data.field.action;
        var type = data.field.type;

        $.ajax({
            type: type,
            url: "/interface/" + uri,
            contentType: "application/json; charset=utf-8",
            data: JSON.stringify(data.field),
            dataType: "json",
            success: function (result) {
                if (result.code === "0") {
                    layer.close(indexForm);
                    layer.msg(result.msg, {icon: 1, time: 1000});
                    tableReload('groupReload', '', 'application/x-www-form-urlencoded', '/interface/groupList', 'get');
                } else {
                    layer.alert(result.msg, {icon: 2}, function () {
                        layer.close(indexForm);
                    });
                }
            }
        });
        return false;
    });
    // 搜索
    form.on('submit(search)', function (data) {
        var formData = data.field;
        var count = checkForm("search_form");
        if (count != 0) {
            tableReload('groupReload', formData, "application/json; charset=utf-8", '/interface/searchGroup', 'post');
        } else {
            parent.layer.msg('请先选择查询条件！', {icon: 2, time: 1500});
        }
        return false;
    });
});