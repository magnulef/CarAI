package utils.json;

import com.fasterxml.jackson.core.type.TypeReference;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.Option;
import com.jayway.jsonpath.TypeRef;
import com.jayway.jsonpath.spi.json.JacksonJsonProvider;
import com.jayway.jsonpath.spi.mapper.JacksonMappingProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.*;
import java.util.stream.Collectors;

@SuppressWarnings("unchecked")
public class JSONPath {
    private static final Logger LOG = LoggerFactory.getLogger(JSONPath.class);

    public static final Configuration JSON_PATH_CONFIGURATION = Configuration
            .builder()
            .jsonProvider(new JacksonJsonProvider(JSON.MAPPER))
            .mappingProvider(new JacksonMappingProvider(JSON.MAPPER))
            .options(Option.DEFAULT_PATH_LEAF_TO_NULL)
            .build();

    private JSONPath() {
    }

    public static <T> T fromJson(String json, String jsonPath, Class<T> tClass) {
        return JsonPath
                .using(JSON_PATH_CONFIGURATION)
                .parse(json)
                .read(jsonPath, tClass);
    }

    public static <T> T fromJson(Object json, String jsonPath, Class<T> tClass) {
        return JsonPath
                .using(JSON_PATH_CONFIGURATION)
                .parse(json)
                .read(jsonPath, tClass);
    }

    public static <T> T fromJson(InputStream json, String jsonPath, Class<T> tClass) {
        return JsonPath
                .using(JSON_PATH_CONFIGURATION)
                .parse(json)
                .read(jsonPath, tClass);
    }

    public static <T> T fromJson(String json, String jsonPath, TypeReference<T> typeRef) {
        return JsonPath
                .using(JSON_PATH_CONFIGURATION)
                .parse(json)
                .read(jsonPath, new TypeRef<T>() {
                    @Override
                    public Type getType() {
                        return typeRef.getType();
                    }
                });
    }

    public static <T> T fromJson(Object json, String jsonPath, TypeReference<T> typeRef) {
        return JsonPath
                .using(JSON_PATH_CONFIGURATION)
                .parse(json)
                .read(jsonPath, new TypeRef<T>() {
                    @Override
                    public Type getType() {
                        return typeRef.getType();
                    }
                });
    }

    public static <T> T fromJson(InputStream json, String jsonPath, TypeReference<T> typeRef) {
        return JsonPath
                .using(JSON_PATH_CONFIGURATION)
                .parse(json)
                .read(jsonPath, new TypeRef<T>() {
                    @Override
                    public Type getType() {
                        return typeRef.getType();
                    }
                });
    }

    public static Map<String, Object> toFlatMap(Map<String, Object> stringObjectMap) {
        Map<String, Object> result = new HashMap<>();

        Set<Map.Entry<String, Object>> entries = stringObjectMap.entrySet();
        entries.forEach(entry -> {
            StringBuilder pathBuilder = new StringBuilder("$");
            result.putAll(buildForEntrySet(entry, pathBuilder));
        });
        return result;
    }

    public static Map<String, Object> fromFlatMap(Map<String, Object> flatMap) {
        Map<String, Object> result = new HashMap<>();

        mergeFlatMapIntoStructured(flatMap, result);
        return result;
    }

    public static void mergeFlatMapIntoStructured(Map<String, Object> flatMap, Map<String, Object> structuredMap) {

        // sorting so we create first elements of list entries first
        List<String> sortedKeys = flatMap.keySet().stream().sorted().collect(Collectors.toList());

        sortedKeys.forEach(sortedKey -> {
            LOG.trace("Structuring flat entrySet {} -> {}", sortedKey, flatMap.get(sortedKey));
            List<PathToken> pathParts = PathToken.parse(sortedKey);

            mergeWithMap(structuredMap, pathParts, flatMap.get(sortedKey));
        });
    }

    private static Map<String, Object> buildForEntrySet(Map.Entry<String, Object> stringObjectEntry, StringBuilder pathBuilder) {
        Map<String, Object> result = new HashMap<>();
        pathBuilder.append(".");
        pathBuilder.append(stringObjectEntry.getKey());

        Object value = stringObjectEntry.getValue();
        if (isPrimitive(value)) {
            result.put(pathBuilder.toString(), value);
            return result;
        } else {
            result.putAll(buildForValue(value, pathBuilder));
        }

        return result;
    }

