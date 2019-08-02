package com.example.demo8.Controller;

import com.example.demo8.Model.Question;
import com.example.demo8.Repository.SecurityQuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


import static org.hibernate.validator.internal.util.Contracts.assertNotNull;

@Service("securityQuestionService")
public class SecurityQuestionService {

    private SecurityQuestionRepository securityQuestionRepository;

    @Autowired
    public SecurityQuestionService(SecurityQuestionRepository securityQuestionRepository) {
        this.securityQuestionRepository = securityQuestionRepository;
    }

    public int getQuestionId(String question) {
        return securityQuestionRepository.getQuestionId(question);
    }

    public List<Question> getAllQuestions() {
        List<Question> questions = securityQuestionRepository.findAllQuestions();
        assertNotNull(questions);
        return questions;
    }

    public Question getQuestionById(int questionId) {
        return securityQuestionRepository.findQuestionById(questionId);
    }

    public Question addNewQuestion(final Question securityQuestion) {
        return securityQuestionRepository.insert(securityQuestion);
    }

    public void updateQuestion(final Question securityQuestion) {
        securityQuestionRepository.updateSecurityQuestion(securityQuestion);
    }

    public Question deleteQuestion(final Question securityQuestion) {
        return securityQuestionRepository.delete(securityQuestion);
    }

}
