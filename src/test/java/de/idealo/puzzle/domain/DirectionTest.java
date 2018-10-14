package de.idealo.puzzle.domain;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * Test for {@link Direction}.
 *
 * @author <a href=mailto:eupakhomov@gmail.com>Eugene Pakhomov</a>
 */
public class DirectionTest {

    @Test
    public void left() {
        Direction underTest = Direction.NORTH;

        Direction rotated = underTest.left();
        assertThat(rotated, is(Direction.WEST));

        rotated = rotated.left();
        assertThat(rotated, is(Direction.SOUTH));

        rotated = rotated.left();
        assertThat(rotated, is(Direction.EAST));

        rotated = rotated.left();
        assertThat(rotated, is(Direction.NORTH));
    }

    @Test
    public void right() {
        Direction underTest = Direction.NORTH;

        Direction rotated = underTest.right();
        assertThat(rotated, is(Direction.EAST));

        rotated = rotated.right();
        assertThat(rotated, is(Direction.SOUTH));

        rotated = rotated.right();
        assertThat(rotated, is(Direction.WEST));

        rotated = rotated.right();
        assertThat(rotated, is(Direction.NORTH));
    }
}