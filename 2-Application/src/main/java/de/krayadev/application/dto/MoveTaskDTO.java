package de.krayadev.application.dto;

import de.krayadev.domain.aggregates.projectAggregate.entities.task.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@AllArgsConstructor
@Getter
public class MoveTaskDTO {
    private String projectName;
    private UUID kanbanBoardId;
    private UUID taskId;
    private TaskStatus newStatus;
    private boolean moveToBacklog;
}
