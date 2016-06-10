package com.accenture.aswhcm.tracker.domain;

public class LeaveRequest {

    private String startDateStr;

    private String endDateStr;

    private LeaveTracker leave;

    private Request request;

    public String getStartDateStr() {

        return startDateStr;
    }

    public void setStartDateStr(String startDateStr) {

        this.startDateStr = startDateStr;
    }

    public String getEndDateStr() {

        return endDateStr;
    }

    public void setEndDateStr(String endDateStr) {

        this.endDateStr = endDateStr;
    }

    public LeaveTracker getLeave() {

        return leave;
    }

    public void setLeave(LeaveTracker leave) {

        this.leave = leave;
    }

    public Request getRequest() {

        return request;
    }

    public void setRequest(Request request) {

        this.request = request;
    }

}
