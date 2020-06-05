layui.use(['table', 'layer', 'form', 'laypage', 'laydate', 'tree'], function () {
    var table = layui.table //表格
        , layer = layui.layer //弹层
        , form = layui.form //form表单
        , tree = layui.tree;
    //执行实例
    table.render({
        elem: '#project_table'
        , id: 'projectReload'
        // , skin: 'line' //行边框风格
        , even: true //开启隔行背景
        , url: '/interface/projectList'
        , request: {
            pageName: 'pageNum',
            limitName: 'pageSize'
        }
        , toolbar: '#project_headerBar'
        , title:
            '项目列表'
        , page:
            true //开启分页
        , limit: 10
        , limits: [1, 5, 10, 20, 50, 100]
        , cols:
            [[{field: 'number', title: '序号', width: 60, align: 'center', type: 'numbers'}
                , {field: 'projectName', title: '项目名称', width: '8%', align: 'center'}
                , {field: 'url', title: '请求地址', width: '20%', align: 'center'}
                , {field: 'creator', title: '创建者', width: '9%', align: 'center'}
                , {field: 'interfaceCount', title: '接口数量', width: '9%', align: 'center'}
                , {field: 'caseCount', title: '用例数量', width: '9%', align: 'center'}
                , {field: 'description', title: '项目说明', align: 'center'}
                , {field: 'id', title: '项目ID', width: '8%', align: 'center', hide: true}
                , {
                    field: 'createTime',
                    title: '修改时间',
                    align: 'center',
                }
                , {
                    field: 'modifyTime',
                    title: '修改时间',
                    align: 'center',
                    hide: true
                }
                , {
                    fixed: 'right',
                    title: '操作',
                    toolbar: '#project_lineBar',
                    width: 268,
                    align: 'center'
                }
            ]]
    });
    //监听头部工具事件
    table.on('toolbar(projectBar)', function (obj) {
        var checkStatus = table.checkStatus(obj.config.id);
        var data = checkStatus.data;
        switch (obj.event) {
            case 'refresh':
                tableReload('projectReload', '', 'application/x-www-form-urlencoded', '/interface/projectList', 'get');
                break;
            case 'add':
                data = {};
                data.action = 'addProject';
                data.type = 'post';
                open_form("#add_div", data, '添加项目', 'url', '610px', '350px');
                break;
            case 'sync':
                $.ajax({
                    type: 'get',
                    url: "/interface/projectSync",
                    contentType: "application/x-www-form-urlencoded",
                    success: function (result) {
                        if (result.code === "0") {
                            layer.msg(result.msg, {icon: 1, time: 1000});
                        } else {
                            layer.alert(result.msg, {
                                icon: 2,
                                time: 2000
                            });
                        }
                    }, error: function (e) {
                        layer.alert(e.responseJSON.message, {
                            icon: 2,
                            time: 2000
                        });
                    }
                });
                break;
        }
    });

    // 监听行工具事件
    table.on('tool(projectBar)', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;
        var projectId = data.id;
        var projectName = data.projectName;

        switch (layEvent) {
            case 'edit':
                data.action = 'updateProject';
                data.type = 'put';
                open_form("#add_div", data, '编辑项目', 'url', '610px', '350px');
                break;
            case 'interfaces':
                openInfo(projectName + '项目接口列表', 'interfaceList.html?projectId=' + projectId + '&interfaceId=');
                break;
            case 'cases':
                openInfo(projectName + '项目用例列表', 'caseList.html?projectId=' + projectId + '&interfaceId=');
                break;
            case 'structure':
                index = layer.open({
                    type: 1,
                    title: data.projectName + '项目结构图',
                    area: ['100%', '100%'],
                    fix: false,
                    maxmin: true,
                    shadeClose: true,
                    shade: 0.4,
                    // skin: 'layui-layer-lan',
                    content: $("#structure"),
                    success: function () {
                        $.ajax({
                            type: 'get',
                            url: "/interface/projectStructure?projectName=" + projectName,
                            contentType: "application/json; charset=utf-8",
                            data: JSON.stringify(data.field),
                            dataType: "json",
                            success: function (result) {
                                if (result.code === "0") {
                                    //定义标题及数据源
                                    //基本演示
                                    tree.render({
                                        elem: '#structure'
                                        , data: result.data
                                        // ,showCheckbox: true  //是否显示复选框
                                        , id: 'demoId1'
                                        , isJump: true //是否允许点击节点时弹出新窗口跳转
                                    });
                                } else {
                                    layer.alert(result.msg, {
                                        icon: 2,
                                        time: 2000
                                    });
                                }
                            }, error: function (e) {
                                layer.alert(e.responseJSON.message, {
                                    icon: 2,
                                    time: 2000
                                });
                            }
                        });
                    }
                });
                break;

            case 'delete':
                layer.confirm('删除该用例将同时删除包含该用例的测试集合，请确认', {
                    skin: 'layui-layer-molv'
                    , closeBtn: 1
                    , btn: ['确定', '取消']
                    , yes: function () {
                        $.ajax({
                            type: 'get',
                            url: '',
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
                    tableReload('projectReload', '', 'application/x-www-form-urlencoded', '/interface/projectList', 'get');
                    layer.close(indexForm);
                    layer.msg(result.msg, {icon: 1, time: 1000});
                } else {
                    layer.alert(result.msg, {
                        icon: 2,
                        time: 2000
                    });
                }
            }
        });
        return false;
    });
});