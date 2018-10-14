package de.idealo.puzzle.business;

import de.idealo.puzzle.domain.Action;
import de.idealo.puzzle.domain.Direction;
import de.idealo.puzzle.domain.Position;
import de.idealo.puzzle.domain.Report;
import de.idealo.puzzle.exception.CancelledActionException;
import de.idealo.puzzle.exception.NotFoundException;
import de.idealo.puzzle.exception.RobotMissingException;

/**
 * Responsible for creating a game and handling all the game actions.
 *
 * @author <a href=mailto:eupakhomov@gmail.com>Eugene Pakhomov</a>
 */
public interface GameService {

    /**
     * Creates a new game and returns game id.
     *
     * @return the created game id
     */
    String createGame();

    /**
     * Placing robot on the game board for a given game.
     *
     * @param gameId id of a game
     * @param position robot's coordinates
     * @param direction robot's direction
     * @throws NotFoundException if a game is not found by id
     * @throws CancelledActionException if a requested robot state is not valid
     */
    void place(String gameId, Position position, Direction direction);

    /**
     * Invoking MOVE, LEFT or RIGHT action on the robot for a given game.
     *
     * @param gameId id of a game
     * @param action the action to be invoked
     * @throws UnsupportedOperationException if given action is not supported by the method
     * @throws NotFoundException if a game is not found by id
     * @throws RobotMissingException if the robot was not placed on the board
     * @throws CancelledActionException if a requested robot state is not valid
     */
    void invokeAction(String gameId, Action action);

    /**
     * Returns report of a robot state for a given game.
     *
     * @param gameId id of a game
     * @return report of a robot state
     * @throws NotFoundException if a game is not found by id
     * @throws RobotMissingException if the robot was not placed on the board
     */
    Report getReport(String gameId);
}
