initMenu();

function initMenu() {
    $.ajax({
        url: "/permissions/current",
        type: "get",
        async: false,
        success: function (data) {
            if (!$.isArray(data)) {
                location.href = '/login.html';
                return;
            }
            // 渲染顶部菜单
            var menu = $("#menu-header");
            $.each(data, function (i, item) {
                var a = $("<a class='menu-header'></a>");

                var css = item.css;
                if (css != null && css != "") {
                    a.append("<i aria-hidden='true' class='fa " + css + "'></i>");
                }
                // a.append("<i aria-hidden='true' class='fa " + css + "'></i>");
                a.append("<cite>" + item.name + "</cite>");
                a.attr("lay-id", item.id);

                var href = item.href;
                if (href != null && href != "") {
                    a.attr("data-url", href);
                }

                var li = $("<li class='layui-nav-item'></li>");
                li.append(a);
                menu.append(li);
                // 初始化左侧默认菜单
                if (i == 0) {
                    setChilds(item.child);
                }
            });
        }
    });
}

// child为二级菜单数据
function setChilds(child) {
    if (child != null && child.length > 0) {
        var menu_left = $("#menu-left");
        $.each(child, function (j, item) {
            var name = item.name;
            var li = $("<li class='layui-nav-item'></li>");
            // var a = $("<a><i class='layui-icon'>&#xe653;</i><cite>" + name + "</cite></a>");
            var a = $("<a><i aria-hidden='true' class='fa " + item.css + "'></i><cite>" + name + "</cite></a>");
            // var a = $("<a><i aria-hidden='true' class='fa fa-users'></i><cite>" + name + "</cite></a>");
            var href = item.href;
            if (href != null && href != "") {
                a.attr("data-url", href);
            }
            a.attr("lay-id", item.id);
            li.append(a);

            // 判断是否有三级菜单
            var grandChild = item.child;
            if (grandChild != null && grandChild.length > 0) {
                var dl = $("<dl class='layui-nav-child'></dl>");
                $.each(grandChild, function (k, item) {
                    // 20200523修改源码，增加三级菜单图标处理逻辑
                    // var a = $("<a><i class='layui-icon'>&#xe67a;</i><cite>" + item.name + "</cite></a>");
                    var a;
                    // 如果没有配置图标，使用默认图标 ⭐
                    if (item.css === "") {
                        a = $("<a><i class='layui-icon'>&#xe67a;</i><cite>" + item.name + "</cite></a>");
                    }else{
                        a = $("<a><i aria-hidden='true' class='fa " + item.css + "'></i><cite>" + name + "</cite></a>");
                    }

                    var href2 = item.href;
                    if (href2 != null && href2 != "") {
                        a.attr("data-url", href2);
                    }
                    a.attr("lay-id", item.id);
                    // var dd = $("<dd class='layui-this'></dd>");
                    var dd = $("<dd></dd>");
                    dd.append(a);
                    dl.append(dd);
                })
            }
            li.append(dl);
            menu_left.append(li);
        })
    }
}

// 登陆用户头像昵称
showLoginInfo();

function showLoginInfo() {
    $.ajax({
        type: 'get',
        url: '/users/current',
        async: false,
        success: function (data) {
            $("#username-header").text(data.nickname);
            $("#username").text(data.nickname);

            var pro = window.location.protocol;
            var host = window.location.host;
            var domain = pro + "//" + host;

            var sex = data.sex;
            var url = data.headImgUrl;
            if (url == null || url == "") {
                if (sex == 1) {
                    url = "/img/avatars/sunny.png";
                } else {
                    url = "/img/avatars/1.png";
                }

                url = domain + url;
            } else {
                url = domain + "/statics" + url;
            }
            var img = $("#userImage-header");
            var img2 = $("#userImage");
            img.attr("src", url);
            img2.attr("src", url);
        }
    });
}

function logout() {
    $.ajax({
        type: 'get',
        url: '/logout',
        success: function (data) {
            localStorage.removeItem("token");
            location.href = '/login.html';
        }
    });
}

