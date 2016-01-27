package org.easy4j.framework.http;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * @author fengwei (ivonvey@gmail.com) //
 * @date 16/1/27 10:29
 */
public class HttpClient {

    /* valid HTTP methods */
    private static final String[] methods = {
            "GET", "POST", "HEAD", "OPTIONS", "PUT", "DELETE", "TRACE"
    };

    /**
     * 默认连接超时时间 : 5s
     */
    private static final int DEFAULT_CONNECT_TIMEOUT = 5000;

    /**
     * 默认读取超时时间 : 5s
     */
    private static final int DEFAULT_READ_TIMEOUT = 5000;

    private int connectTimeout = DEFAULT_CONNECT_TIMEOUT;

    private int readTimeout = DEFAULT_READ_TIMEOUT;

    private String url;

    private String requestMethod;

    private Map<String, String> requestHeaderMap = new HashMap<String, String>();

    //请求参数之类的, 需要写入Server的数据
    private String requestData;

    //TODO 暂不支持
    private File requestFile;

    public HttpResponse execute() {
        HttpResponse response = new HttpResponse();
        try {
            URL u = new URL(url);
            HttpURLConnection huc = (HttpURLConnection) u.openConnection();
            //设置请求
            processRequest(huc);

            //need write
            if (requestData != null) {
                huc.setDoOutput(true);
                Writer writer = new BufferedWriter(new OutputStreamWriter(huc.getOutputStream()));
                writer.write(requestData);
                writer.flush();
                writer.close();
            }

            response.setResponseCode(huc.getResponseCode());
            response.setResponseMessage(huc.getResponseMessage());
            //设置响应header
            Map<String, String> headerMap = new HashMap<String, String>();
            //** 从1开始
            for (int i = 1; ; i++) {
                String key = huc.getHeaderFieldKey(i);
                if (key == null) {
                    break;
                }
                headerMap.put(key, huc.getHeaderField(i));
            }
            response.setResponseHeaders(headerMap);

            //处理body
            String contentEncoding = huc.getContentEncoding();

            String contentType = huc.getContentType();
            long contentLength = huc.getContentLengthLong();
            if (contentType.startsWith("text")) {
                //字节流转化成字符流
                InputStream is;
                try {
                    is = huc.getInputStream();
                } catch (IOException e) {
                    e.printStackTrace();
                    is = huc.getErrorStream();
                }

                Reader reader = new InputStreamReader(is);
                StringBuilder sb = new StringBuilder();

                int bufferLength = 1024;
                char[] data = new char[bufferLength];
                if (contentLength > 0 ) {
                    int readLength = (bufferLength > contentLength) ? (int) contentLength : bufferLength;
                    int length;
                    while ((length = reader.read(data, 0, readLength)) != -1) {
                        sb.append(data, 0, length);
                        contentLength -= length;
                        if (contentLength <= 0) {
                            break;
                        }
                        readLength = (bufferLength > contentLength) ? (int) contentLength : bufferLength;
                    }

                } else { //没有content-length字段返回
                    int ch;
                    while ((ch = reader.read()) != -1) {
                        sb.append((char) ch);
                    }
                }
                reader.close();

                response.setResponseBody(sb.toString());

                //关闭连接
                huc.disconnect();
            } else { //其他类型

            }
        } catch (MalformedURLException e) {

        } catch (IOException e) {

        } finally {

        }
        return response;
    }

    private void processRequest(HttpURLConnection huc) throws ProtocolException {
        //设置request method
        huc.setRequestMethod(requestMethod);
        //connect之前设置超时
        huc.setConnectTimeout(connectTimeout);
        huc.setReadTimeout(readTimeout);
        //设置header
        if (requestHeaderMap.size() > 0) {
            for (String key : requestHeaderMap.keySet()) {
                huc.setRequestProperty(key, requestHeaderMap.get(key));
            }
        }
    }

    public int getConnectTimeout() {
        return connectTimeout;
    }

    public void setConnectTimeout(int connectTimeout) {
        if (connectTimeout < 0) {
            throw new IllegalArgumentException("timeout can not be negative");
        }
        this.connectTimeout = connectTimeout;
    }

    public int getReadTimeout() {
        return readTimeout;
    }

    public void setReadTimeout(int readTimeout) {
        if (readTimeout < 0) {
            throw new IllegalArgumentException("timeout can not be negative");
        }
        this.readTimeout = readTimeout;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        if (url == null) {
            throw new IllegalArgumentException("illegal url");
        }
        this.url = url;
    }

    public String getRequestMethod() {
        return requestMethod;
    }

    public void setRequestMethod(String requestMethod) {
        //TODO 验证mehtod
        this.requestMethod = requestMethod;
    }

    public void addRequesetHeader(String key, String value) {
        //TODO 验证key和value
        this.requestHeaderMap.put(key, value);
    }

    public String getRequestData() {
        return requestData;
    }

    public void setRequestData(String requestData) {
        this.requestData = requestData;
    }
}
