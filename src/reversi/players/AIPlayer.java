package reversi.players;

import reversi.MovePosition;
import reversi.Piece;
import reversi.ReversiGame;

import reversi.players.ai.MiniMax;
import reversi.players.ai.heuristics.AbstractHeuristic;

public class AIPlayer extends AbstractPlayer {

    private AbstractHeuristic mHeuristic;
    private int mDepth = 3;

    public AIPlayer(Piece.Color c, AbstractHeuristic heuristic, int depth) {
        super(c);

        if(heuristic == null)
            throw new IllegalArgumentException("Heuristic cannot be null value");

        if(depth <= 0)
            throw new IllegalArgumentException("Depth must be a positive integer");

        mHeuristic = heuristic;
        mDepth = depth;
    }

    @Override
    public MovePosition playTurn(ReversiGame game) {

        //System.out.println(game.toString());

        MiniMax minimax = new MiniMax(mHeuristic);

        // long startTime = System.currentTimeMillis();
        // long endTime = System.currentTimeMillis();
        // System.out.println("Total execution time: " + (endTime-startTime) + "ms");

        return minimax.explore(game, mDepth);
    }

    /**
     * Recopie du joueur
     * @return copie
     */
    @Override
    public AbstractPlayer copy() {
        AIPlayer copy = new AIPlayer(mColor, mHeuristic.copy(), mDepth);
        copy.setScore(mScore);
        return copy;
    }
}
