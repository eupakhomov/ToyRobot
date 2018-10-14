package de.idealo.puzzle.rest;

import de.idealo.puzzle.business.GameService;
import de.idealo.puzzle.domain.Action;
import de.idealo.puzzle.domain.Position;
import de.idealo.puzzle.exception.BadRequestException;
import de.idealo.puzzle.rest.dto.GameDTO;
import de.idealo.puzzle.rest.dto.action.ActionResultDTO;
import de.idealo.puzzle.rest.dto.action.PlaceActionParametersDTO;
import de.idealo.puzzle.rest.dto.action.ReportActionResultDTO;
import de.idealo.puzzle.rest.dto.action.VoidActionResultDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.*;

/**
 * Game controller.
 * Entry point for all the game actions.
 *
 * @author <a href=mailto:eupakhomov@gmail.com>Eugene Pakhomov</a>
 */
@RestController
@RequestMapping("/api")
@Api(value="robotgame", description="Toy Robot game API")
public class GameController {

    public static final VoidActionResultDTO VOID_RESULT = new VoidActionResultDTO();
    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @ApiOperation(value = "Creates a new game")
    @PostMapping("/games")
    public GameDTO createGame() {
        return new GameDTO(gameService.createGame());
    }

    @ApiOperation(value = "Sends a command to the robot",
            notes = "Returns action result with result type `object` for `report` command and of type `void` for others",
            response = ActionResultDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Action successfully invoked", response = ActionResultDTO.class),
            @ApiResponse(code = 400, message = "Illegal move or action parameters required but not passed"),
            @ApiResponse(code = 404, message = "Game with given id does not exist"),
            @ApiResponse(code = 409, message = "Action requires robot to be on the board but robot was not placed there"),
            @ApiResponse(code = 500, message = "Internal server error")}
    )
    @PostMapping("/games/{gameId}/robot/actions/{actionId}/invoke")
    public ActionResultDTO invokeAction(@PathVariable String gameId,
                                        @PathVariable Action actionId,
                                        @RequestBody(required = false) PlaceActionParametersDTO parameters) {
        if(actionId == Action.PLACE) {
            if(parameters == null) {
                throw new BadRequestException("Place action parameters not passed");
            }

            gameService.place(gameId, Position.with(parameters.getX(), parameters.getY()), parameters.getFacing());
        } else if(actionId == Action.REPORT) {
            return new ReportActionResultDTO(gameService.getReport(gameId));
        } else {
            gameService.invokeAction(gameId, actionId);
        }

        return VOID_RESULT;
    }
}
