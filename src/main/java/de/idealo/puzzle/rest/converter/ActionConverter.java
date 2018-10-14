package de.idealo.puzzle.rest.converter;

import de.idealo.puzzle.domain.Action;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * We need this converter as we must be able to accept standard lowercase URL.
 *
 * @author <a href=mailto:eupakhomov@gmail.com>Eugene Pakhomov</a>
 */
@Component
public class ActionConverter implements Converter<String, Action> {
    @Override
    public Action convert(String s) {
        return s != null ? Action.valueOf(s.toUpperCase()) : null;
    }
}
