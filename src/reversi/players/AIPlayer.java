package reversi.players;

import reversi.MovePosition;
import reversi.PieceColor;
import reversi.ReversiGame;

import reversi.heuristics.AbstractHeuristic;

public class AIPlayer implements PlayerInterface {

    private int mScore = 0;
    private PieceColor mColor;

    private AbstractHeuristic mHeuristic;
    private int mDepth;

    public AIPlayer(PieceColor c, AbstractHeuristic heuristic, int depth) {
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
    public PieceColor getColor() {
        return mColor;
    }

    @Override
    public MovePosition playTurn(ReversiGame game) {

        MiniMax minimax = new MiniMax(mHeuristic);
        minimax.enableAlphaBeta();
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
