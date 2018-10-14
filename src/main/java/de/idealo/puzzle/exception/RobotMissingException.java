package de.idealo.puzzle.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Thrown in case we issue some actions before placing robot.
 *
 * @author <a href=mailto:eupakhomov@gmail.com>Eugene Pakhomov</a>
 */
@ResponseStatus(code = HttpStatus.CONFLICT, reason = "Robot missing")
public class RobotMissingException extends RuntimeException {
    public RobotMissingException() {
        super();
    }

    public RobotMissingException(String message) {
        super(message);
    }
}
