package utils.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.type.CollectionLikeType;
import io.dropwizard.jackson.Jackson;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class JSON {

    public static final ObjectMapper MAPPER = Jackson.newObjectMapper();
    public static final ObjectMapper MAPPER_JERSEY = Jackson.newObjectMapper();

    static {
        MAPPER.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        MAPPER.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        MAPPER_JERSEY.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    }

    private JSON() {

    }

    public static <T> T fromJson(String json, Class<T> tClass) {
        try {
            return MAPPER.readValue(json, tClass);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> List<T> fromJsonList(String json, Class<T> tClass) {
        try {
            return MAPPER.readValue(json, constructCollectionType(List.class, tClass));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> Set<T> fromJsonSet(String json, Class<T> tClass) {
        try {
            return MAPPER.readValue(json, constructCollectionType(Set.class, tClass));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Map<String, Object> convertToMap(Object object) {
        return MAPPER.convertValue(object, new TypeReference<Map<String, Object>>() {});
    }

    public static Map<String, Object> convertToFlatMap(Object object) {
        Map<String, Object> stringObjectMap = MAPPER.convertValue(
                object,
                new TypeReference<Map<String, Object>>() {}
        );
        return JSONPath.toFlatMap(stringObjectMap);
    }

    public static <T> T convertFromMap(Map<String, Object> map, Class<T> tClass) {
        return MAPPER.convertValue(map, tClass);
    }

    public static <T> T convertFromFlatMap(Map<String, Object> map, Class<T> tClass) {
        return MAPPER.convertValue(convertFromFlatMap(map), tClass);
    }

    public static Map<String, Object> convertFromFlatMap(Map<String, Object> map) {
        return JSONPath.fromFlatMap(map);
    }

    private static CollectionLikeType constructCollectionType(Class<?> collection, Class<?> element) {
        JavaType elementType = MAPPER.getTypeFactory().constructType(element);
        return MAPPER.getTypeFactory().constructCollectionLikeType(collection, elementType);
    }

    public static <T> T fromJson(String json, TypeReference<T> typeReference) {
        try {
            return MAPPER.readValue(json, typeReference);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T fromJson(InputStream json, Class<T> tClass) {
        try {
            return MAPPER.readValue(json, tClass);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T fromJson(InputStream json, TypeReference<T> typeReference) {
        try {
            return MAPPER.readValue(json, typeReference);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T fromJson(Reader json, Class<T> tClass) {
        try {
            return MAPPER.readValue(json, tClass);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T fromJson(Reader json, TypeReference<T> typeReference) {
        try {
            return MAPPER.readValue(json, typeReference);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public static String toJson(Object object) {
        try {
            return MAPPER.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
