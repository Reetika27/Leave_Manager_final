package com.example.demo8.Repository;


import com.example.demo8.Model.Leave;
import com.example.demo8.Model.LeaveRowMapper;
import com.example.demo8.Model.Report;
import com.example.demo8.Model.ReportRowMapper;
import org.joda.time.Days;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

@Repository
public class LeaveRepository implements LeaveDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Transactional
    public List<Leave> findAllLeaves() {
        return jdbcTemplate.query("SELECT * FROM leave", new LeaveRowMapper());
    }

    @Transactional
    public Leave findLeaveById(int leaveId) {
        try {
            return jdbcTemplate.queryForObject("select * from leave where leaveId=?;", new Object[]{leaveId}, new LeaveRowMapper());
        } catch (EmptyResultDataAccessException e) {
            System.out.println("No such record exists");
            return new Leave();
        }

    }

    @Transactional
    private int getLeaveId(int userId, Date startDate, Date endDate) {
        int leaveId = 0;
        try {
            leaveId = jdbcTemplate.queryForObject("SELECT leaveId from leave WHERE userId=? AND startDate =? AND endDate =?;", new Object[]{userId, startDate, endDate}, Integer.class);
        } catch (EmptyResultDataAccessException e) {
            System.out.println("No such leave exists");
            return -1;
        }
        return leaveId;
    }


    @Transactional
    private int generateId() {
        String sqlId = " SELECT nextVal('leave_pk');";
        int id = jdbcTemplate.queryForObject(sqlId, Integer.class);
        return id;
    }


    public Leave insert(final Leave leave) {
        try {
            if (leave != null) {
                final String sql = "INSERT INTO leave(leaveId, userId, startDate, endDate, reason, status, lastAccessUser, lastUpdateTime) VALUES (?,?,?,?,?,?,?,?);";
                int id = generateId();
                leave.setLeaveId(id);
                if (!(leave.getStartDate().after(leave.getEndDate()))) {
                    jdbcTemplate.update(new PreparedStatementCreator() {
                        @Override
                        public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                            PreparedStatement preparedStatement = con.prepareStatement(sql);

                            preparedStatement.setInt(1, id);
                            preparedStatement.setInt(2, leave.getUserId());
                            preparedStatement.setDate(3, leave.getStartDate());
                            preparedStatement.setDate(4, leave.getEndDate());
                            preparedStatement.setString(5, leave.getReason());
                            preparedStatement.setString(6, leave.getStatus());
                            preparedStatement.setString(7, leave.getLastUpdateUser());
                            preparedStatement.setTime(8, leave.getLastUpdateTime());
                            return preparedStatement;
                        }

                    });
                } else {
                    System.out.println("Date constraints fail.");
                }
                return leave;
            } else {
                System.out.println("Null object cannot be inserted");
                return null;
            }

        } catch (Exception e) {
            System.out.println("Leave for the user already exists for the following dates");
            return leave;
        }

    }

    public void update(int userId, final Leave leave) {
        try {
            if (leave != null) {
                int leaveId = leave.getLeaveId();
                if (leaveId != -1) {
                    final String sql = "UPDATE leave SET startDate= ?, endDate = ?, status= ?, lastAccessUser=?, lastUpdateTime=?  WHERE leaveId = ? AND userId = ?;";
                    jdbcTemplate.update(new PreparedStatementCreator() {
                        @Override
                        public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                            PreparedStatement preparedStatement = con.prepareStatement(sql);
                            if (!leave.getStartDate().before(Date.valueOf(LocalDate.now())) && !leave.getEndDate().before(Date.valueOf(LocalDate.now())) && !(leave.getEndDate().before(leave.getStartDate()))) {
                                if (leaveId == leave.getLeaveId() && userId == leave.getUserId()) {
                                    preparedStatement.setDate(1, leave.getStartDate());
                                    preparedStatement.setDate(2, leave.getEndDate());
                                    preparedStatement.setString(3, leave.getStatus());
                                    preparedStatement.setString(4, leave.getLastUpdateUser());
                                    preparedStatement.setTime(5, leave.getLastUpdateTime());
                                    preparedStatement.setInt(6, leaveId);
                                    preparedStatement.setInt(7, userId);
                                } else {
                                    System.out.println("User credentials are invalid");
                                }
                            } else {
                                System.out.println("Date constraints failed");
                            }
                            return preparedStatement;
                        }
                    });
                    System.out.println("updated successfully");
                }
            }
        } catch (Exception e) {
            System.out.println("Cannot update the required leave");
        }

    }

    public Leave delete(Leave leave) {
        int leaveId = getLeaveId(leave.getUserId(), leave.getStartDate(), leave.getEndDate());
        if (leaveId == -1) {
            System.out.println("No such leave to delete");
            return leave;
        }
        Leave requestedLeave = findLeaveById(leaveId);
        String sql = "DELETE FROM leave WHERE leaveId =?";
        if (!requestedLeave.getStartDate().before(Date.valueOf(LocalDate.now())) && !requestedLeave.getEndDate().before(Date.valueOf(LocalDate.now()))) {
            jdbcTemplate.update(new PreparedStatementCreator() {
                @Override
                public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                    PreparedStatement preparedStatement = con.prepareStatement(sql);
                    preparedStatement.setInt(1, leaveId);
                    return preparedStatement;
                }
            });
        } else {
            System.out.println("Constraints failed. Wrong dates specified");
        }

        System.out.println("Deleted leave successfully");
        return requestedLeave;
    }


    @Transactional
    public List<Report> getTotalLeavesPerMonth() {

        int year = Calendar.getInstance().get(Calendar.YEAR);
        int month = Calendar.getInstance().get(Calendar.MONTH);
        org.joda.time.LocalDate lastDayOfMonth = new org.joda.time.LocalDate(year, month + 1, 1).dayOfMonth().withMaximumValue();
        org.joda.time.LocalDate firstDayOfMonth = new org.joda.time.LocalDate(year, month + 1, 30).dayOfMonth().withMinimumValue();
        String lastDayOfMonthS = lastDayOfMonth.toString("yyyy/MM/dd");
        String firstDayOfMonthS = firstDayOfMonth.toString("yyyy/MM/dd");
        List<Report> report;

        System.out.println("Start date: " + firstDayOfMonthS + " end Date: " + lastDayOfMonthS);

        String sql = "SELECT userId,startDate,endDate FROM leave WHERE startDate BETWEEN '" + firstDayOfMonthS + "' AND '" + lastDayOfMonthS + "' OR endDate BETWEEN '" + firstDayOfMonth + "' AND '" + lastDayOfMonthS + "' ORDER BY userId,startDate;";
        try {
            report = jdbcTemplate.query(sql, new ReportRowMapper());

            Iterator<Report> iterator = report.iterator();
            int i = 0;
            while (iterator.hasNext()) {
                String name = jdbcTemplate.queryForObject("Select userName from users where userId=?", new Object[]{iterator.next().getUserId()}, String.class);
                report.get(i).setUserName(name);
                i++;
            }
            int j = 0;
            Date start = null;
            Date end = null;
            while (j < report.size()) {
                if (report.get(j).getStartDate().before(firstDayOfMonth.toDate())) {
                    start = new Date(firstDayOfMonth.toDate().getTime());
                }
                if (!report.get(j).getStartDate().before(firstDayOfMonth.toDate()))
                    start = report.get(j).getStartDate();
                if (report.get(j).getEndDate().after(lastDayOfMonth.toDate())) {
                    end = new Date(lastDayOfMonth.toDate().getTime());
                }
                if (!report.get(j).getEndDate().after(lastDayOfMonth.toDate()))
                    end = report.get(j).getEndDate();

                int numberOfDays = Days.daysBetween(new org.joda.time.LocalDate(start), new org.joda.time.LocalDate(end)).getDays();
                report.get(j).setNumberOfLeavesPerMonth(numberOfDays + 1);
                j++;
            }
        } catch (EmptyResultDataAccessException e) {
            System.out.println("No leave records found");
            return null;
        }
        return report;

    }

    @Transactional
    public List<Report> getPreviousMonthReports(Date startDate, Date endDate) {
        org.joda.time.LocalDate firstDayOfMonth = new org.joda.time.LocalDate(startDate).dayOfMonth().withMinimumValue();
        org.joda.time.LocalDate lastDayOfMonth = new org.joda.time.LocalDate(startDate).dayOfMonth().withMaximumValue();
        String lastDayOfMonthS = lastDayOfMonth.toString("yyyy/MM/dd");
        String firstDayOfMonthS = firstDayOfMonth.toString("yyyy/MM/dd");
        String sql = "SELECT userId,startDate,endDate FROM leave WHERE startDate BETWEEN '" + firstDayOfMonthS + "' AND '" + lastDayOfMonthS + "' OR endDate BETWEEN '" + firstDayOfMonthS + "' AND '" + lastDayOfMonthS + "' ORDER BY userId,startDate";
        List<Report> report = new ArrayList<>();
        try {
            report = jdbcTemplate.query(sql, new ReportRowMapper());
            Iterator<Report> iterator = report.iterator();
            int i = 0;
            while (iterator.hasNext()) {
                String name = jdbcTemplate.queryForObject("Select userName from users where userId=?", new Object[]{iterator.next().getUserId()}, String.class);
                report.get(i).setUserName(name);
                i++;
            }

            int j = 0;
            Date start = null;
            Date end = null;

            while (j < report.size()) {
                if (report.get(j).getStartDate().before(firstDayOfMonth.toDate())) {
                    start = new Date(firstDayOfMonth.toDate().getTime());
                }
                if (!report.get(j).getStartDate().before(firstDayOfMonth.toDate()))
                    start = report.get(j).getStartDate();
                if (report.get(j).getEndDate().after(lastDayOfMonth.toDate())) {
                    end = new Date(lastDayOfMonth.toDate().getTime());
                }
                if (!report.get(j).getEndDate().after(lastDayOfMonth.toDate()))
                    end = report.get(j).getEndDate();

                int numberOfDays = Days.daysBetween(new org.joda.time.LocalDate(start), new org.joda.time.LocalDate(end)).getDays();
                report.get(j).setNumberOfLeavesPerMonth(numberOfDays + 1);
                j++;

            }

        } catch (EmptyResultDataAccessException e) {
            System.out.println("No leave records found");
            return null;
        }

        return report;

    }


    @Transactional
    public int getTotalNumberOfLeaves(int userId) {

        int totalNumberOfLeaves = 0;
        String sql = "SELECT SUM(endDate-startDate) FROM leave WHERE userId=? GROUP BY userId;";
        try {
            totalNumberOfLeaves = jdbcTemplate.queryForObject(sql, new Object[]{userId}, Integer.class);
        } catch (EmptyResultDataAccessException e) {
            System.out.println("No leave records for this user found");
            return 0;
        }
        return totalNumberOfLeaves;

    }

    @Transactional
    public List<Leave> searchLeave(Leave leave, Date startDateParam2, Date endDateParam2) {
        if (leave != null) {
            String selectQuery = "SELECT * FROM leave";
            String whereClause = "WHERE";
            String and = "AND ";
            String userIdClause = "userId=";
            String startDateClause = "startDate BETWEEN '";
            String endDateClause = "endDate BETWEEN '";
            String lastUpdateUserClause = "lastAccessUser= '";
            String condition = "";
            String termination = ";";

            /* System.out.println(userIdClause + use);*/
            Integer userId = leave.getUserId();
            if (userId != null && !("".equals(userId)) && userId > 0)
                condition += userIdClause + userId.toString();
            System.out.println(condition);

            if (leave.getStartDate() != null && !("".equals(leave.getStartDate().toString())) && startDateParam2 != null && !(startDateParam2.before(leave.getStartDate()))) {
                if (!(condition.isEmpty())) {
                    condition += " " + and + startDateClause + leave.getStartDate().toString() + "' " + and + " '" + startDateParam2.toString() + "'";
                    System.out.println(condition);
                } else {
                    condition += startDateClause + leave.getStartDate().toString() + "' " + and + " '" + startDateParam2.toString() + "'";
                    System.out.println(condition);
                }

            } else {
                System.out.println("Date constraints failed");
            }

            if (leave.getEndDate() != null && !("".equals(leave.getEndDate())) && endDateParam2 != null && !(endDateParam2.before(leave.getEndDate()))) {
                if (!condition.isEmpty()) {
                    condition += " " + and + endDateClause + leave.getEndDate() + "' " + and + " '" + endDateParam2.toString() + "'";
                    System.out.println(condition);
                } else {
                    condition += endDateClause + leave.getEndDate().toString() + "' " + and + " '" + endDateParam2.toString() + "'";
                    System.out.println(condition);
                }

            } else {
                System.out.println("Date constraints failed");
            }

            if (leave.getLastUpdateUser() != null && !(leave.getLastUpdateUser().equals(""))) {
                if (!condition.isEmpty()) {
                    condition += " " + and + lastUpdateUserClause + leave.getLastUpdateUser() + "'";
                    System.out.println(condition);
                } else {
                    condition += lastUpdateUserClause + leave.getLastUpdateUser() + "'";
                    System.out.println(condition);
                }
            }
            if (!condition.isEmpty())
                selectQuery += " " + whereClause + " " + condition;
            else
                selectQuery += condition;
            selectQuery += termination;
            System.out.println(selectQuery);

            return jdbcTemplate.query(selectQuery, new LeaveRowMapper());
        } else {
            System.out.println("Leave object passed as argument cannot be null");
            return null;
        }
    }

    //Split the sql string at new lines. Remove that part where the parameter passed is null.
    //It is like creating a dynamic sql query where the search parameters differ at runtime.


}
