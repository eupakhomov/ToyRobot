package de.idealo.puzzle.rest;

import de.idealo.puzzle.business.GameService;
import de.idealo.puzzle.domain.Action;
import de.idealo.puzzle.domain.Direction;
import de.idealo.puzzle.domain.Position;
import de.idealo.puzzle.domain.Report;
import de.idealo.puzzle.exception.BadRequestException;
import de.idealo.puzzle.rest.dto.GameDTO;
import de.idealo.puzzle.rest.dto.action.PlaceActionParametersDTO;
import de.idealo.puzzle.rest.dto.action.ReportActionResultDTO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Test for {@link GameController}.
 *
 * @author <a href=mailto:eupakhomov@gmail.com>Eugene Pakhomov</a>
 */
@RunWith(MockitoJUnitRunner.class)
public class GameControllerTest {

    @Mock
    GameService gameService;

    GameController underTest;

    String gameId = "aaaaaaaa-bbbb-cccc-dddd-aaaaaaaaaaaa";

    @Before
    public void setup() {
        underTest = new GameController(gameService);
    }

    @Test
    public void createGame() {
        when(gameService.createGame()).thenReturn(gameId);
        assertThat(underTest.createGame(), is(new GameDTO(gameId)));
        verify(gameService).createGame();
    }

    @Test
    public void invokeAction() {
        assertThat(underTest.invokeAction(gameId, Action.MOVE, null), is(GameController.VOID_RESULT));
        verify(gameService).invokeAction(gameId, Action.MOVE);
    }

    @Test
    public void invokeActionReport() {
        Report report = new Report(Position.DEFAULT_POSITION.getX(),
                Position.DEFAULT_POSITION.getY(),
                Direction.DEFAULT_DIRECTION);

        ReportActionResultDTO expected = new ReportActionResultDTO(report);
        when(gameService.getReport(gameId)).thenReturn(report);

        assertThat(underTest.invokeAction(gameId, Action.REPORT, null), is(expected));
        verify(gameService).getReport(gameId);
    }

    @Test
    public void invokeActionPlace() {
        PlaceActionParametersDTO parameters = new PlaceActionParametersDTO();
        parameters.setX(Position.DEFAULT_POSITION.getX());
        parameters.setY(Position.DEFAULT_POSITION.getY());
        parameters.setFacing(Direction.DEFAULT_DIRECTION);

        assertThat(underTest.invokeAction(gameId, Action.PLACE, parameters), is(GameController.VOID_RESULT));
        verify(gameService).place(gameId,
                Position.with(parameters.getX(), parameters.getY()),
                parameters.getFacing());
    }

    @Test(expected = BadRequestException.class)
    public void invokeActionPlaceBadRequest() {
        underTest.invokeAction(gameId, Action.PLACE, null);
    }
}