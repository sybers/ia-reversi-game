package reversi.players.ai.heuristics;

import reversi.Piece;
import reversi.ReversiGame;

public abstract class AbstractHeuristic {

    /**
     * Evaluate the game board using the current heuristic
     * @return score
     */
    public abstract double evaluate(ReversiGame game, Piece.Color playerColor);
}
