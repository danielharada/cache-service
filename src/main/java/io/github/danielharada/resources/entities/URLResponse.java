package io.github.danielharada.resources.entities;

public class URLResponse {

    private String body;
    private String headers;
    private int httpResponseCode;
    private String url;

    public URLResponse(String body, String headers, int httpResponseCode, String url){
        this.body = body;
        this.headers = headers;
        this.httpResponseCode = httpResponseCode;
        this.url = url;
    }

    public String getBody(){
        return body;
    }

    public String getHeaders(){
        return headers;
    }

    public int getHttpResponseCode(){
        return httpResponseCode;
    }

    public String getUrl(){
        return url;
    }


}
