// 初始模块引用
layui.use(['table', 'layer', 'form', 'laypage', 'laydate'], function () {
    var table = layui.table //表格
        , layer = layui.layer //弹层
        , form = layui.form;

    //执行实例
    table.render({
        elem: '#interface_table'
        , id: 'interfaceReload'
        // , skin: 'line' //行边框风格
        , even: true //开启隔行背景
        , url: '/interface/interfaceList'
        , request: {
            pageName: 'pageNum',
            limitName: 'pageSize'
        }
        , toolbar: '#interface_headerBar'
        , title:
            '接口列表'
        , page:
            true
        , limit: 10
        , limits: [1, 5, 10, 20, 50, 100]
        , cols:
            [[{field: 'number', title: '序号', width: 50, align: 'center', type: 'numbers'}
                , {field: 'id', title: '编号', width: '5%', align: 'center', hide: true}
                , {field: 'projectName', title: '项目', width: '8%', align: 'center'}
                , {field: 'moduleName', title: '模块', width: '8%', align: 'center'}
                , {field: 'interfaceName', title: '接口名称', width: '9.5%', align: 'center'}
                , {field: 'url', title: 'IP地址', width: '15.5%', align: 'center', hide: true}
                , {field: 'path', title: '路径', width: '16%', align: 'center'}
                , {field: 'method', title: '方法', width: '7%', align: 'center'}
                , {field: 'paramType', title: '参数', width: '6.6%', align: 'center'}
                , {field: 'creator', title: '创建者', width: '7%', align: 'center'}
                , {field: 'description', title: '接口说明', align: 'center'}
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
                    toolbar: '#interface_lineBar',
                    width: 115,
                    align: 'center'
                }
            ]]
    });
    selectInit('/interface/getProject', $("#projectNameSearch"), '请选择项目');

    // 监听
    table.on('toolbar(interfaceBar)', function (obj) {
        var checkStatus = table.checkStatus(obj.config.id);
        var data = checkStatus.data; //获取选中的数据

        switch (obj.event) {
            case 'refresh':
                tableReload('interfaceReload', '', 'application/x-www-form-urlencoded', '/interface/interfaceList', 'get');
                selectInit('/interface/getProject', $("#projectNameSearch"), '请选择项目');
                break;
            case 'add':
                data = {};
                data.action = 'addInterface';
                data.type = 'POST';

                selectInit('/interface/getProject', $("#projectNameAdd"), '请选择项目');
                open_form("#add_div", data, '添加接口', 'url', '680px', '450px');
                break;
        }
    });

    // 监听行工具事件
    table.on('tool(interfaceBar)', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;

        switch (layEvent) {
            case 'edit':
                selectInit('/interface/getProject', $("#projectNameAdd"), '请选择项目');
                $("#projectNameAdd").empty().append(new Option(data.projectName));
                $("#moduleNameAdd").empty().append(new Option(data.moduleName));

                data.action = 'updateInterface';
                data.type = 'put';
                open_form("#add_div", data, '编辑接口信息', 'url', '680px', '450px');
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
                    tableReload('interfaceReload', '', 'application/x-www-form-urlencoded', '/interface/interfaceList', 'get');
                    selectInit('/interface/getProject', $("#projectNameSearch"), '请选择项目');
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
    // 搜索
    form.on('submit(search)', function (data) {
        var formData = data.field;
        var count = checkForm("search_form");
        if (count != 0) {
            tableReload('interfaceReload', formData, "application/json; charset=utf-8", '/interface/searchInterface', 'post');
        } else {
            parent.layer.msg('请先选择查询条件！', {icon: 2, time: 1500});
        }
        selectInit('/interface/getProject', $("#projectNameSearch"), '请选择项目');
        return false;
    });

    // 监听project选择事件
    form.on('select(projectNameAdd)', function (data) {
        var projectName = data.value;
        var url = '/interface/getModule?projectName=' + projectName;
        var select = $("#moduleNameAdd");
        selectInit(url, select, '请选择模块');
    });

    form.on('select(projectNameSearch)', function (data) {
        var projectName = data.value;
        var url = '/interface/getModule?projectName=' + projectName;
        var select = $("#moduleNameSearch");
        selectInit(url, select, '请选择模块');
    });
});