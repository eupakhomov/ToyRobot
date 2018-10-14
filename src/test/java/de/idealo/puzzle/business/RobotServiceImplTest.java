package de.idealo.puzzle.business;

import de.idealo.puzzle.domain.Action;
import de.idealo.puzzle.domain.Direction;
import de.idealo.puzzle.domain.Position;
import de.idealo.puzzle.domain.RobotState;
import de.idealo.puzzle.exception.CancelledActionException;
import de.idealo.puzzle.exception.RobotMissingException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Test for {@link RobotServiceImpl}.
 *
 * @author <a href=mailto:eupakhomov@gmail.com>Eugene Pakhomov</a>
 */
@RunWith(MockitoJUnitRunner.class)
public class RobotServiceImplTest {

    @Mock
    RobotStateValidator robotStateValidator;

    RobotState robotState = RobotState.with(Position.DEFAULT_POSITION, Direction.DEFAULT_DIRECTION);

    RobotService underTest;

    @Before
    public void setup() {
        underTest = new RobotServiceImpl(robotStateValidator);
    }

    @Test
    public void setInitialState() {
        RobotState actual = underTest.setInitialState(Position.DEFAULT_POSITION, Direction.DEFAULT_DIRECTION);
        assertThat(actual, is(robotState));
        verify(robotStateValidator).validate(actual);
    }


    @Test(expected = CancelledActionException.class)
    public void setInitialStateActionCancelled() {
        doThrow(new CancelledActionException()).when(robotStateValidator).validate(robotState);
        underTest.setInitialState(Position.DEFAULT_POSITION, Direction.DEFAULT_DIRECTION);
    }

    @Test
    public void invokeActionLeft() {
        RobotState actual = underTest.invokeAction(Action.LEFT, robotState);
        assertThat(actual, is(RobotState.with(Position.DEFAULT_POSITION, Direction.WEST)));
    }

    @Test
    public void invokeActionRight() {
        RobotState actual = underTest.invokeAction(Action.RIGHT, robotState);
        assertThat(actual, is(RobotState.with(Position.DEFAULT_POSITION, Direction.EAST)));
    }

    @Test
    public void invokeActionMoveCase1() {
        RobotState actual = underTest.invokeAction(Action.MOVE, robotState);
        assertThat(actual, is(RobotState.with(Position.with(0, 1), Direction.DEFAULT_DIRECTION)));
    }

    @Test
    public void invokeActionMoveCase2() {
        RobotState actual = underTest.invokeAction(Action.MOVE,
                RobotState.with(Position.DEFAULT_POSITION, Direction.EAST));
        assertThat(actual, is(RobotState.with(Position.with(1, 0), Direction.EAST)));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void invokeActionUnsupportedOperation() {
        underTest.invokeAction(Action.REPORT, robotState);
    }

    @Test(expected = RobotMissingException.class)
    public void invokeActionRobotMissing() {
        underTest.invokeAction(Action.MOVE, null);
    }

    @Test(expected = CancelledActionException.class)
    public void invokeActionCancelled() {
        doThrow(new CancelledActionException()).when(robotStateValidator).validate(RobotState.with(
                Position.with(0, -1), Direction.SOUTH));
        underTest.invokeAction(Action.MOVE, RobotState.with(
                Position.DEFAULT_POSITION, Direction.SOUTH));
    }
}