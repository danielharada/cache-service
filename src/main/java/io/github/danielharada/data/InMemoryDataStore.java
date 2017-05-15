package io.github.danielharada.data;

import io.github.danielharada.resources.entities.CacheEntry;

import java.util.Map;
import java.util.HashMap;


public class InMemoryDataStore {
    private Map<String, Integer> urlToEntryId;
    private Map<Integer, CacheEntry> entryIdToCacheEntry;

    public InMemoryDataStore(){
        this.urlToEntryId = new HashMap<String, Integer>();
        this.entryIdToCacheEntry = new HashMap<Integer, CacheEntry>();
    }


    public CacheEntry getCacheEntryById(int id){
        return entryIdToCacheEntry.get(id);
    }

    public CacheEntry getCacheEntryByUrl(String url){
        // getIdByUrl will give an id of -1 if the URL is not cached.  There should never be an entry for id = -1
        // so the getCacheEntryById should return null for that id
        int entryId = getIdByUrl(url);

        return getCacheEntryById(entryId);
    }

    public int getIdByUrl(String url){
        Integer entryId = urlToEntryId.get(url);
        // If this url isn't cached then we get a null value back from the get call above.
        // We turn this null into a negative value to avoid issues from assigning null to an int later
        if(entryId == null){
            entryId = -1;
        }
        return entryId;
    }

    public void insertCacheEntry(CacheEntry cacheEntry){
        String url = cacheEntry.getUrl();
        int entryId = cacheEntry.getEntryId();

        urlToEntryId.put(url, entryId);
        entryIdToCacheEntry.put(entryId, cacheEntry);
    }

    public void updateCacheEntry(CacheEntry cacheEntry){
        entryIdToCacheEntry.put(cacheEntry.getEntryId(), cacheEntry);
    }

}
