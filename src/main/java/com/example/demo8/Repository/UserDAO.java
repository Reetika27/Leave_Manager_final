package com.example.demo8.Repository;

import com.example.demo8.Model.User;

import java.util.List;

//Operations supported by user repository ie. all CRUD operations and search on userId. It affects the table 'users' in the database.
public interface UserDAO {

    //finds all employees and of all roles
    List<User> findAll();

    //finds a specific user given the user id
    User findUserById(int userId);

    //a helper function used to retrieve user id given email and password of the user.
    int getUserIdFromDB(String email, String password);

    //creates a new user and adds it to the database
    User create(final User user);

    //Updates a field based on the value of 'fieldToBeUpdated' for an existing user.
    void update(final User user);

    //Used to delete a user
    User delete(User user);
}
