package com.accenture.aswhcm.tracker.domain;

import java.util.Date;

public class Request {

    public static final String STATUS_PENDING = "Pending";

    public static final String STATUS_APPROVED = "Approved";

    public static final String STATUS_DECLINED = "Declined";

    public static final String REQUEST_TYPE_LEAVE = "Leave";

    private int id;

    private String userId;

    private String approverId;

    private String requestType;

    private Date requestDate;

    private String status;

    public Request() {
        super();
    }

    public Request(int id, String userId, String approverId, String requestType, Date requestDate, String status) {
        super();
        this.id = id;
        this.userId = userId;
        this.approverId = approverId;
        this.requestType = requestType;
        this.requestDate = requestDate;
        this.status = status;
    }

    public int getId() {

        return id;
    }

    public void setId(int id) {

        this.id = id;
    }

    public String getUserId() {

        return userId;
    }

    public void setUserId(String userId) {

        this.userId = userId;
    }

    public String getApproverId() {

        return approverId;
    }

    public void setApproverId(String approverId) {

        this.approverId = approverId;
    }

    public String getRequestType() {

        return requestType;
    }

    public void setRequestType(String requestType) {

        this.requestType = requestType;
    }

    public Date getRequestDate() {

        return requestDate;
    }

    public void setRequestDate(Date requestDate) {

        this.requestDate = requestDate;
    }

    public String getStatus() {

        return status;
    }

    public void setStatus(String status) {

        this.status = status;
    }

}
