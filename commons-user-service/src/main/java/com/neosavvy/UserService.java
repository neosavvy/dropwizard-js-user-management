package com.neosavvy;

import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.config.Environment;
import com.yammer.dropwizard.db.Database;
import com.yammer.dropwizard.db.DatabaseFactory;

/**
 * Created with IntelliJ IDEA.
 * User: waparrish
 * Date: 5/2/12
 * Time: 11:49 AM
 * To change this template use File | Settings | File Templates.
 */
public class UserService extends Service<UserConfiguration> {

    public static void main(String[] args) throws Exception
    {
        new UserService().run(args);
    }

    private UserService()
    {
        super("user");
    }


    @Override
    protected void initialize(UserConfiguration userConfiguration, Environment environment) throws Exception {

        final DatabaseFactory factory = new DatabaseFactory(environment);
        final Database db = factory.build(userConfiguration.getDatabase(), "mysql");
        final UserDAO dao = db.onDemand(UserDAO.class);
        environment.addResource(new UserResource(dao));
    }
}
