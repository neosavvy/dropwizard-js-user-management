package com.neosavvy;

import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.config.Environment;

/**
 * Created by IntelliJ IDEA.
 * User: waparrish
 * Date: 4/20/12
 * Time: 2:38 PM
 * To change this template use File | Settings | File Templates.
 */
public class HelloWorldService extends Service<HelloWorldConfiguration> {

    public static void main(String[] args) throws Exception
    {
        new HelloWorldService().run(args);
    }

    private HelloWorldService() {
        super("hello-world");
    }

    @Override
    protected void initialize(HelloWorldConfiguration helloWorldConfiguration, Environment environment) throws Exception {
        final String template = helloWorldConfiguration.getTemplate();
        final String defaultName = helloWorldConfiguration.getDefaultName();
        environment.addResource(new HelloWorldResource(template, defaultName));
        environment.addHealthCheck(new TemplateHealthCheck(template));
    }
}
