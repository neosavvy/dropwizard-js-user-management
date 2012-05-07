package com.neosavvy;

import com.neosavvy.model.User;
import com.neosavvy.model.UserMapper;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.Mapper;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: waparrish
 * Date: 5/2/12
 * Time: 2:08 PM
 * To change this template use File | Settings | File Templates.
 */
public interface UserDAO {

    @SqlQuery("select * from user")
    @Mapper(UserMapper.class)
    List<User> findAll();

    @SqlUpdate("insert into user (id, firstName, lastName, email) values (:id, :firstName, :lastName, :email)")
    void insert(
        @Bind("id") Long id
        ,@Bind("firstName") String firstName
        ,@Bind("lastName") String lastName
        ,@Bind("email") String email
    );

    @SqlUpdate("update user set firstName = :firstName, lastName = :lastName, email = :email where id = :id")
    User update(
        @Bind("firstName") String firstName
        ,@Bind("lastName") String lastName
        ,@Bind("email") String email
    );

    @SqlUpdate("delete from user where id = :id")
    void delete(
        @Bind("id") Long id
    );

}
