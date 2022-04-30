package de.krayadev.domain.aggregates.userAggregate.entities.tag;

import de.krayadev.domain.aggregates.taskAggregate.entities.task.Task;
import de.krayadev.domain.aggregates.userAggregate.entities.user.User;
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

    public void changeDescription(String newDescription) {
        this.description = "";
        if (newDescription != null)
            this.description = newDescription;
    }

    public boolean isCreator(User creator) {
        return this.creator.equals(creator);
    }

}
