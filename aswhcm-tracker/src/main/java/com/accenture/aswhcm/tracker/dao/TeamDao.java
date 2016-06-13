package com.accenture.aswhcm.tracker.dao;

import com.accenture.aswhcm.tracker.domain.Team;

public interface TeamDao {

    public boolean createTeam(Team team);

    public boolean updateTeam(Team team);

    public boolean deleteTeam(int id);

    public Team getTeam(int Id);

}
