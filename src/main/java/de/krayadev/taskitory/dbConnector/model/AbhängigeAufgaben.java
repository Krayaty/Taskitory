package de.krayadev.taskitory.dbConnector.model;

import de.krayadev.taskitory.dbConnector.idClasses.AbhängigeAufgabenId;
import de.krayadev.taskitory.dbConnector.types.AufgabenAbhängigekeitsTyp;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "abhaengige_aufgaben", schema = "backend")
public class AbhängigeAufgaben {

    @EmbeddedId
    private AbhängigeAufgabenId id;

    @MapsId("aufgabenId")
    @ManyToOne(targetEntity = Aufgabe.class, cascade = CascadeType.ALL)
    @JoinColumn(table = "aufgabe", referencedColumnName = "id", nullable = false)
    private Aufgabe aufgabe;

    @MapsId("abhängigeAufgabeId")
    @ManyToOne(targetEntity = Aufgabe.class, cascade = CascadeType.ALL)
    @JoinColumn(table = "aufgabe", referencedColumnName = "id", nullable = false)
    private Aufgabe abhängigeAufgabe;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private AufgabenAbhängigekeitsTyp abhängigekeitsTyp;

}
