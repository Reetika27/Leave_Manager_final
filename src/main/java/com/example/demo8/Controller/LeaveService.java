package com.example.demo8.Controller;


import com.example.demo8.Model.Leave;
import com.example.demo8.Model.Report;
import com.example.demo8.Repository.LeaveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.sql.Date;
import java.util.List;

@Service("LeaveService")
@EnableTransactionManagement(proxyTargetClass = true)
public class LeaveService {

    private LeaveRepository leaveRepository;

    @Autowired
    public LeaveService(LeaveRepository leaveRepository) {
        this.leaveRepository = leaveRepository;
    }

    public List<Leave> getAllLeaves() {
        return leaveRepository.findAllLeaves();
    }

    public Leave getLeaveById(int id) {
        return leaveRepository.findLeaveById(id);
    }


    public Leave addNewLeave(final Leave leave) {
        Leave leave1 = leaveRepository.insert(leave);
        return leave1;
    }

    public Leave updateLeave(int userId, final Leave leave) {
        leaveRepository.update(userId, leave);
        return leave;
    }

    public Leave deleteLeave(final Leave leave) {
        Leave leave1 = leaveRepository.delete(leave);
        return leave1;
    }

    public List<Report> getTotalLeavesPerMonth() {
        return leaveRepository.getTotalLeavesPerMonth();
    }

    public int getTotalNumberOfLeaves(int userId) {
        return leaveRepository.getTotalNumberOfLeaves(userId);
    }

    public List<Leave> searchLeave(final Leave leave, Date upperboundStartDate, Date upperboundEndDate) {
        return leaveRepository.searchLeave(leave, upperboundStartDate, upperboundEndDate);
    }

    public List<Report> getPreviousMonthReports(Date startDate, Date endDate) {
        return leaveRepository.getPreviousMonthReports(startDate,endDate);
    }
}
