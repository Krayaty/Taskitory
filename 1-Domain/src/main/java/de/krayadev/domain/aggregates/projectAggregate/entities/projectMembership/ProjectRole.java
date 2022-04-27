package de.krayadev.domain.aggregates.projectAggregate.entities.projectMembership;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum ProjectRole {
    ADMIN("Projekt-Administrator"),
    MEMBER("Team-Mitglied");

    public final String name;

    public static ProjectRole fromString(String name) {
        for (ProjectRole role : values()) {
            if (role.name.equals(name)) {
                return role;
            }
        }
        throw new IllegalArgumentException("No such role: " + name);
    }
}
