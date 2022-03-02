package de.krayadev.taskitory.dbConnector.model;

import de.krayadev.taskitory.dbConnector.idClasses.AbhängigkeitsId;
import de.krayadev.taskitory.dbConnector.types.AufgabenAbhängigekeitsTyp;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "abhaengigkeit", schema = "backend")
public class Abhängigkeit {

    @EmbeddedId
    private AbhängigkeitsId id;

    @MapsId("aufgabenId")
    @ManyToOne(targetEntity = Aufgabe.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "aufgabe", referencedColumnName = "id", nullable = false)
    private Aufgabe aufgabe;

    @MapsId("abhängigeAufgabeId")
    @ManyToOne(targetEntity = Aufgabe.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "abhaengigkeit", referencedColumnName = "id", nullable = false)
    private Aufgabe abhängigkeit;

    @Column(name = "abhaengigkeits_typ", nullable = false)
    @Enumerated(EnumType.STRING)
    private AufgabenAbhängigekeitsTyp abhängigekeitsTyp;

}
