package de.idealo.puzzle.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * Report entity.
 * Contains data about robot state.
 *
 * @author <a href=mailto:eupakhomov@gmail.com>Eugene Pakhomov</a>
 */
@Data
public class Report implements Serializable {
    private final int x;
    private final int y;
    private final Direction facing;
}
