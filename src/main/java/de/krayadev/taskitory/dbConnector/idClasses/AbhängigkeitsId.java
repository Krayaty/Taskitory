package de.krayadev.taskitory.dbConnector.idClasses;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class AbhängigkeitsId implements Serializable {

    @Column(table = "aufgabe", name = "aufgabe", nullable = false)
    private int aufgabenId;

    @Column(table = "aufgabe", name = "abhaengigkeit", nullable = false)
    private int abhängigeAufgabeId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbhängigkeitsId vergleichsobjekt = (AbhängigkeitsId) o;
        return (this.aufgabenId != vergleichsobjekt.aufgabenId
                | this.abhängigeAufgabeId != vergleichsobjekt.abhängigeAufgabeId) ? false : true;
    }

    @Override
    public int hashCode() {
        int result = this.aufgabenId;
        result = 31 * result + this.abhängigeAufgabeId;
        return result;
    }

}
