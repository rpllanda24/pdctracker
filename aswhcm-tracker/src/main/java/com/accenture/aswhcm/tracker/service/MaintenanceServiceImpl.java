package com.accenture.aswhcm.tracker.service;

import org.apache.commons.lang.Validate;
import org.springframework.beans.factory.annotation.Autowired;

import com.accenture.aswhcm.tracker.dao.UserDao;
import com.accenture.aswhcm.tracker.domain.User;

public class MaintenanceServiceImpl
    implements MaintenanceService {

    @Autowired
    UserDao userDao;

    @Override
    public int addUser(User user) {

        Validate.isTrue(user.getUserId() != null);
        Validate.isTrue(user.getPassw() != null);
        Validate.isTrue(user.getFirstName() != null);
        Validate.isTrue(user.getLastName() != null);

        boolean isCreated;
        int created = 0;
        isCreated = userDao.createUser(user);
        if (isCreated) {
            created = 1;
        }
        return created;
    }

    @Override
    public int updateUser(User user) {

        Validate.isTrue(user.getUserId() != null);
        Validate.isTrue(user.getPassw() != null);
        Validate.isTrue(user.getFirstName() != null);
        Validate.isTrue(user.getLastName() != null);

        boolean isUpdated;
        int updated = 0;
        isUpdated = userDao.updateUser(user);
        if (isUpdated) {
            updated = 1;
        }
        return 0;
    }

    @Override
    public void deleteUser(int userId) {

        Validate.notNull(userId);

        userDao.deleteUser(userId);

    }

}
