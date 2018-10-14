package de.idealo.puzzle.business;


import de.idealo.puzzle.domain.Action;
import de.idealo.puzzle.domain.Direction;
import de.idealo.puzzle.domain.Position;
import de.idealo.puzzle.domain.RobotState;
import de.idealo.puzzle.exception.RobotMissingException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static de.idealo.puzzle.business.RobotUtil.isRobotPlaced;

/**
 * Implements {@link RobotService}.
 *
 * @author <a href=mailto:eupakhomov@gmail.com>Eugene Pakhomov</a>
 */
@Service
public class RobotServiceImpl implements RobotService {
    private static final Map<Action, Function<RobotState, RobotState>> actions = new HashMap<>();

    private final RobotStateValidator robotStateValidator;

    public RobotServiceImpl(RobotStateValidator robotStateValidator) {
        this.robotStateValidator = robotStateValidator;

        actions.put(Action.MOVE, this::move);
        actions.put(Action.LEFT, this::left);
        actions.put(Action.RIGHT, this::right);
    }

    public RobotState setInitialState(Position position, Direction direction) {
        return validateState(RobotState.with(position, direction));
    }

    public RobotState invokeAction(final Action action, final RobotState currentState) {
        if(!isRobotPlaced(currentState)) {
            throw new RobotMissingException();
        }

        return actions.getOrDefault(action,
                state -> {throw new UnsupportedOperationException("Action handler is not set");})
                .apply(currentState);
    }

    private RobotState validateState(RobotState targetState) {
        robotStateValidator.validate(targetState);
        return targetState;
    }

    private RobotState left(RobotState currentState) {
        return RobotState.with(currentState.getPosition(), currentState.getDirection().left());
    }

    private RobotState right(RobotState currentState) {
        return RobotState.with(currentState.getPosition(), currentState.getDirection().right());
    }

    private RobotState move(RobotState currentState) {
        final Direction direction = currentState.getDirection();
        return validateState(RobotState.with(calculateNewPosition(direction, currentState.getPosition()), direction));
    }

    private Position calculateNewPosition(Direction direction, Position position) {
        return Position.with(position.getX() + direction.getDirX(),
                position.getY() + direction.getDirY());
    }
}
