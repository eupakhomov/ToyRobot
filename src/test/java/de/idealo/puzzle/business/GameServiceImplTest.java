package de.idealo.puzzle.business;

import de.idealo.puzzle.domain.*;
import de.idealo.puzzle.exception.CancelledActionException;
import de.idealo.puzzle.exception.NotFoundException;
import de.idealo.puzzle.exception.RobotMissingException;
import de.idealo.puzzle.repository.GameRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Test for {@link GameServiceImpl}.
 *
 * @author <a href=mailto:eupakhomov@gmail.com>Eugene Pakhomov</a>
 */
@RunWith(MockitoJUnitRunner.class)
public class GameServiceImplTest {

    @Mock
    GameRepository gameRepository;
    @Mock
    RobotService robotService;
    GameService underTest;
    String gameId = "aaaaaaaa-bbbb-cccc-dddd-aaaaaaaaaaaa";
    RobotState defaultState = RobotState.with(Position.DEFAULT_POSITION, Direction.DEFAULT_DIRECTION);

    @Before
    public void setup() {
        underTest = new GameServiceImpl(gameRepository, robotService);
    }

    @Test
    public void createGame() {
        Game game = new Game(gameId, null);
        when(gameRepository.save(any(Game.class))).thenReturn(game);

        assertThat(underTest.createGame(), is(gameId));
        verify(gameRepository).save(any(Game.class));
    }

    @Test
    public void invokeAction() {
        Game game = new Game(gameId, defaultState);
        RobotState newState = RobotState.with(Position.with(0, 1), Direction.DEFAULT_DIRECTION);

        when(gameRepository.findById(gameId)).thenReturn(Optional.of(game));
        when(robotService.invokeAction(Action.MOVE, defaultState))
                .thenReturn(newState);

        underTest.invokeAction(gameId, Action.MOVE);

        verify(gameRepository).findById(gameId);
        verify(robotService).invokeAction(Action.MOVE, defaultState);
        verify(gameRepository).save(new Game(gameId, newState));
    }

    @Test(expected = NotFoundException.class)
    public void invokeActionGameNotFound() {
        when(gameRepository.findById(gameId)).thenReturn(Optional.empty());

        underTest.invokeAction(gameId, Action.MOVE);
    }

    @Test(expected = RobotMissingException.class)
    public void invokeActionRobotMissing() {
        Game game = new Game(gameId, null);

        when(gameRepository.findById(gameId)).thenReturn(Optional.of(game));
        when(robotService.invokeAction(Action.MOVE, null))
                .thenThrow(new RobotMissingException());

        underTest.invokeAction(gameId, Action.MOVE);
    }

    @Test(expected = CancelledActionException.class)
    public void invokeActionCancelled() {
        RobotState currentState = RobotState.with(Position.DEFAULT_POSITION, Direction.SOUTH);
        Game game = new Game(gameId, currentState);

        when(gameRepository.findById(gameId)).thenReturn(Optional.of(game));
        when(robotService.invokeAction(Action.MOVE, currentState))
                .thenThrow(new CancelledActionException());

        underTest.invokeAction(gameId, Action.MOVE);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void invokeActionUnsupportedAction() {
        Game game = new Game(gameId, null);

        when(gameRepository.findById(gameId)).thenReturn(Optional.of(game));
        when(robotService.invokeAction(Action.PLACE, null))
                .thenThrow(new UnsupportedOperationException());

        underTest.invokeAction(gameId, Action.PLACE);
    }

    @Test
    public void getReport() {
        Game game = new Game(gameId, defaultState);
        Report expected = new Report(Position.DEFAULT_POSITION.getX(),
                Position.DEFAULT_POSITION.getY(),
                Direction.DEFAULT_DIRECTION);

        when(gameRepository.findById(gameId)).thenReturn(Optional.of(game));

        Report actual = underTest.getReport(gameId);

        assertThat(actual, is(expected));

        verify(gameRepository).findById(gameId);
        verify(gameRepository, never()).save(any(Game.class));
    }

    @Test(expected = NotFoundException.class)
    public void getReportGameNotFound() {
        when(gameRepository.findById(gameId)).thenReturn(Optional.empty());

        underTest.getReport(gameId);
    }

    @Test(expected = RobotMissingException.class)
    public void getReportRobotMissing() {
        Game game = new Game(gameId, null);

        when(gameRepository.findById(gameId)).thenReturn(Optional.of(game));

        underTest.getReport(gameId);
    }

    @Test
    public void place() {
        Game game = new Game(gameId, null);

        when(gameRepository.findById(gameId)).thenReturn(Optional.of(game));
        when(robotService.setInitialState(Position.DEFAULT_POSITION, Direction.DEFAULT_DIRECTION))
                .thenReturn(defaultState);

        underTest.place(gameId, Position.DEFAULT_POSITION, Direction.DEFAULT_DIRECTION);

        verify(gameRepository).findById(gameId);
        verify(robotService).setInitialState(Position.DEFAULT_POSITION, Direction.DEFAULT_DIRECTION);
        verify(gameRepository).save(new Game(gameId, defaultState));
    }

    @Test(expected = NotFoundException.class)
    public void placeGameNotFound() {
        when(gameRepository.findById(gameId)).thenReturn(Optional.empty());

        underTest.place(gameId, Position.DEFAULT_POSITION, Direction.DEFAULT_DIRECTION);
    }

    @Test(expected = CancelledActionException.class)
    public void placeActionCancelled() {
        Game game = new Game(gameId, null);
        Position invalidPosition = Position.with(-1, 0);

        when(gameRepository.findById(gameId)).thenReturn(Optional.of(game));
        when(robotService.setInitialState(invalidPosition, Direction.DEFAULT_DIRECTION))
                .thenThrow(new CancelledActionException());

        underTest.place(gameId, invalidPosition, Direction.DEFAULT_DIRECTION);
    }
}