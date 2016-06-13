package com.accenture.aswhcm.tracker.service;

import com.accenture.aswhcm.tracker.domain.Team;

public interface TeamService {

    public int createTeam(Team team);

    public int updateTeam(Team team);

    public void deleteTeam(int id);

}
