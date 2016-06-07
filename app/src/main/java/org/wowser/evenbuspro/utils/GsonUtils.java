package org.wowser.evenbuspro.utils;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

/**
 * Created by IntelliJ IDEA.
 * User: SZY
 * Date: 15/4/7
 * Time: 上午11:37
 */
public class GsonUtils {

    public static <T> T gson2Bean(String jsonString, Class<T> t) {
        Gson gson = new Gson();
        return gson.fromJson(jsonString, t);
    }

    public static <T> T gsonElement2Bean(String jsonString, String element, Class<T> t) {
        JsonParser parser = new JsonParser();
        JsonElement jsonElement = parser.parse(jsonString);
        JsonElement targetElement = jsonElement.getAsJsonObject().get(element);
        Gson gson = new Gson();
        return gson.fromJson(targetElement, t);
    }

    public static String object2Gson(Object o) {
        Gson gson = new Gson();
        return gson.toJson(o);
    }
}
