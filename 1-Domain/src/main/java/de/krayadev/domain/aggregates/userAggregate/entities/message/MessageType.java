package de.krayadev.domain.aggregates.userAggregate.entities.message;

public enum MessageType {
    ROLE_CHANGE,
    PROJECT_DELETION,
    KICKED_FROM_PROJECT,
    LEFT_PROJECT,
    INVITATION;
}
