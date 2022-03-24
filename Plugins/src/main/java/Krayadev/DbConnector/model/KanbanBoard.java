package Krayadev.DbConnector.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @Column(name = "start_of_sprint", length = 6, updatable = false)
    private final Timestamp startOfSprint = Timestamp.valueOf(LocalDateTime.now());;

    @Column(name = "end_of_sprint", length = 6, updatable = false)
    private Timestamp endOfSprint;

    @Column(name = "show_review_column", nullable = false)
    private boolean showReviewColumn = false;

    @Column(name = "show_testing_column", nullable = false)
    private boolean showTestingColumn = false;

    @ManyToOne(targetEntity = Project.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "project", referencedColumnName = "id", nullable = false, updatable = false)
    private Project project;

    @OneToMany(mappedBy = "kanbanBoard", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Task> assignedTasks;

}
