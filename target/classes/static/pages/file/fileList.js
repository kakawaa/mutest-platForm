layui.use(['table', 'layer', 'form', 'laypage', 'laydate'], function () {
    var table = layui.table //表格
        , layer = layui.layer //弹层
        , form = layui.form; //form表单

    var pro = window.location.protocol;
    var host = window.location.host;
    var domain = pro + "//" + host;

    //执行实例
    table.render({
        elem: '#file_table'
        , id: 'fileReload'
        // , skin: 'line' //行边框风格
        , even: true //开启隔行背景
        , url: '/files/fileList'
        , request: {
            pageName: 'pageNum',
            limitName: 'pageSize'
        }
        , toolbar: '#file_headerBar'
        , title:
            '文件列表'
        , page:
            true
        , limit: 10
        , limits: [1, 5, 10, 20, 50, 100]
        , cols:
            [[{field: 'number', title: '序号', width: 55, align: 'center', type: 'numbers'}
                , {
                    field: 'url', title: '文件', width: '5%', align: 'center',
                    templet: function (d) {
                        var u = "";
                        if (d.type == 1) {
                            var src = domain + "/statics" + d.url;
                            u = "<a target='_blank' href='" + src + "'><img width='30' src='" + src + "'></img></a>";
                        } else {
                            u = d.url;
                        }
                        return u;
                    }
                }
                , {field: 'path', title: '路径', width: '30%', align: 'center'}
                , {field: 'contentType', title: '格式', width: '8%', align: 'center'}
                , {field: 'uploader', title: '上传人', width: '8%', align: 'center'}
                , {field: 'description', title: '说明', width: '18%', align: 'center', edit: 'text'}
                , {
                    field: 'createTime',
                    title: '创建时间',
                    align: 'center',
                    hide: true
                }
                , {
                    field: 'modifyTime',
                    title: '更新时间',
                    align: 'center'
                }
                , {
                    fixed: 'right',
                    title: '操作',
                    toolbar: '#file_lineBar',
                    width: 70,
                    align: 'center'
                }
            ]]
        , done: function (res, curr, count) {

        }
    });

    selectInit('/files/getUploaders', $("#uploaderSearch"), "请选择上传人");
    selectInit('/files/getContentType', $("#contentTypeSearch"), "请选择格式");

    layui.use('upload', function () {
        var upload = layui.upload;
        upload.render({
            elem: '#upload' //绑定元素
            , accept: 'file' //允许上传的文件类型
            , url: '/files/upload' //上传接口
            , done: function (res, index, upload) {
                layer.msg("上传成功");
                tableReload('fileReload', '', 'application/x-www-form-urlencoded', '/files/fileList', 'get');
                // example.ajax.reload();
            }
        });
    });

    //监听头部工具事件
    table.on('toolbar(fileList)', function (obj) {
        // var checkStatus = table.checkStatus(obj.config.id);
        // var data = checkStatus.data;
        switch (obj.event) {
            case 'refresh':
                tableReload('fileReload', '', 'application/x-www-form-urlencoded', '/files/fileList', 'get');
                selectInit('/files/getUploaders', $("#uploaderSearch"), "请选择上传人");
                selectInit('/files/getContentType', $("#contentTypeSearch"), "请选择格式");
                break;
        }
    });

    // 监听搜索按钮提交事件
    form.on('submit(search)', function (data) {
        var formData = data.field;
        var count = checkForm("search_form");
        if (count != 0) {
            //数据表格重载
            tableReload('fileReload', formData, "application/json; charset=utf-8", '/files/searchFile', 'post');
            selectInit('/files/getUploaders', $("#uploaderSearch"), "请选择上传人");
            selectInit('/files/getContentType', $("#contentTypeSearch"), "请选择格式");
        } else {
            parent.layer.msg('请先选择查询条件！', {icon: 2, time: 1500});
        }
        return false;
    });

    // 监听行工具事件
    table.on('tool(fileList)', function (obj) {
        // var data = obj.data;
        var layEvent = obj.event;
        var id = obj.data.id;

        switch (layEvent) {
            case 'delete':
                layer.confirm('确认删除？', {
                    skin: 'layui-layer-molv'
                    , closeBtn: 1
                    , btn: ['确定', '取消']
                    , yes: function () {
                        $.ajax({
                            type: 'delete',
                            url: '/files/' + id,
                            contentType: "application/json; charset=utf-8",
                            success: function (result) {
                                tableReload('fileReload', '', 'application/x-www-form-urlencoded', '/files/fileList', 'get');
                                selectInit('/files/getUploaders', $("#uploaderSearch"), "请选择上传人");
                                selectInit('/files/getContentType', $("#contentTypeSearch"), "请选择格式");
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

    table.on('edit(fileList)', function (obj) { //注：edit是固定事件名，test是table原始容器的属性 lay-filter="对应的值"
        layer.confirm('是否保存？', {
            skin: 'layui-layer-molv'
            , closeBtn: 1
            , btn: ['确定', '取消']
            , yes: function () {
                $.ajax({
                    type: 'post',
                    url: '/files/updateFileInfo',
                    contentType: "application/json; charset=utf-8",
                    data: JSON.stringify(obj.data),
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
    });
});