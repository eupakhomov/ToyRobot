package de.idealo.puzzle.business;

import de.idealo.puzzle.domain.Board;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Responsible for providing a game board.
 * This implementation creates a board with certain dimensions which are taken from parameters (5x5 by default).
 *
 * @author <a href=mailto:eupakhomov@gmail.com>Eugene Pakhomov</a>
 */
@Service
public class BoardServiceImpl implements BoardService{
    private final Board board;

    public BoardServiceImpl(@Value("${de.idealo.puzzle.board.dimension.x:5}") int dimensionX,
                            @Value("${de.idealo.puzzle.board.dimension.y:5}") int dimensionY) {
        this.board = new Board(dimensionX, dimensionY);
    }

    @Override
    public Board getBoard() {
        return board;
    }
}
