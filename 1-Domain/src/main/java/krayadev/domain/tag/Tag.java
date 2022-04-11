package krayadev.domain.tag;

import krayadev.domain.task.Task;
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

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "task_tag_assignment",
            joinColumns = { @JoinColumn(name = "tag") },
            inverseJoinColumns = { @JoinColumn(name = "task") }
    )
    @NonNull
    private Set<Task> assignedTo = new HashSet<>();

    public void setDescription(String description) {
        this.description = description;
    }

    public void assignTo(Task task){
        this.assignedTo.add(task);
    }

    public void unassignFrom(Task task){
        this.assignedTo.remove(task);
    }

}