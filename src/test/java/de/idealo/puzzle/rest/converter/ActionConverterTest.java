package de.idealo.puzzle.rest.converter;

import de.idealo.puzzle.domain.Action;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.*;

/**
 * Test for {@link ActionConverter}.
 *
 * @author <a href=mailto:eupakhomov@gmail.com>Eugene Pakhomov</a>
 */
public class ActionConverterTest {

    ActionConverter underTest = new ActionConverter();

    @Test
    public void convertNonNullLowerCase() {
        Action action = underTest.convert("move");
        assertThat(action, is(Action.MOVE));
    }

    @Test
    public void convertNonNullUpperCase() {
        Action action = underTest.convert("MOVE");
        assertThat(action, is(Action.MOVE));
    }

    @Test
    public void convertNull() {
        Action action = underTest.convert(null);
        assertThat(action, is(nullValue()));
    }
}