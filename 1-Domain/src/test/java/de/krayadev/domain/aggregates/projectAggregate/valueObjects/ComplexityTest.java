package de.krayadev.domain.aggregates.projectAggregate.valueObjects;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ComplexityTest {

    @Test
    void testComplextityConstructor() {
        int[] legalValues = {1, 10, 20};
        int[] illegalValues = {-100000000, -1, 100000000};
        Complexity none = Complexity.NONE;

        for (int i : illegalValues) {
            assertThrows(IllegalArgumentException.class, () -> new Complexity(i));
        }

        for (int i : legalValues) {
            assertDoesNotThrow(() -> new Complexity(i));
        }

        assertEquals(none.getValue(), 0);
    }
}
