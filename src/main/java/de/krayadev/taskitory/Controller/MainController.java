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
    private AbhängigeAufgabenRepo abhängigeAufgabenRepo;

    @Autowired
    private AufgabenRepo aufgabenRepo;

    @Autowired
    private KanbanBoardRepo kanbanBoardRepo;

    @Autowired
    private ProjektHatUserRepo projektHatUserRepo;

    @Autowired
    private ProjektRepo projektRepo;

    @Autowired
    private TagRepo tagRepo;

    @Autowired
    private UserRepo userRepo;

    @RolesAllowed("Group-User")
    @RequestMapping(value = "/test")
    public List<User> test(){
        return userRepo.findAll();
    }

}
