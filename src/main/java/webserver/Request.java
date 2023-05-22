package webserver;

import webserver.util.RequestUtil;

import java.util.Map;

public class Request {
    public final String method;
    public final String requestUrl;
    public final Map<String, String> query;

    private Request(String method, String requestUrl, Map<String, String> query) {
        this.method = method;
        this.requestUrl = requestUrl;
        this.query = query;
    }

    public static Request of(String requestLine) {
        String[] split = requestLine.split(" ");
        String method = split[0];
        String requestUrl = RequestUtil.parseRequestUrl(split[1]);
        Map<String, String> query = RequestUtil.parseQuery(split[1]);
        return new Request(method, requestUrl, query);
    }
}
