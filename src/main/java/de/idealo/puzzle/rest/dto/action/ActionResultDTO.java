package de.idealo.puzzle.rest.dto.action;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;

/**
 * Action result DTO.
 * Compliant with Restful Objects Specification v1.1.0 paragraphs 20.3, 20.4.
 *
 * @author <a href=mailto:eupakhomov@gmail.com>Eugene Pakhomov</a>
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public abstract class ActionResultDTO<T> implements Serializable {
    private final String resultType;
    private final T result;

    public ActionResultDTO(ActionResultType resultType, T result) {
        this.resultType = resultType.name().toLowerCase();
        this.result = result;
    }
}
