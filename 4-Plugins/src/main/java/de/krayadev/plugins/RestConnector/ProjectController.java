package de.krayadev.plugins.RestConnector;

import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;

@RestController
@RequestMapping(value = "/api/project", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProjectController {

    @RolesAllowed("Group-Member")
    @RequestMapping(value = "/", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity createProject(@RequestBody JSONObject body) {
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }

    @RolesAllowed("Group-Member")
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity getProjects() {
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }

    @RolesAllowed("Group-Member")
    @RequestMapping(value = "/{projectName}", method = RequestMethod.GET)
    public ResponseEntity getProject(@PathVariable String projectName) {
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }

    @RolesAllowed("Group-Member")
    @RequestMapping(value = "/{projectName}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity updateProject(@PathVariable String projectName, @RequestBody JSONObject body) {
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }

    @RolesAllowed("Group-Member")
    @RequestMapping(value = "/{projectName}", method = RequestMethod.DELETE)
    public ResponseEntity deleteProject(@PathVariable String projectName) {
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }

}
