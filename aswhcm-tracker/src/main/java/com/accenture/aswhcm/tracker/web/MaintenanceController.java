package com.accenture.aswhcm.tracker.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.accenture.aswhcm.tracker.domain.User;
import com.accenture.aswhcm.tracker.service.MaintenanceService;

@Controller
public class MaintenanceController {

    private static final String URL_MAINTAIN_ADD_USER = "/maintain/adduser";

    private static final String URL_MAINTAIN_UPDATE_USER = "/maintain/updateuser";

    private static final String URL_MAINTAIN_DELETE_USER = "/maintain/deleteuser/{id}";

    @Autowired
    MaintenanceService maintainanceService;

    @SuppressWarnings("rawtypes")
    @RequestMapping(value = URL_MAINTAIN_ADD_USER, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody GeneralResponse addUser(@RequestBody User user) {

        GeneralResponse result = null;

        try {
            int added = maintainanceService.addUser(user);
            if (added == 1) { // true
                result = new GeneralResponse<String>(true, "Added user");
            } else {
                final FaultDetails fault = new FaultDetails("Unable to add user");
                result = new FaultResponse(false, fault);
            }
        } catch (IllegalArgumentException ie) {
            final FaultDetails fault = new FaultDetails(ie.getMessage());
            result = new FaultResponse(false, fault);
        }
        return result;
    }

    @SuppressWarnings("rawtypes")
    @RequestMapping(value = URL_MAINTAIN_UPDATE_USER, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody GeneralResponse updateUser(@RequestBody User user) {

        GeneralResponse result = null;

        try {
            int updated = maintainanceService.updateUser(user);
            if (updated == 1) { // true
                result = new GeneralResponse<String>(true, "Updated user");
            } else {
                final FaultDetails fault = new FaultDetails("Unable to update user");
                result = new FaultResponse(false, fault);
            }
        } catch (IllegalArgumentException ie) {
            final FaultDetails fault = new FaultDetails(ie.getMessage());
            result = new FaultResponse(false, fault);
        }
        return result;
    }

    @SuppressWarnings("rawtypes")
    @RequestMapping(value = URL_MAINTAIN_DELETE_USER, method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody GeneralResponse delete(@PathVariable int id) {

        GeneralResponse result = null;

        try {
            maintainanceService.deleteUser(id);
            result = new GeneralResponse<String>(true, "Deleted user");
        } catch (IllegalArgumentException ie) {
            final FaultDetails fault = new FaultDetails(ie.getMessage());
            result = new FaultResponse(false, fault);
        }
        return result;
    }

}
