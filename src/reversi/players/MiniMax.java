package reversi.players;

import reversi.MovePosition;
import reversi.ReversiGame;
import reversi.heuristics.AbstractHeuristic;

import java.util.List;

/**
 * Algorithme Mini-Max
 */
public final class MiniMax {
    private AbstractHeuristic mHeuristic;

    private boolean mUseAlphaBeta = false;

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

        double alpha = Double.NEGATIVE_INFINITY;
        double beta = Double.POSITIVE_INFINITY;

        double maxScore = Double.NEGATIVE_INFINITY;
        MovePosition bestMove = null;

        List<MovePosition> moves = game.getPossibleMoves(game.getCurrentPlayer());
        ReversiGame virtualGame;

        for(MovePosition pos : moves) {

            virtualGame = game.copy();

            // simule le coup
            virtualGame.play(pos);

            double score = mini(virtualGame, depth - 1, alpha, beta);

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
    private double mini(ReversiGame game, int depth, double alpha, double beta) {
        if(depth == 0 || game.isGameOver()){
            return mHeuristic.evaluate(game);
        }

        double score = Double.POSITIVE_INFINITY;

        List<MovePosition> moves = game.getPossibleMoves(game.getCurrentPlayer());
        ReversiGame virtualGame;

        // pas de coups, on passe au joueur suivant
        if (moves.isEmpty()) {
            virtualGame = game.copy();
            virtualGame.play(); // ceci passera le tour au joueur suivant
            return maxi(virtualGame, depth - 1, alpha, beta);
        }


        // minimum des noeuds fils
        for(MovePosition pos : moves) {

            virtualGame = game.copy();

            // simule le coup
            virtualGame.play(pos);

            score = Math.min(score, maxi(virtualGame, depth - 1, alpha, beta));

            if(mUseAlphaBeta && score >= beta)
                return score;

            beta = Math.min(beta, score);
        }

        return score;
    }

    /**
     * Calcul du max
     * @return mouvement choisi
     */
    private double maxi(ReversiGame game, int depth, double alpha, double beta) {
        if(depth == 0 || game.isGameOver()) {
            return mHeuristic.evaluate(game);
        }

        double score = Double.NEGATIVE_INFINITY;

        List<MovePosition> moves = game.getPossibleMoves(game.getCurrentPlayer());
        ReversiGame virtualGame;

        // pas de coups, on passe au joueur suivant
        if (moves.isEmpty()) {
            virtualGame = game.copy();
            virtualGame.play(); // ceci passera le tour au joueur suivant
            return mini(virtualGame, depth - 1, alpha, beta);
        }

        // maximum des noeuds fils
        for(MovePosition pos : moves) {

            virtualGame = game.copy();

            // simule le coup
            virtualGame.play(pos);

            score = Math.max(alpha, mini(virtualGame, depth - 1, alpha, beta));

            if(mUseAlphaBeta && score <= alpha)
                return score;

            alpha = Math.max(alpha, score);
        }

        return score;
    }
}
