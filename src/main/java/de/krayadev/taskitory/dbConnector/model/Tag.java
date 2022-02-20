package de.krayadev.taskitory.dbConnector.model;

import lombok.*;
import javax.persistence.*;

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
    @GeneratedValue
    private int id;

    @Column(length = 100, nullable = false)
    private String bezeichnung;

    @Column
    private String beschreibung;

    @ManyToOne(targetEntity = Projekt.class, cascade = CascadeType.ALL)
    @JoinColumn(table = "projekt", referencedColumnName = "id", nullable = false)
    private Projekt projekt;

}
