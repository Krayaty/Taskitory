package de.krayadev.domain.aggregates.projectAggregate.entities.task;

import lombok.Getter;

@Getter
public enum TaskStatus {
    TODO,
    IN_PROGRESS,
    REVIEW,
    TESTING,
    DONE;
}
