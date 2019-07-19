package com.springtest.toutiao.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

public class JsonUtil {

    private static final Logger logger = LoggerFactory.getLogger(JsonUtil.class);
    private static ObjectMapper objectMapper = new ObjectMapper();

    /**
     * convert a object to json string
     */
    public static String getJsonString(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (Exception e) {
            logger.info(e.getMessage());
        }
        return null;
    }

    /**
     * convert json str to object
     */
    public static <T> T read(String source, Class<T> type) {
        try {
            return objectMapper.readValue(source, type);
        } catch (Exception e) {
            logger.info(e.getMessage());
        }
        return null;
    }

    /**
     * 从json格式的String生成Map
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> getMapFromJson(String jsonStr) {
        return objectFromJson(jsonStr, Map.class);
    }

    /**
     * 从json格式的String获取Object，如果有异常return null
     */
    public static <T> T objectFromJson(String jsonStr, TypeReference<T> valueTypeRef) {
        return objectFromJson(jsonStr, valueTypeRef, null);
    }

    /**
     * 从json格式的String获取Object，如果出现异常，就return参数默认值
     */
    public static <T> T objectFromJson(String jsonStr, TypeReference<T> valueTypeRef, T defaultValue) {
        if (jsonStr == null) {
            return defaultValue;
        }

        try {
            return objectMapper.readValue(jsonStr, valueTypeRef);
        } catch (Exception ex) {
            logger.error(String.format("Failed parse json string: %s", jsonStr), ex);
            return defaultValue;
        }
    }

    /**
     * 从json格式的String获取List
     */
    public static List<?> getListFromJson(String jsonStr) {
        return objectFromJson(jsonStr, List.class);
    }

    /**
     * 从json格式的String获取指定类的List
     */
    public static <T> List<T> getListFromJson(String jsonStr, Class<T> classType) {
        if (jsonStr == null) {
            return null;
        }

        try {
            return objectMapper.readValue(jsonStr, objectMapper.getTypeFactory().constructCollectionType(List.class, classType));
        } catch (Exception ex) {
            logger.error(String.format("Failed parse json string: %s", jsonStr), ex);
            return null;
        }
    }

    /**
     * 从json格式的String获取指定类的Object
     */
    public static <T> T objectFromJson(String json, Class<T> classType) {
        if (json == null) {
            return null;
        }

        try {
            return objectMapper.readValue(json, classType);
        } catch (Exception ex) {
            logger.error(String.format("Failed parse json string: %s", json), ex);
            return null;
        }
    }
}
