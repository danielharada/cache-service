package io.github.danielharada.data;

import io.github.danielharada.resources.entities.CacheEntry;
import io.github.danielharada.resources.entities.Metadata;


public class CacheDAO {
    private InMemoryDataStore dataStore;

    public CacheDAO() {
        this.dataStore = new InMemoryDataStore();
    }

    public void insertCacheEntry(CacheEntry cacheEntry) {
        dataStore.insertCacheEntry(cacheEntry);
    }

    public void updateCacheEntry(CacheEntry cacheEntry) {
        dataStore.updateCacheEntry(cacheEntry);
    }

    // This will return null if no entries are found with the given id
    public CacheEntry getCacheEntry(int id) {
        return dataStore.getCacheEntryById(id);
    }

    // This will return null if no entries are found with the given URL
    public CacheEntry getCacheEntry(String url) {
        return dataStore.getCacheEntryByUrl(url);
    }

    public Metadata getMetadata(int id) {
        CacheEntry cacheEntry = getCacheEntry(id);
        Metadata metadata = null;
        if(cacheEntry != null) {
            // We have something cached at this ID, pull its metadata to return
            metadata = new Metadata(
                    cacheEntry.getUrl(),
                    cacheEntry.getResponseCode(),
                    cacheEntry.getCreationTimeStamp(),
                    cacheEntry.getLastHitTimeStamp(),
                    cacheEntry.getHits());
        }
        return metadata;
    }
}
