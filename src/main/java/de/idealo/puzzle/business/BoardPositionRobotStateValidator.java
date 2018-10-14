package de.idealo.puzzle.business;

import de.idealo.puzzle.domain.Board;
import de.idealo.puzzle.domain.RobotState;
import de.idealo.puzzle.exception.CancelledActionException;
import org.springframework.stereotype.Component;

/**
 * Responsible for validation of a new robot state, caused by certain action.
 * This implementation checks if a new robot position is within board boundaries.
 *
 * @author <a href=mailto:eupakhomov@gmail.com>Eugene Pakhomov</a>
 */
@Component
public class BoardPositionRobotStateValidator implements RobotStateValidator {
    private final Board board;

    public BoardPositionRobotStateValidator(BoardService boardService) {
        this.board = boardService.getBoard();
    }

    public void validate(RobotState newState) {
        if(isOutOfBoundaries(board.getDimensionX(), newState.getPosition().getX())
            || isOutOfBoundaries(board.getDimensionY(), newState.getPosition().getY())) {
               throw new CancelledActionException();
        }
    }

    private boolean isOutOfBoundaries(int dimension, int position) {
        return position < 0 || position >= dimension;
    }
}
