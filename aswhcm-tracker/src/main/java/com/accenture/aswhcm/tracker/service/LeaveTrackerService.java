package com.accenture.aswhcm.tracker.service;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.accenture.aswhcm.tracker.domain.LeaveRequest;
import com.accenture.aswhcm.tracker.domain.LeaveTracker;

public interface LeaveTrackerService {

    public List<LeaveTracker> getLeaveTrackerList(String userId, Date start, Date end);

    public void generateLeaveTrackerReport(List<LeaveTracker> leaveList, HSSFWorkbook workbook);

    public boolean createLeaveRequest(LeaveTracker leave) throws SQLException;

    public List<LeaveRequest> getLeaveRequestsByApprover(String approverId, String status) throws SQLException;

    public boolean approveLeaveRequest(String approverId, int id) throws SQLException;

    public LeaveRequest getLeaveRequestById(String approverId, int id) throws SQLException;

    public boolean declineLeaveRequest(String approverId, int id) throws SQLException;

}
