package Krayadev.DbConnector.idClasses;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Embeddable
public class ProjectMembershipId implements Serializable {

    @Column(length = 100, nullable = false, updatable = false)
    private String projectName;

    @Column(length = 36, nullable = false, updatable = false)
    private String userId;
}
