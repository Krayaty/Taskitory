package model;

import lombok.Getter;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Getter
public class KanbanBoard {

    private final UUID id = UUID.randomUUID();

    private String name;

    private String description;

    private final Timestamp startOfSprint = Timestamp.valueOf(LocalDateTime.now());;

    private Timestamp endOfSprint;

    private boolean showReviewColumn = false;

    private boolean showTestingColumn = false;

    private Project project;

    private Set<Task> assignedTasks;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        KanbanBoard vergleichsobjekt = (KanbanBoard) o;
        return this.id == vergleichsobjekt.id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void showReviewColumn(){
        this.showReviewColumn = true;
    }

    public void hideReviewColumn(){
        this.showReviewColumn = false;
    }

    public void showTestingColumn() {
        this.showTestingColumn = true;
    }

    public void hideTestingColumn() {
        this .showTestingColumn = false;
    }

    public void assign(Task task){
        this.assignedTasks.add(task);
        // Todo andere Seite
    }

    public void unassign(Task task){
        this.assignedTasks.remove(task);
        // Todo andere Seite
    }
}
