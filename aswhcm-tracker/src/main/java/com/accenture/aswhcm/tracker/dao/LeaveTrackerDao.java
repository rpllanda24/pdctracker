package com.accenture.aswhcm.tracker.dao;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import com.accenture.aswhcm.tracker.domain.LeaveRequest;
import com.accenture.aswhcm.tracker.domain.LeaveTracker;
import com.accenture.aswhcm.tracker.domain.LeaveType;
import com.accenture.aswhcm.tracker.domain.Request;

public interface LeaveTrackerDao {

    public List<LeaveTracker> getLeaveTrackerList(String userId, Date start, Date end);

    public List<LeaveType> getLeaveTypes();

    public boolean saveLeave(LeaveTracker leave) throws SQLException;

    public int saveRequest(Request request) throws SQLException;

    public int lastRequestId(String userId) throws SQLException;

    public List<LeaveRequest> getLeaveRequestsByApprover(String approverId, String status) throws SQLException;

    public boolean updateRequestStatus(int id, String status) throws SQLException;

    public boolean updateLeaveStatus(int id, String status) throws SQLException;

    public LeaveRequest getLeaveRequestById(String approverId, int id) throws SQLException;
}
