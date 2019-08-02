package com.example.demo8.Model;

import java.sql.Time;
import java.util.Objects;

public class User {
    private int userId;
    private String userName;
    private String email;
    private String password;
    private Role role;
    private int securityQuestionId;
    private String securityQuestionAnswer;
    private int designationId;
    private String lastUpdateUser;
    private Time lastUpdateTime;

    public User() {
    }

    public User(int userId, String userName, String email, String password, String role, int securityQuestionId, String securityQuestionAnswer, int designationId, String lastAccessUser, Time lastUpdateTime) {
        this.userId = userId;
        this.userName = userName;
        this.email = email;
        this.password = password;
        if (role.equalsIgnoreCase("user")) {
            this.role = Role.celeritio_user;
        } else if (role.equalsIgnoreCase("admin")) {
            this.role = Role.celeritio_admin;
        }
        this.securityQuestionId = securityQuestionId;
        this.securityQuestionAnswer = securityQuestionAnswer;
        this.designationId = designationId;
        this.lastUpdateUser = lastAccessUser;
        this.lastUpdateTime = lastUpdateTime;
    }

    public User(String userName, String email, String password, String role, int securityQuestionId, String securityQuestionAnswer, int designationId, String lastUpdateUser, Time lastUpdateTime) {
        this.userName = userName;
        this.email = email;
        this.password = password;
        if (role.equalsIgnoreCase("user")) {
            this.role = Role.celeritio_user;
        } else if (role.equalsIgnoreCase("admin")) {
            this.role = Role.celeritio_admin;
        }
        this.securityQuestionId = securityQuestionId;
        this.securityQuestionAnswer = securityQuestionAnswer;
        this.designationId = designationId;
        this.lastUpdateUser = lastUpdateUser;
        this.lastUpdateTime = lastUpdateTime;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(String role) {
        if (role.equalsIgnoreCase("user") || role.equalsIgnoreCase("celeritio_user")) {
            this.role = Role.celeritio_user;
        } else if (role.equalsIgnoreCase("admin") || role.equalsIgnoreCase("celeritio_admin")) {
            this.role = Role.celeritio_admin;
        }
    }

    public int getSecurityQuestionId() {
        return securityQuestionId;
    }

    public void setSecurityQuestionId(int securityQuestionId) {
        this.securityQuestionId = securityQuestionId;
    }

    public String getSecurityQuestionAnswer() {
        return securityQuestionAnswer;
    }

    public void setSecurityQuestionAnswer(String securityQuestionAnswer) {
        this.securityQuestionAnswer = securityQuestionAnswer;
    }

    public int getDesignationId() {
        return designationId;
    }

    public void setDesignationId(int designationId) {
        this.designationId = designationId;
    }

    public String getLastUpdateUser() {
        return lastUpdateUser;
    }

    public void setLastUpdateUser(String lastUpdateUser) {
        this.lastUpdateUser = lastUpdateUser;
    }

    public Time getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Time lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return userId == user.userId &&
                password == user.password &&
                securityQuestionId == user.securityQuestionId &&
                designationId == user.designationId &&
                Objects.equals(userName, user.userName) &&
                Objects.equals(email, user.email) &&
                Objects.equals(role, user.role) &&
                Objects.equals(securityQuestionAnswer, user.securityQuestionAnswer) &&
                Objects.equals(lastUpdateUser, user.lastUpdateUser) &&
                Objects.equals(lastUpdateTime, user.lastUpdateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, userName, email, password, role, securityQuestionId, securityQuestionAnswer, designationId, lastUpdateUser, lastUpdateTime);
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                ", password=" + password +
                ", role='" + role + '\'' +
                ", securityQuestionId=" + securityQuestionId +
                ", securityQuestionAnswer='" + securityQuestionAnswer + '\'' +
                ", designationId=" + designationId +
                ", lastUpdateUser='" + lastUpdateUser + '\'' +
                ", lastUpdateTime=" + lastUpdateTime +
                '}';
    }
}

