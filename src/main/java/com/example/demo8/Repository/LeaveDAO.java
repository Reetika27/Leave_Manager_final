package com.example.demo8.Repository;

import com.example.demo8.Model.Leave;
import com.example.demo8.Model.Report;

import java.sql.Date;
import java.util.List;

//THE DAO Handles all leave repository supported functions
public interface LeaveDAO {

    //Finds all leaves taken by all employees
    List<Leave> findAllLeaves();

    //Add a new leave to the database
    Leave insert(final Leave leave);

    //Updates existing leave to support modifications
    void update(int userId, final Leave leave);

    //Deletes a future or present leave
    Leave delete(Leave leave);

    //Generates report of existing leaves in teh current month
    List<Report> getTotalLeavesPerMonth();

    //Gives total number of leaves per user
    int getTotalNumberOfLeaves(int userId);

    //Optimized  query for searching any leaves according to leave id, userId, start date and end dates with upper bounds
    //and last update user
    List<Leave> searchLeave(Leave leave, Date startDateParam2, Date endDateParam2);

    List<Report> getPreviousMonthReports(Date startDate, Date endDate);

}
