package de.idealo.puzzle.rest.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * Game data DTO.
 *
 * @author <a href=mailto:eupakhomov@gmail.com>Eugene Pakhomov</a>
 */
@Data
public class GameDTO implements Serializable {
    private final String id;
}
