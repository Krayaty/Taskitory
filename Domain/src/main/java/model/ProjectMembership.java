package model;

import lombok.Getter;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Getter
public class ProjectMembership {

    private ProjectMembershipId id;

    private Project project;

    private User user;

    private final Timestamp startOfMembership = Timestamp.valueOf(LocalDateTime.now());

    private boolean admin = false;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProjectMembership vergleichsobjekt = (ProjectMembership) o;
        return this.id == vergleichsobjekt.id;
    }

    public void promote(){
        this.admin = true;
    }

    public void demote(){
        this.admin = false;
    }
}
