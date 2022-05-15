package de.krayadev.domain.aggregates.projectAggregate.entities.kanbanBoard;

import de.krayadev.domain.aggregates.projectAggregate.valueObjects.Sprint;
import de.krayadev.domain.aggregates.projectAggregate.entities.task.Task;
import de.krayadev.domain.valueObjects.Description;
import de.krayadev.domain.valueObjects.Name;
import lombok.NonNull;
import org.apache.commons.lang3.builder.Builder;
import de.krayadev.domain.aggregates.projectAggregate.entities.project.Project;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public final class CreateKanbanBoard  {

    private Name name;

    private Description description;

    private Sprint sprint;

    private boolean showReviewColumn;

    private boolean showTestingColumn;

    private Project project;

    private Set<Task> assignedTasks;

    private CreateKanbanBoard(@NonNull String name) {
        this.name = new Name(name);
        this.description = new Description();
        this.showReviewColumn = false;
        this.showTestingColumn = false;
        this.assignedTasks = new HashSet<>();
    }

    public static CreateKanbanBoard named(@NonNull String name) {
        return new CreateKanbanBoard(name);
    }

    public CreatableKanbanBoard forProjectAndSprint(@NonNull Project project, @NonNull Sprint sprint) {
        this.project = project;
        this.sprint = sprint;
        return new CreatableKanbanBoard();
    }

    private KanbanBoard build() {
        return new KanbanBoard(name, description, sprint, showReviewColumn, showTestingColumn, project, assignedTasks);
    }

    public class CreatableKanbanBoard implements Builder<KanbanBoard> {

        @Override
        public KanbanBoard build() {
            return CreateKanbanBoard.this.build();
        }

        public CreatableKanbanBoard withDescription(@NonNull String description) {
            CreateKanbanBoard.this.description = new Description(description);
            return this;
        }

        public CreatableKanbanBoard withDescription(@NonNull Description description) {
            CreateKanbanBoard.this.description = description;
            return this;
        }

        public CreatableKanbanBoard withAssignedTasks(@NonNull Collection<Task> assignedTasks) {
            CreateKanbanBoard.this.assignedTasks.addAll(assignedTasks);
            return this;
        }

        public CreatableKanbanBoard withAssignedTask(@NonNull Task assignedTask) {
            CreateKanbanBoard.this.assignedTasks.add(assignedTask);
            return this;
        }

        public CreatableKanbanBoard withShowReviewColumn(@NonNull boolean showReviewColumn) {
            CreateKanbanBoard.this.showReviewColumn = showReviewColumn;
            return this;
        }

        public CreatableKanbanBoard withShowTestingColumn(@NonNull boolean showTestingColumn) {
            CreateKanbanBoard.this.showTestingColumn = showTestingColumn;
            return this;
        }
    }

}
