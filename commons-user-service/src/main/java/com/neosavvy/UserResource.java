package com.neosavvy;

import com.neosavvy.model.User;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: waparrish
 * Date: 5/2/12
 * Time: 11:52 AM
 * To change this template use File | Settings | File Templates.
 */
@Path("/backend/user")
@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_JSON})
public class UserResource {

    private UserDAO userDAO;

    public UserResource(UserDAO userDAO) {
        super();
        this.userDAO = userDAO;
    }

    @GET
    public List<User> fetch(User user){

        return userDAO.findAll();
    }

    @PUT
    public User update(User user)
    {

        return null;
    }

    @POST
    public void add(User user)
    {
        userDAO.insert(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail());
    }

    @DELETE
    public User delete(User user)
    {
        return null;
    }

}
