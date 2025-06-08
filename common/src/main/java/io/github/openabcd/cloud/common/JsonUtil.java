package io.github.openabcd.cloud.common;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.openabcd.cloud.common.exception.JsonConvertException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JsonUtil {

    private static final ObjectMapper mapper = new ObjectMapper();

    public static String toJson(Object object) {
        try {
            return mapper.writeValueAsString(object);
        } catch (Exception e) {
            throw new JsonConvertException("Failed to convert object to JSON", e);
        }
    }

    public static <T> T fromJson(String json, Class<T> valueType) {
        try {
            return mapper.readValue(json, valueType);
        } catch (Exception e) {
            throw new JsonConvertException("Failed to convert JSON to object", e);
        }
    }

    public static <T> T fromJson(String json, TypeReference<T> valueTypeRef) {
        try {
            return mapper.readValue(json, valueTypeRef);
        } catch (Exception e) {
            throw new JsonConvertException("Failed to convert JSON to object", e);
        }
    }

    public static <T> T fromJson(byte[] json, Class<T> valueType) {
        try {
            return mapper.readValue(json, valueType);
        } catch (Exception e) {
            throw new JsonConvertException("Failed to convert JSON to object", e);
        }
    }
}
