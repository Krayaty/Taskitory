package krayadev.domain.kanbanboard;

import krayadev.domain.task.Task;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import krayadev.domain.project.Project;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(exclude = {"assignedTasks"})
@EqualsAndHashCode(of = "id")
@Getter
@Setter
@Entity
@Table(name = "kanban_board",
        schema = "backend",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"name", "project"})
        })
public class KanbanBoard {

    @Id
    private final UUID id = UUID.randomUUID();

    @Column(length = 100, nullable = false)
    private String name;

    @Column(length = 500)
    private String description;

    @Column(name = "start_of_sprint", length = 6, nullable = false, updatable = false)
    private Timestamp startOfSprint;

    @Column(name = "end_of_sprint", length = 6, nullable = false, updatable = false)
    private Timestamp endOfSprint;

    @Column(name = "show_review_column", nullable = false)
    private boolean showReviewColumn = false;

    @Column(name = "show_testing_column", nullable = false)
    private boolean showTestingColumn = false;

    @ManyToOne(targetEntity = Project.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "project", referencedColumnName = "name", nullable = false, updatable = false)
    private Project project;

    @OneToMany(mappedBy = "assignedToKanbanBoard", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Task> assignedTasks = new HashSet<>();

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

}
