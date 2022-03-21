package de.krayadev.taskitory.Controller;

import de.krayadev.taskitory.dbConnector.model.User;
import de.krayadev.taskitory.dbConnector.repos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class MainController {

    @Autowired
    private UserRepo userRepo;

    @RolesAllowed("Group-Member")
    @RequestMapping(value = "/test")
    public List<User> test(){
        return userRepo.findAll();
    }

}
