// 初始模块引用
layui.use(['table', 'layer', 'form', 'laypage', 'laydate'], function () {
    var table = layui.table //表格
        , layer = layui.layer //弹层
        , form = layui.form;

    //执行实例
    table.render({
        elem: '#module_table'
        , id: 'moduleReload'
        // , skin: 'line' //行边框风格
        , even: true //开启隔行背景
        , url: '/interface/moduleList'
        , request: {
            pageName: 'pageNum',
            limitName: 'pageSize'
        }
        , toolbar: '#module_headerBar'
        , title:
            '接口列表'
        , page:
            true
        , limit: 10
        , limits: [1, 5, 10, 20, 50, 100]
        , cols:
            [[{field: 'number', title: '序号', width: 60, align: 'center', type: 'numbers'}
                , {field: 'id', title: '模块ID', width: 60, align: 'center', hide: true}
                , {field: 'projectName', title: '项目名称', width: '15%', align: 'center'}
                , {field: 'moduleName', title: '模块名称', width: '15%', align: 'center'}
                , {field: 'creator', title: '创建者', width: '12%', align: 'center'}
                , {field: 'description', title: '模块说明', align: 'center'}
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
                    width: 240,
                }
                , {
                    fixed: 'right',
                    title: '操作',
                    toolbar: '#module_lineBar',
                    width: 120,
                    align: 'center'
                }
            ]]
    });

    selectInit('/interface/getProject', $("#projectNameSearch"), '请选择项目');

    // 监听
    table.on('toolbar(moduleBar)', function (obj) {
        var checkStatus = table.checkStatus(obj.config.id);
        var data = checkStatus.data; //获取选中的数据

        switch (obj.event) {
            case 'refresh':
                tableReload('moduleReload', '', 'application/x-www-form-urlencoded', '/interface/moduleList', 'get');
                selectInit('/interface/getProject', $("#projectNameSearch"), '请选择项目');
                break;
            case 'add':
                selectInit('/interface/getProject', $("#projectNameAdd"), '请选择项目');
                data = {};
                data.action = 'addModule';
                data.type = 'POST';

                open_form("#add_div", data, '添加模块', 'url', '680px', '300px');
                break;
        }
    });

    // 监听
    table.on('tool(moduleBar)', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;
        var moduleId = data.id;

        switch (layEvent) {
            case 'edit':
                selectInit('/interface/getProject', $("#projectNameAdd"), '请选择项目');
                $("#projectAdd").empty().append(new Option(data.project));
                data.action = 'updateModule';
                data.type = 'put';
                open_form("#add_div", data, '编辑模块信息', 'url', '680px', '300px');
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
                    tableReload('moduleReload', '', 'application/x-www-form-urlencoded', '/interface/moduleList', 'get');
                    selectInit('/interface/getProject', $("#projectNameAdd"), '请选择项目');
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
            tableReload('moduleReload', formData, "application/json; charset=utf-8", '/interface/searchModule', 'post');
        } else {
            parent.layer.msg('请先选择查询条件！', {icon: 2, time: 1500});
        }
        selectInit('/interface/getProject', $("#projectNameSearch"), '请选择项目');
        return false;
    });
});