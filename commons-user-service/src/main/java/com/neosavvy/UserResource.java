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

@Path("/backend")
public class UserResource {

    private UserDAO userDAO;

    public UserResource(UserDAO userDAO) {
        super();
        this.userDAO = userDAO;
    }

    @GET
    @Path("/user")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public List<User> fetch(){

        return userDAO.findAll();
    }

    @PUT
    @Path("/user/{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public User update(@PathParam("id") Long id, User user)
    {
        return userDAO.update(
                id,
                user.getFirstName(),
                user.getLastName(),
                user.getEmail()
        );
    }

    @POST
    @Path("/user")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public void add(User user)
    {
        userDAO.insert(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail());
    }

    @DELETE
    @Path("/user/{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public void delete(@PathParam("id") Long id)
    {
        userDAO.delete(id);
    }

}
