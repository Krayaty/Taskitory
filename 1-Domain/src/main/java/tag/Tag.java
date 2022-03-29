package tag;

import lombok.*;
import project.Project;
import task.Task;

import java.util.HashSet;
import java.util.Set;

@RequiredArgsConstructor
@ToString(exclude = {"assignedTo"})
@EqualsAndHashCode(exclude = {"assignedTo", "description"})
@Getter
public class Tag {

    @NonNull
    private String name;

    @NonNull
    private String description;

    @NonNull
    private final Project project;

    private Set<Task> assignedTo = new HashSet<Task>();

    public void setDescription(String description) {
        this.description = description;
    }

    public void assignTo(Task task){
        this.assignedTo.add(task);
    }

    public void unassignFrom(Task task){
        this.assignedTo.remove(task);
    }

}
