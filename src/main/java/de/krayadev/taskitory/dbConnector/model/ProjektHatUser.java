package de.krayadev.taskitory.dbConnector.model;

import de.krayadev.taskitory.dbConnector.idClasses.ProjektHatUserId;
import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "projekt_hat_user",
        schema = "backend",
        uniqueConstraints = {
            @UniqueConstraint(columnNames = {"admin", "projekt"})
        })
public class ProjektHatUser {

    @EmbeddedId
    private ProjektHatUserId id;

    @MapsId("projektId")
    @ManyToOne(targetEntity = Projekt.class, cascade = CascadeType.ALL)
    @JoinColumn(table = "projekt", referencedColumnName = "id", nullable = false)
    private Projekt projekt;

    @MapsId("userId")
    @ManyToOne(targetEntity = User.class, cascade = CascadeType.ALL)
    @JoinColumn(table = "user_entity", referencedColumnName = "id", nullable = false)
    private User user;

    @Column(length = 6, nullable = false)
    private Timestamp beitritt;

    @Column(length = 6)
    private Timestamp austritt;

    @Column(name = "admin", nullable = false)
    private boolean isAdmin;
}
