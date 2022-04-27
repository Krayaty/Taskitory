package de.krayadev.domain.aggregates.taskAggregate.entities.task;

import de.krayadev.domain.aggregates.userAggregate.entities.user.User;
import de.krayadev.domain.aggregates.projectAggregate.entities.kanbanBoard.KanbanBoard;
import de.krayadev.domain.aggregates.taskAggregate.valueObjects.Complexity;
import de.krayadev.domain.aggregates.taskAggregate.valueObjects.Priority;
import de.krayadev.domain.aggregates.taskAggregate.valueObjects.TaskLifeTime;
import lombok.NonNull;
import org.apache.commons.lang3.builder.Builder;
import de.krayadev.domain.aggregates.projectAggregate.entities.project.Project;
import de.krayadev.domain.aggregates.taskAggregate.entities.tag.Tag;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class CreateTask {

    private String name;

    private String description;

    private Complexity complexity;

    private Priority priority;

    private TaskLifeTime lifeTime;

    private TaskStatus status;

    private Project project;

    private User responsibleUser;

    private User creator;

    private KanbanBoard kanbanBoard;

    private Set<Tag> assignedTags;

    public CreateTask(String name) {
        this.name = name;
        this.description = "";
        this.lifeTime = new TaskLifeTime();
        this.status = TaskStatus.UNASSIGNED;
        this.complexity = Complexity.NONE;
        this.priority = Priority.NONE;
        this.responsibleUser = null;
        this.kanbanBoard = null;
        this.assignedTags = new HashSet<Tag>();
    }

    public static CreateTask named(String name) {
        return new CreateTask(name);
    }

    public CreatableTask forProjectWithCreator(Project project, User creator) {
        this.project = project;
        this.creator = creator;
        return new CreatableTask();
    }

    private Task build() {
        return new Task(name, description, complexity, priority, lifeTime, status, project, responsibleUser, creator, kanbanBoard, assignedTags);
    }

    class CreatableTask implements Builder<Task> {

        @Override
        public Task build() {
            return CreateTask.this.build();
        }

        public CreatableTask withDescription(@NonNull String description) {
            CreateTask.this.description = description;
            return this;
        }

        public CreatableTask withComplexity(@NonNull int complexity) {
            CreateTask.this.complexity = new Complexity(complexity);
            return this;
        }

        public CreatableTask withPriority(@NonNull int priority) {
            CreateTask.this.priority = new Priority(priority);
            return this;
        }

        public CreatableTask withStatus(@NonNull TaskStatus status) {
            CreateTask.this.status = status;
            return this;
        }

        public CreatableTask withResponsibleUser(@NonNull User responsibleUser) {
            CreateTask.this.responsibleUser = responsibleUser;
            return this;
        }

        public CreatableTask withKanbanBoard(@NonNull KanbanBoard kanbanBoard) {
            CreateTask.this.kanbanBoard = kanbanBoard;
            return this;
        }

        public CreatableTask withAssignedTags(@NonNull Collection<Tag> assignedTags) {
            CreateTask.this.assignedTags.addAll(assignedTags);
            return this;
        }
    }
}
