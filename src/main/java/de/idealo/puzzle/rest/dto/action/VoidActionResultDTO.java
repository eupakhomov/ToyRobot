package de.idealo.puzzle.rest.dto.action;

/**
 * Void action result DTO.
 *
 * @author <a href=mailto:eupakhomov@gmail.com>Eugene Pakhomov</a>
 */
public class VoidActionResultDTO extends ActionResultDTO<Void> {
    public VoidActionResultDTO() {
        super(ActionResultType.VOID, null);
    }
}
