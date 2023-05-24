package webserver.util;

public class ResponseUtil {

    public static final String BASE_PATH = "src/main/resources/";

    public static String getAbsolutePath(String requestUrl) {
        String path = BASE_PATH;
        if (isStatic(requestUrl)) {
            path += "static";
        } else {
            path += "templates";
        }
        return path + requestUrl;
    }

    public static String getType(String requestUrl) {
        for (ContentType contentType : ContentType.values()) {
            if (requestUrl.endsWith(contentType.getExtension())) {
                return contentType.getContentType();
            }
        }
        throw new RuntimeException("지원하지 않는 타입입니다");
    }

    private static boolean isStatic(String requestUrl) {
        return requestUrl.endsWith(".css") || requestUrl.endsWith(".js")
                || requestUrl.endsWith(".ico") || requestUrl.endsWith(".png") || requestUrl.endsWith(".jpg");
    }
}
