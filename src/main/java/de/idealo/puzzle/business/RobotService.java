package de.idealo.puzzle.business;

import de.idealo.puzzle.domain.Action;
import de.idealo.puzzle.domain.Direction;
import de.idealo.puzzle.domain.Position;
import de.idealo.puzzle.domain.RobotState;
import de.idealo.puzzle.exception.CancelledActionException;
import de.idealo.puzzle.exception.RobotMissingException;

/**
 * Handles actions performed on the robot.
 *
 * @author <a href=mailto:eupakhomov@gmail.com>Eugene Pakhomov</a>
 */
public interface RobotService {

    /**
     * Sets initial robot coordinates and direction.
     *
     * @param position robot's y coordinates
     * @param direction robot's direction
     * @return a resulting robot state
     * @throws CancelledActionException if the robot cannot be placed with given parameters
     */
    RobotState setInitialState(Position position, Direction direction);

    /**
     * Invokes MOVE, LEFT or RIGHT action on the robot and transforms an old robot state to a new state.
     *
     * @param action action to invoke on the robot
     * @param currentState a current robot state
     * @return a new robot state caused by action invocation
     * @throws UnsupportedOperationException if given action is not supported by the method
     * @throws CancelledActionException if given action cannot be applied to the robot as it causes an illegal new state
     * @throws RobotMissingException if robot was not placed on the board
     */
    RobotState invokeAction(Action action, RobotState currentState);
}
