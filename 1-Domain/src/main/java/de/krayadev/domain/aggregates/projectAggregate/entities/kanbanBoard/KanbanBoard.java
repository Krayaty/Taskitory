package de.krayadev.domain.aggregates.projectAggregate.entities.kanbanBoard;

import de.krayadev.domain.aggregates.projectAggregate.valueObjects.Sprint;
import de.krayadev.domain.aggregates.projectAggregate.entities.task.Task;
import com.fasterxml.jackson.annotation.JsonIgnore;
import de.krayadev.domain.aggregates.projectAggregate.entities.task.TaskStatus;
import de.krayadev.domain.valueObjects.Description;
import de.krayadev.domain.valueObjects.Name;
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
@Entity
@Table(name = "kanban_board",
        schema = "backend",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"name", "project"})
        })
public class KanbanBoard {

    @Id
    private final UUID id = UUID.randomUUID();

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "value", column = @Column(length = 100, nullable = false))
    })
    private Name name;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "value", column = @Column(length = 500))
    })
    private Description description;

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

    private boolean contains(@NonNull Task task) {
        return assignedTasks.contains(task);
    }

    public boolean isUsable(){
        return this.sprint.isOver();
    }

    private boolean isMovable(@NonNull Task task) {
        return isTargetable(task.getStatus());
    }

    private boolean isTargetable(@NonNull TaskStatus status) {
        if(status == TaskStatus.REVIEW && !this.showReviewColumn)
            return false;

        if(status == TaskStatus.TESTING && !this.showTestingColumn)
            return false;

        return false;
    }

    public KanbanBoard migrate(@NonNull Sprint newSprint) {
        if(this.isUsable())
            throw new IllegalArgumentException("Kanban board is still usable and can therefore not be migrated. Consider extending the sprint instead");

        return new KanbanBoard(this.name, this.description, newSprint, this.showReviewColumn, this.showTestingColumn, this.project, this.assignedTasks);
    }

    public void rename(@NonNull String newName) {
        this.name = new Name(newName);
    }

    public void changeDescription(String newDescription) {
        this.description = new Description(newDescription);
    }

    public void setEndOfSprint(Timestamp endOfSprint) {
        this.sprint = new Sprint(this.sprint.getStartOfSprint(), endOfSprint);
    }

    public void extendSprint(@NonNull int seconds) {
        long remaining_duration = Duration.between(this.sprint.getEndOfSprint().toInstant(), Timestamp.valueOf(LocalDateTime.now()).toInstant()).toSeconds();
        this.sprint = new Sprint(this.sprint.getStartOfSprint(), Duration.ofSeconds(remaining_duration + seconds));
    }

    public void extendSprint(@NonNull Duration duration) {
        long remaining_duration = Duration.between(this.sprint.getEndOfSprint().toInstant(), Timestamp.valueOf(LocalDateTime.now()).toInstant()).toSeconds();
        this.sprint = new Sprint(this.sprint.getStartOfSprint(), Duration.ofSeconds(remaining_duration + duration.getSeconds()));
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

    public void assign(@NonNull Task task) {
        if(!this.isUsable())
            throw new IllegalStateException("Kanban board is editable after the sprint is over");

        if(this.contains(task))
            throw new IllegalArgumentException("Task is already assigned to this kanban board");

        this.assignedTasks.add(task);
    }

    public Map<TaskStatus, List<Task>> getAssignedTasksPerStatus() {
        Map<TaskStatus, List<Task>> tasksPerStatus = new HashMap<>();
        for(TaskStatus status : TaskStatus.values())
            tasksPerStatus.put(status, new ArrayList<>());

        for(Task task : this.assignedTasks)
            if(this.isMovable(task))
                tasksPerStatus.get(task.getStatus()).add(task);

        return tasksPerStatus;
    }

    public List<Task> getTasksInTodo() {
        return this.assignedTasks.stream().filter(task -> task.hasStatus(TaskStatus.TODO)).toList();
    }

    public List<Task> getTasksInProgress() {
        return this.assignedTasks.stream().filter(task -> task.hasStatus(TaskStatus.IN_PROGRESS)).toList();
    }

    public List<Task> getTasksInReview() {
        if(!this.showReviewColumn)
            return List.of();

        return this.assignedTasks.stream().filter(task -> task.hasStatus(TaskStatus.REVIEW)).toList();
    }

    public List<Task> getTasksInTesting() {
        if(!this.showTestingColumn)
            return List.of();

        return this.assignedTasks.stream().filter(task -> task.hasStatus(TaskStatus.TESTING)).toList();
    }

    public List<Task> getTasksInDone() {
        return this.assignedTasks.stream().filter(Task::isCompleted).toList();
    }

    public void move(@NonNull Task task, @NonNull TaskStatus newState) {
        if(!this.isUsable())
            throw new IllegalArgumentException("Kanban board is not usable because the sprint is over");

        if(!this.contains(task))
            throw new IllegalArgumentException("Task should be assigned to this kanban board");

        if(!this.isMovable(task))
            throw new IllegalArgumentException("Task can not be moved from hidden column");

        if(!this.isTargetable(newState))
            throw new IllegalArgumentException("Task can not be moved to hidden column");

        task.changeStatus(newState);
    }

    public void remove(@NonNull Task task) {
        if(!this.isUsable())
            throw new IllegalStateException("Kanban board is editable after the sprint is over");

        if(!this.contains(task))
            throw new IllegalArgumentException("Task is not assigned to this kanban board");

        if(!this.isMovable(task))
            throw new IllegalArgumentException("Task can not be removed from hidden column");

        this.assignedTasks.remove(task);
    }

    public Map<TaskStatus, Integer> getNumberOfTasksPerState(){
        Map<TaskStatus, Integer> tasksPerState = new HashMap<>();
        for(TaskStatus state : TaskStatus.values()){
            tasksPerState.put(state, 0);
        }
        for (Task task : this.assignedTasks){
            int numTasksOfState =  tasksPerState.get(task.getStatus());
            tasksPerState.put(task.getStatus(), numTasksOfState + 1);
        }
        return tasksPerState;
    }

    public Duration getAvgProcessingDuration(){
        int sum = 0;
        int count = 0;
        for (Task task : this.assignedTasks){
            if(task.isCompleted()){
                sum += task.getProcessingDuration().toSeconds();
                count++;
            }
        }
        return Duration.ofSeconds((int) sum / count);
    }

}
