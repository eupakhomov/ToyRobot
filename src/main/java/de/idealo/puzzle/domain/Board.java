package de.idealo.puzzle.domain;

import lombok.Data;

/**
 * Board entity.
 *
 * @author <a href=mailto:eupakhomov@gmail.com>Eugene Pakhomov</a>
 */
@Data
public class Board {
    private final int dimensionX;
    private final int dimensionY;
}
