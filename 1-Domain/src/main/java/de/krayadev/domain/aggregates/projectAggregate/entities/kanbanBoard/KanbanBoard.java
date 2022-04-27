package de.krayadev.domain.aggregates.projectAggregate.entities.kanbanBoard;

import de.krayadev.domain.aggregates.projectAggregate.valueObjects.Sprint;
import de.krayadev.domain.aggregates.taskAggregate.entities.task.Task;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import de.krayadev.domain.aggregates.projectAggregate.entities.project.Project;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Duration;
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

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "startOfSprint", column = @Column(name = "start_of_sprint", length = 6, nullable = false, updatable = false)),
            @AttributeOverride(name = "endOfSprint", column = @Column(name = "end_of_sprint", length = 6, nullable = false, updatable = false))
    })
    private Sprint sprint;

    @Column(name = "show_review_column", nullable = false)
    private boolean showReviewColumn = false;

    @Column(name = "show_testing_column", nullable = false)
    private boolean showTestingColumn = false;

    @ManyToOne(targetEntity = Project.class)
    @JoinColumn(name = "project", referencedColumnName = "name", nullable = false, updatable = false)
    private Project project;

    @OneToMany(mappedBy = "assignedToKanbanBoard", cascade = CascadeType.MERGE)
    @JsonIgnore
    private Set<Task> assignedTasks = new HashSet<>();

    public void setName(@NonNull String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setEndOfSprint(Timestamp endOfSprint) {
        this.sprint = new Sprint(this.sprint.getStartOfSprint(), endOfSprint);
    }

    public void setEndOfSprint(@NonNull int duration) {
        this.sprint = new Sprint(this.sprint.getStartOfSprint(), Duration.ofSeconds(duration));
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
        this.showTestingColumn = false;
    }

    public void assign(@NonNull Task task){
        this.assignedTasks.add(task);
    }

    public void unassign(@NonNull Task task){
        this.assignedTasks.remove(task);
    }

}
