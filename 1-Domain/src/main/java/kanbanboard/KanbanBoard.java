package kanbanboard;

import lombok.*;
import project.Project;
import task.Task;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(exclude = {"assignedTasks", "project", "description"})
@EqualsAndHashCode(exclude = {"showReviewColumn", "showTestingColumn", "assignedTasks"})
@Getter
public class KanbanBoard {

    @NonNull
    private String name;

    @NonNull
    private String description;

    @NonNull
    private final Timestamp startOfSprint;

    @NonNull
    private Timestamp endOfSprint;

    @NonNull
    private boolean showReviewColumn;

    @NonNull
    private boolean showTestingColumn;

    @NonNull
    private final Project project;

    @NonNull
    private Set<Task> assignedTasks;

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setEndOfSprint(Timestamp endOfSprint) {
        if (this.startOfSprint.after(endOfSprint))
            throw new IllegalArgumentException("End of sprint must be after start of sprint");

        this.endOfSprint = endOfSprint;
    }

    public void showReviewColumn(){
        this.showReviewColumn = true;
    }

    public void hideReviewColumn(){
        this.showReviewColumn = false;
    }

    public void showTestingColumn() {
        this.showTestingColumn = true;
    }

    public void hideTestingColumn() {
        this .showTestingColumn = false;
    }

    public void assign(Task task){
        this.assignedTasks.add(task);
    }

    public void unassign(Task task){
        this.assignedTasks.remove(task);
    }

    public static KanbanBoard createNullKanbanBoard(){
        return new KanbanBoard("NULL", "NULL", Timestamp.valueOf(LocalDateTime.now()), Timestamp.valueOf(LocalDateTime.now()), false, false, Project.createNullProject(), new HashSet<>());
    }
}
