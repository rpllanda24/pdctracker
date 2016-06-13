package com.accenture.aswhcm.tracker.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.accenture.aswhcm.tracker.dao.TeamDao;
import com.accenture.aswhcm.tracker.domain.Team;

@Service("teamService")
public class TeamServiceImpl
    implements TeamService {

    private TeamDao teamDao;

    public TeamDao getTeamDao() {

        return teamDao;
    }

    @Autowired
    public void setTeamDao(TeamDao teamDao) {

        this.teamDao = teamDao;
    }

    public List<Team> getTeams() {

        return getTeamDao().getTeams();
    }

    public Team getTeam(int id) throws IllegalArgumentException {

        Validate.isTrue(id > 0, "invalid ID");
        return getTeamDao().getTeam(id);
    }

    public boolean createTeam(Team team) throws IllegalArgumentException {

        Validate.isTrue(team != null, "Team is null");
        Validate.isTrue(StringUtils.isNotBlank(team.getTeamCode()), "team code is blank/null");
        Validate.isTrue(StringUtils.isNotBlank(team.getTeamDesc()), "team description is blank/null");
        return getTeamDao().createTeam(team);
    }

    @Override
    public boolean updateTeam(Team team) throws IllegalArgumentException {

        Validate.isTrue(team != null, "Team is null");
        return getTeamDao().updateTeam(team);
    }

    public boolean deleteTeam(int id) throws IllegalArgumentException {

        Validate.isTrue(id > 0, "Invalid ID");
        return getTeamDao().deleteTeam(id);
    }

}
