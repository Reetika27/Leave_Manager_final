package com.example.demo8.Model;

import java.sql.Time;
import java.util.Objects;

public class LeaveEntitlement {
    private int designationId;
    private String designation;
    private int totalNumberOfPermissibleLeaves;
    private String lastUpdateUser;

    public LeaveEntitlement(int designationId, String designation, int totalNumberOfPermissibleLeaves, String lastUpdateUser, Time lastUpdateTime) {
        this.designationId = designationId;
        this.designation = designation;
        this.totalNumberOfPermissibleLeaves = totalNumberOfPermissibleLeaves;
        this.lastUpdateUser = lastUpdateUser;
        this.lastUpdateTime = lastUpdateTime;
    }

    private Time lastUpdateTime;

    public LeaveEntitlement() {
    }

    public int getDesignationId() {
        return designationId;
    }

    public void setDesignationId(int designationId) {
        this.designationId = designationId;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public int getTotalNumberOfPermissibleLeaves() {
        return totalNumberOfPermissibleLeaves;
    }

    public void setTotalNumberOfPermissibleLeaves(int totalNumberOfPermissibleLeaves) {
        this.totalNumberOfPermissibleLeaves = totalNumberOfPermissibleLeaves;
    }

    public String getLastUpdateUser() {
        return lastUpdateUser;
    }

    public void setLastUpdateUser(String lastUpdateUser) {
        this.lastUpdateUser = lastUpdateUser;
    }

    public Time getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Time lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LeaveEntitlement)) return false;
        LeaveEntitlement that = (LeaveEntitlement) o;
        return getDesignationId() == that.getDesignationId() &&
                getTotalNumberOfPermissibleLeaves() == that.getTotalNumberOfPermissibleLeaves() &&
                Objects.equals(getDesignation(), that.getDesignation()) &&
                Objects.equals(getLastUpdateUser(), that.getLastUpdateUser()) &&
                Objects.equals(getLastUpdateTime(), that.getLastUpdateTime());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDesignationId(), getDesignation(), getTotalNumberOfPermissibleLeaves(), getLastUpdateUser(), getLastUpdateTime());
    }

    @Override
    public String toString() {
        return "LeaveEntitlement{" +
                "designationId=" + designationId +
                ", designation='" + designation + '\'' +
                ", totalNumberOfPermissibleLeaves=" + totalNumberOfPermissibleLeaves +
                ", lastUpdateUser='" + lastUpdateUser + '\'' +
                ", lastUpdateTime=" + lastUpdateTime +
                '}';
    }
}
