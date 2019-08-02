package com.example.demo8.Model;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        User user = new User();
        user.setUserId(rs.getInt("userId"));
        user.setUserName(rs.getString("userName"));
        user.setEmail(rs.getString("email"));
        user.setPassword(rs.getString("password"));
        user.setRole(rs.getString("role"));
        user.setSecurityQuestionId(rs.getInt("securityQuestionId"));
        user.setSecurityQuestionAnswer(rs.getString("securityQuestionAnswer"));
        user.setDesignationId(rs.getInt("designationId"));
        user.setLastUpdateUser(rs.getString("lastAccessUser"));
        user.setLastUpdateTime(rs.getTime("lastUpdateTime"));
        return user;
    }
}
