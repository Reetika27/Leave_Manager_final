package com.example.demo8.Controller;


import com.example.demo8.Model.Leave;
import com.example.demo8.Model.LeaveEntitlement;
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
public class LeaveEntitlementController {

    @Autowired
    private LeaveEntitlementService leaveEntitlementService;

    @RequestMapping("/getAllDesignations")
    private ModelAndView showAllLeaveEntitlements() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("designationDetails");
        List<LeaveEntitlement> designationList = leaveEntitlementService.getAllLeaveEntitlements();
        modelAndView.addObject("designationList", designationList);
        return modelAndView;
    }

    @GetMapping("/searchDesignationsForm")
    public String searchLeaveEntitlement() {
        return "searchLeaveEntitlement";
    }

    @PostMapping("/searchLeaveEntitlement")
    public ModelAndView modelAndView(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("designationDetails");
        List<LeaveEntitlement> leaveEntitlements = new ArrayList<>();
        int designationId = 0;
        String id = request.getParameter("designationId");
        if (id != null && !(id.equals("")) || id.equals("0")) {
            if (!id.equals("0"))
                designationId = Integer.parseInt(id);
            LeaveEntitlement leaveEntitlement = leaveEntitlementService.getLeaveEntitlementById(designationId);
            if (leaveEntitlement == null) {
                modelAndView.setViewName("index");
                return modelAndView;
            }
            leaveEntitlements.add(leaveEntitlement);
        } else
            leaveEntitlements = leaveEntitlementService.getAllLeaveEntitlements();
        modelAndView.addObject("designationList", leaveEntitlements);
        return modelAndView;
    }


    @GetMapping("/addLeaveEntitlementForm")
    public String addLeaveEntitlement() {
        return "addLeaveEntitlement";
    }

    @GetMapping("/updateLeaveEntitlementForm")
    public String updateLeaveEntitlement() {
        return "updateLeaveEntitlement";
    }

    @GetMapping("/deleteLeaveEntitlementForm")
    public String deleteLeaveEntitlement() {
        return "deleteLeaveEntitlement";
    }


    @PostMapping("/addLeaveEntitlement")
    public ModelAndView addLeaveEntitlement(@ModelAttribute LeaveEntitlement leaveEntitlement, HttpSession session) {
        leaveEntitlement.setLastUpdateTime(Time.valueOf(LocalTime.now()));
        leaveEntitlement.setLastUpdateUser((String) session.getAttribute("admin"));
        leaveEntitlementService.addNewLeaveEntitlement(leaveEntitlement);
        return showAllLeaveEntitlements();
    }

    @PostMapping("/updateLeaveEntitlement")
    public ModelAndView updateLeaveEntitlement(@ModelAttribute LeaveEntitlement leaveEntitlement, HttpSession session, HttpServletRequest request) {
        String designationName[] = request.getParameterValues("designationNames");
        if (designationName != null && !designationName[0].isEmpty())
            leaveEntitlement.setDesignation(designationName[0]);
        leaveEntitlement.setLastUpdateTime(Time.valueOf(LocalTime.now()));
        leaveEntitlement.setLastUpdateUser((String) session.getAttribute("admin"));
        leaveEntitlementService.updateLeaveEntitlement(leaveEntitlement);
        return showAllLeaveEntitlements();
    }

    @PostMapping("/deleteLeaveEntitlement")
    public ModelAndView deleteLeaveEntitlement(@ModelAttribute LeaveEntitlement leaveEntitlement, HttpServletRequest request) {
        String designationName[] = request.getParameterValues("designationNames");
        if (designationName != null && !designationName[0].isEmpty())
            leaveEntitlement.setDesignation(designationName[0]);
        leaveEntitlement.setLastUpdateTime(Time.valueOf(LocalTime.now()));
        leaveEntitlementService.deleteLeaveEntitlement(leaveEntitlement);
        return showAllLeaveEntitlements();
    }

    @ModelAttribute("designationList")
    public List<LeaveEntitlement> designations() {
        List<LeaveEntitlement> designationList = leaveEntitlementService.getAllLeaveEntitlements();
        return designationList;
    }

}
