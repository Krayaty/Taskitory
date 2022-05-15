package de.krayadev.domain.aggregates.projectAggregate.entities.message;

public enum MessageType {
    ROLE_CHANGE,
    PROJECT_DELETION,
    NEW_TEAM_MEMBER,
    KICKED_FROM_PROJECT,
    LEFT_PROJECT,
    INVITATION;
}
