package com.example.demo8.Controller;

import com.example.demo8.Model.LeaveEntitlement;
import com.example.demo8.Model.Question;
import com.example.demo8.Model.User;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.MessageDigest;
import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private LeaveEntitlementService leaveEntitlementService;

    @Autowired
    private SecurityQuestionService securityQuestionService;

    @RequestMapping("/")
    public String login() {
        return "login";
    }

    @PostMapping("/login")
    public String login(HttpServletRequest request, HttpSession session) {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String generatedPassword = null;
        if (email == null && password == null) {
            return "addUser";
        }
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(password.getBytes());
            byte[] bytes = md.digest();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        int userId = userService.getUserId(email, generatedPassword);
        if (userId != -1) {
            User user = userService.getUserById(userId);
            session.setAttribute("admin", user.getUserName());
            return "index";
        } else
            return "login";
    }

    @RequestMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "login";
    }


    @RequestMapping("/getAllUsers")
    private ModelAndView getAllUsers() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("userDetails");
        List<User> users = userService.findAllUsers();
        modelAndView.addObject("users", users);
        return modelAndView;
    }

    @GetMapping("/searchUsersForm")
    public String searchUser() {
        return "searchUser";
    }

    @PostMapping("/searchUser")
    public ModelAndView modelAndView(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("userDetails");
        List<User> users = new ArrayList<>();
        String id = request.getParameter("userId");
        if (id != null && !(id.equals(""))) {
            int userId = Integer.parseInt(id);
            if (userId > 0 && userId != 0) {
                User user = userService.getUserById(userId);
                if (user == null) {
                    modelAndView.setViewName("index");
                    return modelAndView;
                }
                users.add(user);
            }
            else
            {
                modelAndView.setViewName("index");
            }
        } else
            users = userService.findAllUsers();
        modelAndView.addObject("users", users);
        return modelAndView;
    }


    @GetMapping("/addUserForm")
    public String addUser() {
        return "addUser";
    }

    @RequestMapping("/updateUserForm")
    public String updateUser(HttpServletRequest request) {
        int userId = Integer.parseInt(request.getParameter("userId"));
        return "updateUser";
    }

    @GetMapping("/deleteUserForm")
    public String deleteUser() {
        return "deleteUser";
    }

    @GetMapping("/findUserByIdForm")
    public String findUserById() {
        return "findUserById";
    }

    @PostMapping("/addUser")
    public ModelAndView addUser(@ModelAttribute User user, HttpSession session, HttpServletRequest request) {
        String designation[] = request.getParameterValues("designation");
        String question[] = request.getParameterValues("securityQuestion");
        if (designation != null && !designation[0].isEmpty()) {
            int designationId = leaveEntitlementService.getDesignationId(designation[0]);
            if (designationId != -1)
                user.setDesignationId(designationId);
        }
        if (question[0] != null && !question[0].isEmpty()) {
            int questionId = securityQuestionService.getQuestionId(question[0]);
            if (questionId != -1)
                user.setSecurityQuestionId(questionId);
        }
        user.setLastUpdateTime(Time.valueOf(LocalTime.now()));
        user.setLastUpdateUser((String) session.getAttribute("admin"));
        userService.addUser(user);
        return getAllUsers();
    }

    @PostMapping("/updateUser")
    public ModelAndView updateUser(@ModelAttribute User user, HttpServletRequest request, HttpSession session) {
        String designation[] = request.getParameterValues("designation");
        String question[] = request.getParameterValues("securityQuestion");
        if (designation != null && !designation[0].isEmpty()) {
            int designationId = leaveEntitlementService.getDesignationId(designation[0]);
            if (designationId != -1)
                user.setDesignationId(designationId);
        }
        if (question != null && !question[0].isEmpty()) {
            int questionId = securityQuestionService.getQuestionId(question[0]);
            if (questionId != -1)
                user.setSecurityQuestionId(questionId);
        }
        user.setLastUpdateUser((String) session.getAttribute("admin"));
        user.setLastUpdateTime(Time.valueOf(LocalTime.now()));
        userService.updateUser(user);
        return getAllUsers();
    }

    @PostMapping("/deleteUser")
    public ModelAndView deleteUser(@ModelAttribute User user, HttpServletRequest request) {
        String designation[] = request.getParameterValues("designation");
        String question[] = request.getParameterValues("securityQuestion");
        if (designation != null && !designation[0].isEmpty()) {
            int designationId = leaveEntitlementService.getDesignationId(designation[0]);
            if (designationId != -1)
                user.setDesignationId(designationId);
        }
        if (question != null && !question[0].isEmpty()) {
            int questionId = securityQuestionService.getQuestionId(question[0]);
            if (questionId != -1)
                user.setSecurityQuestionId(questionId);
        }
        user.setLastUpdateTime(Time.valueOf(LocalTime.now()));
        userService.deleteUser(user);
        return getAllUsers();
    }

    @GetMapping("/searchByIdForm")
    public String searchUserById() {
        return "SearchUserById";
    }

    @PostMapping("/searchUserById")
    public String searchUserById(@RequestParam("userId") int userId) {
        User user = userService.getUserById(userId);
        System.out.println(user.getUserName() + " " + user.getUserId());
        return "index";
    }

    @ModelAttribute("questions")
    public List<Question> questions() {
        List<Question> questions = securityQuestionService.getAllQuestions();
        return questions;
    }

    @ModelAttribute("designations")
    public List<LeaveEntitlement> leaveEntitlements() {
        List<LeaveEntitlement> designations = leaveEntitlementService.getAllLeaveEntitlements();
        return designations;
    }
}