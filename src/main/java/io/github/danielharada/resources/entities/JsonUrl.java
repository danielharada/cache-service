package io.github.danielharada.resources.entities;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class JsonUrl {

    private String url;

    @JsonCreator
    public JsonUrl(@JsonProperty("url") String url){
        this.url = url;
    }

    public String getUrl(){return this.url;}
}
