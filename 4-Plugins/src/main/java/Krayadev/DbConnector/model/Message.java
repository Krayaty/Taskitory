package Krayadev.DbConnector.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "message", schema = "backend")
public class Message {

    @Id
    private final UUID id = UUID.randomUUID();

    @Column(length = 500, nullable = false, updatable = false)
    private String contents;

    @Column(length = 6, nullable = false, updatable = false)
    private final Timestamp dispatch = Timestamp.valueOf(LocalDateTime.now());

    @Column(nullable = false)
    private boolean read;

    @ManyToOne(targetEntity = Project.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "origin", referencedColumnName = "id", nullable = false, updatable = false)
    private Project origin;

    @ManyToOne(targetEntity = User.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "recipient", referencedColumnName = "id", nullable = false, updatable = false)
    private User recipient;
}
