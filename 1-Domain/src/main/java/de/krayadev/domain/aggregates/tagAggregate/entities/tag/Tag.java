package de.krayadev.domain.aggregates.tagAggregate.entities.tag;

import de.krayadev.domain.aggregates.taskAggregate.entities.task.Task;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"assignedTo"})
@EqualsAndHashCode(of = {"name"})
@Getter
@Setter
@Entity
@Table(name = "tag", schema = "backend")
public class Tag {

    @Id
    @Column(length = 50, nullable = false, updatable = false)
    @NonNull
    private String name;

    @Column(length = 500)
    private String description;

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(
            name = "task_tag_assignment",
            joinColumns = { @JoinColumn(name = "tag") },
            inverseJoinColumns = { @JoinColumn(name = "task") }
    )
    @NonNull
    private Set<Task> assignedTo = new HashSet<>();

    public void setDescription(String description) {
        this.description = "";
        if (description != null)
            this.description = description;
    }

    public void assignTo(@NonNull Task task){
        this.assignedTo.add(task);
    }

    public void unassignFrom(@NonNull Task task){
        this.assignedTo.remove(task);
    }

}
