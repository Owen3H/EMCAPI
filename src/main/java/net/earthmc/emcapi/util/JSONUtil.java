package net.earthmc.emcapi.util;

import com.google.gson.*;
import io.javalin.http.BadRequestResponse;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class JSONUtil {

    public static JsonArray getJsonArrayUnique(JsonArray arr) {
        ConcurrentHashMap<JsonElement, Boolean> seen = new ConcurrentHashMap<>();
        List<JsonElement> unique = arr.asList().parallelStream()
                .filter(element -> seen.putIfAbsent(element, Boolean.TRUE) == null)
                .toList();

        JsonArray resultArr = new JsonArray();
        unique.forEach(resultArr::add);

        return resultArr;
    }

    public static JsonObject getJsonObjectFromString(String string) {
        try {
            return JsonParser.parseString(string).getAsJsonObject();
        } catch (Exception e) {
            throw new BadRequestResponse("Invalid JSON body provided");
        }
    }

    public static String getJsonElementAsStringOrNull(JsonElement element) {
        if (element == null) return null;

        if (!element.isJsonPrimitive()) return null;

        JsonPrimitive primitive = element.getAsJsonPrimitive();
        if (!primitive.isString()) return null;

        return primitive.getAsString();
    }

    public static Integer getJsonElementAsIntegerOrNull(JsonElement element) {
        if (element == null) return null;

        if (!element.isJsonPrimitive()) return null;

        JsonPrimitive primitive = element.getAsJsonPrimitive();
        if (!primitive.isNumber()) return null;

        return primitive.getAsInt();
    }

    public static JsonArray getJsonElementAsJsonArrayOrNull(JsonElement element) {
        if (element == null) return null;

        if (!element.isJsonArray()) return null;
        return element.getAsJsonArray();
    }

    public static JsonObject getJsonElementAsJsonObjectOrNull(JsonElement element) {
        if (element == null) return null;

        if (!element.isJsonObject()) return null;
        return element.getAsJsonObject();
    }
}