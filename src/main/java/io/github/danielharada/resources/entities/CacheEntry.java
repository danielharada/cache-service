package io.github.danielharada.resources.entities;

import java.time.ZonedDateTime;

import com.codahale.metrics.Counter;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CacheEntry {

    // This is kind of a crappy way to track entries, and will reset upon restart of the service.
    // That behavior is fine while the cache is just in memory, but if we move to a persistent store
    // we'll need to persist the nextEntryId as well to ensure that we don't duplicate IDs
    private static int nextEntryId = 1;

    private int entryId;
    private String url;
    private int responseCode;
    private String body;
    private String headers;
    // DropWizard is packaged with some kind of date handling library, maybe there will be a
    // better type than String for these timestamps
    private ZonedDateTime creationTimeStamp;
    private ZonedDateTime lastHitTimeStamp;
    private Counter hits;

    @JsonCreator
    public CacheEntry(@JsonProperty("url") String url,
                      @JsonProperty("responseCode") int responseCode,
                      @JsonProperty("body") String body,
                      @JsonProperty("headers") String headers) {
        this.url = url;
        this.responseCode = responseCode;
        this.body = body;
        this.headers = headers;
        this.creationTimeStamp = ZonedDateTime.now();
        this.lastHitTimeStamp = null;

        //Grab the the next available entryId, and then increment it to prepare for the next cacheEntry
        this.entryId = nextEntryId;
        nextEntryId++;

        this.hits = new Counter();
    }

    @JsonProperty
    public int getEntryId(){ return entryId; }

    @JsonProperty
    public String getBody(){ return body; }

    @JsonProperty
    public String getHeaders(){ return headers; }

    @JsonProperty
    public ZonedDateTime getCreationTimeStamp(){return creationTimeStamp;}

    @JsonProperty
    public ZonedDateTime getLastHitTimeStamp(){return lastHitTimeStamp;}

    @JsonProperty
    public long getHits(){return hits.getCount();}

    @JsonProperty
    public int getResponseCode(){return responseCode;}

    @JsonProperty
    public String getUrl() {return url;}

    public void incrementHits(){
        this.hits.inc();
    }

    public void setCreationTimeToNow(){
        this.creationTimeStamp = ZonedDateTime.now();
    }

    public void setLastHitTimeToNow(){
        this.lastHitTimeStamp = ZonedDateTime.now();
    }
}
