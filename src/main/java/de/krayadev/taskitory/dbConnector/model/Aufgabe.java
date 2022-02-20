package de.krayadev.taskitory.dbConnector.model;

import lombok.*;
import de.krayadev.taskitory.dbConnector.types.AufgabenStatus;

import javax.persistence.*;
import java.sql.Timestamp;

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
    @GeneratedValue
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
    @JoinColumn(table = "projekt", referencedColumnName = "id", nullable = false)
    private Projekt projekt;

    @ManyToOne(targetEntity = User.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "zustaendig", table = "user_entity", referencedColumnName = "id")
    private User zuständig;

    @ManyToOne(targetEntity = KanbanBoard.class, cascade = CascadeType.ALL)
    @JoinColumn(table = "kanbanboard", referencedColumnName = "id")
    private KanbanBoard kanbanboard;

}
