package Krayadev.DbConnector.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "project", schema = "backend")
public class Project {

    @Id
    private final UUID id = UUID.randomUUID();

    @Column(length = 100, nullable = false)
    private String name;

    @Column(length = 1000)
    private String description;

    @Column(columnDefinition = "bpchar(36)", length = 36, nullable = false, unique = true, updatable = false)
    @GeneratedValue
    private String key;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Tag> tags;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<KanbanBoard> kanbanBoards;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<ProjectMembership> memberships;

    @OneToMany(mappedBy = "origin", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Message> sentMessages;

}
