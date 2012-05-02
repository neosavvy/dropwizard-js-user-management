package com.neosavvy;

import com.yammer.dropwizard.config.Configuration;
import org.codehaus.jackson.annotate.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Created with IntelliJ IDEA.
 * User: waparrish
 * Date: 5/2/12
 * Time: 11:49 AM
 * To change this template use File | Settings | File Templates.
 */
public class UserConfiguration extends Configuration {

    @NotEmpty
    @JsonProperty
    private String testVariable = "things";
}
