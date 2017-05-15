package io.github.danielharada.resources;

import io.github.danielharada.resources.entities.JsonUrl;
import io.github.danielharada.core.CacheEntryBuilder;
import io.github.danielharada.resources.entities.Metadata;
import io.github.danielharada.resources.entities.NarrowCacheEntry;

import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.POST;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("v1/cache")
public class CacheResource {

    private CacheEntryBuilder cacheEntryBuilder;


    public CacheResource(){
        this.cacheEntryBuilder = new CacheEntryBuilder();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response postUrlToCache(JsonUrl jsonUrl) {
        int entryId = cacheEntryBuilder.buildOrUpdateCacheEntry(jsonUrl.getUrl());

        return Response.ok().entity(String.format("{\"Cache Entry ID\" : \"%d\"}\n", entryId)).build();
    }

    @Path("{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCacheData(@PathParam("id") int id) {
        NarrowCacheEntry narrowCacheEntry =  cacheEntryBuilder.getAndUpdateNarrowCacheEntry(id);
        if(narrowCacheEntry == null){
            return Response.status(Status.NOT_FOUND)
                    .entity(String.format("No cached data found for ID %d\n", id))
                    .build();
        }
        return Response.ok().entity(narrowCacheEntry).build();
    }

    @Path("{id}/metadata")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMetadata(@PathParam("id") int id) {
        Metadata metadata = cacheEntryBuilder.getMetadata(id);
        if(metadata == null){
            return Response.status(Status.NOT_FOUND)
                    .entity(String.format("No cached data found for ID %d\n", id))
                    .build();
        }
        return Response.ok().entity(metadata).build();
    }
}
