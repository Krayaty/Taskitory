package de.krayadev.taskitory.dbConnector.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Project vergleichsobjekt = (Project) o;
        return this.id == vergleichsobjekt.id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void add(Tag tag){
        this.tags.add(tag);
        // Todo andere Seite
    }

    public void remove(Tag tag){
        this.tags.remove(tag);
        // Todo andere Seite
    }

    public void add(KanbanBoard kanbanBoard){
        this.kanbanBoards.add(kanbanBoard);
        // Todo andere Seite
    }

    public void remove(KanbanBoard kanbanBoard){
        this.kanbanBoards.remove(kanbanBoard);
        // Todo andere Seite
    }

    public void add(ProjectMembership membership){
        this.memberships.add(membership);
        // Todo andere Seite
    }

    public void remove(ProjectMembership membership){
        this.memberships.remove(membership);
        // Todo andere Seite
    }

}
