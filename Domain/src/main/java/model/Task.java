package model;

import lombok.Getter;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Getter
public class Task {

    private final UUID id = UUID.randomUUID();

    private String name;

    private String description;

    private int complexity = -1;

    private final Timestamp createdOn = Timestamp.valueOf(LocalDateTime.now());

    private Timestamp completedOn;

    private TaskStatus status = TaskStatus.UNASSIGNED;

    private int priority;

    private Project project;

    private User responsibleUser;

    private User creator;

    private KanbanBoard kanbanBoard;

    private Set<Tag> assignedTags;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task vergleichsobjekt = (Task) o;
        return this.id == vergleichsobjekt.id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setComplexity(int complexity) {
        this.complexity = complexity;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public void completed(Timestamp on){
        this.completedOn = on;
    }

    public void assignTo(KanbanBoard kanbanBoard) {
        this.kanbanBoard = kanbanBoard;
        // Todo andere Seite
    }

    public void unassignFromKanbanBoard(){
        this.kanbanBoard = null;
        // Todo andere Seite
    }

    public void assign(User responsibleUser){
        this.responsibleUser = responsibleUser;
        // Todo andere Seite
    }

    public void unassignUser(){
        this.responsibleUser = null;
        // Todo andere Seite
    }

    public void assign(Tag tag){
        this.assignedTags.add(tag);
        // Todo andere Seite
    }

    public void unassign(Tag tag){
        this.assignedTags.remove(tag);
        // Todo andere Seite
    }

}
