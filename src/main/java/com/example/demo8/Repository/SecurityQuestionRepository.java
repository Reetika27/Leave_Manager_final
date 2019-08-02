package com.example.demo8.Repository;

import com.example.demo8.Model.Question;
import com.example.demo8.Model.QuestionRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalTime;
import java.util.List;

@Repository
public class SecurityQuestionRepository implements SecurityQuestioDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Transactional
    public List<Question> findAllQuestions() {
        return jdbcTemplate.query("SELECT * FROM securityquestion", new QuestionRowMapper());
    }

    @Transactional
    public Question findQuestionById(int questionId) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM securityquestion WHERE securityQuestionId = ?", new Object[]{questionId}, new QuestionRowMapper());
        } catch (EmptyResultDataAccessException e) {
            System.out.println("No such question exists");
            return new Question();
        }

    }

    @Transactional
    public int getQuestionId(String question) {
        try {
            String sql = "SELECT securityQuestionId from securityquestion WHERE question=?;";
            int questionId;
            questionId = jdbcTemplate.queryForObject(sql, new Object[]{question}, Integer.class);
            return questionId;
        } catch (EmptyResultDataAccessException e) {
            System.out.println("No such question exists");
            return -1;
        }

    }

    @Transactional
    private int generateId() {
        String sqlId = " SELECT nextVal('question_pk');";
        int id = jdbcTemplate.queryForObject(sqlId, Integer.class);
        return id;
    }


    public Question insert(final Question securityQuestion) {
        try {
            final String sql = "insert into securityquestion(securityQuestionId, question, lastAccessUser, lastUpdateTime) values (?,?,?,?)";
            int id = generateId();
            jdbcTemplate.update(new PreparedStatementCreator() {
                @Override
                public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                    PreparedStatement preparedStatement = con.prepareStatement(sql);
                    preparedStatement.setInt(1, id);
                    preparedStatement.setString(2, securityQuestion.getQuestion());
                    preparedStatement.setString(3, securityQuestion.getLastAccessUser());
                    preparedStatement.setTime(4, securityQuestion.getLastUpdateTime());
                    return preparedStatement;
                }
            });
            securityQuestion.setId(id);
            System.out.println("Successfully added question!");
            return securityQuestion;
        } catch (Exception e) {
            System.out.println("Question already exists");
            return null;
        }
    }

    public void updateSecurityQuestion(Question securityQuestion) {
        // int securityQuestionId = securityQuestion.getId();
        if (securityQuestion.getId() != -1) {
            String sql = "UPDATE securityquestion SET question=?, lastAccessUser=? , lastUpdateTime = ? WHERE securityQuestionId = ?";
            jdbcTemplate.update(new PreparedStatementCreator() {
                @Override
                public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                    PreparedStatement preparedStatement = con.prepareStatement(sql);

                    preparedStatement.setString(1, securityQuestion.getQuestion());
                    preparedStatement.setString(2, securityQuestion.getLastAccessUser());
                    preparedStatement.setTime(3, Time.valueOf(LocalTime.now()));
                    preparedStatement.setInt(4, securityQuestion.getId());


                    return preparedStatement;
                }
            });
            System.out.println("Updated successfully");
        } else {
            System.out.println("security question cannot be updated");
        }
    }

    public Question delete(Question securityQuestion) {
        int questionId = getQuestionId(securityQuestion.getQuestion());
        if (questionId != -1) {
            String sql = "DELETE FROM securityquestion WHERE securityQuestionId =?;";
            jdbcTemplate.update(new PreparedStatementCreator() {
                @Override
                public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                    PreparedStatement preparedStatement = con.prepareStatement(sql);
                    preparedStatement.setInt(1, securityQuestion.getId());
                    return preparedStatement;
                }
            });
            return securityQuestion;
        } else {
            System.out.println("No question to delete");
            return securityQuestion;
        }

    }


}
