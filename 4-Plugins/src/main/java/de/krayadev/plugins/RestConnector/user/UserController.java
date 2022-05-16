package de.krayadev.plugins.RestConnector.user;

import de.krayadev.plugins.IamConnector.KeycloakSecurityConfig;
import org.keycloak.representations.AccessToken;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
@RequestMapping(value = "/api/user", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

    private String iamEndpoint = "http://localhost:50000/auth/admin/realms/Taskitory-Realm/users";

    @RolesAllowed("Group-Member")
    @RequestMapping(value = "/", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity createUser(HttpServletRequest request, @RequestBody UserInfoPayload body){
        String username = body.getUsername();
        String password = body.getPassword();
        if(username == null || password == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

        /*String payload = "{\"username\":\"" + username + "\",\"enabled\":true,\"credentials\":[{\"type\":\"password\",\"value\":\"" + password + "\"}]}";

        return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(iamEndpoint))
                .header("Authorization", "Bearer" + "eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICIwMXlfWW93Y2F4WmI5UDhKcUNDamtaMEtGNG5DdkRJNmsyOGZGaHByWmE0In0.eyJleHAiOjE2NTI2OTczOTYsImlhdCI6MTY1MjY5NzA5NiwianRpIjoiZmU3ODQ0ODQtZTdlYy00NmUwLWIyZDgtYzIyYWZhZWFhZmZlIiwiaXNzIjoiaHR0cDovL2lhbTo4MDgwL2F1dGgvcmVhbG1zL1Rhc2tpdG9yeS1SZWFsbSIsImF1ZCI6WyJUYXNraXRvcnktQ2xpZW50IiwiYWNjb3VudCJdLCJzdWIiOiI2YTI2YWRjZS01MjdiLTRiYzMtYjExYS1hMDZjOTk4N2ZmYTUiLCJ0eXAiOiJCZWFyZXIiLCJhenAiOiJUYXNraXRvcnktQ2xpZW50IiwiYWNyIjoiMSIsInJlYWxtX2FjY2VzcyI6eyJyb2xlcyI6WyJkZWZhdWx0LXJvbGVzLXRhc2tpdG9yeS1yZWFsbSIsIm9mZmxpbmVfYWNjZXNzIiwidW1hX2F1dGhvcml6YXRpb24iXX0sInJlc291cmNlX2FjY2VzcyI6eyJhY2NvdW50Ijp7InJvbGVzIjpbIm1hbmFnZS1hY2NvdW50IiwibWFuYWdlLWFjY291bnQtbGlua3MiLCJ2aWV3LXByb2ZpbGUiXX19LCJzY29wZSI6Imdvb2Qtc2VydmljZSIsImNsaWVudEhvc3QiOiIxNzIuMTguMC4xIiwiY2xpZW50SWQiOiJUYXNraXRvcnktQ2xpZW50IiwicHJlZmVycmVkX3VzZXJuYW1lIjoic2VydmljZS1hY2NvdW50LXRhc2tpdG9yeS1jbGllbnQiLCJjbGllbnRBZGRyZXNzIjoiMTcyLjE4LjAuMSJ9.UCqvFZLf6x3nYUeU8SubornPo5lba4Wlum5U-pJ9eKusa1S9OQDp3KAcXjtCBnhzaJToMQd5C2atwAYaJyyBm4zep02tAJ7ZHH6pHDzgFy4LUO4chTy4nc3t2yUjWi7Tf2Gpphj1Kmt_m9N-PgJ79hVsir-JmN22VukSXrKZ_4fCxxosAAJlFyMT0ol7dJfxONLSt0hbfKBityIeKQTUklKduw1tjX4yfgmQS64md2_uOknsPwuqcHED0evkDSOIn7sWxHGtGfq5q6up0YgW0BI4ukjotJOF92qQCnrnAG3fP_UCxyPuMgQY0FNn3VuQfER2ahvwq0y7knpy9Jbl9g")//getAccessTokenString(request))
                .body(payload);

         */
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }

    @RolesAllowed("Group-Member")
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String getUserInfo(HttpServletRequest request){
        AccessToken token = KeycloakSecurityConfig.getAccessToken(request);
        return token.getPreferredUsername();
    }

    @RolesAllowed("Group-Member")
    @RequestMapping(value = "/", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity updateUser(HttpServletRequest request, @RequestBody UserInfoPayload body) throws IOException {
        String username = body.getUsername();
        String password = body.getPassword();
        if(username == null && password == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }

    @RolesAllowed("Group-Member")
    @RequestMapping(value = "/", method = RequestMethod.DELETE)
    public ResponseEntity deleteUser(HttpServletRequest request){
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
        //return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(iamEndpoint + "/" + getUserId(request))).build();
    }
}
