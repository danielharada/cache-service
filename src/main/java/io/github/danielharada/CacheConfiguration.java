package io.github.danielharada;

import io.dropwizard.Configuration;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.*;
import javax.validation.constraints.*;

public class CacheConfiguration extends Configuration {

    private String healthCheckUrl;

    @JsonProperty
    public String getHealthCheckUrl(){ return healthCheckUrl; }


}
