package com.example.demo8.Repository;

import com.example.demo8.Model.LeaveEntitlement;

import java.util.List;

//The DAO handles all leave entitlement repository supported operations and effects leaveinfo table in the database
public interface LeaveEntitlementDAO {

    //Finds all designations in the company
    List<LeaveEntitlement> findAllLeaveEntitlements();

    //A helper function to get designationId from given designation name
    int getDesignationId(String designation);

    //Finding the total number of permissible leaves per year for each designation/position
    LeaveEntitlement findLeaveEntitlementByDesignationId(int designationId);

    //Adds a new job position to the database
    LeaveEntitlement create(final LeaveEntitlement leaveEntitlement);

    //Updates total number of leaves for specified designation
    void update(final LeaveEntitlement leaveEntitlement);

    //Deletes a specified job position
    LeaveEntitlement delete(LeaveEntitlement leaveEntitlement);


}
