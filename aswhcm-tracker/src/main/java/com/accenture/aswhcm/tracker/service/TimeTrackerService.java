package com.accenture.aswhcm.tracker.service;

import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.accenture.aswhcm.tracker.domain.TimeTracker;

public interface TimeTrackerService {

    public List<TimeTracker> getTimeTrackerList(String userId, Date startDate, Date endDate);

    public void generateTimeTrackerReport(List<TimeTracker> timeLogs, HSSFWorkbook workbook);

    public List<TimeTracker> getTimeTrackerByLogin(String userId, Date login);

    public boolean login(String userId) throws IllegalArgumentException;

    public boolean logout(String userId) throws IllegalArgumentException;

    public boolean correction(TimeTracker timeTracker) throws IllegalArgumentException;
}
