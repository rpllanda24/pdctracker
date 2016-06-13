package com.accenture.aswhcm.tracker.dao;

import java.util.List;

import com.accenture.aswhcm.tracker.domain.User;

public interface UserDao {

    public List<User> getUsers();

    public User getUser(int id);

    public boolean login(User user);

    public boolean createUser(User user);

    public boolean deleteUser(int id);

    public boolean resetPassword(int id, String password);

    public boolean updateUser(User user);

}
