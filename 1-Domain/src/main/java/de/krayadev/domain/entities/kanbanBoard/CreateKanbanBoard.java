package de.krayadev.domain.entities.kanbanBoard;

import de.krayadev.domain.entities.task.Task;
import de.krayadev.domain.valueobjects.Sprint;
import lombok.NonNull;
import org.apache.commons.lang3.builder.Builder;
import de.krayadev.domain.entities.project.Project;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class CreateKanbanBoard  {

    private String name;

    private String description;

    private Sprint sprint;

    private boolean showReviewColumn = false;

    private boolean showTestingColumn = false;

    private Project project;

    private Set<Task> assignedTasks;

    private CreateKanbanBoard(@NonNull String name) {
        this.name = name;
        this.description = "";
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

    class CreatableKanbanBoard implements Builder<KanbanBoard> {

        @Override
        public KanbanBoard build() {
            return CreateKanbanBoard.this.build();
        }

        public CreatableKanbanBoard withDescription(@NonNull String description) {
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
