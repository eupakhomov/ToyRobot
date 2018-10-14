package de.idealo.puzzle.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

/**
 * Game entity.
 *
 * @author <a href=mailto:eupakhomov@gmail.com>Eugene Pakhomov</a>
 */
@Data
@Document
public class Game {

    @Id
    private final String id;

    private RobotState robotState;

    public Game(String id, RobotState robotState) {
        this.id = id;
        this.robotState = robotState;
    }

    public Game() {
        this(UUID.randomUUID().toString(), null);
    }
}
