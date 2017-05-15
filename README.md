# cache-service

Brief example service built on the dropwizard framework.  Exposes the following endpoints:
* POST /v1/cache/
  * Takes a JSON object of the form {"url" : "http://urltocache.example"}
  * Will make a call out to the provided URL to grab the body and headers at that site, and store that information in memory
  * Returns a JSON object giving the "Cache Entry ID" for entry you just created
* GET /v1/cache/{id}
  * Gets the cached data for entry #{id}
  * Data returned:
    * The URL of the entry
    * HTTP response code received at that URL
    * Headers received
    * The body from that URL
  * If no cached data, will return a 404 response
* GET /v1/cache/{id}/metadata
  * Returns the following information about cached entry #{id}:
    * Its URL
    * The HTTP response code we received
    * Timestamp of when the entry was created
    * Timestamp when the entry was last accessed by the /v1/cache/{id} endpoint
    * The number of times the /v1/cache/{id} endpoint was hit

Currently all attempts to create a new cache entry will succeed, but the entry may have null values for the header and body if we cannot access the site.  This is by design, the CacheEntryBuilder class can be updated to only persist entries if receiving a 20X response code.

The CacheDAO class is largely duplicative of the InMemoryDataStore class to facilitate a transition to a persistent data store if desired.  Only the DAO class needs to be updated with the details of the desired data store, the CacheEntryBuilder which calls upon the DAO should not need to know what the backend store is.  The current implementation of the in memory store is fairly brittle, as it can only be accessed by a single instance of the DAO.  Making the InMemoryDataStore class follow the singleton pattern should eliminate this issue, allowing multiple classes to call into the DAO and have the same data being surfaced across all classes.


How to start the Cache application
---

1. Run `mvn clean install` to build your application
1. Start application with `java -jar target/cache-service-1.0-SNAPSHOT.jar server config.yml`
1. To check that your application is running enter url `http://localhost:8080`

Health Check
---

To see your applications health enter url `http://localhost:8081/healthcheck`
