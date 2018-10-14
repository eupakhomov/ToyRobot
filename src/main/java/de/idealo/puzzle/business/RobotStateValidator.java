package de.idealo.puzzle.business;

import de.idealo.puzzle.domain.RobotState;
import de.idealo.puzzle.exception.CancelledActionException;

/**
 * Responsible for validation of a new robot state, caused by certain action.
 *
 * @author <a href=mailto:eupakhomov@gmail.com>Eugene Pakhomov</a>
 */
public interface RobotStateValidator {

    /**
     * Validates a new robot state.
     *
     * @param newState a new robot state
     * @throws CancelledActionException if a new robot state is not valid
     */
    void validate(RobotState newState);
}
