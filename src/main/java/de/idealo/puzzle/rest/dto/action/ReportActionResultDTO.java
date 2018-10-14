package de.idealo.puzzle.rest.dto.action;

import de.idealo.puzzle.domain.Report;

/**
 * Action result with report DTO.
 *
 * @author <a href=mailto:eupakhomov@gmail.com>Eugene Pakhomov</a>
 */
public class ReportActionResultDTO extends ActionResultDTO<Report> {
    public ReportActionResultDTO(Report result) {
        super(ActionResultType.OBJECT, result);
    }
}
