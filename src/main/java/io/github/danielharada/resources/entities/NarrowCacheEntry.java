package io.github.danielharada.resources.entities;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class NarrowCacheEntry {

    private String url;
    private int responseCode;
    private String headers;
    private String body;

    @JsonCreator
    public NarrowCacheEntry(@JsonProperty("url") String url,
                            @JsonProperty("responseCode") int responseCode,
                            @JsonProperty("headers") String headers,
                            @JsonProperty("body") String body) {
        this.url = url;
        this.responseCode = responseCode;
        this.headers = headers;
        this.body = body;
    }

    @JsonProperty
    public String getUrl() {return url;}

    @JsonProperty
    public int getResponseCode(){return responseCode;}

    @JsonProperty
    public String getBody(){ return body; }

    @JsonProperty
    public String getHeaders(){ return headers; }
}
