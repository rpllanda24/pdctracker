package com.accenture.aswhcm.tracker.service;

import org.apache.commons.lang.Validate;
import org.springframework.beans.factory.annotation.Autowired;

import com.accenture.aswhcm.tracker.dao.TeamDao;
import com.accenture.aswhcm.tracker.domain.Team;

public class TeamServiceImpl
    implements TeamService {

    @Autowired
    TeamDao teamDao;

    @Override
    public int createTeam(Team team) {

        Validate.isTrue(team.getTeamCode() != null);
        Validate.isTrue(team.getTeamDesc() != null);

        boolean isCreated;
        int created = 0;
        isCreated = teamDao.createTeam(team);
        if (isCreated) {
            created = 1;
        }
        return created;
    }

    @Override
    public int updateTeam(Team team) {

        Validate.isTrue(team.getTeamCode() != null);
        Validate.isTrue(team.getTeamDesc() != null);

        boolean isUpdated;
        int updated = 0;
        isUpdated = teamDao.createTeam(team);
        if (isUpdated) {
            updated = 1;
        }
        return updated;
    }

    @Override
    public void deleteTeam(int id) {

        Validate.notNull(id);

        teamDao.deleteTeam(id);

    }

}
