package de.krayadev.domain.aggregates.projectAggregate.valueObjects;

import de.krayadev.domain.aggregates.projectAggregate.entities.task.TaskStatus;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TaskLifecycleTest {

    @Test
    void testChange() {
        TaskLifecycle taskLifecycle = new TaskLifecycle();
        for(TaskStatus preChangeStatus : TaskStatus.values()) {
            if(preChangeStatus != TaskStatus.TODO)
                taskLifecycle.change(preChangeStatus);

            for (TaskStatus status : TaskStatus.values()) {
                if(preChangeStatus == status) {
                    assertThrows(IllegalArgumentException.class, () -> taskLifecycle.change(status));
                    return;
                }

                taskLifecycle.change(status);
                assertEquals(status, taskLifecycle.getStatus());

                if(preChangeStatus == TaskStatus.DONE) {
                    assertEquals(null, taskLifecycle.getCompletedOn());
                } else if(status == TaskStatus.DONE) {
                    assertEquals(LocalDateTime.now().getSecond(), taskLifecycle.getCompletedOn().toLocalDateTime().getSecond());
                }
            }
        }
    }

    @Test
    void testGetProcessingDuration() {
        TaskLifecycle taskLifecycle = new TaskLifecycle();
        Timestamp createdOn = Timestamp.valueOf(LocalDateTime.now());
        assertThrows(IllegalStateException.class, () -> taskLifecycle.getProcessingDuration());

        taskLifecycle.change(TaskStatus.DONE);
        Timestamp completedOn = Timestamp.valueOf(LocalDateTime.now());
        Duration processionDuration = Duration.between(createdOn.toLocalDateTime(), completedOn.toLocalDateTime());
        assertEquals(processionDuration.toSeconds(), taskLifecycle.getProcessingDuration().toSeconds());
    }
}
