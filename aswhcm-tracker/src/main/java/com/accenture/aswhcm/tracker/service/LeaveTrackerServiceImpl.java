package com.accenture.aswhcm.tracker.service;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.accenture.aswhcm.tracker.dao.LeaveTrackerDao;
import com.accenture.aswhcm.tracker.domain.LeaveRequest;
import com.accenture.aswhcm.tracker.domain.LeaveTracker;
import com.accenture.aswhcm.tracker.domain.LeaveType;
import com.accenture.aswhcm.tracker.domain.Request;

@Service("leaveTrackerService")
public class LeaveTrackerServiceImpl
    implements LeaveTrackerService {

    private LeaveTrackerDao leaveTrackerDao;

    public LeaveTrackerDao getLeaveTrackerDao() {

        return leaveTrackerDao;
    }

    @Autowired
    public void setLeaveTrackerDao(LeaveTrackerDao leaveTrackerDao) {

        this.leaveTrackerDao = leaveTrackerDao;
    }

    public List<LeaveTracker> getLeaveTrackerList(String userId, Date start, Date end) {

        return getLeaveTrackerDao().getLeaveTrackerList(userId, start, end);
    }

    public void generateLeaveTrackerReport(List<LeaveTracker> leaveList, HSSFWorkbook workbook) {

        HSSFSheet sheet = workbook.createSheet("Leave Tracker");

        Row headRow = sheet.createRow(0);

        Cell nameHeaderCell = headRow.createCell(0);
        nameHeaderCell.setCellValue("Name");

        Cell leaveTypeHeaderCell = headRow.createCell(1);
        leaveTypeHeaderCell.setCellValue("Leave Type");

        Cell fromHeaderCell = headRow.createCell(2);
        fromHeaderCell.setCellValue("From");

        Cell toHeaderCell = headRow.createCell(3);
        toHeaderCell.setCellValue("To");

        Cell approverHeaderCell = headRow.createCell(4);
        approverHeaderCell.setCellValue("Approver");

        Cell statusHeaderCell = headRow.createCell(5);
        statusHeaderCell.setCellValue("Status");

        int row = 1;
        for (LeaveTracker tracker : leaveList) {
            Row dataRow = sheet.createRow(row);

            CellStyle style = workbook.createCellStyle();
            style.setFillForegroundColor(IndexedColors.ROSE.getIndex());
            style.setFillPattern(CellStyle.SOLID_FOREGROUND);

            String name = tracker.getUser().getLastName() + ", " + tracker.getUser().getFirstName();
            Cell nameCell = dataRow.createCell(0);
            nameCell.setCellValue(name);

            Cell leaveTypeCell = dataRow.createCell(1);
            leaveTypeCell.setCellValue(tracker.getLeaveType());

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            String from = "";
            if (tracker.getStartDate() != null) {
                from = dateFormat.format(tracker.getStartDate());
            }

            Cell dateFromCell = dataRow.createCell(2);
            dateFromCell.setCellValue(from);

            String to = "";
            if (tracker.getEndDate() != null) {
                to = dateFormat.format(tracker.getEndDate());
            }

            Cell dateToCell = dataRow.createCell(3);
            dateToCell.setCellValue(to);

            String approver = tracker.getApprover().getLastName() + ", " + tracker.getApprover().getFirstName();
            Cell approverCell = dataRow.createCell(4);
            approverCell.setCellValue(approver);

            Cell statusCell = dataRow.createCell(5);
            statusCell.setCellValue(tracker.getStatus());

            if (tracker.getStatus() == "Declined" || "Declined".equalsIgnoreCase(tracker.getStatus())) {
                nameCell.setCellStyle(style);
                leaveTypeCell.setCellStyle(style);
                dateFromCell.setCellStyle(style);
                dateToCell.setCellStyle(style);
                approverCell.setCellStyle(style);
                statusCell.setCellStyle(style);
            }

            row = row + 1;
        }

    }

    public boolean createLeaveRequest(LeaveTracker leave) throws SQLException {

        boolean result = false;

        leave.setStatus(Request.STATUS_PENDING);

        try {
            if (isLeaveForApproval(leave)) {
                // create a request with status PENDING
                Request request = new Request();
                request.setApproverId(leave.getApprover().getUserId());
                request.setRequestDate(new Date());
                request.setRequestType(Request.REQUEST_TYPE_LEAVE);
                request.setUserId(leave.getUser().getUserId());
                request.setStatus(Request.STATUS_PENDING);

                int requestId = getLeaveTrackerDao().saveRequest(request);
                leave.setRequestId(getLeaveTrackerDao().lastRequestId(request.getUserId()));

                boolean flag2 = getLeaveTrackerDao().saveLeave(leave);

                if (requestId != 0 && flag2) {
                    result = true;
                }

            } else {
                result = getLeaveTrackerDao().saveLeave(leave);
            }
        } catch (SQLException e) {
            throw e;
        }

        return result;
    }

    private boolean isLeaveForApproval(LeaveTracker leave) {

        boolean flag = false;
        List<LeaveType> leaveTypes = getLeaveTrackerDao().getLeaveTypes();
        for (LeaveType type : leaveTypes) {
            if (type.getId() == leave.getLeaveTypeId() || type.getLeaveType().equalsIgnoreCase(leave.getLeaveType())) {
                flag = type.isForApproval();
            }
        }
        return flag;
    }

    public List<LeaveRequest> getLeaveRequestsByApprover(String approverId, String status) throws SQLException {

        return getLeaveTrackerDao().getLeaveRequestsByApprover(approverId, status);
    }

    public boolean approveLeaveRequest(String approverId, int id) throws SQLException {

        LeaveRequest leaveRequest = getLeaveRequestById(approverId, id);
        boolean flag1 =
            getLeaveTrackerDao().updateRequestStatus(leaveRequest.getRequest().getId(), Request.STATUS_APPROVED);
        boolean flag2 =
            getLeaveTrackerDao().updateLeaveStatus(leaveRequest.getLeave().getId(), Request.STATUS_APPROVED);

        if (flag1 && flag2) {
            return true;
        }

        return false;
    }

    public LeaveRequest getLeaveRequestById(String approverId, int id) throws SQLException {

        return getLeaveTrackerDao().getLeaveRequestById(approverId, id);
    }

    public boolean declineLeaveRequest(String approverId, int id) throws SQLException {

        LeaveRequest leaveRequest = getLeaveRequestById(approverId, id);
        boolean flag1 =
            getLeaveTrackerDao().updateRequestStatus(leaveRequest.getRequest().getId(), Request.STATUS_DECLINED);
        boolean flag2 =
            getLeaveTrackerDao().updateLeaveStatus(leaveRequest.getLeave().getId(), Request.STATUS_DECLINED);

        if (flag1 && flag2) {
            return true;
        }

        return false;
    }

}
