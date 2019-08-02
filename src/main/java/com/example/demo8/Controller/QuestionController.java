package com.example.demo8.Controller;

import com.example.demo8.Model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Controller
public class QuestionController {

    @Autowired
    private SecurityQuestionService securityQuestionService;

    @RequestMapping("/index")
    public String home() {
        return "index";
    }

    @RequestMapping("/getAllQuestions")
    private ModelAndView displayAllQuestions() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("questionDetails");
        List<Question> questions = securityQuestionService.getAllQuestions();
        mav.addObject("questions", questions);
        return mav;
    }

    @GetMapping("/searchQuestionsForm")
    public String searchQuestions() {
        return "searchQuestion";
    }

    @PostMapping("/searchQuestion")
    public ModelAndView searchQuestion(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("questionDetails");
        List<Question> questions = new ArrayList<>();
        String id = request.getParameter("questionId");
        if (id != null && id != "") {
            int questionId = Integer.valueOf(id);
            Question question = securityQuestionService.getQuestionById(questionId);
            if (question.getId() == 0 && question.getQuestion() == null && question.getLastAccessUser() == null && question.getLastUpdateTime() == null) {
                modelAndView.setViewName("index");
                return modelAndView;
            }
            questions.add(question);
        } else {
            questions = securityQuestionService.getAllQuestions();
        }
        modelAndView.addObject("questions", questions);
        return modelAndView;
    }

    @GetMapping("/addQuestionForm")
    public String addQuestion() {
        return "addQuestion";
    }

    @PostMapping(value = "/addQuestion")
    public ModelAndView addQuestion(@ModelAttribute Question question, HttpSession session) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("questionDetails");
        question.setLastUpdateTime(Time.valueOf(LocalTime.now()));
        question.setLastAccessUser((String) session.getAttribute("admin"));
        securityQuestionService.addNewQuestion(question);
        return displayAllQuestions();
    }

    @GetMapping("/updateQuestionForm")
    public String updateQuestion() {
        return "updateQuestion";
    }

    @PostMapping(value = "/updateQuestion")
    public ModelAndView updateQuestion(@ModelAttribute Question question, HttpServletRequest request) {
        question.setLastUpdateTime(Time.valueOf(LocalTime.now()));
        HttpSession session = request.getSession(false);
        String name = (String) session.getAttribute("admin");
        question.setLastAccessUser(name);
        securityQuestionService.updateQuestion(question);
        return displayAllQuestions();
    }

    @GetMapping("/deleteQuestionForm")
    public String deleteQuestion() {
        return "deleteQuestion";
    }


    @RequestMapping(value = "/deleteQuestion", method = RequestMethod.POST)
    public ModelAndView deleteQuestion(@ModelAttribute Question question, HttpServletRequest request) {
        String id = request.getParameter("questionId");
        question.setId(Integer.parseInt(id));
        question.setLastUpdateTime(Time.valueOf(LocalTime.now()));
        securityQuestionService.deleteQuestion(question);
        return displayAllQuestions();
    }


}


