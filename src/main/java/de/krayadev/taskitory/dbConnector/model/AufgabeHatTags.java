package de.krayadev.taskitory.dbConnector.model;

import de.krayadev.taskitory.dbConnector.idClasses.AufgabeHatTagsId;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "aufgabe_hat_tags", schema = "backend")
public class AufgabeHatTags {

    @EmbeddedId
    private AufgabeHatTagsId id;

    @MapsId("aufgabenId")
    @ManyToOne(targetEntity = Aufgabe.class, cascade = CascadeType.ALL)
    @JoinColumn(table = "aufgabe", referencedColumnName = "id", nullable = false)
    private Aufgabe aufgabe;

    @MapsId("tagId")
    @ManyToOne(targetEntity = Tag.class, cascade = CascadeType.ALL)
    @JoinColumn(table = "tag", referencedColumnName = "id", nullable = false)
    private Tag tag;

}
