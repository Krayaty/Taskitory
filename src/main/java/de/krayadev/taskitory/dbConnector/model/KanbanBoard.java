package de.krayadev.taskitory.dbConnector.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Set;

import static javax.persistence.GenerationType.SEQUENCE;

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
    @GeneratedValue(strategy = SEQUENCE, generator = "kanbanboard_generator")
    @SequenceGenerator(name = "kanbanboard_generator", sequenceName = "kanbanboard_id_seq", allocationSize = 1)
    private int id;

    @Column(length = 100, nullable = false)
    private String bezeichnung;

    @Column
    private String beschreibung;

    @Column(name = "sprintstart", length = 6)
    private Timestamp sprintStart;

    @Column(name = "sprintende", length = 6)
    private Timestamp sprintEnde;

    @Column(name = "progress_ebene", nullable = false)
    private boolean inProgressEbene;

    @Column(name = "review_ebene", nullable = false)
    private boolean reviewEbene;

    @Column(name = "testing_ebene", nullable = false)
    private boolean testingEbene;

    @ManyToOne(targetEntity = Projekt.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "projekt", referencedColumnName = "id", nullable = false)
    private Projekt projekt;

    @OneToMany(mappedBy = "kanbanBoard", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Aufgabe> aufgaben;
}
