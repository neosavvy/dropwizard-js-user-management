package com.neosavvy;

/**
 * Created by IntelliJ IDEA.
 * User: waparrish
 * Date: 4/20/12
 * Time: 2:32 PM
 * To change this template use File | Settings | File Templates.
 */

import com.yammer.dropwizard.config.Configuration;
import org.codehaus.jackson.annotate.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

public class HelloWorldConfiguration extends Configuration {
    
    @NotEmpty
    @JsonProperty
    private String template;
    
    @NotEmpty
    @JsonProperty
    private String defaultName = "Stranger";

    public String getTemplate() {
        return template;
    }

    public String getDefaultName() {
        return defaultName;
    }
}
