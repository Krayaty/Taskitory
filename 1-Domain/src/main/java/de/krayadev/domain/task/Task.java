package de.krayadev.domain.task;

import de.krayadev.domain.user.User;
import lombok.*;
import de.krayadev.domain.kanbanBoard.KanbanBoard;
import de.krayadev.domain.tag.Tag;
import de.krayadev.domain.project.Project;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
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

    @Column(length = 2)
    private int complexity;

    @Column(length = 2)
    private int priority;

    @Column(name = "created_on", length = 6)
    private final Timestamp createdOn = Timestamp.valueOf(LocalDateTime.now());

    @Column(name = "completed_on", length = 6)
    private Timestamp completedOn;

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

    public void setDescription(@NonNull String description) {
        this.description = description;
    }

    public void setComplexity(int complexity) {
        if (complexity <= 0) {
            throw new IllegalArgumentException("Complexity must be positive");
        }

        this.complexity = complexity;
    }

    public void setToToDo() {
        if (this.status == TaskStatus.DONE)
            this.completedOn = null;

        this.status = TaskStatus.TODO;
    }

    public void setToInProgress() {
        if (this.status == TaskStatus.DONE)
            this.completedOn = null;

        this.status = TaskStatus.IN_PROGRESS;
    }

    public void setToReview() {
        if (!this.assignedToKanbanBoard.isShowReviewColumn())
            throw new IllegalStateException("Review column is not enabled");

        if (this.status == TaskStatus.DONE)
            this.completedOn = null;

        this.status = TaskStatus.REVIEW;
    }

    public void setToTesting() {
        if (!this.assignedToKanbanBoard.isShowTestingColumn())
            throw new IllegalStateException("Testing column is not enabled");

        if (this.status == TaskStatus.DONE)
            this.completedOn = null;

        this.status = TaskStatus.TESTING;
    }

    public void setToDone() {
        this.completedOn = Timestamp.valueOf(LocalDateTime.now());
        this.status = TaskStatus.DONE;
    }

    public void assignTo(KanbanBoard kanbanBoard) {
        this.assignedToKanbanBoard = kanbanBoard;
    }

    public void unassignFromKanbanBoard(){
        this.assignedToKanbanBoard = null;
    }

    public void assign(User responsibleUser){
        this.responsibleUser = responsibleUser;
    }

    public void unassignUser(){
        this.responsibleUser = null;
    }

    public void assign(Tag tag){
        this.assignedTags.add(tag);
    }

    public void unassign(Tag tag){
        this.assignedTags.remove(tag);
    }

}
