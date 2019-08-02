package com.example.demo8.Controller;

import com.example.demo8.Model.Leave;
import com.example.demo8.Model.Report;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalTime;
import java.util.List;
import java.util.ListIterator;

@Controller
public class LeaveController {

    @Autowired
    private LeaveService leaveService;

    //Change report path name as per convenience
    private String reportPathName = "/home/reetika/Documents/";

    @RequestMapping("/showAllLeaves")
    private ModelAndView showAllLeaves() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("leaveDetails");
        List<Leave> leaves = leaveService.getAllLeaves();
        modelAndView.addObject("leaves", leaves);
        return modelAndView;
    }

    @GetMapping("/addLeaveForm")
    public String addLeave() {
        return "addLeave";
    }

    @PostMapping("/addLeave")
    public ModelAndView addLeave(@ModelAttribute Leave leave, HttpSession session, HttpServletRequest request) {
        String status[] = request.getParameterValues("status");
        if (status != null && !status[0].isEmpty()) {
           leave.setStatus(status[0]);
        }
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("leaveDetails");
        leave.setLastUpdateTime(Time.valueOf(LocalTime.now()));
        leave.setLastUpdateUser((String) session.getAttribute("admin"));
        leaveService.addNewLeave(leave);
        return showAllLeaves();
    }

    @GetMapping("/updateLeaveForm")
    public String updateLeave() {
        return "updateLeave";
    }

    @GetMapping("/deleteLeaveForm")
    public String deleteLeave() {
        return "deleteLeave";
    }

    @PostMapping("/updateLeave")
    public ModelAndView updateLeave(@ModelAttribute Leave leave, HttpSession session, HttpServletRequest request) {
        String status[] = request.getParameterValues("status");
        if (status != null && !status[0].isEmpty()) {
            leave.setStatus(status[0]);
        }
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("leaveDetails");
        leave.setLastUpdateTime(Time.valueOf(LocalTime.now()));
        leave.setLastUpdateUser((String) session.getAttribute("admin"));
        leaveService.updateLeave(leave.getUserId(), leave);
        return showAllLeaves();
    }

    @PostMapping("/deleteLeave")
    public ModelAndView deleteLeave(@ModelAttribute Leave leave,HttpServletRequest request) {
        String status[] = request.getParameterValues("status");
        if (status != null && !status[0].isEmpty()) {
            leave.setStatus(status[0]);
        }
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("leaveDetails");
        leave.setLastUpdateTime(Time.valueOf(LocalTime.now()));
        leaveService.deleteLeave(leave);
        return showAllLeaves();
    }

    @GetMapping("/searchLeaveForm")
    public String searchLeave() {
        return "searchLeave";
    }

    @PostMapping("/searchLeave")
    public ModelAndView searchLeave(@ModelAttribute Leave leave, HttpServletRequest request) {
        String status[] = request.getParameterValues("status");
        if (status != null && !status[0].isEmpty()) {
            leave.setStatus(status[0]);
        }
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("leaveDetails");
        String startDate = request.getParameter("startDate1");
        String endDate = request.getParameter("endDate1");
        String leaveId = request.getParameter("leaveId1");
        String userId = request.getParameter("userId1");
        if (userId != null && !userId.equals("")) {
            leave.setUserId(Integer.parseInt(userId));
        } else
            leave.setUserId(-1);
        leave.setLastUpdateTime(Time.valueOf(LocalTime.now()));
        if (leaveId != null && !(leaveId.equals(""))) {
            leave.setLeaveId(Integer.parseInt(leaveId));
            Leave leave1 = leaveService.getLeaveById(leave.getLeaveId());
            if (leave1.getLeaveId() == 0 && leave1.getUserId() == 0) {
                modelAndView.setViewName("index");
                return modelAndView;
            }
            modelAndView.addObject("leaves", leave1);
            return modelAndView;
        } else {
            leave.setLeaveId(-1);
            Date startDateBound, endDateBound, startD, endD;
            String start = request.getParameter("startDateBound");
            String end = request.getParameter("endDateBound");
            if (startDate != null && !(startDate.equals(""))) {
                startD = Date.valueOf(startDate);
            } else {
                startD = null;
            }
            leave.setStartDate(startD);
            if (endDate != null && !(endDate.equals(""))) {
                endD = Date.valueOf(endDate);

            } else {
                endD = null;
            }
            leave.setEndDate(endD);
            if (start != null && !(start.equals(""))) {
                startDateBound = Date.valueOf(start);
            } else {
                startDateBound = null;
            }
            if (end != null && !(end.equals(""))) {
                endDateBound = Date.valueOf(end);
            } else {
                endDateBound = null;
            }

            List<Leave> leaves = leaveService.searchLeave(leave, startDateBound, endDateBound);
            if (leaves != null && !(leaves.isEmpty()))
                modelAndView.addObject("leaves", leaves);
            else {
                System.out.println("Nothing to show");
                modelAndView.setViewName("index");
                modelAndView.addObject("Message: ", "No leaves!");
            }
            return modelAndView;
        }

    }

    @RequestMapping("/report")
    public ModelAndView reportGeneration() throws IOException {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("report");
        List<Report> report = leaveService.getTotalLeavesPerMonth();
        FileWriter fileWriter = new FileWriter(new File(reportPathName + "report.csv"));
        fileWriter.write("userId, userName,startDate,endDate, totalLeaves" + "\n");
        ListIterator<Report> iterator = report.listIterator();
        while (iterator.hasNext()) {
            Report report1 = iterator.next();
            fileWriter.write(report1.toString() + "\n");
        }
        fileWriter.close();
        modelAndView.addObject("report", report);
        return modelAndView;
    }

    @GetMapping("/reportPForm")
    public String previousReports() {
        return "reportPForm";
    }

    @PostMapping("/previousReports")
    public ModelAndView previousReports(HttpServletRequest request) throws IOException {
        ModelAndView modelAndView = new ModelAndView();
        String date1 = request.getParameter("startDate1");
        String date2 = request.getParameter("endDate1");
        if (date1 == null && date1.isEmpty() || date2 == null && date2.isEmpty()) {
            modelAndView.setViewName("index");
        } else {
            modelAndView.setViewName("report");
            List<Report> report = leaveService.getPreviousMonthReports(Date.valueOf(date1), Date.valueOf(date2));
            FileWriter fileWriter = new FileWriter(new File(reportPathName + "prevReport.csv"));
            fileWriter.write("userId, userName,startDate,endDate, totalLeaves" + "\n");
            ListIterator<Report> iterator = report.listIterator();
            while (iterator.hasNext()) {
                Report report1 = iterator.next();
                fileWriter.write(report1.toString() + "\n");
            }
            fileWriter.close();
            modelAndView.addObject("report", report);
        }
        return modelAndView;
    }
}
