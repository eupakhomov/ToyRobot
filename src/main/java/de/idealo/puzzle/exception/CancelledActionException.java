package de.idealo.puzzle.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Thrown in case we received a request to invoke an action which causes an illegal robot state.
 *
 * @author <a href=mailto:eupakhomov@gmail.com>Eugene Pakhomov</a>
 */
@ResponseStatus(code = HttpStatus.CONFLICT, reason = "Illegal move: cancel to prevent robot from falling")
public class CancelledActionException extends RuntimeException {

    public CancelledActionException() {
        super();
    }

    public CancelledActionException(String message) {
        super(message);
    }

}
