package de.krayadev.taskitory.dbConnector.model;

import de.krayadev.taskitory.dbConnector.idClasses.ProjektMitgliedschaftsId;
import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "projekt_mitgliedschaft",
        schema = "backend",
        uniqueConstraints = {
            @UniqueConstraint(columnNames = {"admin", "projekt"})
        })
public class ProjektMitgliedschaft {

    @EmbeddedId
    private ProjektMitgliedschaftsId id;

    @MapsId("projektId")
    @ManyToOne(targetEntity = Projekt.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "projekt", referencedColumnName = "id", nullable = false)
    private Projekt projekt;

    @MapsId("userId")
    @ManyToOne(targetEntity = User.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "user", referencedColumnName = "id", nullable = false)
    private User user;

    @Column(length = 6, nullable = false)
    private Timestamp beitritt;

    @Column(length = 6)
    private Timestamp austritt;

    @Column(name = "admin", nullable = false)
    private boolean isAdmin;
}
