package com.accenture.aswhcm.tracker.dao;

import java.util.Date;
import java.util.List;

import com.accenture.aswhcm.tracker.domain.TimeTracker;

public interface TimeTrackerDao {

    public List<TimeTracker> getTimeTrackerList(String userId, Date startDate, Date endDate);

    public boolean createTimeTracker(TimeTracker timeTracker);

    public boolean updateTimeTracker(TimeTracker timeTracker);

    public TimeTracker getLastTimeTracker(String userId);

    public TimeTracker getTimeTracker(int id);
}
