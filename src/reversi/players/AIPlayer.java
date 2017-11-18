package reversi.players;

import reversi.MovePosition;
import reversi.ReversiGame;

import reversi.players.ai.MiniMax;
import reversi.players.ai.heuristics.AbstractHeuristic;

import java.util.List;

public class AIPlayer extends AbstractPlayer {

    private AbstractHeuristic mHeuristic;
    private int mDepth = 3;

    public AIPlayer(AbstractHeuristic heuristic, int depth) {
        super();

        if(heuristic == null)
            throw new IllegalArgumentException("Heuristic cannot be null value");

        if(depth <= 0)
            throw new IllegalArgumentException("Depth must be a positive integer");

        mHeuristic = heuristic;
        mDepth = depth;
    }

    @Override
    public MovePosition playTurn(ReversiGame game, List<MovePosition> possibleMoves) {

        System.out.println(game.toString());

        MiniMax minimax = new MiniMax(mHeuristic);

        return minimax.explore(game, mDepth);
    }

    /**
     * Recopie du joueur
     * @return copie
     */
    @Override
    public AbstractPlayer copy() {
        AIPlayer copy = new AIPlayer(mHeuristic, mDepth);
        copy.setScore(mScore);
        copy.setColor(mColor);
        return copy;
    }
}
