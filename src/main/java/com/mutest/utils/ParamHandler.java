package com.mutest.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * @author guozhengMu
 * @version 1.0
 * @date 2019/7/17 15:17
 * @description 参数处理
 * @modify
 */
public class ParamHandler {
    /**
     * 将JSONArray格式的参数信息转换为form格式
     *
     * @param map
     * @param paramArray paramArray JSONArray格式的字符串（例如：[{"headerName":"aaa","headerValue":"bbb"}]）
     * @param key1       headerName
     * @param key2       headerValue
     * @return 例如：a=1&b=2&c=3
     */
    public static String arrayToString(Map<String, String> map, String paramArray, String key1, String key2) {
        JSONArray paramList = JSON.parseArray(paramArray);
        StringBuilder bodyIni = new StringBuilder();
        for (int i = 0; i < paramList.size(); i++) {
            JSONObject bodyObject = paramList.getJSONObject(i);
            String paramValue = bodyObject.getString(key2);
            if (paramValue.contains("${")) {
                // 解析出${}中的值，并以此为键在关联信息桶中获取相应的值
                paramValue = map.get(paramValue.substring(2, paramValue.length() - 1));
            }
            bodyIni.append(bodyObject.getString(key1)).append("=").append(paramValue).append("&");
        }
        // 处理请求体为空的情况
        if (bodyIni.length() > 0) {
            return bodyIni.substring(0, bodyIni.length() - 1);
        } else {
            return "";
        }
    }

    public static JSONObject arrayToJson(Map<String, String> map, String paramArray, String key1, String key2) {
        JSONObject result = new JSONObject();
        JSONArray paramList = JSON.parseArray(paramArray);
        for (int i = 0; i < paramList.size(); i++) {
            JSONObject bodyObject = paramList.getJSONObject(i);
            String paramName = bodyObject.getString(key1);
            String paramValue = bodyObject.getString(key2);
            if (paramValue.contains("${")) {
                // 解析出${}中的值，并以此为键在关联信息桶中获取相应的值
                paramValue = map.get(paramValue.substring(2, paramValue.length() - 1));
            }
            result.put(paramName, paramValue);
        }
        return result;
    }

    /**
     * 将JSONArray格式的参数信息转换为map
     *
     * @param paramArray
     * @param key1
     * @param key2
     * @return
     */
    public static Map<String, String> arrayToMap(Map<String, String> map, String paramArray, String key1, String key2) {
        JSONArray paramList = JSON.parseArray(paramArray);
        Map<String, String> paramMap = new HashMap<>();
        for (int i = 0; i < paramList.size(); i++) {
            JSONObject headerObject = paramList.getJSONObject(i);
            String value = headerObject.getString(key2);
            if (value.contains("${")) {
                // 解析出${}中的值，并以此为键在关联信息桶中获取相应的值
                value = map.get(value.substring(2, value.length() - 1));
            }
            paramMap.put(headerObject.getString(key1), value);
        }
        return paramMap;
    }

    /**
     * 处理每个用例的关联，将关联提取结果放入流程的关联桶
     *
     * @param map    放置关联信息的map
     * @param array  关联信息:[{"default":"X","keyName":"token","jsonPath":"$.data.accessToken"}]
     * @param result 接口用例的响应结果
     */
    public static void correlationHandler(Map map, String array, String result) {
        JSONArray list = JSON.parseArray(array);
        for (int i = 0; i < list.size(); i++) {
            JSONObject object = list.getJSONObject(i);
            String keyName = object.getString("keyName");
            String jsonPath = object.getString("jsonPath");
            String defaultValue = object.getString("default");

            try {
                String value = JSONExtractor.jsonExtractor(result, jsonPath);
                if (value == null || value.equals("")) {
                    value = defaultValue;
                }
                map.put(keyName, value);
            } catch (Exception e) {
                map.put(keyName, defaultValue);
            }
        }
    }

    // 去除header和body中的空[]
    public static JSONArray removeNull(JSONArray array) {
        for (int i = array.size() - 1; i >= 0; i--) {
            String string = array.getString(i);
            if (string.length() <= 2)
                array.remove(i);
        }
        return array;
    }
}
