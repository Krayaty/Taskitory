package task;

import lombok.Getter;

@Getter
public enum TaskStatus {
    UNASSIGNED,
    TODO,
    IN_PROGRESS,
    REVIEW,
    TESTING,
    DONE;
}
