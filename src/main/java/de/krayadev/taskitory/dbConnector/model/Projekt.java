package de.krayadev.taskitory.dbConnector.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

import static javax.persistence.GenerationType.SEQUENCE;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "projekt", schema = "backend")
public class Projekt {

    @Id
    @GeneratedValue(strategy = SEQUENCE, generator = "projekt_generator")
    @SequenceGenerator(name = "projekt_generator", sequenceName = "projekt_id_seq", allocationSize = 1)
    private int id;

    @Column(length = 100, nullable = false)
    private String bezeichnung;

    @Column
    private String beschreibung;

    @Column(name = "schluessel", columnDefinition = "bpchar(36)", length = 36, nullable = false, unique = true)
    @GeneratedValue
    private String schlüssel;

    @OneToMany(mappedBy = "projekt", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Tag> tagsOfGroup;

    @OneToMany(mappedBy = "projekt", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<KanbanBoard> kanbanBoards;

    @OneToMany(mappedBy = "projekt", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<ProjektMitgliedschaft> mitgliedschaften;

}
