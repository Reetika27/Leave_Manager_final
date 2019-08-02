package com.example.demo8.Controller;


import com.example.demo8.Model.LeaveEntitlement;
import com.example.demo8.Repository.LeaveEntitlementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("LeaveEntitlementService")
public class LeaveEntitlementService {

    private LeaveEntitlementRepository leaveEntitlementRepository;

    @Autowired
    public LeaveEntitlementService(LeaveEntitlementRepository leaveEntitlementRepository) {
        this.leaveEntitlementRepository = leaveEntitlementRepository;
    }

    public List<LeaveEntitlement> getAllLeaveEntitlements() {
        return leaveEntitlementRepository.findAllLeaveEntitlements();
    }

    public int getDesignationId(String designation) {
        return leaveEntitlementRepository.getDesignationId(designation);
    }

    public LeaveEntitlement getLeaveEntitlementById(int designationId) {
        return leaveEntitlementRepository.findLeaveEntitlementByDesignationId(designationId);
    }

    public LeaveEntitlement addNewLeaveEntitlement(LeaveEntitlement leaveEntitlement) {
        LeaveEntitlement leaveEntitlement1 = leaveEntitlementRepository.create(leaveEntitlement);
        return leaveEntitlement1;
    }

    public void updateLeaveEntitlement(LeaveEntitlement leaveEntitlement) {
        leaveEntitlementRepository.update(leaveEntitlement);
    }

    public LeaveEntitlement deleteLeaveEntitlement(LeaveEntitlement leaveEntitlement) {
        LeaveEntitlement leaveEntitlement1 = leaveEntitlementRepository.delete(leaveEntitlement);
        return leaveEntitlement;
    }

}
