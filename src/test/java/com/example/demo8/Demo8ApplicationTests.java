package com.example.demo8;

import com.example.demo8.Model.Leave;
import com.example.demo8.Model.Question;
import com.example.demo8.Model.User;
import com.example.demo8.Repository.LeaveRepository;
import com.example.demo8.Repository.SecurityQuestionRepository;
import com.example.demo8.Repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalTime;
import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Demo8ApplicationTests {
/*
    @Autowired
    private SecurityQuestionRepository securityQuestionRepository;

    @Test
    public void findAllQuestions() {
        List<Question> questions = securityQuestionRepository.findAllQuestions();
        assertNotNull(questions);
        assertTrue(!questions.isEmpty());
    }*/

  /*  @Test
    public void findQuestionById() {
        Question questions = securityQuestionRepository.findQuestionById(1);
        assertNotNull(questions);

    }*/

   /* @Test
    public void  insertQuestions() {
        Question question = new Question(4, "favourite movie", "admin2", Time.valueOf(LocalTime.now()));
        Question newQuestion = securityQuestionRepository.insert(question);
        //assertNotNull(newQuestion);

    }*/

   /* @Test
    public void getQuestionId() {
        int questionId = securityQuestionRepository.getQuestionId("sport");
        System.out.println(questionId);
    }

    @Test
    public void updateQuestion() {
        Question question = new Question(4, "favourite actor", "admin2", Time.valueOf(LocalTime.now()));
        securityQuestionRepository.updateSecurityQuestion(question);
    }

    @Test
    public void deleteQuestion() {
        Question question = new Question(4, "favourite movie", "admin2", Time.valueOf(LocalTime.now()));
        Question securityQuestionDeleted = securityQuestionRepository.delete(question);
    }*/

  /* @Autowired
    private UserRepository userRepository;

   @Test
   public void createUser() {
       User user = new User("Reetika Ghag", "ghagreetika@gmail.com", 123456, "USER", 1, "donkey", 4, "admin2", Time.valueOf(LocalTime.now()));

       userRepository.create(user);
   }
*/
 /* @Autowired
    private LeaveRepository leaveRepository;

  @Test
    public void searchLeave()
  {
      Leave leave = new Leave(1,-1, Date.valueOf("2019-06-30"), Date.valueOf("2019-07-05"),"college","approved"," ",Time.valueOf(LocalTime.now()));
      List<Leave> leaves = leaveRepository.searchLeave(leave, null, null);
        System.out.println(leaves);
  }*/


}
