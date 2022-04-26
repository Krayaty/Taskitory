package de.krayadev.domain.entities.projectMembership;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum ProjectRole {
    ADMIN("Projekt-Administrator"),
    MEMBER("Team-Mitglied");

    public final String name;
}
