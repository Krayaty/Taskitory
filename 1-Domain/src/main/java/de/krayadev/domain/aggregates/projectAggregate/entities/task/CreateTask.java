package de.krayadev.domain.aggregates.projectAggregate.entities.task;

import de.krayadev.domain.aggregates.userAggregate.entities.user.User;
import de.krayadev.domain.aggregates.projectAggregate.entities.kanbanBoard.KanbanBoard;
import de.krayadev.domain.aggregates.projectAggregate.valueObjects.Complexity;
import de.krayadev.domain.aggregates.projectAggregate.valueObjects.Priority;
import de.krayadev.domain.aggregates.projectAggregate.valueObjects.TaskLifecycle;
import de.krayadev.domain.valueObjects.Description;
import de.krayadev.domain.valueObjects.Name;
import lombok.NonNull;
import org.apache.commons.lang3.builder.Builder;
import de.krayadev.domain.aggregates.projectAggregate.entities.project.Project;
import de.krayadev.domain.aggregates.userAggregate.entities.tag.Tag;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class CreateTask {

    private Name name;

    private Description description;

    private Complexity complexity;

    private Priority priority;

    private TaskLifecycle lifecycle;

    private TaskStatus status;

    private Project project;

    private User responsibleUser;

    private User creator;

    private KanbanBoard kanbanBoard;

    private Set<Tag> assignedTags;

    public CreateTask(String name) {
        this.name = new Name(name);
        this.description = new Description();
        this.lifecycle = new TaskLifecycle();
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
        return new Task(name, description, complexity, priority, lifecycle, project, responsibleUser, creator, kanbanBoard, assignedTags);
    }

    class CreatableTask implements Builder<Task> {

        @Override
        public Task build() {
            return CreateTask.this.build();
        }

        public CreatableTask withDescription(@NonNull String description) {
            CreateTask.this.description = new Description(description);
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
