package de.krayadev.plugins.RestConnector;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;
import java.net.URI;

@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class MainController {

    @RolesAllowed("Group-Member")
    @RequestMapping(value = "/")
    public ResponseEntity start(){
        return ResponseEntity.status(HttpStatus.FOUND).location(URI.create("/project")).build();
    }

}
