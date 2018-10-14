package de.idealo.puzzle.domain;

import lombok.Data;

/**
 * Robot position entity.
 *
 * @author <a href=mailto:eupakhomov@gmail.com>Eugene Pakhomov</a>
 */
@Data
public class Position {
    public final static Position DEFAULT_POSITION = Position.with(0, 0);

    private final int x;
    private final int y;

    public static Position with(int x, int y) {
        return new Position(x, y);
    }
}
