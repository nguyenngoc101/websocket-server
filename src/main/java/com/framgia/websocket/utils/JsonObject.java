package com.framgia.websocket.utils;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class JsonObject <T> {

    private T obj;

    public JsonObject(T obj) {
        this.obj = obj;
    }

    public Map<String, Object> toJsonObject() {
        List<String> fields = Arrays.stream(obj.getClass().getDeclaredFields()).map(e -> e.getName()).collect(Collectors.toList());
        return toJsonObject(fields);
    };

    public Map<String, Object> toJsonObject(List<String> fields) {
        return Arrays.stream(obj.getClass().getDeclaredFields())
                .peek(e -> e.setAccessible(true))
                .filter(e -> fields.contains(e.getName()))
                .collect(Collectors.toMap(e -> e.getName(), e -> {
                    try {
                        return e.get(obj);
                    } catch (IllegalAccessException e1) {
                        e1.printStackTrace();
                        return new IllegalArgumentException();
                    }
                }));
    };

}
