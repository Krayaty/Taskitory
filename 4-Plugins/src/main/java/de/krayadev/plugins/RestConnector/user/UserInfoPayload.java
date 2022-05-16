package de.krayadev.plugins.RestConnector.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserInfoPayload {
    private String username;
    private String password;
}
