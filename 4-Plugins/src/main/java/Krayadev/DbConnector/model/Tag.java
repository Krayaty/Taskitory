package Krayadev.DbConnector.model;

import lombok.*;
import javax.persistence.*;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tag", schema = "backend")
public class Tag {

    @Id
    @Column(length = 50, nullable = false, updatable = false)
    private String name;

    @Column(length = 500)
    private String description;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "task_tag_assignment",
            joinColumns = { @JoinColumn(name = "tag") },
            inverseJoinColumns = { @JoinColumn(name = "task") }
    )
    private Set<Task> assignedTo;

}
