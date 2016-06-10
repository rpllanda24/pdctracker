package com.accenture.aswhcm.tracker.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.accenture.aswhcm.tracker.dao.UserDao;
import com.accenture.aswhcm.tracker.domain.User;

@Service("userService")
public class UserServiceImpl
    implements UserService {

    private UserDao userDao;

    public UserDao getUserDao() {

        return userDao;
    }

    @Autowired
    public void setUserDao(UserDao userDao) {

        this.userDao = userDao;
    }

    public List<User> getUsers() {

        return getUserDao().getUsers();
    }

    public boolean login(User user) {

        return getUserDao().login(user);
    }

    public boolean createUser(User user) throws IllegalArgumentException {

        Validate.isTrue(user != null, "User is null");
        Validate.isTrue(StringUtils.isNotBlank(user.getUserId()), "username is blank/null");
        Validate.isTrue(StringUtils.isNotBlank(user.getPassw()), "password is blank/null");
        Validate.isTrue(-1 < user.getRole(), "role is invalid");
        Validate.isTrue(StringUtils.isNotBlank(user.getFirstName()), "firstname is blank/null");
        Validate.isTrue(StringUtils.isNotBlank(user.getLastName()), "lastname is blank/null");
        Validate.isTrue(StringUtils.isNotBlank(user.getTeam()), "team is blank/null");
        return getUserDao().createUser(user);

    }

    public boolean deleteUser(int id) throws IllegalArgumentException {

        Validate.isTrue(id > 0, "invalid ID");
        return getUserDao().deleteUser(id);
    }

    public boolean resetPassword(int id, String password) throws IllegalArgumentException {

        Validate.isTrue(id > 0, "invalid ID");
        Validate.isTrue(StringUtils.isNotBlank(password), "password is blank/null");
        return getUserDao().resetPassword(id, password);
    }

    public User getUser(int id) throws IllegalArgumentException {

        Validate.isTrue(id > 0, "invalid ID");
        return getUserDao().getUser(id);
    }

}
