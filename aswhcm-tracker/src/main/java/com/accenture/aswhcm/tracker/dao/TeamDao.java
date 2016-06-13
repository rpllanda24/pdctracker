package com.accenture.aswhcm.tracker.dao;

import java.util.List;

import com.accenture.aswhcm.tracker.domain.Team;

public interface TeamDao {

    public List<Team> getTeams();

    public Team getTeam(int id);

    public boolean createTeam(Team team);

    public boolean updateTeam(Team team);

    public boolean deleteTeam(int id);

}
