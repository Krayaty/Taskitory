package de.krayadev.domain.valueObjects;

import lombok.Getter;
import lombok.NonNull;
import org.apache.commons.lang3.RandomStringUtils;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
@Getter
public class Name {

    private final String value;

    public Name(@NonNull String value) {
        if(value.isEmpty() || value.isBlank())
            throw new IllegalArgumentException("Name cannot be empty");

        if(value.length() > 100)
            throw new IllegalArgumentException("Name cannot be longer than 100 characters");

        this.value = value;
    }

    public Name() {
        this.value = RandomStringUtils.random(50, true, false);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Name name = (Name) o;
        return this.value.equals(name.value);
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
