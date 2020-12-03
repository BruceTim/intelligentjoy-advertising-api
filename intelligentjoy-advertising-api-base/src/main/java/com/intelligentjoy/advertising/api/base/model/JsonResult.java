package com.intelligentjoy.advertising.api.base.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class JsonResult implements Serializable {

    private static final long serialVersionUID = 8489164604035464060L;

    public JsonResult() {
    }

    public static Object success() {
        Map<String, Object> map = new HashMap();
        map.put("code", 0);
        return map;
    }

    public static Object success(Object data) {
        Map<String, Object> map = new LinkedHashMap();
        map.put("code", 0);
        map.put("data", data);
        return map;
    }

    public static Object success(Map<String, Object> data) {
        Map<String, Object> map = new HashMap();
        map.put("code", 0);
        map.put("data", data);
        return map;
    }

    public static Object success(List<?> list) {
        Map<String, Object> map = new LinkedHashMap();
        map.put("code", 0);
        map.put("data", list);
        return map;
    }

    public static Object fail(Integer code, String msg) {
        Map<String, Object> map = new HashMap();
        map.put("code", code);
        map.put("msg", msg);
        return map;
    }

}
