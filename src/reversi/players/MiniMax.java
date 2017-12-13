package reversi.players;

import reversi.MovePosition;
import reversi.ReversiGame;
import reversi.heuristics.AbstractHeuristic;

import java.util.List;

/**
 * Algorithme Mini-Max
 */
public final class MiniMax {
    private boolean mUseAlphaBeta = false;

    private AbstractHeuristic mHeuristic;

    public MiniMax(AbstractHeuristic heuristic) {
        mHeuristic = heuristic;
    }

    /**
     * Active l'élagage alpha-beta pour l'exploration des solutions
     */
    public void enableAlphaBeta() {
        mUseAlphaBeta = true;
    }

    /**
     * Explore l'arbre des possibilités en utilisant l'algorithme Mini-Max
     * @return le mouvement choisi
     */
    public MovePosition explore(ReversiGame game, int depth) {
        if(game == null)
            throw new IllegalArgumentException("game cannot be null");

        double maxScore = Double.NEGATIVE_INFINITY;
        MovePosition bestMove = null;

        List<MovePosition> moves = game.getPossibleMoves(game.getCurrentPlayer());
        ReversiGame virtualGame;

        for(MovePosition pos : moves) {

            virtualGame = game.copy();

            // simule le coup
            virtualGame.play(pos);

            double score = min(virtualGame, depth - 1);

            if(score > maxScore || score == maxScore && Math.random() > 0.5) {
                maxScore = score;
                bestMove = pos;
            }

        }

        return bestMove;
    }

    /**
     * Calcul du min
     * @return mouvement choisi
     */
    private double min(ReversiGame game, int depth) {
        if(depth == 0 || game.isGameOver()){
            return mHeuristic.evaluate(game);
        }

        double minScore = Double.POSITIVE_INFINITY;

        List<MovePosition> moves = game.getPossibleMoves(game.getCurrentPlayer());
        ReversiGame virtualGame;

        // pas de coups, on passe au joueur suivant
        if (moves.isEmpty()) {
            virtualGame = game.copy();
            virtualGame.play();
            return max(virtualGame, depth - 1);
        }


        for(MovePosition pos : moves) {

            virtualGame = game.copy();

            // simule le coup
            virtualGame.play(pos);

            double score = max(virtualGame, depth - 1);

            if(score < minScore)
                minScore = score;
        }

        return minScore;
    }

    /**
     * Calcul du max
     * @return mouvement choisi
     */
    private double max(ReversiGame game, int depth) {
        if(depth == 0 || game.isGameOver()) {
            return mHeuristic.evaluate(game);
        }

        double maxScore = Double.NEGATIVE_INFINITY;

        List<MovePosition> moves = game.getPossibleMoves(game.getCurrentPlayer());
        ReversiGame virtualGame;

        // pas de coups, on passe au joueur suivant
        if (moves.isEmpty()) {
            virtualGame = game.copy();
            virtualGame.play();
            return min(virtualGame, depth - 1);
        }

        for(MovePosition pos : moves) {

            virtualGame = game.copy();

            // simule le coup
            virtualGame.play(pos);

            double score = min(virtualGame, depth - 1);

            if(score > maxScore)
                maxScore = score;
        }

        return maxScore;
    }
}
