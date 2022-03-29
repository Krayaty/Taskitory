package project;

import lombok.*;
import message.Message;
import kanbanboard.KanbanBoard;
import tag.Tag;
import projectMembership.ProjectMembership;

import java.util.HashSet;
import java.util.Set;

@RequiredArgsConstructor
@ToString(exclude = {"sentMessages", "tags", "kanbanBoards"})
@EqualsAndHashCode(of = {"name"})
@Getter
public class Project {

    @NonNull
    private String name;

    @NonNull
    private String description;

    private final ProjectSecurityKey key = new ProjectSecurityKey();

    private Set<Tag> tags = new HashSet<Tag>();

    private Set<KanbanBoard> kanbanBoards = new HashSet<KanbanBoard>();

    private Set<ProjectMembership> memberships = new HashSet<ProjectMembership>();

    private Set<Message> sentMessages = new HashSet<Message>();

    public Project(String name) {
        this.name = name;
        this.description = "";
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void add(Tag tag){
        this.tags.add(tag);
    }

    public void remove(Tag tag){
        this.tags.remove(tag);
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

    public static Project createNullProject() {
        return new Project("NULL");
    }

}