layui.config({
    base: "js/"
}).extend({
    "bodyTab": "bodyTab"
});
layui.use(['bodyTab', 'form', 'element', 'layer', 'jquery'], function () {
    var form = layui.form,
        element = layui.element;
    $ = layui.$;
    layer = parent.layer === undefined ? layui.layer : top.layer;
    tab = layui.bodyTab({
        openTabNum: "50",  //最大可打开窗口数量
        // url : "json/navs.json" //获取菜单json地址

    });

    // 监听顶部菜单点击事件，刷新左导菜单
    $("a[class='menu-header']").click(function () {
        $("#menu-left").empty();
        id = $(this).attr("lay-id");
        var child;
        $.ajax({
            url: "/permissions/current",
            type: "get",
            async: false,
            success: function (data) {
                if (!$.isArray(data)) {
                    location.href = '/login.html';
                    return;
                }
                $.each(data, function (i, item) {
                    if (item.id == id) {
                        child = item.child;
                        return false;
                    }
                })
            }
        });
        setChilds(child);
        // 如果没有这行代码，则二级菜单没有展开效果
        element.init();
    });

    //隐藏左侧导航
    $(".hideMenu").click(function () {
        if ($(".topLevelMenus li.layui-this a").data("url")) {
            layer.msg("此栏目状态下左侧菜单不可展开");  //主要为了避免左侧显示的内容与顶部菜单不匹配
            return false;
        }
        $(".layui-layout-admin").toggleClass("showMenu");
        //渲染顶部窗口
        tab.tabMove();
    });

    // 添加新窗口
    $("body").on("click", ".layui-nav .layui-nav-item a:not('.mobileTopLevelMenus .layui-nav-item a')", function () {
        //如果不存在子级
        if ($(this).siblings().length == 0) {
            addTab($(this));
            $('body').removeClass('site-mobile');  //移动端点击菜单关闭菜单层
        }
        $(this).parent("li").siblings().removeClass("layui-nav-itemed");
    });

    //清除缓存
    $(".clearCache").click(function () {
        window.sessionStorage.clear();
        window.localStorage.clear();
        var index = layer.msg('清除缓存中，请稍候', {icon: 16, time: false, shade: 0.8});
        setTimeout(function () {
            layer.close(index);
            layer.msg("缓存清除成功！");
        }, 1000);
    });

    //刷新后还原打开的窗口
    if (cacheStr == "true") {
        if (window.sessionStorage.getItem("menu") != null) {
            menu = JSON.parse(window.sessionStorage.getItem("menu"));
            curmenu = window.sessionStorage.getItem("curmenu");
            var openTitle = '';
            for (var i = 0; i < menu.length; i++) {
                openTitle = '';
                if (menu[i].icon) {
                    if (menu[i].icon.split("-")[0] == 'icon') {
                        openTitle += '<i class="seraph ' + menu[i].icon + '"></i>';
                    } else {
                        openTitle += '<i class="layui-icon">' + menu[i].icon + '</i>';
                    }
                }
                openTitle += '<cite>' + menu[i].title + '</cite>';
                openTitle += '<i class="layui-icon layui-unselect layui-tab-close" data-id="' + menu[i].layId + '">&#x1006;</i>';
                element.tabAdd("bodyTab", {
                    title: openTitle,
                    content: "<iframe src='" + menu[i].href + "' data-id='" + menu[i].layId + "'></frame>",
                    id: menu[i].layId
                })
                //定位到刷新前的窗口
                if (curmenu != "undefined") {
                    if (curmenu == '' || curmenu == "null") {  //定位到后台首页
                        element.tabChange("bodyTab", '');
                    } else if (JSON.parse(curmenu).title == menu[i].title) {  //定位到刷新前的页面
                        element.tabChange("bodyTab", menu[i].layId);
                    }
                } else {
                    element.tabChange("bodyTab", menu[menu.length - 1].layId);
                }
            }
            //渲染顶部窗口
            tab.tabMove();
        }
    } else {
        window.sessionStorage.removeItem("menu");
        window.sessionStorage.removeItem("curmenu");
    }
});

//打开新窗口
function addTab(_this) {
    tab.tabAdd(_this);
}

//图片管理弹窗
function showImg() {
    $.getJSON('json/images.json', function (json) {
        var res = json;
        layer.photos({
            photos: res,
            anim: 5
        });
    });
}