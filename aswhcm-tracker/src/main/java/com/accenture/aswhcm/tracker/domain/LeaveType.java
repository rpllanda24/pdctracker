package com.accenture.aswhcm.tracker.domain;

public class LeaveType {

    private int id;

    private String leaveType;

    private boolean forApproval;

    public LeaveType(int id, String leaveType, boolean forApproval) {
        super();
        this.id = id;
        this.leaveType = leaveType;
        this.forApproval = forApproval;
    }

    public int getId() {

        return id;
    }

    public String getLeaveType() {

        return leaveType;
    }

    public boolean isForApproval() {

        return forApproval;
    }

}
