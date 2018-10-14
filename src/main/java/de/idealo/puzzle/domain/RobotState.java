package de.idealo.puzzle.domain;

import lombok.Data;

/**
 * Robot state entity.
 * Contains data about robot state which must be persisted.
 *
 * @author <a href=mailto:eupakhomov@gmail.com>Eugene Pakhomov</a>
 */
@Data
public class RobotState {
    private final Position position;
    private final Direction direction;

    public static RobotState with(Position position, Direction direction) {
        return new RobotState(position, direction);
    }
}
