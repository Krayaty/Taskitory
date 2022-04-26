package de.krayadev.domain.kanbanBoard;

import de.krayadev.domain.task.Task;
import lombok.NonNull;
import org.apache.commons.lang3.builder.Builder;
import de.krayadev.domain.project.Project;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class CreateKanbanBoard  {

    private String name;

    private String description;

    private Timestamp startOfSprint;

    private Timestamp endOfSprint;

    private boolean showReviewColumn = false;

    private boolean showTestingColumn = false;

    private Project project;

    private Set<Task> assignedTasks;

    private CreateKanbanBoard(@NonNull String name) {
        this.name = name;
        this.description = "";
        this.startOfSprint = Timestamp.valueOf(LocalDateTime.now());
        this.showReviewColumn = false;
        this.showTestingColumn = false;
        this.assignedTasks = new HashSet<>();
    }

    public static CreateKanbanBoard named(@NonNull String name) {
        return new CreateKanbanBoard(name);
    }

    public CreatableKanbanBoard forProjectAndEndOfSprint(@NonNull Project project, @NonNull Timestamp endOfSprint) {
        this.project = project;
        this.endOfSprint = endOfSprint;
        return new CreatableKanbanBoard();
    }

    private KanbanBoard build() {
        return new KanbanBoard(name, description, startOfSprint, endOfSprint, showReviewColumn, showTestingColumn, project, assignedTasks);
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

        public CreatableKanbanBoard withStartOfSprint(@NonNull Timestamp startOfSprint) {
            if (CreateKanbanBoard.this.endOfSprint.after(startOfSprint)) {
                throw new IllegalArgumentException("Start of sprint must be before end of sprint");
            }

            CreateKanbanBoard.this.startOfSprint = startOfSprint;
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
