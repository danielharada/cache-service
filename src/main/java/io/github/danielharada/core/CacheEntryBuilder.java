package io.github.danielharada.core;

import io.github.danielharada.data.CacheDAO;
import io.github.danielharada.resources.entities.CacheEntry;
import io.github.danielharada.resources.entities.URLResponse;
import io.github.danielharada.resources.entities.Metadata;
import io.github.danielharada.resources.entities.NarrowCacheEntry;

public class CacheEntryBuilder {

    private CacheDAO cacheDAO;
    private URLHandler urlHandler;

    public CacheEntryBuilder() {
        this.cacheDAO = new CacheDAO();
        this.urlHandler = new URLHandler();
    }


    public int buildOrUpdateCacheEntry(String url){
        CacheEntry cacheEntry = cacheDAO.getCacheEntry(url);
        if(cacheEntry == null){
            // No entry for this URL yet, build it out and save it
            cacheEntry = buildCacheEntry(url);
            insertCacheEntry(cacheEntry);
        } else {
            // A cached entry already exists, update its creation time and save that update
            cacheEntry.setCreationTimeToNow();
            updateCacheEntry(cacheEntry);
        }
        return cacheEntry.getEntryId();
    }

    public CacheEntry buildCacheEntry(String url) {
        URLResponse urlResponse = urlHandler.getUrlResponse(url);

        return new CacheEntry(urlResponse.getUrl(),
                urlResponse.getHttpResponseCode(),
                urlResponse.getBody(),
                urlResponse.getHeaders());
    }

    public NarrowCacheEntry getAndUpdateNarrowCacheEntry(int id){
        CacheEntry cacheEntry = cacheDAO.getCacheEntry(id);
        NarrowCacheEntry narrowCacheEntry = null;
        if(cacheEntry != null){
            // We found something in cache, update its metadata and return the content
            cacheEntry.incrementHits();
            cacheEntry.setLastHitTimeToNow();
            insertCacheEntry(cacheEntry);
            narrowCacheEntry = dropEntryMetadata(cacheEntry);
        }
        return narrowCacheEntry;
    }

    public void insertCacheEntry(CacheEntry cacheEntry){
        cacheDAO.insertCacheEntry(cacheEntry);
    }

    public void updateCacheEntry(CacheEntry cacheEntry) {
        cacheDAO.updateCacheEntry(cacheEntry);
    }

    public Metadata getMetadata(int id) {
        return cacheDAO.getMetadata(id);
    }

    public NarrowCacheEntry dropEntryMetadata(CacheEntry cacheEntry) {
        return new NarrowCacheEntry(cacheEntry.getUrl(),
                cacheEntry.getResponseCode(),
                cacheEntry.getHeaders(),
                cacheEntry.getBody());
    }

}
