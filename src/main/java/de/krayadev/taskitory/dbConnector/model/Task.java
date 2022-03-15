package de.krayadev.taskitory.dbConnector.model;

import lombok.*;
import de.krayadev.taskitory.dbConnector.types.TaskStatus;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@AllArgsConstructor
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

    @Column(length = 2)
    private int complexity = -1;

    @Column(name = "created_on", length = 6, nullable = false)
    @GeneratedValue
    private final Timestamp createdOn = Timestamp.valueOf(LocalDateTime.now());

    @Column(name = "completed_on", length = 6)
    private Timestamp completedOn;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TaskStatus status = TaskStatus.UNASSIGNED;

    @ManyToOne(targetEntity = Project.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "project", referencedColumnName = "id", nullable = false, updatable = false)
    private Project project;

    @ManyToOne(targetEntity = User.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "responsible_user", referencedColumnName = "id")
    private User responsibleUser;

    @ManyToOne(targetEntity = KanbanBoard.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "kanban_board", referencedColumnName = "id")
    private KanbanBoard kanbanBoard;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "task_has_tags",
            joinColumns = { @JoinColumn(name = "task") },
            inverseJoinColumns = { @JoinColumn(name = "tag") }
    )
    private Set<Tag> assignedTags;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task vergleichsobjekt = (Task) o;
        return this.id == vergleichsobjekt.id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setComplexity(int complexity) {
        this.complexity = complexity;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public void completed(Timestamp on){
        this.completedOn = on;
    }

    public void assignTo(KanbanBoard kanbanBoard) {
        this.kanbanBoard = kanbanBoard;
        // Todo andere Seite
    }

    public void unassignFromKanbanBoard(){
        this.kanbanBoard = null;
        // Todo andere Seite
    }

    public void assign(User responsibleUser){
        this.responsibleUser = responsibleUser;
        // Todo andere Seite
    }

    public void unassignUser(){
        this.responsibleUser = null;
        // Todo andere Seite
    }

    public void assign(Tag tag){
        this.assignedTags.add(tag);
        // Todo andere Seite
    }

    public void unassign(Tag tag){
        this.assignedTags.remove(tag);
        // Todo andere Seite
    }

}
