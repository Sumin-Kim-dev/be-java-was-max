package webserver.util;

import java.util.HashMap;
import java.util.Map;

public class RequestUtil {

    public static String parseRequestUrl(String request) {
        if (request.contains("?")) {
            return request.split("\\?")[0];
        }
        return request;
    }

    public static Map<String, String> parseQuery(String request) {
        if (!request.contains("?")) {
            return null;
        }
        Map<String, String> query = new HashMap<>();
        String[] params = request.split("\\?")[1].split("&");
        for (String param : params) {
            String[] keyValue = param.split("=");
            query.put(keyValue[0], keyValue[1]);
        }
        return query;
    }
}
