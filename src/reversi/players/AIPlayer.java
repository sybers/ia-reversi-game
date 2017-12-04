package reversi.players;

import reversi.MovePosition;
import reversi.Piece;
import reversi.ReversiGame;

import reversi.players.ai.MiniMax;
import reversi.players.ai.heuristics.AbstractHeuristic;

public class AIPlayer implements PlayerInterface {

    protected int mScore = 0;
    protected Piece.Color mColor;

    private AbstractHeuristic mHeuristic;
    private int mDepth = 3;

    public AIPlayer(Piece.Color c, AbstractHeuristic heuristic, int depth) {
        if(heuristic == null)
            throw new IllegalArgumentException("Heuristic cannot be null value");

        if(depth <= 0)
            throw new IllegalArgumentException("Depth must be a positive integer");

        mColor = c;
        mHeuristic = heuristic;
        mDepth = depth;
    }

    @Override
    public void setScore(int score) {
        mScore = score;
    }

    @Override
    public int getScore() {
        return mScore;
    }

    @Override
    public Piece.Color getColor() {
        return mColor;
    }

    @Override
    public MovePosition playTurn(ReversiGame game) {

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
    public PlayerInterface copy() {
        AIPlayer copy = new AIPlayer(mColor, mHeuristic.copy(), mDepth);
        copy.setScore(mScore);
        return copy;
    }
}
