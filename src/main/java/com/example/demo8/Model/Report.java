package com.example.demo8.Model;

import java.sql.Date;
import java.util.Objects;

public class Report {
    private int userId;
    private String userName;
    private Date startDate;
    private Date endDate;
    private int numberOfLeavesPerMonth;


    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getNumberOfLeavesPerMonth() {
        return numberOfLeavesPerMonth;
    }

    public void setNumberOfLeavesPerMonth(int numberOfLeavesPerMonth) {
        this.numberOfLeavesPerMonth = numberOfLeavesPerMonth;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public Report() {
    }

    @Override
    public String toString() {
        return
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                "," + numberOfLeavesPerMonth ;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Report)) return false;
        Report report = (Report) o;
        return getUserId() == report.getUserId() &&
                getNumberOfLeavesPerMonth() == report.getNumberOfLeavesPerMonth() &&
                Objects.equals(getUserName(), report.getUserName()) &&
                Objects.equals(getStartDate(), report.getStartDate()) &&
                Objects.equals(getEndDate(), report.getEndDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUserId(), getUserName(), getStartDate(), getEndDate(), getNumberOfLeavesPerMonth());
    }
}
