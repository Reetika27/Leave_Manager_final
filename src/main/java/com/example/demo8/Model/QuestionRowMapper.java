package com.example.demo8.Model;

import com.example.demo8.Model.Question;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class QuestionRowMapper implements RowMapper<Question> {
    @Override
    public Question mapRow(ResultSet rs, int rowNum) throws SQLException {
        Question securityQuestion = new Question();
        securityQuestion.setId(rs.getInt("securityQuestionId"));
        securityQuestion.setQuestion(rs.getString("question"));
        securityQuestion.setLastAccessUser(rs.getString("lastAccessUser"));
        securityQuestion.setLastUpdateTime(rs.getTime("lastUpdateTime"));
        return securityQuestion;
    }
}

