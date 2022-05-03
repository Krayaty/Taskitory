package de.krayadev.domain.aggregates.projectAggregate.entities.task;

import de.krayadev.domain.aggregates.userAggregate.entities.user.User;
import de.krayadev.domain.aggregates.projectAggregate.valueObjects.Complexity;
import de.krayadev.domain.aggregates.projectAggregate.valueObjects.Priority;
import de.krayadev.domain.aggregates.projectAggregate.valueObjects.TaskLifecycle;
import de.krayadev.domain.valueObjects.Description;
import de.krayadev.domain.valueObjects.Name;
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
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
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
    @AttributeOverrides({ @AttributeOverride(name = "value", column = @Column(name = "complexity", length = 2)) })
    private Complexity complexity = Complexity.NONE;

    @Embedded
    @AttributeOverrides({ @AttributeOverride(name = "value", column = @Column(name = "priority", length = 2)) })
    private Priority priority = Priority.NONE;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "status", column = @Column(nullable = false)),
            @AttributeOverride(name = "createdOn", column = @Column(name = "created_on", length = 6)),
            @AttributeOverride(name = "completedOn", column = @Column(name = "completed_on", length = 6))
    })
    private TaskLifecycle lifecycle;

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

    public boolean isInBacklog() {
        return this.assignedKanbanBoard == null;
    }

    public boolean isOnKanbanBoard() {
        return !this.isInBacklog();
    }

    public TaskStatus getStatus() {
        return this.lifecycle.getStatus();
    }

    public boolean hasStatus(@NonNull TaskStatus status){
        return this.lifecycle.inStatus(status);
    }

    public boolean isResponsible(User user){
        return this.responsibleUser == user;
    }

    public Duration getProcessingDuration() {
        return this.lifecycle.getProcessingDuration();
    }

    public boolean isCompleted() {
        return this.lifecycle.isCompleted();
    }

    public void changeName(@NonNull String newName) {
        this.name = new Name(newName);
    }

    public void changeDescription(String newDescription) {
        this.description = new Description(newDescription);
    }

    public void changeComplexity(@NonNull int complexity) {
        this.complexity = new Complexity(complexity);
    }

    public void changeComplexity(@NonNull Complexity complexity) {
        this.complexity = complexity;
    }

    public void changeStatus(@NonNull TaskStatus newStatus) {
        this.lifecycle.change(newStatus);
    }

}
