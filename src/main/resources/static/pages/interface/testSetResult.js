layui.use(['table', 'layer', 'form', 'laypage', 'laydate'], function () {
    var table = layui.table //表格
        , layer = layui.layer //弹层
        , form = layui.form;
    table.render({
        elem: '#testSetResult_table',
        id: 'testSetResultReload'
        , url: '/interface/testSetResultList'
        , request: {
            pageName: 'pageNum',
            limitName: 'pageSize'
        }
        , toolbar: '#testSetResult_headerBar'
        , title:
            '测试集合执行结果'
        , page:
            true
        , limit: 10
        , limits: [1, 5, 10, 20, 50, 100]
        , cols:
            [[{field: 'number', title: '序号', width: 60, align: 'center', type: 'numbers'},
                {field: 'id', title: '结果编号', width: 80, hide: true, align: 'center'}
                , {field: 'testSetId', title: '场景编号', width: 80, align: 'center', hide: true}
                , {field: 'projectName', title: '项目', width: 115, align: 'center'}
                , {field: 'caseCount', title: '用例数', width: 72, align: 'center'}
                , {field: 'failedCount', title: '失败数', width: 72, align: 'center'}
                , {field: 'executeId', title: '执行编号', hide: true, align: 'center'}
                , {field: 'caseNameQueue', title: '用例队列', align: 'center'}
                , {field: 'failedList', title: '失败用例集', width: '16%', align: 'center'}
                , {field: 'executor', title: '执行人', width: 81, align: 'center'}
                , {
                    field: 'create_time',
                    title: '执行时间',
                    width: 175,
                    align: 'center'
                }
                , {
                    fixed: 'right',
                    title: '操作',
                    toolbar: '#testSetResult_lineBar',
                    width: 80,
                    align: 'center'
                }
            ]]
    });

    // 初始化搜索框
    selectInit('/interface/getProject', $("#projectNameSearch"), '请选择项目');
    table.on('toolbar(testSetResultBar)', function (obj) {
        switch (obj.event) {
            case 'refresh':
                tableReload('testSetResultReload', '', 'application/x-www-form-urlencoded', '/interface/testSetResultList', 'get');
                selectInit('/interface/getProject', $("#projectNameSearch"), '请选择项目');
                break;
        }
    });

    table.on('tool(testSetResultBar)', function (obj) {
        var data = obj.data; //获得当前行数据
        var layEvent = obj.event;
        var executeId = data.executeId;

        switch (layEvent) {
            case 'detail':
                open_form("#detail", '', '测试结果详情', 'url', '100%', '100%');
                $("#executeId").val(executeId);
                $.ajax({
                    type: 'get',
                    url: "/interface/caseResultList"
                    , data: {executeId: executeId},
                    dataType: "json",
                    success: function (result) {
                        var caseResultList = result.data;
                        if (result.code === "0") {
                            table.render({
                                elem: '#caseResult_table',
                                id: 'caseResult_table',
                                // toolbar: true,
                                title:
                                    '用例执行结果表'
                                , page:
                                    true
                                , limit: 10
                                , limits: [1, 5, 10, 20, 50, 100]
                                , cols:
                                    [[{field: 'caseId', title: '用例ID', width: 75, align: 'center'}
                                        , {
                                            field: 'caseAssertion', title: '断言结果', width: 88, align: 'center'
                                            , templet: function (d) {
                                                var caseAssertion = d.caseAssertion;
                                                if (caseAssertion == '未校验') {
                                                    return '<font color="#0000FF">未校验</font>';
                                                } else if (caseAssertion == '成功') {
                                                    return '<font color="#5FB878">成功</font>';
                                                } else {
                                                    return '<font color="#FF0000">失败</font>';
                                                }
                                            }
                                        }
                                        , {field: 'interfaceName', title: '接口名称', width: 110, align: 'center'}
                                        , {field: 'caseRequest', title: '请求路径', width: '25%', align: 'center'}
                                        , {field: 'bodyData', title: '请求体', width: '28%', align: 'center'}
                                        , {field: 'caseResponse', title: '响应信息', hide: true, align: 'center'}
                                        , {field: 'assertionDetail', title: '断言详情', align: 'center'}
                                        , {
                                            fixed: 'right',
                                            title: '操作',
                                            toolbar: '#caseDetail_lineBar',
                                            width: 70,
                                            align: 'center'
                                        }
                                    ]],
                                data: caseResultList
                            });
                        } else {
                            layer.alert(result.msg, {icon: 2}, function () {
                            });
                        }
                    }
                });
                break;
        }
    });

    form.on('submit(search)', function (data) {
        var formData = data.field;
        var count = checkForm("search_form");
        if (count != 0) {
            tableReload('testSetResultReload', formData, "application/json; charset=utf-8", '/interface/searchTestSetResult', 'post');
            selectInit('/interface/getProject', $("#projectNameSearch"), '请选择项目');
        } else {
            parent.layer.msg('请先选择查询条件！', {icon: 2, time: 1500});
        }
        return false;
    });

    // 监听用例结果行工具点击事件
    table.on('tool(caseResultBar)', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;
        var caseId = data.caseId;

        switch (layEvent) {
            case 'detail':
                openInfo('用例详情页', 'caseInfo.html?id=' + caseId);
                break;
        }
    });

    //监听用例结果行双击事件
    table.on('rowDouble(caseResultBar)', function (obj) {
        var data = obj.data.caseResponse;

        layer.alert(data, {
            title: '响应结果：'
        });

        //标注选中样式
        obj.tr.addClass('layui-table-click').siblings().removeClass('layui-table-click');
    });
});