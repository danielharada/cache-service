package io.github.danielharada.health;

import com.codahale.metrics.health.HealthCheck;
import io.github.danielharada.core.URLHandler;


public class CacheServiceHealthCheck extends HealthCheck {

    private final URLHandler urlHandler = new URLHandler();

    public CacheServiceHealthCheck(){}

    @Override
    protected Result check() throws Exception{
        int testResponseCode = urlHandler.getHealthCheckResponseCode();

        return (testResponseCode == 200) ? Result.healthy() : Result.unhealthy("Test URL did not return a 200 response");
    }


}
