package com.etrade.puggo.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class JsonUtils {

    public static ObjectMapper mapper;


    public JsonUtils(@Autowired ObjectMapper mapper) {
        JsonUtils.mapper = mapper;
    }

    public static <T> String toJson(T object) {
        try {
            return mapper.writeValueAsString(object);
        } catch (Throwable e) {
            log.error("convert to json error , parameter is {}, error is ", object.toString(), e);
        }
        return "";
    }

    public static <T> T fromJson(String string, Class<T> clazz) {
        try {
            return mapper.readValue(string, clazz);
        } catch (JsonProcessingException e) {
            log.error("convert to json error , parameter is {}, error is ", string, e);
        }
        return null;
    }

    public static <T> T fromJson(String string, TypeReference<T> reference) {
        try {
            return mapper.readValue(string, reference);
        } catch (JsonProcessingException e) {
            log.error("convert to json error , parameter is {}, error is ", string, e);
        }
        return null;
    }
}
