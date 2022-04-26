package de.krayadev.domain.entities.project;

import de.krayadev.domain.entities.kanbanBoard.KanbanBoard;
import de.krayadev.domain.entities.message.Message;
import de.krayadev.domain.entities.projectMembership.ProjectMembership;
import de.krayadev.domain.entities.task.Task;
import com.fasterxml.jackson.annotation.JsonIgnore;
import de.krayadev.domain.valueobjects.ProjectSecurityKey;
import lombok.*;

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

    @Embedded
    @AttributeOverrides({ @AttributeOverride(name = "key", column = @Column(name = "key", length = 128, columnDefinition = "bpchar(128)", unique = true, updatable = false)) })
    private final ProjectSecurityKey key = new ProjectSecurityKey();

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

    public Project(@NonNull String name) {
        this.name = name;
        this.description = "";
    }

    public Project(@NonNull String name, String description) {
        this.name = name;
        this.description = "";
        if(description != null)
            this.description = description;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = "";
        if(description != null)
            this.description = description;
    }

    public void add(@NonNull KanbanBoard kanbanBoard){
        this.kanbanBoards.add(kanbanBoard);
    }

    public void remove(@NonNull KanbanBoard kanbanBoard){
        this.kanbanBoards.remove(kanbanBoard);
    }

    public void add(@NonNull ProjectMembership membership){
        this.memberships.add(membership);
    }

    public void remove(@NonNull ProjectMembership membership){
        this.memberships.remove(membership);
    }

}
