package com.mutest.utils;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import bsh.EvalError;
import bsh.Interpreter;
import com.alibaba.fastjson.JSONObject;

public class ResponseUtil {

    public static void responseJson(HttpServletResponse response, int status, Object data) {
        try {
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Access-Control-Allow-Methods", "*");
            response.setContentType("application/json;charset=UTF-8");
            response.setStatus(status);

            response.getWriter().write(JSONObject.toJSONString(data));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String assertByScript(String script, String response) {
        Interpreter interpreter = new Interpreter();
        try {
            interpreter.set("response", response);
            Object object = interpreter.eval(script);
            return object.toString();
        } catch (EvalError e) {
            e.printStackTrace();
            return "error";
        }
    }
}
