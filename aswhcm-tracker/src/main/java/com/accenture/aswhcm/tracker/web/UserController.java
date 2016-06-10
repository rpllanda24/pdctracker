package com.accenture.aswhcm.tracker.web;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.accenture.aswhcm.tracker.domain.User;
import com.accenture.aswhcm.tracker.service.UserService;

@Controller
public class UserController {

    private static final String URL_GET_USERS = "/userlist";

    private static final String URL_AUTHENTICATE = "/authenticate";

    private static final String URL_CREATE = "/createuser";

    private static final String URL_RESET = "/resetpassword";

    private static final String URL_DELETE = "/deleteuser/{id}";

    @Autowired
    UserService userService;

    @Autowired
    private HttpSession httpSession;

    @SuppressWarnings("rawtypes")
    @RequestMapping(value = URL_GET_USERS, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody GeneralResponse getUsersList() {

        List<User> users = userService.getUsers();
        GeneralResponse result = new GeneralResponse<List<User>>(true, users);

        return result;
    }

    @SuppressWarnings("rawtypes")
    @RequestMapping(value = URL_AUTHENTICATE, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody GeneralResponse login(@RequestBody User user) {

        boolean flag = userService.login(user);
        GeneralResponse result;
        if (flag) {
            httpSession.setAttribute("userId", user.getUserId());
            result = new GeneralResponse<String>(true, (String) httpSession.getAttribute("userId"));
        } else {
            final FaultDetails fault = new FaultDetails("Access denied.");
            result = new FaultResponse(false, fault);
        }

        return result;
    }

    @SuppressWarnings("rawtypes")
    @RequestMapping(value = URL_CREATE, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody GeneralResponse create(@RequestBody User user) {

        GeneralResponse result;
        try {
            boolean created = userService.createUser(user);
            if (created) {
                result = new GeneralResponse<String>(true, "Created user");
            } else {
                final FaultDetails fault = new FaultDetails("Unable to create user");
                result = new FaultResponse(false, fault);
            }
        } catch (IllegalArgumentException ie) {
            final FaultDetails fault = new FaultDetails(ie.getMessage());
            result = new FaultResponse(false, fault);
        }
        return result;
    }

    @SuppressWarnings("rawtypes")
    @RequestMapping(value = URL_DELETE, method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody GeneralResponse delete(@PathVariable int id) {

        GeneralResponse result;

        try {
            userService.deleteUser(id);
            result = new GeneralResponse<String>(true, "Deleted user");
        } catch (IllegalArgumentException ie) {
            final FaultDetails fault = new FaultDetails(ie.getMessage());
            result = new FaultResponse(false, fault);
        }
        return result;
    }

    @SuppressWarnings("rawtypes")
    @RequestMapping(value = URL_RESET, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody GeneralResponse resetPassword(@RequestBody User user) {

        GeneralResponse result;
        try {
            boolean flag = userService.resetPassword(user.getId(), user.getPassw());
            if (flag) {
                httpSession.setAttribute("userId", user.getUserId());
                result = new GeneralResponse<String>(true, "password reset successful");
            } else {
                final FaultDetails fault = new FaultDetails("User doesn't exist");
                result = new FaultResponse(false, fault);
            }
        } catch (IllegalArgumentException ie) {
            final FaultDetails fault = new FaultDetails(ie.getMessage());
            result = new FaultResponse(false, fault);
        }

        return result;
    }
}
