layui.use(['table', 'layer', 'form', 'laypage', 'laydate'], function () {
    var table = layui.table //表格
        , layer = layui.layer //弹层
        , form = layui.form; //form表单
    //执行实例
    table.render({
        elem: '#case_table'
        , id: 'caseReload'
        , skin: 'line' //行边框风格
        , even: true //开启隔行背景
        , url: '/interface/caseList'
        , request: {
            pageName: 'pageNum',
            limitName: 'pageSize'
        }
        , toolbar: '#case_headerBar'
        , title:
            '接口用例列表'
        , page:
            true //开启分页
        , limit: 5
        , limits: [1, 5, 10, 20, 50, 100]
        , cols:
            [[{field: 'id', title: '编号', width: 60, align: 'center'}
                , {field: 'projectName', title: '项目名称', width: '8%', align: 'center'}
                , {field: 'moduleName', title: '模&emsp;块', width: '9%', align: 'center'}
                , {field: 'interfaceName', title: '接口名称', width: '11%', align: 'center'}
                , {
                    // 使用自定义模板
                    field: 'caseType', title: '用例类型', width: '8%', align: 'center'
                    , templet: function (d) {
                        var caseType = d.caseType;
                        if (caseType === '标准用例') {
                            return '<font color="#0000FF">标准用例</font>';
                        } else if (caseType === '正常用例') {
                            return '<font color="#5FB878">正常用例</font>';
                        } else {
                            return '<font color="#FF0000">异常用例</font>';
                        }
                    }
                }
                , {field: 'creator', title: '创建者', width: '7.5%', align: 'center'}
                , {field: 'delay', title: '前置延迟', width: '7.5%', align: 'center'}
                , {field: 'description', title: '用例说明', align: 'center'}
                , {field: 'projectId', title: '项目ID', width: '8%', align: 'center', hide: true}
                , {
                    field: 'createTime',
                    title: '修改时间',
                    align: 'center',
                    hide: true
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
                    toolbar: '#case_lineBar',
                    width: 210,
                    align: 'center'
                }
            ]]
    });
    selectInit('/interface/getProject', $("#projectNameSearch"), '请选择项目');
    //监听头部工具事件
    table.on('toolbar(caseBar)', function (obj) {
        var checkStatus = table.checkStatus(obj.config.id);
        var data = checkStatus.data;
        switch (obj.event) {
            case 'refresh':
                tableReload('caseReload', '', 'application/x-www-form-urlencoded', '/interface/caseList', 'get');
                selectInit('/interface/getProject', $("#projectNameSearch"));
                break;
            case 'add':
                data = {};
                data.action = 'addCase';
                data.type = 'post';
                selectInit('/interface/getProject', $("#projectNameAdd"));
                open_form("#add_div", data, '添加用例', 'url', '670px', '450px');
                break;
        }
    });

    // 监听行工具事件
    table.on('tool(caseBar)', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;
        var caseId = data.id;

        switch (layEvent) {
            case 'edit':
                selectInit('/interface/getProject', $("#projectNameAdd"));
                $("#projectNameAdd").empty().append(new Option(data.projectName));
                $("#moduleNameAdd").empty().append(new Option(data.moduleName));
                $("#interfaceName").empty().append(new Option(data.interfaceName));
                data.action = 'updateCaseMain';
                data.type = 'post';
                open_form("#add_div", data, '编辑用例', 'url', '670px', '450px');
                break;
            case 'detail':
                openInfo('用例详情页', 'caseInfo.html?id=' + caseId);
                // window.location = 'caseInfo.html?id=' + caseId;
                break;

            case 'delete':
                layer.confirm('删除该用例将同时删除包含该用例的测试集合，请确认', {
                    skin: 'layui-layer-molv'
                    , closeBtn: 1
                    , btn: ['确定', '取消']
                    , yes: function () {
                        $.ajax({
                            type: 'get',
                            url: '/interface/deleteCaseById?caseId=' + caseId,
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
            case 'execute':
                layer.msg('开始执行', {icon: 1, time: 500});
                $.ajax({
                    type: 'post',
                    url: '/interface/executeCase',
                    contentType: "application/json; charset=utf-8",
                    data: JSON.stringify({"caseId": caseId}),
                    success: function (result) {
                        if (result.code === 0) {
                            try {
                                $("#showResponse").html(JSON.stringify(JSON.parse(result.data.caseResponse), null, 4));
                            } catch (e) {
                                $("#showResponse").html(result.data.caseResponse);
                            }
                        } else {
                            $("#showResponse").html(result.msg);
                        }
                        $("#showError").html(result.data.caseRequest + "<br/><br/><br/>POST data:<br/>" + result.data.bodyData + "<br/><br/>Request Headers:<br/>" + result.data.headerData);
                        layer.msg(result.msg, {icon: 1, time: 1000});
                    }, error: function (e) {
                        console.log(e, 'error');
                        $("#showResponse").html("接口调用失败，请联系管理员！");
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
                    tableReload('caseReload', '', 'application/x-www-form-urlencoded', '/interface/caseList', 'get');
                    selectInit('/interface/getProject', $("#projectNameSearch"), '请选择项目');
                    layer.close(indexForm);
                    layer.msg(result.msg, {icon: 1, time: 1000});
                    var caseId = result.data.id;

                    var index3 = layer.confirm('是否立即完善该用例？', {
                        skin: 'layui-layer-molv'
                        , closeBtn: 1
                        , btn: ['确定', '取消']
                        , yes: function () {
                            layer.close(index3);
                            openInfo('用例详情页', 'caseInfo.html?id=' + caseId);
                        },
                        btn2: function () {
                            layer.msg('好的,暂时不执行', {
                                icon: 1,
                                time: 1000
                            });
                        }
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
        return false;
    });

    // 监听搜索按钮提交事件
    form.on('submit(search)', function (data) {
        var formData = data.field;
        var count = checkForm("search_form");
        if (count !== 0) {
            //数据表格重载
            tableReload('caseReload', formData, "application/json; charset=utf-8", '/interface/searchCase', 'post');
            selectInit('/interface/getProject', $("#projectNameSearch"), '请选择项目');
        } else {
            parent.layer.msg('请先选择查询条件！', {icon: 2, time: 1500});
        }
        return false;
    });

    // 监听搜索框项目选择事件
    form.on('select(projectNameSearch)', function (data) {
        var projectName = data.value;
        var url = '/interface/getModule?projectName=' + projectName;
        var select = $("#moduleNameSearch");
        selectInit(url, select, '模块');
    });

    // 监听新增页面项目选择事件
    form.on('select(projectNameAdd)', function (data) {
        var projectName = data.value;
        var url = '/interface/getModule?projectName=' + projectName;
        var select = $("#moduleNameAdd");
        selectInit(url, select, '请选择模块');
    });

    // 监听新增页面模块选择事件
    form.on('select(moduleNameAdd)', function (data) {
        var projectName = $('#projectNameAdd option:selected').val();
        var moduleName = data.value;
        var url = '/interface/getInterface?projectName=' + projectName + "&moduleName=" + moduleName;
        var select = $("select[name='interfaceName']");

        selectInit(url, select, '请选择接口');
    });
});