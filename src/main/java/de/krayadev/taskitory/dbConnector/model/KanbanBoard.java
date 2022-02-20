package de.krayadev.taskitory.dbConnector.model;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "kanbanboard",
        schema = "backend",
        uniqueConstraints = {
            @UniqueConstraint(columnNames = {"bezeichnung", "projekt"})
        })
public class KanbanBoard {

    @Id
    @GeneratedValue
    private int id;

    @Column(length = 100, nullable = false)
    private String bezeichnung;

    @Column
    private String beschreibung;

    @Column(length = 6)
    private Timestamp sprintStart;

    @Column(length = 6)
    private Timestamp sprintEnde;

    @Column(name = "progress_ebene", nullable = false)
    private boolean inProgressEbene;

    @Column(name = "review_ebene", nullable = false)
    private boolean reviewEbene;

    @Column(name = "testing_ebene", nullable = false)
    private boolean testingEbene;

    @ManyToOne(targetEntity = Projekt.class, cascade = CascadeType.ALL)
    @JoinColumn(table = "projekt", referencedColumnName = "id", nullable = false)
    private Projekt projekt;
}
