package com.accenture.aswhcm.tracker.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.Validate;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.lang3.time.DurationFormatUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.accenture.aswhcm.tracker.dao.TimeTrackerDao;
import com.accenture.aswhcm.tracker.domain.TimeTracker;

@Service("timeTrackerService")
public class TimeTrackerServiceImpl
    implements TimeTrackerService {

    private TimeTrackerDao timeTrackerDao;

    public TimeTrackerDao getTimeTrackerDao() {

        return timeTrackerDao;
    }

    @Autowired
    public void setTimeTrackerDao(TimeTrackerDao timeTrackerDao) {

        this.timeTrackerDao = timeTrackerDao;
    }

    public List<TimeTracker> getTimeTrackerList(String userId, Date startDate, Date endDate) {

        return getTimeTrackerDao().getTimeTrackerList(userId, startDate, endDate);
    }

    public void generateTimeTrackerReport(List<TimeTracker> timeLogs, HSSFWorkbook workbook) {

        HSSFSheet sheet = workbook.createSheet("TimeTracker");

        Row headRow = sheet.createRow(0);

        Cell nameHeaderCell = headRow.createCell(0);
        nameHeaderCell.setCellValue("Name");

        Cell loginHeaderCell = headRow.createCell(1);
        loginHeaderCell.setCellValue("Login");

        Cell loginCorHeaderCell = headRow.createCell(2);
        loginCorHeaderCell.setCellValue("Login (correction)");

        Cell logoutHeaderCell = headRow.createCell(3);
        logoutHeaderCell.setCellValue("Logout");

        Cell logoutCorHeaderCell = headRow.createCell(4);
        logoutCorHeaderCell.setCellValue("Logout (correction)");

        Cell commentHeaderCell = headRow.createCell(5);
        commentHeaderCell.setCellValue("Comment");

        Cell totalTimeCell = headRow.createCell(6);
        totalTimeCell.setCellValue("Duration");

        int row = 1;
        for (TimeTracker tracker : timeLogs) {
            Row dataRow = sheet.createRow(row);

            String name = tracker.getUser().getLastName() + ", " + tracker.getUser().getFirstName();
            Cell nameCell = dataRow.createCell(0);
            nameCell.setCellValue(name);

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            String login = "";
            if (tracker.getLogin() != null) {
                login = dateFormat.format(tracker.getLogin());
            }

            Cell loginCell = dataRow.createCell(1);
            loginCell.setCellValue(login);

            String logingCorrection = "";
            if (tracker.getLoginCorrection() != null) {
                logingCorrection = dateFormat.format(tracker.getLoginCorrection());
            }

            Cell loginCorrectionCell = dataRow.createCell(2);
            loginCorrectionCell.setCellValue(logingCorrection);

            String logout = "";
            if (tracker.getLogout() != null) {
                logout = dateFormat.format(tracker.getLogout());
            }

            Cell logoutCell = dataRow.createCell(3);
            logoutCell.setCellValue(logout);

            String logoutCorrection = "";
            if (tracker.getLogoutCorrection() != null) {
                logoutCorrection = dateFormat.format(tracker.getLogoutCorrection());
            }

            Cell logoutCorrectionCell = dataRow.createCell(4);
            logoutCorrectionCell.setCellValue(logoutCorrection);

            Cell commentCell = dataRow.createCell(5);
            commentCell
                .setCellValue(tracker.getComment() == "" || tracker.getComment() == null ? "" : tracker.getComment());

            Cell durationCell = dataRow.createCell(6);
            String duration = "";
            if (tracker.getLoginCorrection() == null) {
                if (tracker.getLogoutCorrection() == null) {
                    // compute time duration based on logout
                    duration = getTimeDifference(tracker.getLogout(), tracker.getLogin());
                } else {
                    duration = getTimeDifference(tracker.getLogoutCorrection(), tracker.getLogin());
                }
            } else {
                if (tracker.getLogoutCorrection() == null) {
                    // compute time duration based on logout
                    duration = getTimeDifference(tracker.getLogout(), tracker.getLoginCorrection());
                } else {
                    duration = getTimeDifference(tracker.getLogoutCorrection(), tracker.getLoginCorrection());
                }
            }

            durationCell.setCellValue(duration);

            row = row + 1;
        }
    }

    private String getTimeDifference(Date login, Date logout) {

        long difference = login.getTime() - logout.getTime();
        String duration = DurationFormatUtils.formatDuration(difference, "HH:mm");

        return duration;
    }

    public List<TimeTracker> getTimeTrackerByLogin(String userId, Date login) {

        // TODO Auto-generated method stub
        return null;
    }

    public boolean login(String userId) throws IllegalArgumentException {

        Validate.isTrue(StringUtils.isNotBlank(userId), "userId =" + userId);

        TimeTracker previousTimeTracker = getTimeTrackerDao().getLastTimeTracker(userId);

        Date date = new Date();
        boolean result = false;
        if (previousTimeTracker == null || !DateUtils.isSameDay(previousTimeTracker.getLogin(), date)) {
            // get last login if date is similar throw error
            TimeTracker timeTracker = new TimeTracker(date, userId);
            result = getTimeTrackerDao().createTimeTracker(timeTracker);
        }
        return result;
    }

    public boolean logout(String userId) throws IllegalArgumentException {

        TimeTracker timeTracker = getTimeTrackerDao().getLastTimeTracker(userId);
        boolean result = false;
        // check lastlogin if it has a logout, throw error
        if (timeTracker.getLogout() == null) {
            Date date = new Date();
            timeTracker.setLogout(date);
            result = getTimeTrackerDao().updateTimeTracker(timeTracker);
        }
        return result;
    }

    public boolean correction(TimeTracker timeTracker) throws IllegalArgumentException {

        TimeTracker oldTimeTracker = getTimeTrackerDao().getTimeTracker(timeTracker.getId());
        boolean result = false;
        if (oldTimeTracker != null
            && timeTracker.getUser().getUserId().equalsIgnoreCase(oldTimeTracker.getUser().getUserId())) {
            oldTimeTracker.setLoginCorrection(timeTracker.getLoginCorrection());
            oldTimeTracker.setLogoutCorrection(timeTracker.getLogoutCorrection());
            oldTimeTracker.setComment(timeTracker.getComment());
            result = getTimeTrackerDao().updateTimeTracker(oldTimeTracker);
        }
        return result;
    }

}
