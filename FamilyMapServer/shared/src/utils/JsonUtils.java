package utils;

import com.google.gson.Gson;

public final class JsonUtils {
    /**
     * Converts JSON String into Java Object
     * Example of usage: JavaObject javaObj = deserializeJson(jsonStr, JavaObject.class);
     */
    public static <T> T deserializeJson(String jsonStr, Class<T> tClass) {
        Gson gson = new Gson();

        return gson.fromJson(jsonStr, tClass);
    }

    /**
     * Converts Java Object into JSON String
     * Example of usage: String jsonStr = serializeJson(javaObj);
     */
    public static String serializeJson(Object object) {
        Gson gson = new Gson();

        return gson.toJson(object);
    }
}
