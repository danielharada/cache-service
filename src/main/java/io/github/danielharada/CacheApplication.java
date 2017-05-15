package io.github.danielharada;

import io.github.danielharada.resources.CacheResource;
import io.github.danielharada.health.CacheServiceHealthCheck;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class CacheApplication extends Application<CacheConfiguration> {

    public static void main(final String[] args) throws Exception {
        new CacheApplication().run(args);
    }

    @Override
    public String getName() {
        return "Cache";
    }

    @Override
    public void initialize(final Bootstrap<CacheConfiguration> bootstrap) {
        // TODO: application initialization
    }

    @Override
    public void run(final CacheConfiguration configuration,
                    final Environment environment) {
        final CacheResource cacheResource = new CacheResource();
        final CacheServiceHealthCheck healthCheck = new CacheServiceHealthCheck();
        environment.healthChecks().register("cache service", healthCheck);
        environment.jersey().register(cacheResource);
    }

}
