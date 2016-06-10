package com.accenture.aswhcm.tracker.service;

import java.util.List;

import com.accenture.aswhcm.tracker.domain.User;

public interface UserService {

    public List<User> getUsers();

    public User getUser(int id) throws IllegalArgumentException;

    public boolean login(User user);

    public boolean createUser(User user) throws IllegalArgumentException;

    public boolean deleteUser(int id) throws IllegalArgumentException;

    public boolean resetPassword(int id, String password) throws IllegalArgumentException;
}
