package de.krayadev.taskitory.dbConnector.idClasses;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class ProjektMitgliedschaftsId implements Serializable {

    @Column(nullable = false)
    private int projektId;

    @Column(length = 36, nullable = false)
    private String userId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProjektMitgliedschaftsId vergleichsobjekt = (ProjektMitgliedschaftsId) o;
        return (this.projektId != vergleichsobjekt.projektId
                | this.userId != vergleichsobjekt.userId) ? false : true;
    }

    @Override
    public int hashCode() {
        int result = this.projektId;
        result = 31 * result + this.userId.hashCode();
        return result;
    }
}
