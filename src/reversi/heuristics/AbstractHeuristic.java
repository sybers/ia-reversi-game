package reversi.heuristics;

import reversi.ReversiGame;

public abstract class AbstractHeuristic {

    /**
     * Evaluate the game board using the current heuristic
     * @return score
     */
    public abstract double evaluate(ReversiGame game);

    /**
     * Copie de l'instance
     * @return nouvelle instante
     */
    public abstract AbstractHeuristic copy();
}
