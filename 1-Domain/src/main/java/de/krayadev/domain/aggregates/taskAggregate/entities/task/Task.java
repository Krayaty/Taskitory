package de.krayadev.domain.aggregates.taskAggregate.entities.task;

import de.krayadev.domain.aggregates.userAggregate.entities.user.User;
import de.krayadev.domain.aggregates.taskAggregate.valueObjects.Complexity;
import de.krayadev.domain.aggregates.taskAggregate.valueObjects.Priority;
import de.krayadev.domain.aggregates.taskAggregate.valueObjects.TaskLifeTime;
import lombok.*;
import de.krayadev.domain.aggregates.projectAggregate.entities.kanbanBoard.KanbanBoard;
import de.krayadev.domain.aggregates.userAggregate.entities.tag.Tag;
import de.krayadev.domain.aggregates.projectAggregate.entities.project.Project;

import javax.persistence.*;
import java.time.Duration;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor
@Getter
@Entity
@Table(name = "task",
        schema = "backend",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"name", "project"})
        })
public class Task {

    @Id
    private final UUID id = UUID.randomUUID();

    @Column(length = 100, nullable = false)
    private String name;

    @Column(length = 500)
    private String description;

    @Embedded
    @AttributeOverrides({ @AttributeOverride(name = "value", column = @Column(name = "complexity", length = 2)) })
    private Complexity complexity = Complexity.NONE;

    @Embedded
    @AttributeOverrides({ @AttributeOverride(name = "value", column = @Column(name = "priority", length = 2)) })
    private Priority priority = Priority.NONE;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "createdOn", column = @Column(name = "created_on", length = 6)),
            @AttributeOverride(name = "completedOn", column = @Column(name = "completed_on", length = 6))
    })
    private TaskLifeTime lifeTime;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TaskStatus status = TaskStatus.TODO;

    @ManyToOne(targetEntity = Project.class)
    @JoinColumn(name = "project", referencedColumnName = "name", nullable = false, updatable = false)
    private Project project;

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "responsible_user", referencedColumnName = "id")
    private User responsibleUser;

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "creator", referencedColumnName = "id")
    private User creator;

    @ManyToOne(targetEntity = KanbanBoard.class)
    @JoinColumn(name = "kanban_board", referencedColumnName = "id")
    private KanbanBoard assignedKanbanBoard;

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(
            name = "task_tag_assignment",
            joinColumns = { @JoinColumn(name = "task") },
            inverseJoinColumns = { @JoinColumn(name = "tag") }
    )
    private Set<Tag> assignedTags = new HashSet<>();

    public boolean belongsTo(@NonNull Project project) {
        return this.project.equals(project);
    }

    public boolean isResponsible(User user){
        return this.responsibleUser == user;
    }

    public Duration getProcessingDuration() {
        return this.lifeTime.getProcessingDuration();
    }

    public boolean completed() {
        return this.lifeTime.isCompleted();
    }

    private boolean isAssignedTo(KanbanBoard kanbanBoard){
        return this.assignedKanbanBoard.equals(kanbanBoard);
    }

    public void changeName(@NonNull String newName) {
        this.name = newName;
    }

    public void changeDescription(String newDescription) {
        this.description = "";
        if (newDescription != null)
            this.description = newDescription;
    }

    public void changeComplexity(@NonNull int complexity) {
        this.complexity = new Complexity(complexity);
    }

    public void changeComplexity(@NonNull Complexity complexity) {
        this.complexity = complexity;
    }

    public void changeStatus(@NonNull TaskStatus newStatus) {
        if(this.status == newStatus)
            throw new IllegalArgumentException("Task status cannot be changed to the same status");

        if (this.status == TaskStatus.DONE)
            this.lifeTime.reopen();

        this.status = newStatus;
    }

    public void moveToBacklog() {
        this.assignedKanbanBoard = null;
    }

    public void assign(@NonNull Tag tag){
        if(this.assignedTags.contains(tag))
            throw new IllegalArgumentException("Task is already assigned to this tag");

        this.assignedTags.add(tag);
    }

    public void unassign(Tag tag){
        if(!this.assignedTags.contains(tag))
            throw new IllegalArgumentException("Task is not assigned to this tag");

        this.assignedTags.remove(tag);
    }

}
