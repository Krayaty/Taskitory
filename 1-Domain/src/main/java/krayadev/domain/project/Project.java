package krayadev.domain.project;

import krayadev.domain.kanbanboard.KanbanBoard;
import krayadev.domain.task.Task;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import krayadev.domain.message.Message;
import krayadev.domain.projectMembership.ProjectMembership;

import javax.persistence.*;
import java.util.Set;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(exclude = {"sentMessages", "tags", "kanbanBoards"})
@EqualsAndHashCode(of = {"name"})
@Getter
@Setter
@Entity
@Table(name = "project", schema = "backend")
public class Project {

    @Id
    @Column(length = 100, nullable = false)
    private String name;

    @Column(length = 1000)
    private String description;

    @Column(length = 36, nullable = false, unique = true, updatable = false)
    private String key;
    //private final ProjectSecurityKey key = new ProjectSecurityKey();

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Task> tasks;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<KanbanBoard> kanbanBoards;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<ProjectMembership> memberships;

    @OneToMany(mappedBy = "origin", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Message> sentMessages;

    public Project(String name) {
        this.name = name;
        this.description = "";
    }

    public Project(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void add(KanbanBoard kanbanBoard){
        this.kanbanBoards.add(kanbanBoard);
    }

    public void remove(KanbanBoard kanbanBoard){
        this.kanbanBoards.remove(kanbanBoard);
    }

    public void add(ProjectMembership membership){
        this.memberships.add(membership);
    }

    public void remove(ProjectMembership membership){
        this.memberships.remove(membership);
    }

}
