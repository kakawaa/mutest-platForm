layui.use(['form', 'layer', 'jquery'], function () {
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : top.layer
    $ = layui.jquery;

    // 登录状态
    var token = localStorage.getItem("token");
    if (token != null && token.trim().length != 0) {
        $.ajax({
            type: 'get',
            url: '/users/current?token=' + token,
            success: function (data) {
                location.href = '/index.html';
            },
            error: function (xhr, textStatus, errorThrown) {
                var msg = xhr.responseText;
                var response = JSON.parse(msg);
                var code = response.code;
                var message = response.message;
                if (code == 401) {
                    localStorage.removeItem("token");
                }
            }
        });
    }

    //登录按钮
    form.on("submit(login)", function (obj) {
        $(this).text("登录中...").attr("disabled", "disabled").addClass("layui-disabled");
        $.ajax({
            type: 'post',
            url: '/login',
            data: $("#login-form").serialize(),
            success: function (data) {
                localStorage.setItem("token", data.token);
                location.href = '/index.html';
            },
            error: function (data) {
                console.log(data);
                layer.alert(data.responseJSON.message, {icon: 2});
                $("#login").text("登录").attr("disabled", false).removeClass("layui-disabled");
            }
            // error: function (xhr, textStatus, errorThrown) {
            //     var msg = xhr.responseText;
            //     var response = JSON.parse(msg);
            //     layer.alert(msg, {icon: 2});
            //     $(obj).attr("disabled", false);
            // }
        });
        return false;
    });

    //表单输入效果
    $(".loginBody .input-item").click(function (e) {
        e.stopPropagation();
        $(this).addClass("layui-input-focus").find(".layui-input").focus();
    });
    $(".loginBody .layui-form-item .layui-input").focus(function () {
        $(this).parent().addClass("layui-input-focus");
    });
    $(".loginBody .layui-form-item .layui-input").blur(function () {
        $(this).parent().removeClass("layui-input-focus");
        if ($(this).val() != '') {
            $(this).parent().addClass("layui-input-active");
        } else {
            $(this).parent().removeClass("layui-input-active");
        }
    })
});
