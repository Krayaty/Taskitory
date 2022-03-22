package model;

import java.util.Set;

public class User {

    private String id;

    private String email;

    private String email_constraint;

    private boolean email_verified;

    private boolean enabled;

    private String federation_link;

    private String first_name;

    private String last_name;

    private String realm_id;

    private String username;

    private long created_timestamp;

    private String service_account_client_link;

    private int not_before;

    private Set<Task> assignedTasks;

    private Set<ProjectMembership> memberships;

    @Override
    public String toString(){
        String result = "id: " + this.id + "\n" +
                "username: " + this.username + "\n" +
                "assignedTasks: [";
        for (Task task: this.assignedTasks) {
            result += "{taskId: " + task.getId() + "},\n";
        }
        result += "]\n" + "memberships: [";
        for (ProjectMembership membership: this.memberships) {
            result += "{projectId" + membership.getProject() + "},\n";
        }
        result += "]";

        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User vergleichsobjekt = (User) o;
        return this.id == vergleichsobjekt.id;
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public Set<Task> getAssignedTasks() {
        return assignedTasks;
    }

    public Set<ProjectMembership> getMemberships() {
        return memberships;
    }

}
