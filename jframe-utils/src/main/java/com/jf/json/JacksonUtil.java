package com.jf.json;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description: fastxml Jackson
 * User: xujunfei
 * Date: 2018-06-29
 * Time: 11:28
 */
public class JacksonUtil {

    private final static ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
    }

    /**
     * object转换为json字符串
     *
     * @param obj
     * @return
     * @throws Exception
     */
    public static String objectToJson(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new JsonFormatException("Object转Json发生错误：" + e.getMessage());
        }
    }

    /**
     * json转JavaBean
     *
     * @param jsonString
     * @param clazz
     * @param <T>
     * @return
     * @throws Exception
     */
    public static <T> T jsonToBean(String jsonString, Class<T> clazz) {
        if (jsonString == null || "".equals(jsonString)) {
            return null;
        }
        objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        try {
            return objectMapper.readValue(jsonString, clazz);
        } catch (IOException e) {
            throw new JsonFormatException("Json转Bean发生错误：" + e.getMessage());
        }
    }

    /**
     * map转JavaBean
     *
     * @param map
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T mapToBean(Map map, Class<T> clazz) {
        return objectMapper.convertValue(map, clazz);
    }

    /**
     * json字符串转换为map
     *
     * @param jsonString
     * @param <T>
     * @return
     * @throws Exception
     */
    public static <T> Map<String, Object> jsonToMap(String jsonString) {
        try {
            return objectMapper.readValue(jsonString, Map.class);
        } catch (IOException e) {
            throw new JsonFormatException("Json转Map发生错误：" + e.getMessage());
        }
    }

    /**
     * json字符串转换为list
     *
     * @param jsonArrayStr
     * @param clazz
     * @param <T>
     * @return
     * @throws Exception
     */
    public static <T> List<T> jsonToList(String jsonArrayStr, Class<T> clazz) {
        JavaType javaType = getCollectionType(ArrayList.class, clazz);
        List<T> list = null;
        try {
            list = (List<T>) objectMapper.readValue(jsonArrayStr, javaType);
        } catch (IOException e) {
            throw new JsonFormatException("Json转List发生错误：" + e.getMessage());
        }
        return list;
    }

    /**
     * 获取泛型的Collection Type
     *
     * @param collectionClass
     * @param elementClasses
     * @return
     */
    private static JavaType getCollectionType(Class<?> collectionClass, Class<?>... elementClasses) {
        return objectMapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);
    }
}
