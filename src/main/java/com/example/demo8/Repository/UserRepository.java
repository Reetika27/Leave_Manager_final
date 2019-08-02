package com.example.demo8.Repository;


import com.example.demo8.Model.User;
import com.example.demo8.Model.UserRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalTime;
import java.util.List;

@Repository
public class UserRepository implements UserDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Transactional
    public List<User> findAll() {
        return jdbcTemplate.query("select * from users", new UserRowMapper());
    }

    @Transactional
    public User findUserById(int userId) {

        try {
            return jdbcTemplate.queryForObject("select * from users where userId=?", new Object[]{userId}, new UserRowMapper());

        } catch (EmptyResultDataAccessException e) {
            System.out.println(e.getMessage());
        }
        return new User();
    }


    @Transactional
    public int getUserIdFromDB(String email, String password) {
        String sql = "SELECT userId from users WHERE email=? AND password=?;";
        int userId;
        try {
            userId = jdbcTemplate.queryForObject(sql, new Object[]{email, password}, Integer.class);
        } catch (EmptyResultDataAccessException e) {
            System.out.println("User does not exists");
            return -1;
        }
        return userId;
    }

    @Transactional
    public int getUserId(String username) {
        String sql = "SELECT userId from users WHERE username=?;";
        int userId;
        try {
            userId = jdbcTemplate.queryForObject(sql, new Object[]{username}, Integer.class);
        } catch (EmptyResultDataAccessException e) {
            System.out.println("User does not exists");
            return -1;
        }
        return userId;
    }


    /*public User create(final User user) {
        try {
            final String sql = "insert into users(userId, userName, email, password, role, securityQuestionId, securityQuestionAnswer, designationId, lastAccessUser, lastUpdateTime) values (?,?,?,?,?,?,?,?,?,?)";

            jdbcTemplate.update(new PreparedStatementCreator() {
                @Override
                public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                    PreparedStatement preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.setInt(1, user.getUserId());
                    preparedStatement.setString(2, user.getUserName());
                    preparedStatement.setString(3, user.getEmail());
                    preparedStatement.setInt(4, user.getPassword());
                    preparedStatement.setString(5, user.getRole());
                    preparedStatement.setInt(6, user.getSecurityQuestionId());
                    preparedStatement.setString(7, user.getSecurityQuestionAnswer());
                    preparedStatement.setInt(8, user.getDesignationId());
                    preparedStatement.setString(9, user.getLastUpdateUser());
                    preparedStatement.setTime(10, user.getLastUpdateTime());
                    return preparedStatement;
                }
            });
       *//* int newUserId = keyHolder.getKey().intValue();
        user.setUserId(newUserId);*//*
            return user;
        } catch (Exception e) {
            System.out.println("User already exists!");
        }
        return user;
    }
*/

    @Transactional
    private int generateId() {
        String sqlId = " SELECT nextVal('user_pk');";
        int id = jdbcTemplate.queryForObject(sqlId, Integer.class);
        return id;
    }


    public User create(User user) {
        try {
            final String sql = "insert into users(userId,userName, email, password, role, securityQuestionId, securityQuestionAnswer, designationId, lastAccessUser, lastUpdateTime) values (?,?,?,?,?,?,?,?,?,?)";
            int id = generateId();
            String generatedPassword = generatePassword(user);
            user.setPassword(generatedPassword);
            jdbcTemplate.update(new PreparedStatementCreator() {
                @Override
                public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                    PreparedStatement preparedStatement = con.prepareStatement(sql);

                    preparedStatement.setInt(1, id);
                    preparedStatement.setString(2, user.getUserName());
                    preparedStatement.setString(3, user.getEmail());
                    preparedStatement.setString(4, user.getPassword());
                    preparedStatement.setString(5, user.getRole().name());
                    preparedStatement.setInt(6, user.getSecurityQuestionId());
                    preparedStatement.setString(7, user.getSecurityQuestionAnswer());
                    preparedStatement.setInt(8, user.getDesignationId());
                    preparedStatement.setString(9, user.getLastUpdateUser());
                    preparedStatement.setTime(10, user.getLastUpdateTime());
                    return preparedStatement;

                }
            });
            user.setUserId(id);
            System.out.println("Successfully added user!");
            return user;
        } catch (Exception e) {
            System.out.println("User already exists");
            return null;
        }

    }


    public void update(final User user) {
        int userId = getUserId(user.getUserName());
        if (userId != -1) {
            final String sql = "UPDATE users SET  email=?, password= ?, role=?, securityQuestionId=?, securityQuestionAnswer=?, designationId=?, lastAccessUser = ?, lastUpdateTime = ? WHERE userId = ?;";
            jdbcTemplate.update(new PreparedStatementCreator() {
                @Override
                public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                    PreparedStatement preparedStatement = con.prepareStatement(sql);
                    String generatedPassword = generatePassword(user);
                    user.setPassword(generatedPassword);
                    preparedStatement.setString(1, user.getEmail());
                    preparedStatement.setString(2, user.getPassword());
                    preparedStatement.setString(3, user.getRole().name());
                    preparedStatement.setInt(4, user.getSecurityQuestionId());
                    preparedStatement.setString(5, user.getSecurityQuestionAnswer());
                    preparedStatement.setInt(6, user.getDesignationId());
                    preparedStatement.setString(7, user.getLastUpdateUser());
                    preparedStatement.setTime(8, Time.valueOf(LocalTime.now()));
                    preparedStatement.setInt(9, userId);

                    return preparedStatement;
                }


            });
            System.out.println("Updated user info successfully");
        }

    }

    public User delete(User user) {
        String generatedPassword = generatePassword(user);
        int userId = getUserIdFromDB(user.getEmail(), generatedPassword);
        if (userId == -1) {
            System.out.println("Such user cannot be deleted as it is non existing");
            return user;
        }
        final String sql = "DELETE FROM users WHERE userId=?;";
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement preparedStatement = con.prepareStatement(sql);
                preparedStatement.setInt(1, userId);
                return preparedStatement;
            }
        });
        System.out.println("Deleted user successfully");
        return user;
    }

    private String generatePassword(User user) {
        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(user.getPassword().getBytes());
            byte[] bytes = md.digest();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return generatedPassword;
    }

}

