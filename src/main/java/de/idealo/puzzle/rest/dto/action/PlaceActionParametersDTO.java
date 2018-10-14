package de.idealo.puzzle.rest.dto.action;

import de.idealo.puzzle.domain.Direction;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Place action parameters DTO.
 *
 * @author <a href=mailto:eupakhomov@gmail.com>Eugene Pakhomov</a>
 */
@Data
public class PlaceActionParametersDTO implements Serializable {
    private int x;
    private int y;

    @NotNull
    private Direction facing;
}