    private static Map<String, Object> buildForList(List<Object> listValue, StringBuilder pathBuilder) {
        Map<String, Object> result = new HashMap<>();
        String listPath = pathBuilder.toString();

        for (int i = 0; i < listValue.size(); i++) {

            StringBuilder elementPathBuilder = new StringBuilder(listPath);
            elementPathBuilder.append("[");
            elementPathBuilder.append(i);
            elementPathBuilder.append("]");
            Object value = listValue.get(i);
            result.putAll(buildForValue(value, elementPathBuilder));
        }

        return result;
    }

    private static Map<String, Object> buildForValue(Object value, StringBuilder pathBuilder) {
        Map<String, Object> result = new HashMap<>();

        if (isPrimitive(value)) {

            result.put(pathBuilder.toString(), value);
        } else if (value instanceof Map) {
            Map<String, Object> mapValue = (Map<String, Object>) value;

            mapValue.entrySet().forEach(entry -> {
                result.putAll(buildForEntrySet(entry, new StringBuilder(pathBuilder.toString())));
            });
        } else if (value instanceof List) {

            result.putAll(buildForList((List<Object>) value, new StringBuilder(pathBuilder.toString())));
        } else {
            throw new RuntimeException(String.format("Unsupported type of value: %s", value));
        }

        return result;
    }

    private static boolean isPrimitive(Object value) {
        return value instanceof String
                || value == null
                || value.getClass().isPrimitive()
                || WRAPPER_TYPES.contains(value.getClass());
    }

    public static boolean isWrapperType(Class<?> clazz) {
        return WRAPPER_TYPES.contains(clazz);
    }

    private static final Set<Class<?>> WRAPPER_TYPES = getWrapperTypes();

    private static Set<Class<?>> getWrapperTypes() {
        Set<Class<?>> ret = new HashSet<>();
        ret.add(Boolean.class);
        ret.add(Character.class);
        ret.add(Byte.class);
        ret.add(Short.class);
        ret.add(Integer.class);
        ret.add(Long.class);
        ret.add(Float.class);
        ret.add(Double.class);
        ret.add(Void.class);
        return ret;
    }

    private static void mergeWithMap(Map<String, Object> result, List<PathToken> pathParts, Object value) {
        Object parentNode = result;

        for (PathToken currentToken : pathParts) {

            if (parentNode instanceof Map) {

                if (currentToken instanceof AttributeToken) {
                    parentNode = handleMapNode((Map<String, Object>) parentNode, (AttributeToken) currentToken, value);

                } else {
                    throw new RuntimeException(String.format("Expected attribute token path in map node, but got array token path at %s of path %s", currentToken, pathParts));
                }
            } else if (parentNode instanceof List) {

                if (currentToken instanceof ArrayToken) {
                    parentNode = handleListNode((List<Object>) parentNode, (ArrayToken) currentToken, value);

                } else {
                    throw new RuntimeException(String.format("Expected array token path in array node, but got attribute token path at %s of path %s", currentToken, pathParts));
                }
            }
        }
    }

    private static Object handleListNode(List<Object> parentNode, ArrayToken currentToken, Object value) {
        if (!currentToken.hasNext()) {
            parentNode.add(value);
            return null;
        }

        if (currentToken.getIndex() >= parentNode.size()) {
            Object nextParent;
            if (currentToken.isNextTokenArray()) {
                nextParent = new ArrayList<>();
            } else {
                nextParent = new HashMap<String, Object>();
            }

            parentNode.add(nextParent);
            return nextParent;
        } else {
            return parentNode.get(currentToken.getIndex());
        }
    }

    private static Object handleMapNode(Map<String, Object> parentNode, AttributeToken currentToken, Object value) {
        if (!currentToken.hasNext()) {
            parentNode.put(currentToken.getName(), value);
            return null;
        }

        if (!parentNode.containsKey(currentToken.getName())) {
            Object nextParent;
            if (currentToken.isNextTokenArray()) {
                nextParent = new ArrayList<>();
            } else {
                nextParent = new HashMap<String, Object>();
            }

            parentNode.put(currentToken.getName(), nextParent);
            return nextParent;
        } else {
            return parentNode.get(currentToken.getName());
        }
    }
}