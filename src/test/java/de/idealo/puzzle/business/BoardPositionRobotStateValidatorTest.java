package de.idealo.puzzle.business;

import de.idealo.puzzle.domain.Board;
import de.idealo.puzzle.domain.Direction;
import de.idealo.puzzle.domain.Position;
import de.idealo.puzzle.domain.RobotState;
import de.idealo.puzzle.exception.CancelledActionException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

/**
 * Test for {@link BoardPositionRobotStateValidator}.
 *
 * @author <a href=mailto:eupakhomov@gmail.com>Eugene Pakhomov</a>
 */
@RunWith(MockitoJUnitRunner.class)
public class BoardPositionRobotStateValidatorTest {

    @Mock
    BoardService boardService;
    RobotStateValidator underTest;
    Board board = new Board(5, 5);

    @Before
    public void setup() {
        when(boardService.getBoard()).thenReturn(board);
        underTest = new BoardPositionRobotStateValidator(boardService);
    }

    @Test
    public void validate() {
        RobotState robotState = RobotState.with(Position.with(0,0), Direction.NORTH);
        underTest.validate(robotState);
    }

    @Test(expected = CancelledActionException.class)
    public void validateNonValidStateCase1() {
        RobotState robotState = RobotState.with(Position.with(-1,0), Direction.NORTH);
        underTest.validate(robotState);
    }

    @Test(expected = CancelledActionException.class)
    public void validateNonValidStateCase2() {
        RobotState robotState = RobotState.with(Position.with(0,5), Direction.NORTH);
        underTest.validate(robotState);
    }
}