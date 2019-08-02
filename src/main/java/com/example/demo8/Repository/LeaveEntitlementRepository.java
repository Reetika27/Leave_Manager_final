package com.example.demo8.Repository;

// Add basic Update Query
//Add keyholders to create query


import com.example.demo8.Model.LeaveEntitlement;
import com.example.demo8.Model.LeaveEntitlementRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Repository
public class LeaveEntitlementRepository implements LeaveEntitlementDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Transactional
    public List<LeaveEntitlement> findAllLeaveEntitlements() {
        return jdbcTemplate.query("select * from leaveinfo", new LeaveEntitlementRowMapper());
    }

  /*  @Transactional
    public LeaveEntitlement findLeaveEntitlementByDesignationId(int designationId) {
        return jdbcTemplate.queryForObject("SELECT * FROM leaveinfo WHERE designationId =? ", new Object[]{designationId}, new LeaveEntitlementRowMapper());
    }*/

    @Transactional
    public int getDesignationId(String designation) {
        String sql = "SELECT designationId from leaveinfo WHERE designation=?;";
        int designationId;
        try {
            designationId = jdbcTemplate.queryForObject(sql, new Object[]{designation}, Integer.class);
        } catch (EmptyResultDataAccessException e) {
            System.out.println("User must be an employee. No specific user designation found");
            return 0;
        }

        return designationId;
    }

    @Transactional
    public LeaveEntitlement findLeaveEntitlementByDesignationId(int designationId) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM leaveinfo WHERE designationId =? ", new Object[]{designationId}, new LeaveEntitlementRowMapper());
        } catch (EmptyResultDataAccessException e) {
            System.out.println("No such designation exists");
            return null;
        }

    }

    @Transactional
    private int generateId() {
        String sqlId = " SELECT nextVal('designation_pk');";
        int id = jdbcTemplate.queryForObject(sqlId, Integer.class);
        return id;
    }


    public LeaveEntitlement create(final LeaveEntitlement leaveEntitlement) {
        try {
            if (leaveEntitlement != null) {
                final String sql = "Insert into leaveinfo( designationId, designation, totalPermissibleLeaves, lastAccessUser, lastUpdateTime) VALUES (?,?,?,?,?); ";
                int id = generateId();
                jdbcTemplate.update(new PreparedStatementCreator() {

                    @Override
                    public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                        PreparedStatement preparedStatement = con.prepareStatement(sql);
                        preparedStatement.setInt(1, id);
                        preparedStatement.setString(2, leaveEntitlement.getDesignation());
                        preparedStatement.setInt(3, leaveEntitlement.getTotalNumberOfPermissibleLeaves());
                        preparedStatement.setString(4, leaveEntitlement.getLastUpdateUser());
                        preparedStatement.setTime(5, leaveEntitlement.getLastUpdateTime());
                        return preparedStatement;
                    }
                });
                leaveEntitlement.setDesignationId(id);
                System.out.println("Inserted successfully");
                return leaveEntitlement;
            } else {
                System.out.println("Invalid insert request made");
                return null;
            }

        } catch (Exception e) {
            System.out.println("Designation already exists");
            return null;
        }

    }

    public void update(final LeaveEntitlement leaveEntitlement) {
        int designationId = getDesignationId(leaveEntitlement.getDesignation());
        if (designationId != -1) {
            String sql = "UPDATE leaveinfo SET totalPermissibleLeaves=?, lastAccessUser=?, lastUpdateTime=? WHERE designationId=?; ";
            jdbcTemplate.update(new PreparedStatementCreator() {
                @Override
                public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                    PreparedStatement preparedStatement = con.prepareStatement(sql);
                    preparedStatement.setInt(1, leaveEntitlement.getTotalNumberOfPermissibleLeaves());
                    preparedStatement.setString(2, leaveEntitlement.getLastUpdateUser());
                    preparedStatement.setTime(3, leaveEntitlement.getLastUpdateTime());
                    preparedStatement.setInt(4, designationId);
                    return preparedStatement;
                }
            });
            System.out.println("Updated successfully");

        } else {
            System.out.println("No records to update");
        }

    }

    public LeaveEntitlement delete(LeaveEntitlement leaveEntitlement) {

        int designationId = getDesignationId(leaveEntitlement.getDesignation());
        if (designationId == 0) {
            System.out.println("Default designation cannot be deleted");
            return null;
        }
        String sql = "DELETE FROM leaveinfo WHERE designationId =?;";
        leaveEntitlement.setDesignationId(designationId);
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement preparedStatement = con.prepareStatement(sql);
                preparedStatement.setInt(1, leaveEntitlement.getDesignationId());
                return preparedStatement;
            }
        });
        System.out.println("Deleted record successfully");
        return leaveEntitlement;
    }
}
