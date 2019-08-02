package com.example.demo8.Model;

import java.sql.Date;
import java.sql.Time;
import java.util.Objects;

public class Leave {
    private int leaveId;
    private int userId;
    private Date startDate;
    private Date endDate;
    private String Reason;
    private String status;
    private String lastUpdateUser;
    private Time lastUpdateTime;

    public Leave() {
    }

    public Leave(int leaveId, int userId, Date startDate, Date endDate, String reason, String status, String lastUpdateUser, Time lastUpdateTime) {
        this.leaveId = leaveId;
        this.userId = userId;
        this.startDate = startDate;
        this.endDate = endDate;
        Reason = reason;
        this.status = status;
        this.lastUpdateUser = lastUpdateUser;
        this.lastUpdateTime = lastUpdateTime;
    }

    public Leave(int userId, Date startDate, Date endDate, String reason, String status, String lastUpdateUser, Time lastUpdateTime) {
        this.userId = userId;
        this.startDate = startDate;
        this.endDate = endDate;
        Reason = reason;
        this.status = status;
        this.lastUpdateUser = lastUpdateUser;
        this.lastUpdateTime = lastUpdateTime;
    }

    public int getLeaveId() {
        return leaveId;
    }

    public void setLeaveId(int leaveId) {
        this.leaveId = leaveId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getReason() {
        return Reason;
    }

    public void setReason(String reason) {
        Reason = reason;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
    public String toString() {
        return "Leave{" +
                "leaveId=" + leaveId +
                ", userId=" + userId +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", Reason='" + Reason + '\'' +
                ", status='" + status + '\'' +
                ", lastUpdateUser='" + lastUpdateUser + '\'' +
                ", lastUpdateTime=" + lastUpdateTime +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Leave)) return false;
        Leave leave = (Leave) o;
        return getLeaveId() == leave.getLeaveId() &&
                getUserId() == leave.getUserId() &&
                Objects.equals(getStartDate(), leave.getStartDate()) &&
                Objects.equals(getEndDate(), leave.getEndDate()) &&
                Objects.equals(getReason(), leave.getReason()) &&
                Objects.equals(getStatus(), leave.getStatus()) &&
                Objects.equals(getLastUpdateUser(), leave.getLastUpdateUser()) &&
                Objects.equals(getLastUpdateTime(), leave.getLastUpdateTime());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getLeaveId(), getUserId(), getStartDate(), getEndDate(), getReason(), getStatus(), getLastUpdateUser(), getLastUpdateTime());
    }
}
