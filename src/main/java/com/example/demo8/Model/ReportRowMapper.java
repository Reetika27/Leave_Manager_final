package com.example.demo8.Model;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ReportRowMapper implements RowMapper<Report> {
    @Override
    public Report mapRow(ResultSet rs, int rowNum) throws SQLException {
        Report report = new Report();
        report.setUserId(rs.getInt("userId"));
        report.setStartDate(rs.getDate("startDate"));
        report.setEndDate(rs.getDate("endDate"));
        return report;
    }

}
