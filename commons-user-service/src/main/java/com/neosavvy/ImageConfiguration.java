package com.neosavvy;

import com.yammer.dropwizard.config.Configuration;
import org.codehaus.jackson.annotate.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

public class ImageConfiguration extends Configuration {

    @NotEmpty
    @JsonProperty
    private String imageDirectory = "things";

}
