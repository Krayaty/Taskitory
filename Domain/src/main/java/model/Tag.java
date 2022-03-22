package model;

import lombok.Getter;

import java.util.Set;
import java.util.UUID;

@Getter
public class Tag {

    private final UUID id = UUID.randomUUID();

    private String name;

    private String description;

    private Project project;

    private Set<Task> assignedTo;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tag vergleichsobjekt = (Tag) o;
        return this.id == vergleichsobjekt.id;
    }

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
