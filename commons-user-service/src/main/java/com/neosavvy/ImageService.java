package com.neosavvy;

import com.neosavvy.utils.HttpServletRequestFilter;
import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.config.Environment;

/**
 * Created by IntelliJ IDEA.
 * User: waparrish
 * Date: 4/20/12
 * Time: 2:38 PM
 * To change this template use File | Settings | File Templates.
 */
public class ImageService extends Service<ImageConfiguration> {

    public static void main(String[] args) throws Exception
    {
        new ImageService().run(args);
    }

    private ImageService() {
        super("image");
    }

    @Override
    protected void initialize(ImageConfiguration configuration, Environment environment) throws Exception {
        environment.addResource(new ImageResource());

        environment.addFilter(new HttpServletRequestFilter(), "/*");
    }
}
