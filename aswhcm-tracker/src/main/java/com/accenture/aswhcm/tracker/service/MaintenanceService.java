package com.accenture.aswhcm.tracker.service;

import com.accenture.aswhcm.tracker.domain.User;

public interface MaintenanceService {

    public int addUser(User user);

    public int updateUser(User user);

    public void deleteUser(int userId);

}
