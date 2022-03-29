package projectMembership;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum Role {
    ADMIN("Projekt-Administrator"),
    USER("Team-Mitglied");

    public final String name;
}
