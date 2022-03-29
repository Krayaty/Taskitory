package user;

import lombok.*;
import message.Message;
import projectMembership.ProjectMembership;
import task.Task;

import java.util.HashSet;
import java.util.Set;

@RequiredArgsConstructor
@ToString(of = {"username"})
@EqualsAndHashCode(of = {"username"})
public class User {

    @NonNull
    private String username;

    private Set<Task> assignedTasks = new HashSet<Task>();

    private Set<ProjectMembership> memberships = new HashSet<ProjectMembership>();

    private Set<Message> receivedMessages = new HashSet<Message>();

    public String getUsername() {
        return username;
    }

    public void assignTask(Task task){
        assignedTasks.add(task);
    }

    public void unassignTask(Task task){
        assignedTasks.remove(task);
    }

    public void addMembership(ProjectMembership membership){
        memberships.add(membership);
    }

    public void removeMembership(ProjectMembership membership){
        memberships.remove(membership);
    }

    public void receiveMessage(Message message){
        receivedMessages.add(message);
    }

    public void deleteMessage(Message message){
        receivedMessages.remove(message);
    }

    public static User createNullUser() {
        return new User("NULL");
    }

}
