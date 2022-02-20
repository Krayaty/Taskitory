package de.krayadev.taskitory.dbConnector.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "projekt", schema = "backend")
public class Projekt {

    @Id
    @GeneratedValue
    private int id;

    @Column(length = 100, nullable = false)
    private String bezeichnung;

    @Column
    private String beschreibung;

    @Column(name = "schluessel", length = 36, nullable = false, unique = true)
    @GeneratedValue
    private String schlüssel;

    @OneToMany(mappedBy = "projekt", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Tag> tagsOfGroup;

}
