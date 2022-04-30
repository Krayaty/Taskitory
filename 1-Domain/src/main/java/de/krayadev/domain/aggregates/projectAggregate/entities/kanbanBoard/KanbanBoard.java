package de.krayadev.domain.aggregates.projectAggregate.entities.kanbanBoard;

import de.krayadev.domain.aggregates.projectAggregate.valueObjects.Sprint;
import de.krayadev.domain.aggregates.taskAggregate.entities.task.Task;
import com.fasterxml.jackson.annotation.JsonIgnore;
import de.krayadev.domain.aggregates.taskAggregate.entities.task.TaskStatus;
import lombok.*;
import de.krayadev.domain.aggregates.projectAggregate.entities.project.Project;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

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

    @OneToMany(mappedBy = "assignedKanbanBoard", cascade = CascadeType.MERGE)
    @JsonIgnore
    private Set<Task> assignedTasks = new HashSet<>();

    public KanbanBoard migrate(@NonNull Sprint newSprint) {
        return new KanbanBoard(this.name, this.description, newSprint, this.showReviewColumn, this.showTestingColumn, this.project, this.assignedTasks);
    }

    public boolean belongsTo(@NonNull Project project) {
        return this.project.equals(project);
    }

    public boolean contains(@NonNull Task task) {
        return assignedTasks.contains(task);
    }

    public boolean isUsable(){
        return this.sprint.isOver();
    }

    public void rename(@NonNull String newName) {
        this.name = newName;
    }

    public void changeDescription(String newDescription) {
        this.description = newDescription;
    }

    public void setEndOfSprint(Timestamp endOfSprint) {
        this.sprint = new Sprint(this.sprint.getStartOfSprint(), endOfSprint);
    }

    public void extendSprint(@NonNull int duration) {
        long remaining_duration = Duration.between(this.sprint.getEndOfSprint().toInstant(), Timestamp.valueOf(LocalDateTime.now()).toInstant()).toSeconds();
        this.sprint = new Sprint(this.sprint.getStartOfSprint(), Duration.ofSeconds(remaining_duration + duration));
    }

    public void assign(@NonNull Task task) {
        if(!this.isUsable())
            throw new IllegalStateException("Kanban board is editable after the sprint is over");

        if(!this.project.equals(task.getProject()))
            throw new IllegalArgumentException("Task and kanban board are not part of the same project");

        if(this.contains(task))
            throw new IllegalArgumentException("Task is already assigned to this kanban board");

        this.assignedTasks.add(task);
    }

    public void remove(@NonNull Task task) {
        if(!this.isUsable())
            throw new IllegalStateException("Kanban board is editable after the sprint is over");

        if(!this.project.equals(task.getProject()))
            throw new IllegalArgumentException("Task and kanban board are not part of the same project");

        if(!this.contains(task))
            throw new IllegalArgumentException("Task is not assigned to this kanban board");

        this.assignedTasks.remove(task);
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

    public Map<TaskStatus, Integer> getTasksPerState(){
        Map<TaskStatus, Integer> tasksPerState = new HashMap<>();
        for(TaskStatus state : TaskStatus.values()){
            tasksPerState.put(state, 0);
        }
        for (Task task : this.assignedTasks){
            tasksPerState.put(task.getStatus(), tasksPerState.get(task.getStatus()) + 1);
        }
        return tasksPerState;
    }

    public Duration getAvgProcessingDuration(){
        int sum = 0;
        int count = 0;
        for (Task task : this.assignedTasks){
            if(task.completed()){
                sum += task.getProcessingDuration().toSeconds();
                count++;
            }
        }
        return Duration.ofSeconds((int) sum / count);
    }

}
