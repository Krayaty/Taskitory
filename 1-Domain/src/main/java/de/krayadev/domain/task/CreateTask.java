package de.krayadev.domain.task;

import de.krayadev.domain.user.User;
import de.krayadev.domain.kanbanBoard.KanbanBoard;
import lombok.NonNull;
import org.apache.commons.lang3.builder.Builder;
import de.krayadev.domain.project.Project;
import de.krayadev.domain.tag.Tag;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class CreateTask {

    private String name;

    private String description;

    private int complexity;

    private int priority;

    private Timestamp completedOn;

    private TaskStatus status;

    private Project project;

    private User responsibleUser;

    private User creator;

    private KanbanBoard kanbanBoard;

    private Set<Tag> assignedTags;

    public CreateTask(String name) {
        this.name = name;
        this.completedOn = null;
        this.status = TaskStatus.UNASSIGNED;
        this.complexity = 0;
        this.priority = 0;
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
        return new Task(name, description, complexity, priority, completedOn, status, project, responsibleUser, creator, kanbanBoard, assignedTags);
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
            if (complexity <= 0)
                throw new IllegalArgumentException("Complexity must be positive");

            CreateTask.this.complexity = complexity;
            return this;
        }

        public CreatableTask withPriority(@NonNull int priority) {
            if (priority <= 0)
                throw new IllegalArgumentException("Priority must be positive");

            CreateTask.this.priority = priority;
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
