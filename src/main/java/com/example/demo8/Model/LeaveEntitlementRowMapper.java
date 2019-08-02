package com.example.demo8.Model;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LeaveEntitlementRowMapper implements RowMapper<LeaveEntitlement> {
    @Override
    public LeaveEntitlement mapRow(ResultSet rs, int rowNum) throws SQLException {
        LeaveEntitlement leaveEntitlement = new LeaveEntitlement();
        leaveEntitlement.setDesignationId(rs.getInt("designationId"));
        leaveEntitlement.setDesignation(rs.getString("designation"));
        leaveEntitlement.setTotalNumberOfPermissibleLeaves(rs.getInt("totalPermissibleLeaves"));
        leaveEntitlement.setLastUpdateUser(rs.getString("lastAccessUser"));
        leaveEntitlement.setLastUpdateTime(rs.getTime("lastUpdateTime"));
        return leaveEntitlement;
    }
}
