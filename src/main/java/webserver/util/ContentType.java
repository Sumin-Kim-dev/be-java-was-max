package webserver.util;

public enum ContentType {

    HTML("text/html;charset=utf-8",".html"),
    CSS("text/css;charset=utf-8", ".css"),
    JS("text/javascript;charset=utf-8", ".js"),
    ICO("image/x-icon", ".ico"),
    PNG("image/png", ".png"),
    JPG("image/jpeg", ".jpg");

    private final String contentType;
    private final String extension;

    ContentType(String contentType, String extension) {
        this.contentType = contentType;
        this.extension = extension;
    }

    public String getExtension() {
        return extension;
    }

    public String getContentType() {
        return contentType;
    }
}
