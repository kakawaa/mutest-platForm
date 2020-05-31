//form序列化为json
$.fn.serializeObject = function () {
    var o = {};
    var a = this.serializeArray();
    $.each(a, function () {
        if (o[this.name] !== undefined) {
            if (!o[this.name].push) {
                o[this.name] = [o[this.name]];
            }
            o[this.name].push(this.value || '');
        } else {
            o[this.name] = this.value || '';
        }
    });
    return o;
};

//获取url后的参数值
function getUrlParam(key) {
    var href = window.location.href;
    var url = href.split("?");
    if (url.length <= 1) {
        return "";
    }
    var params = url[1].split("&");

    for (var i = 0; i < params.length; i++) {
        var param = params[i].split("=");
        if (key == param[0]) {
            return param[1];
        }
    }
}

//序列化form表单字段为json对象格式
$.fn.serializeFormToJson = function () {
    var arr = $(this).serializeArray();//form表单数据 name：value
    var param = {};
    $.each(arr, function (i, obj) { //将form表单数据封装成json对象
        param[obj.name] = obj.value;
    })
    return param;
}

/**
 * 将form里面的内容序列化成json
 * 相同的checkbox用分号拼接起来
 * @param {dom} 指定的选择器
 * @param {obj} 需要拼接在后面的json对象
 * @method serializeJson
 * */
$.fn.serializeJson = function (otherString) {
    var serializeObj = {},
        array = this.serializeArray();
    $(array).each(function () {
        if (serializeObj[this.name]) {
            serializeObj[this.name] += ';' + this.value;
        } else {
            serializeObj[this.name] = this.value;
        }
    });
    if (otherString != undefined) {
        var otherArray = otherString.split(';');
        $(otherArray).each(function () {
            var otherSplitArray = this.split(':');
            serializeObj[otherSplitArray[0]] = otherSplitArray[1];
        });
    }
    return serializeObj;
};

/**
 * 将josn对象赋值给form
 * @param jsonValue
 */
$.fn.setForm = function (jsonValue) {
    var obj = this;
    $.each(jsonValue, function (name, ival) {
        var $oinput = obj.find("input[name=" + name + "]");
        if ($oinput.attr("type") == "checkbox") {
            if (ival !== null) {
                var checkboxObj = $("[name=" + name + "]");
                var checkArray = ival.split(";");
                for (var i = 0; i < checkboxObj.length; i++) {
                    for (var j = 0; j < checkArray.length; j++) {
                        if (checkboxObj[i].value == checkArray[j]) {
                            checkboxObj[i].click();
                        }
                    }
                }
            }
        }
        else if ($oinput.attr("type") == "radio") {
            $oinput.each(function () {
                var radioObj = $("[name=" + name + "]");
                for (var i = 0; i < radioObj.length; i++) {
                    if (radioObj[i].value == ival) {
                        radioObj[i].click();
                    }
                }
            });
        }
        else if ($oinput.attr("type") == "textarea") {
            obj.find("[name=" + name + "]").html(ival);
        }
        else {
            obj.find("[name=" + name + "]").val(ival);
        }
    })
};

function checkForm(formId) {
    var form = document.getElementById(formId);
    var count = 0;
    for (var i = 0; i < form.elements.length - 1; i++) {
        if (!form.elements[i].value == "") {
            count++;
        }
    }
    return count;
}

/** 表单序列化成json字符串的方法  */
function form2JsonString(formId) {
    var paramArray = $(formId).serializeArray();
    /*请求参数转json对象*/
    var jsonObj = {};

    $(paramArray).each(function () {
        jsonObj[this.name] = this.value;
    });
    // json对象再转换成json字符串
    // return JSON.stringify(jsonObj);
    return jsonObj;
}

$.fn.extend({
    initForm: function (options) {
        //默认参数
        var defaults = {
            jsonValue: "",
            isDebug: false //是否需要调试，这个用于开发阶段，发布阶段请将设置为false，默认为false,true将会把name value打印出来
        };
        //设置参数
        var setting = $.extend({}, defaults, options);
        var form = this;
        var jsonValue = setting.jsonValue;
        //如果传入的json字符串，将转为json对象
        if ($.type(setting.jsonValue) === "string") {
            jsonValue = $.parseJSON(jsonValue);
        }
        //如果传入的json对象为空，则不做任何操作
        if (!$.isEmptyObject(jsonValue)) {
            var debugInfo = "";
            $.each(jsonValue, function (key, value) {
                //是否开启调试，开启将会把name value打印出来
                if (setting.isDebug) {
                    alert("name:" + key + "; value:" + value);
                    debugInfo += "name:" + key + "; value:" + value + " || ";
                }
                var formField = form.find("[name='" + key + "']");
                if ($.type(formField[0]) === "undefined") {
                    if (setting.isDebug) {
                        alert("can not find name:[" + key + "] in form!!!"); //没找到指定name的表单
                    }
                } else {
                    var fieldTagName = formField[0].tagName.toLowerCase();
                    if (fieldTagName == "input") {
                        if (formField.attr("type") == "radio") {
                            $("input:radio[name='" + key + "'][value='" + value + "']").attr("checked", "checked");
                        } else {
                            formField.val(value);
                        }
                    } else if (fieldTagName == "select") {
                        //do something special
                        formField.val(value);
                        layui.form.render();  // 下拉框赋值
                    } else if (fieldTagName == "textarea") {
                        formField.val(value);
                    } else {
                        formField.val(value);
                    }
                }
            });
            if (setting.isDebug) {
                alert(debugInfo);
            }
        }
        return form; //返回对象，提供链式操作
    }
});