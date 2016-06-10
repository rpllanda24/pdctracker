package com.accenture.aswhcm.tracker.web;

import java.io.IOException;
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

import com.accenture.aswhcm.tracker.domain.TimeTracker;
import com.accenture.aswhcm.tracker.domain.User;
import com.accenture.aswhcm.tracker.service.TimeTrackerService;
import com.accenture.aswhcm.tracker.web.data.CorrectionData;

@Controller
public class TimeTrackerController {

    private static final String URL_GET_TIME_TRACKER = "/timetracker";

    private static final String URL_LOGIN = "/timetracker/login";

    private static final String URL_LOGOUT = "/timetracker/logout";

    private static final String URL_CORRECTION = "/timetracker/correction";

    private static final String URL_GET_TIME_TRACKER_USER = "/timetracker/{username}";

    private static final String URL_GET_TIME_TRACKER_EXPORT = "/timetracker/export/{username}";

    @Autowired
    private HttpSession httpSession;

    @Autowired
    TimeTrackerService timeTrackerService;

    @SuppressWarnings("rawtypes")
    @RequestMapping(value = URL_GET_TIME_TRACKER, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody GeneralResponse getTimeTracker() {

        String userId = (String) httpSession.getAttribute("userId");
        GeneralResponse result;
        if (userId == null) {
            final FaultDetails fault = new FaultDetails("Access denied.");
            result = new FaultResponse(false, fault);
        } else {
            List<TimeTracker> timeLogs = timeTrackerService.getTimeTrackerList(userId, null, null);
            result = new GeneralResponse<List<TimeTracker>>(true, timeLogs);
        }

        return result;
    }

    @SuppressWarnings("rawtypes")
    @RequestMapping(value = URL_GET_TIME_TRACKER_USER, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody GeneralResponse viewTimeTracker(@PathVariable String username,
                                                         @RequestParam(value = "start", required = false) Date startDate,
                                                         @RequestParam(value = "end", required = false) Date endDate) {

        String id = (String) httpSession.getAttribute("userId");
        GeneralResponse result;
        if (id == null) {
            final FaultDetails fault = new FaultDetails("Access denied.");
            result = new FaultResponse(false, fault);
        } else {
            List<TimeTracker> timeLogs = timeTrackerService.getTimeTrackerList(username, startDate, endDate);
            result = new GeneralResponse<List<TimeTracker>>(true, timeLogs);
        }

        return result;
    }

    @SuppressWarnings("rawtypes")
    @RequestMapping(value = URL_GET_TIME_TRACKER_EXPORT, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody GeneralResponse exportTimeTracker(@PathVariable String username,
                                                           @RequestParam(value = "start", required = false) Date startDate,
                                                           @RequestParam(value = "end", required = false) Date endDate,
                                                           HttpServletResponse response) {

        String id = (String) httpSession.getAttribute("userId");
        GeneralResponse result;
        if (id == null) {
            final FaultDetails fault = new FaultDetails("Access denied.");
            result = new FaultResponse(false, fault);
        } else {
            List<TimeTracker> timeLogs = timeTrackerService.getTimeTrackerList(username, startDate, endDate);

            // export logs to excel
            HSSFWorkbook workbook = new HSSFWorkbook();
            timeTrackerService.generateTimeTrackerReport(timeLogs, workbook);
            try {
                response.setContentType("application/vnd.ms-excel");
                workbook.write(response.getOutputStream());
                response.getOutputStream().close();

            } catch (IOException e) {
                e.printStackTrace();
            }
            result = new GeneralResponse<String>(true, "Report has been generated");
        }
        return result;
    }

    @SuppressWarnings("rawtypes")
    @RequestMapping(value = URL_LOGIN, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody GeneralResponse login() {

        String userId = (String) httpSession.getAttribute("userId");
        GeneralResponse result;
        if (userId == null) {
            final FaultDetails fault = new FaultDetails("Access denied.");
            result = new FaultResponse(false, fault);
        } else {
            boolean status = timeTrackerService.login(userId);
            if (status) {
                result = new GeneralResponse<String>(true, userId + " was able to login.");
            } else {
                result = new GeneralResponse<String>(false, userId + " wasn't able to login.");
            }
        }

        return result;
    }

    @SuppressWarnings("rawtypes")
    @RequestMapping(value = URL_LOGOUT, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody GeneralResponse logout() {

        String userId = (String) httpSession.getAttribute("userId");
        GeneralResponse result;
        if (userId == null) {
            final FaultDetails fault = new FaultDetails("Access denied.");
            result = new FaultResponse(false, fault);
        } else {
            boolean status = timeTrackerService.logout(userId);
            if (status) {
                result = new GeneralResponse<String>(true, userId + " was able to logout.");
            } else {
                result = new GeneralResponse<String>(false, userId + " wasn't able to logout.");
            }
        }

        return result;
    }

    @SuppressWarnings("rawtypes")
    @RequestMapping(value = URL_GET_TIME_TRACKER, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody GeneralResponse correction(@RequestBody CorrectionData correctionData) {

        String userId = (String) httpSession.getAttribute("userId");
        GeneralResponse result;

        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
        if (userId == null) {
            final FaultDetails fault = new FaultDetails("Access denied.");
            result = new FaultResponse(false, fault);
        } else {
            Date loginCorrection = null;
            Date logoutCorrection = null;
            try {
                if (correctionData.getLoginCorrection() != null)
                    loginCorrection = formatter.parse(correctionData.getLoginCorrection());

            } catch (ParseException e) {

                e.printStackTrace();
            }

            try {
                if (correctionData.getLogoutCorrection() != null)
                    logoutCorrection = formatter.parse(correctionData.getLogoutCorrection());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            TimeTracker timeTracker = new TimeTracker(correctionData.getId(), null, null, loginCorrection,
                logoutCorrection, correctionData.getComment(), new User(userId));
            boolean status = timeTrackerService.correction(timeTracker);
            if (status) {
                result = new GeneralResponse<String>(true, userId + " was able to update data.");
            } else {
                result = new GeneralResponse<String>(false, userId + " wasn't able to update data.");
            }
        }

        return result;
    }
}
