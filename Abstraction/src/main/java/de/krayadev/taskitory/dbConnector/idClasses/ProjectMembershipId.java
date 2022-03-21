package de.krayadev.taskitory.dbConnector.idClasses;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Embeddable
public class ProjectMembershipId implements Serializable {

    @Column(nullable = false)
    private UUID projectId;

    @Column(length = 36, nullable = false)
    private String userId;
}
