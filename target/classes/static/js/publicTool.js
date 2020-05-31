/**
 * ip配置
 * @returns {string}
 */
function hostInit() {
    var host = "192.168.112.40";
    // var host = "localhost";
    return host;
}

/**
 * 打开详情页
 * @param title 详情页名称
 * @param content 内容

 */
function openInfo(title, content) {
    index = layui.layer.open({
        title: title,
        type: 2,
        content: content,
        // success: function (layero, index) {
        //     var body = layui.layer.getChildFrame('body', index);
        //     body.find(element).val(value);
        // }
    });
    console.log("index-=================", index);
    layui.layer.full(index);
    //改变窗口大小时，重置弹窗的宽高，防止超出可视区域（如F12调出debug的操作）
    $(window).on("resize", function () {
        layui.layer.full(index);
    })
}

/**
 * 给下拉选择框赋值
 * @param url
 * @param select
 */
function selectInit(url, select, title) {
    if (title == null) {
        title = '请选择';
    }
    // select.empty().append(new Option("请选择负责人"));
    select.empty().append("<option value=''>" + title + "</option>");
    $.ajax({
        type: 'get',
        url: url,
        contentType: "application/json; charset=utf-8",
        success: function (data) {
            $.each(data, function (index, item) {
                select.append(new Option(item));//往下拉菜单里添加元素
            });
            layui.form.render();//菜单渲染 把内容加载进去
        }, error: function (e) {//响应不成功时返回的函数
            console.log(e, 'error');
        }
    });
}

/**
 * 单选框动态赋值
 * @param url
 * @param select
 */
function radioInit(url, radio, name) {
    $.ajax({
        type: 'get',
        url: url,
        contentType: "application/json; charset=utf-8",
        success: function (data) {
            $.each(data, function (index, item) {
                radio.append('<input type="radio" name=' + name + ' value=' + item.value + ' title=' + item.title + '>');
            });
            layui.form.render();//菜单渲染 把内容加载进去
        }, error: function (e) {//响应不成功时返回的函数
            console.log(e, 'error');
        }
    });
}

/**
 * 打开弹层
 * @param element 弹层div的id
 * @param data 数据
 * @param title 弹层标题
 * @param url
 * @param width 弹层宽度，'400px'或'100%'
 * @param height 弹层高度，'400px'或'100%'
 */
var index;
var indexForm;

function open_form(element, data, title, url, width, height) {
    if (title == null || title === '') {
        title = false;
    }
    if (url == null || url === '') {
    }
    if (width == null || width === '') {
        width = '100%';
    }
    if (height == null || height === '') {
        height = '100%';
    }
    //layer提供了5种层类型。可传入的值有：0（信息框，默认）1（页面层）2（iframe层）3（加载层）4（tips层）
    indexForm = layer.open({
        // layer.open({
        type: 1,
        title: title,
        // area: ['auto', 'auto'],//类型：String/Array，默认：'auto'  只有在宽高都定义的时候才不会自适应
        area: [width, height],
        fix: false, //不固定
        maxmin: true,//开启最大化最小化按钮
        shadeClose: true,//点击阴影处可关闭
        shade: 0.4,//背景灰度
        // skin: 'layui-layer-rim', //加上边框
        content: $(element),
        success: function () {
            console.log("index2-----------------------------", index);
            $(element).setForm(data);
            layui.form.render();  // 下拉框赋值
        }
    });
}

/**
 * 获取当前登录用户
 * @returns {string}
 */
// function currentUser() {
//     var user = '未知';
//     $.ajax({
//         type: 'get',
//         url: '/users/current',
//         async: false,
//         success: function (result) {
//             user = result.nickname;
//         }
//     });
//     return user;
// }

function currentUser() {
    var urer;
    $.ajax({
        type: 'get',
        url: '/users/current',
        async: false,
        success: function (result) {
            user = result;
        }
    });
    return user;
}

/**
 * 表格重载
 * @param tableId
 * @param where
 * @param contentType
 * @param url
 * @param method
 */
function tableReload(tableId, where, contentType, url, method) {
    layui.table.reload(tableId, {
        where: where,
        contentType: contentType,
        page: {
            curr: 1 //重新从第 1 页开始
        },
        url: url
        , method: method
    });
}

/**
 * 切换表格中的switch开关，修改表格缓存
 * @param data switch
 * @param value1 开关开启对应的值
 * @param value2 开关关闭对应的值
 */
function modifyTableCacheBySwitch(data, status) {
    var table = layui.table;
    var switchElem = $(data.elem);
    var tdElem = switchElem.closest('td');
    var trElem = tdElem.closest('tr');
    var tableView = trElem.closest('.layui-table-view');
    table.cache[tableView.attr('lay-id')][trElem.data('index')][tdElem.data('field')] = status;
}

/**
 * 文本框适应内容高度
 */
function readyNumber() {
    $('textarea').each(function () {
        this.setAttribute('style', 'height:' + (this.scrollHeight) + 'px;overflow-y:hidden;');
    }).on('input', function () {
        this.style.height = 'auto';
        this.style.height = (this.scrollHeight) + 'px';
    })
}