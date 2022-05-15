package de.krayadev.domain.aggregates.projectAggregate.valueObjects;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class SprintTest {

    @Test
    void testIsOver() {
        Timestamp startTimestamp = new Timestamp(1);
        Timestamp endTimestamp = Timestamp.valueOf(startTimestamp.toLocalDateTime().plus(Duration.ofSeconds(5 * 60 + 1)));
        Sprint sprint = new Sprint(startTimestamp, endTimestamp);
        assertTrue(sprint.isOver());
    }

    @Test
    void testSprintConstructorWithGivenStartAndEnd(){
        Timestamp startTimestamp1 = Timestamp.valueOf(LocalDateTime.now());
        Timestamp endTimestamp1 = null;
        assertEquals(new Sprint(startTimestamp1, endTimestamp1), new Sprint(startTimestamp1, Duration.ofDays(14)));

        Timestamp endTimestamp2 = Timestamp.valueOf(LocalDateTime.now());
        Timestamp startTimestamp2 = Timestamp.valueOf(endTimestamp2.toLocalDateTime().plus(Duration.ofSeconds(1)));
        assertThrows(IllegalArgumentException.class, () -> new Sprint(startTimestamp2, endTimestamp2));

        Timestamp startTimestamp3 = Timestamp.valueOf(LocalDateTime.now());
        Timestamp endTimestamp3 = Timestamp.valueOf(startTimestamp3.toLocalDateTime().plus(Duration.ofSeconds(5 * 60 - 1)));
        assertThrows(IllegalArgumentException.class, () -> new Sprint(startTimestamp3, endTimestamp3));

        Timestamp startTimestamp4 = Timestamp.valueOf(LocalDateTime.now());
        Timestamp endTimestamp4 = Timestamp.valueOf(startTimestamp4.toLocalDateTime().plus(Duration.ofSeconds(5 * 60 + 1)));
        assertDoesNotThrow(() -> new Sprint(startTimestamp4, endTimestamp4));

    }

}
