package de.krayadev.domain.aggregates.projectAggregate.entities;

import de.krayadev.domain.aggregates.projectAggregate.entities.kanbanBoard.CreateKanbanBoard;
import de.krayadev.domain.aggregates.projectAggregate.entities.kanbanBoard.KanbanBoard;
import de.krayadev.domain.aggregates.projectAggregate.entities.project.Project;
import de.krayadev.domain.aggregates.projectAggregate.entities.task.CreateTask;
import de.krayadev.domain.aggregates.projectAggregate.entities.task.Task;
import de.krayadev.domain.aggregates.projectAggregate.entities.task.TaskStatus;
import de.krayadev.domain.aggregates.projectAggregate.valueObjects.Sprint;
import de.krayadev.domain.aggregates.userAggregate.entities.user.User;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.time.Duration;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class KanbanBoardTest {

    Random random = new Random();

    private KanbanBoard createKanbanBoard(List<Task> withTasks) {
        return CreateKanbanBoard.named("Name" + random.nextInt(0, 100))
                .forProjectAndSprint(mock(Project.class), new Sprint())
                .withDescription("Description" + random.nextInt(0, 100))
                .withAssignedTasks(withTasks)
                .withShowReviewColumn(random.nextBoolean())
                .withShowTestingColumn(random.nextBoolean())
                .build();
    }

    private Task createTask(KanbanBoard kanbanBoard, TaskStatus status) {
        Task task = CreateTask.named("Name" + random.nextInt(0, 100))
                .forProjectWithCreator(mock(Project.class), mock(User.class))
                .withDescription("Description" + random.nextInt(0, 100))
                .withKanbanBoard(kanbanBoard)
                .build();

        if(status != TaskStatus.TODO)
            task.changeStatus(status);

        return task;

    }

    @Test
    void testMigrate() {
        for (int i = 0; i < 10; i++) {
            Sprint sprint = mock(Sprint.class);
            List<Task> tasks = new ArrayList<>();
            for(int j = 0; j< random.nextInt(10); j++) {
                tasks.add(mock(Task.class));
            }
            KanbanBoard kanbanBoard = createKanbanBoard(tasks);

            KanbanBoard newKanbanBoard = kanbanBoard.migrate(sprint);

            assertNotEquals(kanbanBoard.getId(), newKanbanBoard.getId());
            assertEquals(kanbanBoard.getName(), newKanbanBoard.getName());
            assertEquals(kanbanBoard.getDescription(), newKanbanBoard.getDescription());
            assertEquals(sprint, newKanbanBoard.getSprint());
            assertEquals(kanbanBoard.isShowReviewColumn(), newKanbanBoard.isShowReviewColumn());
            assertEquals(kanbanBoard.isShowTestingColumn(), newKanbanBoard.isShowTestingColumn());
            assertEquals(kanbanBoard.getProject(), newKanbanBoard.getProject());
            assertEquals(kanbanBoard.getAssignedTasks(), newKanbanBoard.getAssignedTasks());
        }
    }

    @Test
    void testExtendSprint() {
        KanbanBoard kanbanBoard = createKanbanBoard(new ArrayList<>());
        Duration duration = Duration.ofSeconds(random.nextInt(0,100));
        Sprint oldSprint = kanbanBoard.getSprint();
        Sprint newSprint = new Sprint(oldSprint.getStartOfSprint(), Timestamp.valueOf(oldSprint.getEndOfSprint().toLocalDateTime().plus(duration)));

        kanbanBoard.extendSprint(duration);

        assertEquals(newSprint, kanbanBoard.getSprint());
    }

    @Test
    void getAssignedTasksPerStatus() {
        KanbanBoard kanbanBoard = createKanbanBoard(new ArrayList<>());
        Map<TaskStatus, List<Task>> tasksPerStatus = new HashMap<>();
        for(TaskStatus status : TaskStatus.values()) {
            if(kanbanBoard.isTargetable(status)) {
                List<Task> tasks = new ArrayList<>();
                for (int i = 0; i < random.nextInt(10); i++) {
                    Task task = createTask(kanbanBoard, status);
                    tasks.add(task);
                    kanbanBoard.assign(task);
                }
                tasksPerStatus.put(status, tasks);
            }
        }

        Map<TaskStatus, List<Task>> calculatedTasksPerStatus = kanbanBoard.getAssignedTasksPerStatus();

        for(Map.Entry<TaskStatus, List<Task>> entry : tasksPerStatus.entrySet()) {
            assertEquals(entry.getValue().size(), calculatedTasksPerStatus.get(entry.getKey()).size());
            for(Task task : entry.getValue())
                assertTrue(calculatedTasksPerStatus.get(entry.getKey()).contains(task));
        }


    }

    @Test
    void move() {
    }

    @Test
    void remove() {
    }

    @Test
    void getNumberOfTasksPerState() {
    }

    @Test
    void getAvgProcessingDuration() {
    }
}
