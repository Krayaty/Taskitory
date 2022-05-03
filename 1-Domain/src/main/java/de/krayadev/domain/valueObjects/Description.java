package de.krayadev.domain.valueObjects;

import lombok.Getter;
import lombok.NonNull;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
@Getter
public class Description {

    private final String value;

    public Description(@NonNull String value) {
        if(value.isEmpty() || value.isBlank()){
            this.value = "";
            return;
        }

        if(value.length() > 500)
            throw new IllegalArgumentException("Name cannot be longer than 500 characters");

        this.value = value;
    }

    public Description() {
        this.value = "";
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Description that = (Description) o;
        return this.value.equals(that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.value);
    }

    @Override
    public String toString() {
        return this.value;
    }
}
