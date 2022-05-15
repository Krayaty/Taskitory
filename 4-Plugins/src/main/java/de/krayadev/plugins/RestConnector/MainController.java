package de.krayadev.plugins.RestConnector;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;

@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class MainController {

    @RolesAllowed("Group-Member")
    @RequestMapping(value = "/test")
    public String test(){
        return "Hallo, Welt!";
    }

}
