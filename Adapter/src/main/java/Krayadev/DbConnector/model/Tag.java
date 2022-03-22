package Krayadev.DbConnector.model;

import lombok.*;
import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tag",
        schema = "backend",
        uniqueConstraints = {
            @UniqueConstraint(columnNames = {"name", "project"})
        })
public class Tag {

    @Id
    private final UUID id = UUID.randomUUID();

    @Column(length = 50, nullable = false, updatable = false)
    private String name;

    @Column(length = 500)
    private String description;

    @ManyToOne(targetEntity = Project.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "project", referencedColumnName = "id", nullable = false, updatable = false)
    private Project project;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "task_has_tags",
            joinColumns = { @JoinColumn(name = "tag") },
            inverseJoinColumns = { @JoinColumn(name = "task") }
    )
    private Set<Task> assignedTo;

}
