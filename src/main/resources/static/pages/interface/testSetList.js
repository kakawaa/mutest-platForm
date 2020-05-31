// 初始模块引用
layui.use(['table', 'layer', 'form', 'laypage', 'laydate'], function () {
    var table = layui.table
        , layer = layui.layer
        , form = layui.form;
    //执行实例
    table.render({
        elem: '#testSet_table',
        id: 'testSetReload'
        , url: '/interface/testSetList'
        , request: {
            pageName: 'pageNum',
            limitName: 'pageSize'
        }
        , toolbar: '#testSet_headerBar'
        , title:
            '接口列表'
        , page:
            true //开启分页
        , limit: 10
        , limits: [1, 5, 10, 20, 50, 100]
        , cols:
            [[{field: 'id', title: '编号', width: 60, align: 'center'}
                , {field: 'projectName', title: '项目名称', width: '9%', align: 'center'}
                , {field: 'caseIdQueue', title: '用例队列', width: '20%', align: 'center'}
                , {field: 'caseNameQueue', title: '队列描述', width: '28%', align: 'center'}
                , {field: 'creator', title: '创建者', width: '8%', align: 'center'}
                , {field: 'description', title: '用例说明', align: 'center'}
                , {
                    field: 'createTime',
                    title: '创建时间',
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
                    toolbar: '#testSet_lineBar',
                    width: 160,
                    align: 'center'
                }
            ]]
    });
    // 下拉选择框初始化
    selectInit('/interface/getProject', $("#projectNameSearch"), '请选择项目');
    // 监听头部工具事件
    table.on('toolbar(testSetBar)', function (obj) {
        switch (obj.event) {
            case 'refresh':
                tableReload('testSetReload', '', 'application/x-www-form-urlencoded', '/interface/testSetList', 'get');
                selectInit('/interface/getProject', $("#projectNameSearch"), '请选择项目');
                break;
            case 'add':
                var data = {"testSetId": -1};
                open_form('#add_div', data, '测试场景详情', 'url', '100%', '100%');
                initInfo(-1);
                // window.location = 'testSetInfo.html?id=-1';
                break;
            case 'executeAll':
                layer.confirm('确认执行？', {
                    skin: 'layui-layer-molv'
                    , closeBtn: 1
                    , btn: ['确定', '取消']
                    , yes: function () {
                        layer.msg('执行开始', {
                            time: 1000
                        });
                        var executor = currentUser().nickname;
                        $.ajax({
                            type: 'get',
                            url: '/interface/executeAllTestSet?executor=' + executor,
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
    // 监听行工具事件
    table.on('tool(testSetBar)', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;
        var testSetId = data.id;
        data.testSetId = testSetId;

        switch (layEvent) {
            case 'edit':
                // window.location = 'testSetInfo.html?id=' + testSetId;
                open_form('#add_div', data, '测试场景详情', 'url', '100%', '100%');
                initInfo(testSetId);
                break;
            case 'execute':
                layer.msg('执行开始', {
                    time: 1000
                });
                $.ajax({
                    type: 'post',
                    url: '/interface/executeTestSet',
                    contentType: "application/json; charset=utf-8",
                    data: JSON.stringify({"testSetId": testSetId}),
                    success: function () {
                        layer.msg('执行完成，请到测试结果中查看', {
                            time: 1000
                        });
                    }, error: function (e) {
                        console.log(e, 'error');
                    }
                });
                break;
        }
    });

    // 初始化新增或编辑测试集合页面
    function initInfo(testSetId) {
        selectInit('/interface/getProject', $("#projectName"), '请选择项目');
        if (testSetId > 0) {
            $.ajax({
                type: 'get',
                url: '/interface/getTestSetById?testSetId=' + testSetId,
                contentType: "application/json; charset=utf-8",
                success: function (result) {
                    caseIdQueue = result.data.testSetInfo.caseIdQueue;
                    var testSetInfo = result.data.testSetInfo;
                    $("#testSetInfoForm").setForm(testSetInfo);
                    // $("#projectName").empty().append(new Option(testSetInfo.projectName));
                    caseTable_render(result.data.caseInfoList);
                    form.render();
                }, error: function (e) {
                    console.log(e, 'error');
                }
            });
        } else {
            $("#projectName").empty().append(new Option("主站"));
            // 主动赋值，避免缓存干扰
            $("#testSetId").val("-1");
            var demo = {
                "id": 0,
                "projectName": "示例",
                "moduleName": "示例",
                "interfaceName": "示例",
                "creator": "系统",
                "description": "该行数据仅为示例，不会被保存"
            };
            caseTable_render([demo]);
        }
    }

    // 监听搜索事件
    form.on('submit(search)', function (data) {
        var formData = data.field;
        var count = checkForm("search_form");
        if (count != 0) {
            tableReload('testSetReload', formData, "application/json; charset=utf-8", '/interface/searchTestSet', 'post');
            selectInit('/interface/getProject', $("#projectNameSearch"), '请选择项目');
        } else {
            parent.layer.msg('请先选择查询条件！', {icon: 2, time: 1500});
        }
        return false;
    });

    // 初始化用例表格
    function caseTable_render(data) {
        table.render({
            elem: '#case_table',
            id: 'caseReload'
            , title:
                '用例列表'
            , cols:
                [[{type: 'radio'}
                    , {field: 'id', title: '用例编号', width: '6%', align: 'center'}
                    , {field: 'projectName', title: '项目名称', width: '10%', align: 'center'}
                    , {field: 'moduleName', title: '模&emsp;块', width: '10%', align: 'center'}
                    , {field: 'interfaceName', title: '接口名称', width: '10%', align: 'center'}
                    , {
                        // 使用自定义模板
                        field: 'caseType', title: '用例类型', width: '10%', align: 'center'
                        , templet: function (d) {
                            var caseType = d.caseType;
                            if (caseType == '标准用例') {
                                return '<font color="#0000FF">标准用例</font>';
                            } else if (caseType == '正常用例') {
                                return '<font color="#5FB878">正常用例</font>';
                            } else {
                                return '<font color="#FF0000">异常用例</font>';
                            }
                        }
                    }
                    , {field: 'creator', title: '创建者', width: '9%', align: 'center'}
                    , {field: 'description', title: '用例说明', align: 'center'}
                    , {
                        fixed: 'right',
                        title: '操作',
                        toolbar: '#case_lineBar',
                        align: 'center',
                        width: 165
                    }
                ]]
            , data: data
        });
    }

    // 测试场景详情页保存事件
    form.on('submit(testSet_submit)', function (data) {
        data.field.casesInfo = table.cache.caseReload;
        $.ajax({
            type: 'post',
            url: "/interface/updateTestSet",
            contentType: "application/json; charset=utf-8",
            data: JSON.stringify(data.field),
            dataType: "json",
            success: function (result) {
                if (result.code === "0") {
                    tableReload('testSetReload', '', 'application/x-www-form-urlencoded', '/interface/testSetList', 'get');
                    layer.msg(result.msg, {icon: 1, time: 1000});
                    layer.close(index);
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

    /**
     * 表格行移动方法
     */
    var data_tr;
    table.on('radio(caseBar)', function (obj) {
        data_tr = $(this);
        console.log(data_tr);
    });

    // 上移
    $("#up").on("click", function () {
        var tbData = table.cache.caseReload;
        if (data_tr == null) {
            layer.msg("请选择元素");
            return;
        }
        var tr = $(data_tr).parent().parent().parent();
        if ($(tr).prev().html() == null) {
            layer.msg("已经是最顶部了");
            return false;
        } else {
            var tem = tbData[tr.index()];
            var tem2 = tbData[tr.prev().index()];

            $(tr).insertBefore($(tr).prev());
            tbData[tr.index()] = tem;
            tbData[tr.next().index()] = tem2;
        }
        return false;
    });
    // 下移
    $("#down").on("click", function () {
        var tbData = layui.table.cache.caseReload;
        if (data_tr == null) {
            layer.msg("请选择元素");
            return false;
        }
        var tr = $(data_tr).parent().parent().parent();
        if ($(tr).next().html() == null) {
            layui.layer.msg("已经是最底部了");
            return false;
        } else {
            var tem = tbData[tr.index()];
            var tem2 = tbData[tr.next().index()];
            $(tr).insertAfter($(tr).next());
            tbData[tr.index()] = tem;
            tbData[tr.prev().index()] = tem2;
        }
        return false;
    });
    $("#add").on("click", function () {
        selectInit('/interface/getProject', $("#projectNameAdd"), '请选择项目');
        open_form("#add_case", '', '添加用例', '', '100%', '100%');
        return false;
    });

    // 监听测试集合详情页用例表行工具
    table.on('tool(caseBar)', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;
        var caseId = data.id;

        switch (layEvent) {
            case 'del':
                layer.confirm('确认删除该行？', function (index) {
                    obj.del();
                    layer.close(index);
                });
                break;
            case 'detail':
                openInfo('用例详情页', 'caseInfo.html?id=' + caseId);
                break;
        }
    });

    //监听返回按钮点击事件
    form.on('submit(cancel_button)', function () {
        // var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
        // console.log("window,name--------------", window.name);
        // console.log("index-----------------------------", index);
        // parent.layer.close(index); //再执行关闭
        layer.close(indexForm);
        // location.href = "userList.html";
        return false;
    });

// ---------------------------------用例添加页面-----------------------------------
    // 监听项目选择事件
    form.on('select(projectNameAdd)', function (data) {
        var projectName = data.value;
        var url = '/interface/getModule?projectName=' + projectName;
        var select = $("#moduleNameAdd");
        selectInit(url, select, '请选择模块');
    });
    // 监听模块选择事件
    form.on('select(moduleNameAdd)', function (data) {
        var projectName = $('#projectNameAdd option:selected').val();
        var moduleName = data.value;
        var url = '/interface/getInterface?projectName=' + projectName + "&moduleName=" + moduleName;
        var select = $("select[name='interfaceName']");

        selectInit(url, select, '请选择接口');
    });
    // 监听接口名称选择事件
    form.on('select(interfaceName)', function (data) {
        var projectName = $('#projectNameAdd option:selected').val();
        var moduleName = $('#moduleNameAdd option:selected').val();
        var interfaceName = data.value;
        var select = $("#creator");
        var url = '/interface/getcreators?projectName=' + projectName + "&moduleName=" + moduleName + '&interfaceName=' + interfaceName;
        selectInit(url, select, '请选择创建人');

        var sentData = {};
        sentData.projectName = projectName;
        sentData.moduleName = moduleName;
        sentData.interfaceName = interfaceName;
        getCaseDesc(sentData);

    });
    // 监听创建人选择事件
    form.on('select(creator)', function () {
        getCaseDesc(form2JsonString("#add_form"));
    });
    // 监听用例类型选择事件
    form.on('select(caseType)', function () {
        getCaseDesc(form2JsonString("#add_form"));
    });
    // 监听用例添加页保存事件
    form.on('submit(add_submit)', function (data) {
        var caseDesc = data.field.caseDesc;
        var dataBak = table.cache.caseReload;
        var caseInfo;
        $.ajax({
            type: 'get',
            url: '/interface/getCaseByDesc?caseDesc=' + caseDesc,
            success: function (result) {
                caseInfo = result.data;
                dataBak.push(caseInfo);
                table.reload('caseReload', {data: dataBak});
            }
        });
        layer.close(indexForm);
        layer.msg('修改成功', {icon: 1, time: 1000});
        return false;
    });

    function getCaseDesc(data) {
        var select = $("#caseDesc");
        select.empty().append(new Option(""));
        $.ajax({
            type: 'POST',
            url: '/interface/getCaseDesc',
            contentType: "application/json; charset=utf-8",
            data: JSON.stringify(data),
            success: function (result) {
                $.each(result.data, function (index, item) {
                    select.append(new Option(item));
                });
                layui.form.render();
            }, error: function (e) {
                console.log(e, 'error');
            }
        });
    }
});