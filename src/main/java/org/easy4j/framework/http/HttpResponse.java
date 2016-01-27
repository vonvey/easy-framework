package org.easy4j.framework.http;

import java.util.Map;

/**
 * @author fengwei (ivonvey@gmail.com) //
 * @date 16/1/27 10:54
 */
public class HttpResponse {
    private int responseCode = -1;

    private String responseMessage;

    private String version;

    private Map<String, String> responseHeaderMap;

    private Object responseBody;

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public Map<String, String> getResponseHeaders() {
        return responseHeaderMap;
    }

    public void setResponseHeaders(Map<String, String> responseHeaderMap) {
        this.responseHeaderMap = responseHeaderMap;
    }

    public Object getResponseBody() {
        return responseBody;
    }

    public void setResponseBody(Object responseBody) {
        this.responseBody = responseBody;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    private String toHeaderString () {
        StringBuilder sb = new StringBuilder();
        for (String key : responseHeaderMap.keySet()) {
            sb.append(key).append(": ").append(responseHeaderMap.get(key)).append("\r\n");
        }

        return sb.toString();
    }
    @Override
    public String toString() {

        return "HttpResponse.toString:\r\n"
                + "HTTP/1.x " +responseCode + " " + responseMessage + "\r\n"
                + toHeaderString()
                + "\r\n"
                + responseBody.toString();
    }
}
