package de.krayadev.taskitory.Controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api")
public class MainController {

    @RequestMapping(value = "/test", produces = MediaType.APPLICATION_JSON_VALUE)
    public String test(){
        return "Hallo, Welt!";
    }

}
