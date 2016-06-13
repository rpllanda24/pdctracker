package com.accenture.aswhcm.tracker.service;

import com.accenture.aswhcm.tracker.domain.Team;

public interface TeamService {

    // public List<Team> getTeams();

    // public Team getTeam(int id) throws IllegalArgumentException;

    public boolean createTeam(Team team) throws IllegalArgumentException;

    public boolean updateTeam(Team team) throws IllegalArgumentException;

    public boolean deleteTeam(int id) throws IllegalArgumentException;

}
