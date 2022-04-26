package de.krayadev.domain.entities.task;

import de.krayadev.domain.entities.user.User;
import de.krayadev.domain.valueobjects.Complexity;
import de.krayadev.domain.valueobjects.Priority;
import de.krayadev.domain.valueobjects.TaskLifeTime;
import lombok.*;
import de.krayadev.domain.entities.kanbanBoard.KanbanBoard;
import de.krayadev.domain.entities.tag.Tag;
import de.krayadev.domain.entities.project.Project;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor
@Getter
@Setter
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
    private TaskStatus status = TaskStatus.UNASSIGNED;

    @ManyToOne(targetEntity = Project.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "project", referencedColumnName = "name", nullable = false, updatable = false)
    private Project project;

    @ManyToOne(targetEntity = User.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "responsible_user", referencedColumnName = "id")
    private User responsibleUser;

    @ManyToOne(targetEntity = User.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "creator", referencedColumnName = "id")
    private User creator;

    @ManyToOne(targetEntity = KanbanBoard.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "kanban_board", referencedColumnName = "id")
    private KanbanBoard assignedToKanbanBoard;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "task_tag_assignment",
            joinColumns = { @JoinColumn(name = "task") },
            inverseJoinColumns = { @JoinColumn(name = "tag") }
    )
    private Set<Tag> assignedTags = new HashSet<>();

    public void setName(@NonNull String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = "";
        if (description != null)
            this.description = description;
    }

    public void setComplexity(@NonNull int complexity) {
        this.complexity = new Complexity(complexity);
    }

    public void setComplexity(@NonNull Complexity complexity) {
        this.complexity = complexity;
    }

    public void changeStatus(@NonNull TaskStatus toStatus) {
        if(toStatus == TaskStatus.UNASSIGNED){
            this.moveToBacklog();
            return;
        }

        if (this.status == TaskStatus.DONE)
            this.lifeTime.reopen();

        this.status = toStatus;
    }

    public void moveToBacklog() {
        this.status = TaskStatus.UNASSIGNED;
    }

    public void assignTo(@NonNull KanbanBoard kanbanBoard) {
        this.assignedToKanbanBoard = kanbanBoard;
    }

    public void unassignFromKanbanBoard(){
        this.assignedToKanbanBoard = null;
    }

    public void assign(@NonNull User responsibleUser){
        this.responsibleUser = responsibleUser;
    }

    public void unassignResponsibleUser(){
        this.responsibleUser = null;
    }

    public void assign(Tag tag){
        this.assignedTags.add(tag);
    }

    public void unassign(Tag tag){
        this.assignedTags.remove(tag);
    }

}
