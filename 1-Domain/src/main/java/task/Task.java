package task;

import lombok.*;
import kanbanboard.KanbanBoard;
import tag.Tag;
import user.User;
import project.Project;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Task {

    @NonNull
    private String name;

    @NonNull
    private String description;

    @NonNull
    private int complexity;

    @NonNull
    private final Timestamp createdOn;

    @NonNull
    private Timestamp completedOn;

    @NonNull
    private TaskStatus status;

    @NonNull
    private int priority;

    @NonNull
    private final Project project;

    @NonNull
    private User responsibleUser;

    @NonNull
    private final User creator;

    @NonNull
    private KanbanBoard kanbanBoard;

    @NonNull
    private Set<Tag> assignedTags;

    public void setName(@NonNull String name) {
        this.name = name;
    }

    public void setDescription(@NonNull String description) {
        this.description = description;
    }

    public void setComplexity(int complexity) {
        if (complexity <= 0) {
            throw new IllegalArgumentException("Complexity must be positive");
        }

        this.complexity = complexity;
    }

    public void setToToDo() {
        if (this.status == TaskStatus.DONE)
            this.completedOn = null;

        this.status = TaskStatus.TODO;
    }

    public void setToInProgress() {
        if (this.status == TaskStatus.DONE)
            this.completedOn = null;

        this.status = TaskStatus.IN_PROGRESS;
    }

    public void setToReview() {
        if (!this.kanbanBoard.isShowReviewColumn())
            throw new IllegalStateException("Review column is not enabled");

        if (this.status == TaskStatus.DONE)
            this.completedOn = null;

        this.status = TaskStatus.REVIEW;
    }

    public void setToTesting() {
        if (!this.kanbanBoard.isShowTestingColumn())
            throw new IllegalStateException("Testing column is not enabled");

        if (this.status == TaskStatus.DONE)
            this.completedOn = null;

        this.status = TaskStatus.TESTING;
    }

    public void setToDone() {
        this.completedOn = Timestamp.valueOf(LocalDateTime.now());
        this.status = TaskStatus.DONE;
    }

    public void assignTo(KanbanBoard kanbanBoard) {
        this.kanbanBoard = kanbanBoard;
    }

    public void unassignFromKanbanBoard(){
        this.kanbanBoard = null;
    }

    public void assign(User responsibleUser){
        this.responsibleUser = responsibleUser;
    }

    public void unassignUser(){
        this.responsibleUser = null;
    }

    public void assign(Tag tag){
        this.assignedTags.add(tag);
    }

    public void unassign(Tag tag){
        this.assignedTags.remove(tag);
    }

}
