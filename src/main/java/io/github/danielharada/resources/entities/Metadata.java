package io.github.danielharada.resources.entities;

import java.time.ZonedDateTime;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Metadata {

    private String url;
    int responseCode;
    private ZonedDateTime creationTimestamp;
    private ZonedDateTime lastHitTimestamp;
    private long hits;

    @JsonCreator
    public Metadata(@JsonProperty("url") String url,
                    @JsonProperty("response code") int responseCode,
                    @JsonProperty("creation timestamp") ZonedDateTime creationTimestamp,
                    @JsonProperty("last hit timestamp") ZonedDateTime lastHitTimestamp,
                    @JsonProperty("hits") long hits){
        this.url = url;
        this.responseCode = responseCode;
        this.creationTimestamp = creationTimestamp;
        this.lastHitTimestamp = lastHitTimestamp;
        this.hits = hits;
    }

    public String getUrl(){return url;}

    public int getResponseCode(){return responseCode;}

    public ZonedDateTime getCreationTimestamp() {return creationTimestamp;}

    public ZonedDateTime getLastHitTimestamp() {return lastHitTimestamp;}

    public long getHits() {return hits;}
}
