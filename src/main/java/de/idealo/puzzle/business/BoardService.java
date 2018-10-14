package de.idealo.puzzle.business;

import de.idealo.puzzle.domain.Board;

/**
 * Responsible for providing a game board.
 *
 * @author <a href=mailto:eupakhomov@gmail.com>Eugene Pakhomov</a>
 */
public interface BoardService {

    /**
     * Returns a game board.
     *
     * @return a game board
     */
    Board getBoard();
}
