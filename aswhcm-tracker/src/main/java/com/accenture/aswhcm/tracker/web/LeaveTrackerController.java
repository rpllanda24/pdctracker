package com.accenture.aswhcm.tracker.web;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.accenture.aswhcm.tracker.domain.LeaveRequest;
import com.accenture.aswhcm.tracker.domain.LeaveTracker;
import com.accenture.aswhcm.tracker.service.LeaveTrackerService;

@Controller
public class LeaveTrackerController {

    private static final String URL_GET_LEAVE_TRACKER = "/leavetracker/";

    private static final String URL_GET_LEAVE_TRACKER_EXPORT = "/leavetracker/export/{username}";

    private static final String URL_LEAVE_REQUEST = "/leavetracker/request";

    private static final String URL_LEAVE_APPROVAL = "/leavetracker/approval/{status}";

    private static final String URL_LEAVE_APPROVE = "/leavetracker/approve";

    private static final String URL_LEAVE_DECLINE = "/leavetracker/decline";

    @Autowired
    private HttpSession httpSession;

    @Autowired
    LeaveTrackerService leaveTrackerService;

    @SuppressWarnings("rawtypes")
    @RequestMapping(value = URL_GET_LEAVE_TRACKER, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody GeneralResponse getLeaveTracker() {

        String userId = (String) httpSession.getAttribute("userId");
        GeneralResponse result;

        if (userId == null) {
            final FaultDetails fault = new FaultDetails("Access denied.");
            result = new FaultResponse(false, fault);
        } else {
            List<LeaveTracker> leaveList = leaveTrackerService.getLeaveTrackerList(userId, null, null);
            result = new GeneralResponse<List<LeaveTracker>>(true, leaveList);
        }

        return result;
    }

    @SuppressWarnings("rawtypes")
    @RequestMapping(value = URL_GET_LEAVE_TRACKER_EXPORT, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody GeneralResponse exportLeaveTracker(@PathVariable String username,
                                                            @RequestParam(value = "start", required = false) Date startDate,
                                                            @RequestParam(value = "end", required = false) Date endDate,
                                                            HttpServletResponse response) {

        String id = (String) httpSession.getAttribute("userId");
        GeneralResponse result;
        if (id == null) {
            final FaultDetails fault = new FaultDetails("Access denied.");
            result = new FaultResponse(false, fault);
        } else {
            List<LeaveTracker> leaveList = leaveTrackerService.getLeaveTrackerList(username, startDate, endDate);
            // export logs to excel
            HSSFWorkbook workbook = new HSSFWorkbook();
            leaveTrackerService.generateLeaveTrackerReport(leaveList, workbook);

            try {
                response.setContentType("application/vnd.ms-excel");
                workbook.write(response.getOutputStream());
                response.getOutputStream().close();

            } catch (IOException e) {
                final FaultDetails fault = new FaultDetails(e.getMessage());
                result = new FaultResponse(false, fault);
            }
            result = new GeneralResponse<String>(true, "Report has been generated");
        }
        return result;
    }

    @SuppressWarnings("rawtypes")
    @RequestMapping(value = URL_LEAVE_REQUEST, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody GeneralResponse sendLeaveRequest(@RequestBody LeaveRequest leaveRequest) {

        GeneralResponse result;

        String id = (String) httpSession.getAttribute("userId");
        if (id == null) {
            final FaultDetails fault = new FaultDetails("Access denied.");
            result = new FaultResponse(false, fault);
            return result;
        }

        // convert startDateStr and endDateStr to util.Date()
        LeaveTracker leave = leaveRequest.getLeave();
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            leave.setStartDate(dateFormat.parse(leaveRequest.getStartDateStr()));
            leave.setEndDate(dateFormat.parse(leaveRequest.getEndDateStr()));

        } catch (ParseException e) {
            final FaultDetails fault = new FaultDetails(e.getMessage());
            result = new FaultResponse(false, fault);
        }

        try {
            boolean flag = leaveTrackerService.createLeaveRequest(leave);
            if (flag) {
                result = new GeneralResponse<String>(true, "Leave request saved!");
            } else {
                final FaultDetails fault = new FaultDetails("An error occurred while saving leave request");
                result = new FaultResponse(false, fault);
            }
        } catch (SQLException e) {
            final FaultDetails fault =
                new FaultDetails("An error occurred while saving leave request " + e.getMessage());
            result = new FaultResponse(false, fault);
        }

        return result;
    }

    @SuppressWarnings("rawtypes")
    @RequestMapping(value = URL_LEAVE_APPROVAL, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody GeneralResponse getLeaveApproval(@PathVariable String status) {

        GeneralResponse result;

        String id = (String) httpSession.getAttribute("userId");
        if (id == null) {
            final FaultDetails fault = new FaultDetails("Access denied.");
            result = new FaultResponse(false, fault);
            return result;
        }

        try {
            List<LeaveRequest> leaveRequest = leaveTrackerService.getLeaveRequestsByApprover(id, status);
            result = new GeneralResponse<List<LeaveRequest>>(true, leaveRequest);

        } catch (SQLException e) {
            final FaultDetails fault =
                new FaultDetails("An error occurred while saving leave request " + e.getMessage());
            result = new FaultResponse(false, fault);
        }

        return result;
    }

    @SuppressWarnings("rawtypes")
    @RequestMapping(value = URL_LEAVE_APPROVE, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody GeneralResponse approveLeaveRequest(@RequestParam(value = "id", required = true) int id) {

        GeneralResponse result;

        String userId = (String) httpSession.getAttribute("userId");
        if (userId == null) {
            final FaultDetails fault = new FaultDetails("Access denied.");
            result = new FaultResponse(false, fault);
            return result;
        }

        try {
            boolean flag = leaveTrackerService.approveLeaveRequest(userId, id);
            if (flag) {
                result = new GeneralResponse<String>(true, "Request has been approved!");
            } else {
                final FaultDetails fault = new FaultDetails("An error occurred while approving leave request");
                result = new FaultResponse(false, fault);
            }

        } catch (SQLException e) {
            final FaultDetails fault =
                new FaultDetails("An error occurred while approving leave request" + e.getMessage());
            result = new FaultResponse(false, fault);
        }

        return result;

    }

    @SuppressWarnings("rawtypes")
    @RequestMapping(value = URL_LEAVE_DECLINE, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody GeneralResponse declineLeaveRequest(@RequestParam(value = "id", required = true) int id) {

        GeneralResponse result;

        String userId = (String) httpSession.getAttribute("userId");
        if (userId == null) {
            final FaultDetails fault = new FaultDetails("Access denied.");
            result = new FaultResponse(false, fault);
            return result;
        }

        try {
            boolean flag = leaveTrackerService.declineLeaveRequest(userId, id);
            if (flag) {
                result = new GeneralResponse<String>(true, "Request has been declined.!");
            } else {
                final FaultDetails fault = new FaultDetails("An error occurred while updating leave request status");
                result = new FaultResponse(false, fault);
            }

        } catch (SQLException e) {
            final FaultDetails fault =
                new FaultDetails("An error occurred while updating leave request status: " + e.getMessage());
            result = new FaultResponse(false, fault);
        }

        return result;

    }

}
