package com.example.demo8.Model;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LeaveRowMapper implements RowMapper<Leave> {
    @Override
    public Leave mapRow(ResultSet rs, int rowNum) throws SQLException {
        Leave leave = new Leave();
        leave.setLeaveId(rs.getInt("leaveId"));
        leave.setUserId(rs.getInt("userId"));
        leave.setStartDate(rs.getDate("startDate"));
        leave.setEndDate(rs.getDate("endDate"));
        leave.setReason(rs.getString("reason"));
        leave.setStatus(rs.getString("status"));
        leave.setLastUpdateUser(rs.getString("lastAccessUser"));
        leave.setLastUpdateTime(rs.getTime("lastUpdateTime"));
        return leave;
    }
}
