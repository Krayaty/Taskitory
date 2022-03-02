package de.krayadev.taskitory.dbConnector.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import de.krayadev.taskitory.dbConnector.types.AufgabenStatus;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Set;

import static javax.persistence.GenerationType.AUTO;
import static javax.persistence.GenerationType.SEQUENCE;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "aufgabe",
        schema = "backend",
        uniqueConstraints = {
            @UniqueConstraint(columnNames = {"bezeichnung", "projekt"})
        })
public class Aufgabe {

    @Id
    @GeneratedValue(strategy=SEQUENCE, generator="aufgaben_generator")
    @SequenceGenerator(name = "aufgaben_generator", sequenceName = "aufgabe_id_seq", allocationSize = 1)
    private int id;

    @Column(length = 100, nullable = false)
    private String bezeichnung;

    @Column(length = 510)
    private String beschreibung;

    @Column(name = "komplexitaet", length = 2)
    private int komplexität;

    @Column(length = 6, nullable = false)
    @GeneratedValue
    private Timestamp erstellung;

    @Column(length = 6)
    private Timestamp fertigstellung;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private AufgabenStatus status;

    @ManyToOne(targetEntity = Projekt.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "projekt", referencedColumnName = "id", nullable = false)
    private Projekt projekt;

    @ManyToOne(targetEntity = User.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "zustaendig", referencedColumnName = "id")
    private User zuständig;

    @ManyToOne(targetEntity = KanbanBoard.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "kanbanboard", referencedColumnName = "id")
    private KanbanBoard kanbanBoard;

    @OneToMany(mappedBy = "abhängigkeit", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Abhängigkeit> abhängigkeiten;

    @OneToMany(mappedBy = "aufgabe", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Abhängigkeit> abhängigVon;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "aufgabe_hat_tags",
            joinColumns = { @JoinColumn(name = "aufgabe") },
            inverseJoinColumns = { @JoinColumn(name = "tag") }
    )
    private Set<Tag> zugewieseneTags;

}
