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
@Table(name = "tag",
        schema = "backend",
        uniqueConstraints = {
            @UniqueConstraint(columnNames = {"bezeichnung", "projekt"})
        })
public class Tag {

    @Id
    @GeneratedValue(strategy = SEQUENCE, generator = "tag_generator")
    @SequenceGenerator(name = "tag_generator", sequenceName = "tag_id_seq", allocationSize = 1)
    private int id;

    @Column(length = 100, nullable = false)
    private String bezeichnung;

    @Column
    private String beschreibung;

    @ManyToOne(targetEntity = Projekt.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "projekt", referencedColumnName = "id", nullable = false)
    private Projekt projekt;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "aufgabe_hat_tags",
            joinColumns = { @JoinColumn(name = "tag") },
            inverseJoinColumns = { @JoinColumn(name = "aufgabe") }
    )
    private Set<Aufgabe> angewendetAufAufgaben;

}
