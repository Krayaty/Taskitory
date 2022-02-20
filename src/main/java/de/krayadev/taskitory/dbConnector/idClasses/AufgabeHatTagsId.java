package de.krayadev.taskitory.dbConnector.idClasses;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class AufgabeHatTagsId implements Serializable {

    @Column(nullable = false)
    private int aufgabenId;

    @Column(nullable = false)
    private int tagId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AufgabeHatTagsId vergleichsobjekt = (AufgabeHatTagsId) o;
        return (this.aufgabenId != vergleichsobjekt.aufgabenId
                | this.tagId != vergleichsobjekt.tagId) ? false : true;
    }

    @Override
    public int hashCode() {
        int result = this.aufgabenId;
        result = 31 * result + this.tagId;
        return result;
    }

}
