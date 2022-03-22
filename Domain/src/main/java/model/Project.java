package model;

import lombok.Getter;

import java.util.Set;
import java.util.UUID;

@Getter
public class Project {

    private final UUID id = UUID.randomUUID();

    private String name;

    private String description;

    private String key;

    private Set<Tag> tags;

    private Set<KanbanBoard> kanbanBoards;

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
