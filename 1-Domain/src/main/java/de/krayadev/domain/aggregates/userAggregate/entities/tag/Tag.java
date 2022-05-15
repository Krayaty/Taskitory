package de.krayadev.domain.aggregates.userAggregate.entities.tag;

import de.krayadev.domain.aggregates.projectAggregate.entities.task.Task;
import de.krayadev.domain.aggregates.userAggregate.entities.user.User;
import de.krayadev.domain.valueObjects.Description;
import de.krayadev.domain.valueObjects.Name;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@AllArgsConstructor
@RequiredArgsConstructor
@ToString(exclude = {"assignedTo"})
@EqualsAndHashCode(of = {"name"})
@Getter
@Entity
@Table(name = "tag", schema = "backend")
public class Tag {

    @Id
    @Column(length = 100, nullable = false, updatable = false)
    @NonNull
    private String name;

    @Column(length = 500)
    private String description;

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "creator", referencedColumnName = "id", nullable = false, updatable = false)
    @NonNull
    private User creator;

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(
            name = "task_tag_assignment",
            joinColumns = { @JoinColumn(name = "tag") },
            inverseJoinColumns = { @JoinColumn(name = "task") }
    )
    @NonNull
    private Set<Task> assignedTo = new HashSet<>();

    protected Tag() {
        Name name = new Name();
        this.name = name.getValue();
        this.description = new Description().getValue();
        this.creator = null;
    }

    public void changeDescription(Description newDescription) {
        this.description = newDescription.getValue();
    }

    public void assignTo(@NonNull Task task) {
        if(this.assignedTo.contains(task))
           throw new IllegalArgumentException("Tag is already assigned to the given task");

        this.assignedTo.add(task);
    }

    public void unassignFrom(@NonNull Task task) {
        if(!this.assignedTo.contains(task))
            throw new IllegalArgumentException("Tag is not assigned to the given task");

        this.assignedTo.remove(task);
    }

}
