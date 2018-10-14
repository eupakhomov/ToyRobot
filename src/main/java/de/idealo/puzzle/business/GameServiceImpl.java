package de.idealo.puzzle.business;

import de.idealo.puzzle.domain.*;
import de.idealo.puzzle.exception.NotFoundException;
import de.idealo.puzzle.exception.RobotMissingException;
import de.idealo.puzzle.repository.GameRepository;
import org.springframework.stereotype.Service;

import static de.idealo.puzzle.business.RobotUtil.isRobotPlaced;

/**
 * Implements {@link GameService}.
 * Responsible for a robot state persistence.
 *
 * @author <a href=mailto:eupakhomov@gmail.com>Eugene Pakhomov</a>
 */
@Service
public class GameServiceImpl implements GameService {

    private final GameRepository gameRepository;
    private final RobotService robotService;

    public GameServiceImpl(GameRepository gameRepository, RobotService robotService) {
        this.gameRepository = gameRepository;
        this.robotService = robotService;
    }

    @Override
    public String createGame() {
        return gameRepository.save(new Game()).getId();
    }

    @Override
    public synchronized void place(String gameId, Position position, Direction facing) {
        Game game = getGame(gameId);
        RobotState newState = robotService.setInitialState(position,facing);
        updateGame(game, newState);
    }

    @Override
    public synchronized void invokeAction(final String gameId, final Action action) {
        Game game = getGame(gameId);
        RobotState newState = robotService.invokeAction(action, game.getRobotState());
        updateGame(game, newState);
    }

    @Override
    public Report getReport(String gameId) {
        Game game = getGame(gameId);
        RobotState currentState = game.getRobotState();

        if(!isRobotPlaced(currentState)) {
            throw new RobotMissingException();
        }

        return new Report(currentState.getPosition().getX(),
                currentState.getPosition().getY(),
                currentState.getDirection());
    }

    private Game getGame(String gameId) {
        return gameRepository.findById(gameId)
                .orElseThrow(() -> new NotFoundException("Game " + gameId + " not found"));
    }

    private void updateGame(Game game, RobotState newState) {
        game.setRobotState(newState);
        gameRepository.save(game);
    }
}
