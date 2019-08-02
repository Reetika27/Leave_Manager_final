package com.example.demo8.Repository;

import com.example.demo8.Model.Question;

import java.util.List;

//Supports all operations in security question repository and affects the table securityquestion
public interface SecurityQuestioDAO {

    //Shows a list of all questions
    List<Question> findAllQuestions();

    //Used to find a specific question given an id
    Question findQuestionById(int questionId);

    //A helper function to find question id given a question
    int getQuestionId(String question);

    //Adds a new security question to the database
    Question insert(Question securityQuestion);

    //Updates an existing question in the database
    void updateSecurityQuestion(Question securityQuestion);

    //Used to delete a question from the database
    Question delete(Question securityQuestion);
}
