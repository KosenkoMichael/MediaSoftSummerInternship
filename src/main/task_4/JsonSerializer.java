import java.lang.reflect.Field;
import java.util.Map;
import java.util.LinkedHashMap;

public class JsonSerializer {
    public static String toJson(Object obj) {
        Class<?> cl = obj.getClass();
        Map<String, String> jsonMap = new LinkedHashMap<>();
        for (Field field : cl.getDeclaredFields()) {
            if (field.isAnnotationPresent(JsonField.class)) {
                field.setAccessible(true);
                JsonField annotation = field.getAnnotation(JsonField.class);
                String jsonName = annotation.name();

                try {
                    Object value = field.get(obj);
                    String valueString = (value instanceof String) ? "\"" + value + "\"" : String.valueOf(value);
                    jsonMap.put(jsonName, valueString);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException("Access error to " + field.getName(), e);
                }
            }
        }

        StringBuilder json = new StringBuilder("{");
        boolean first = true;
        for (Map.Entry<String, String> entry : jsonMap.entrySet()) {
            if (!first)
                json.append(", ");
            json.append("\"").append(entry.getKey()).append("\": ").append(entry.getValue());
            first = false;
        }
        json.append("}");
        return json.toString();
    }
}
