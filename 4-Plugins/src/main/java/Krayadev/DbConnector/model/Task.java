package Krayadev.DbConnector.model;

import Krayadev.DbConnector.types.TaskStatus;
import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@AllArgsConstructor
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

    @Column(name = "created_on", length = 6, nullable = false)
    @GeneratedValue
    private final Timestamp createdOn = Timestamp.valueOf(LocalDateTime.now());

    @Column(name = "completed_on", length = 6)
    private Timestamp completedOn;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TaskStatus status = TaskStatus.UNASSIGNED;

    @Column(length = 2)
    private int priority;

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
    private Set<Tag> assignedTags;

}
