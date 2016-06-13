package com.accenture.aswhcm.tracker.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.accenture.aswhcm.tracker.domain.Team;
import com.accenture.aswhcm.tracker.service.TeamService;

@Controller
public class TeamController {

    private static final String URL_CREATE_TEAM = "/createteam";

    private static final String URL_UPDATE_TEAM = "/modifyteam";

    private static final String URL_DELETE_TEAM = "/deleteteam/{id}";

    @Autowired
    TeamService teamService;

    @SuppressWarnings("rawtypes")
    @RequestMapping(value = URL_CREATE_TEAM, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody GeneralResponse createTeam(@RequestBody Team team) {

        GeneralResponse result = null;

        try {
            boolean created = teamService.createTeam(team);
            if (created) { // true
                result = new GeneralResponse<String>(true, "Team is added");
            } else {
                final FaultDetails fault = new FaultDetails("Unable to add team");
                result = new FaultResponse(false, fault);
            }
        } catch (IllegalArgumentException ie) {
            final FaultDetails fault = new FaultDetails(ie.getMessage());
            result = new FaultResponse(false, fault);
        }
        return result;
    }

    @SuppressWarnings("rawtypes")
    @RequestMapping(value = URL_UPDATE_TEAM, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody GeneralResponse updateTeam(@RequestBody Team team) {

        GeneralResponse result = null;

        try {
            boolean updated = teamService.updateTeam(team);
            if (updated) {
                result = new GeneralResponse<String>(true, "Team is updated");
            } else {
                final FaultDetails fault = new FaultDetails("Unable to update team");
                result = new FaultResponse(false, fault);
            }
        } catch (IllegalArgumentException ie) {
            final FaultDetails fault = new FaultDetails(ie.getMessage());
            result = new FaultResponse(false, fault);
        }
        return result;
    }

    @SuppressWarnings("rawtypes")
    @RequestMapping(value = URL_DELETE_TEAM, method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody GeneralResponse deleteTeam(@PathVariable int id) {

        GeneralResponse result = null;

        try {
            teamService.deleteTeam(id);
            result = new GeneralResponse<String>(true, "Deleted team");
        } catch (IllegalArgumentException ie) {
            final FaultDetails fault = new FaultDetails(ie.getMessage());
            result = new FaultResponse(false, fault);
        }
        return result;
    }
}
