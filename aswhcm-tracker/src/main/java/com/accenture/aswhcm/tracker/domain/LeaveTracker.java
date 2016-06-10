package com.accenture.aswhcm.tracker.domain;

import java.util.Date;

public class LeaveTracker {

    private int id;

    private String leaveType;

    private Date startDate;

    private Date endDate;

    private String status;

    private User user;

    private User approver;

    private int leaveTypeId;

    private int requestId;

    public LeaveTracker() {
        super();
    }

    public LeaveTracker(int id, String leaveType, Date startDate, Date endDate, String status, User user, User approver,
                        int leaveTypeId) {
        super();
        this.id = id;
        this.leaveType = leaveType;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.user = user;
        this.approver = approver;
        this.leaveTypeId = leaveTypeId;
    }

    public int getId() {

        return id;
    }

    public void setId(int id) {

        this.id = id;
    }

    public String getLeaveType() {

        return leaveType;
    }

    public void setLeaveType(String leaveType) {

        this.leaveType = leaveType;
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

    public String getStatus() {

        return status;
    }

    public void setStatus(String status) {

        this.status = status;
    }

    public User getUser() {

        return user;
    }

    public void setUser(User user) {

        this.user = user;
    }

    public User getApprover() {

        return approver;
    }

    public void setApprover(User approver) {

        this.approver = approver;
    }

    public int getLeaveTypeId() {

        return leaveTypeId;
    }

    public void setLeaveTypeId(int leaveTypeId) {

        this.leaveTypeId = leaveTypeId;
    }

    public int getRequestId() {

        return requestId;
    }

    public void setRequestId(int requestId) {

        this.requestId = requestId;
    }

}
