package de.idealo.puzzle.business;

import de.idealo.puzzle.domain.RobotState;

/**
 * Utility class to help with robot-related logic.
 *
 * @author <a href=mailto:eupakhomov@gmail.com>Eugene Pakhomov</a>
 */
public class RobotUtil {
    static boolean isRobotPlaced(RobotState currentState) {
        return currentState != null;
    }
}
