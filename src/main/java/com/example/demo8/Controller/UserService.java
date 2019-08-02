package com.example.demo8.Controller;

import com.example.demo8.Model.User;
import com.example.demo8.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;

@Service("userService")
public class UserService {

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User addUser(User newUser) {
        //User addUser = new User("reetika", "ghagr@gmail.com", 63746, "user", 1, "mango", 0, "admin2", Time.valueOf(LocalTime.now()));
        return userRepository.create(newUser);
    }


    public int getUserId(String email, String password) {
        return userRepository.getUserIdFromDB(email, password);
    }


    public User getUserById(int userId) {
        User user = userRepository.findUserById(userId);
        assertNotNull(user);
        return user;
    }


    public User updateUser(final User user) {
        //User user = new User(5, "reetika", "ghagreetika@gmail.com", 63746, "USER", 1, "donkey", 0, "admin1", Time.valueOf(LocalTime.now()));
        userRepository.update(user);
        return user;
    }


    public User deleteUser(final User user) {
        //User user = new User("reetika", "ghagreetika@gmail.com", 63746, "USER", 1, "donkey", 0, "admin1", Time.valueOf(LocalTime.now()));
        return userRepository.delete(user);
    }


    public List<User> findAllUsers() {
        List<User> users = userRepository.findAll();
        assertNotNull(users);
        return users;
    }
}
