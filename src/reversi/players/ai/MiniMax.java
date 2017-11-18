package reversi.players.ai;

import reversi.MovePosition;
import reversi.ReversiGame;
import reversi.players.ai.heuristics.AbstractHeuristic;

import java.util.List;

/**
 * Algorithme Mini-Max
 */
public class MiniMax {
    private AbstractHeuristic mHeuristic;

    public MiniMax(AbstractHeuristic heuristic) {
        mHeuristic = heuristic;
    }

    /**
     * Explore l'arbre des possibilités en utilisant l'algorithme Mini-Max
     * @return le mouvement choisi
     */
    public MovePosition explore(ReversiGame game, int depth) {
        if(game == null)
            throw new IllegalArgumentException("game cannot be null");

        System.out.println("----------");
        System.out.println("Début de l'exploration min/max");

        ReversiGame virtualGame = game.copy();

        double maxScore = Double.NEGATIVE_INFINITY;
        MovePosition bestMove = null;

        List<MovePosition> movesAvailable = virtualGame.getPossibleMoves(virtualGame.getCurrentPlayerColor());

        System.out.println("Il y a " + movesAvailable.size() + " coups disponibles.");

        for(MovePosition pos : movesAvailable) {

            // simule le coup
            virtualGame.performMove(pos, virtualGame.getCurrentPlayerColor());

            double score = min(virtualGame, depth);

            if(score > maxScore || score == maxScore && Math.random() > 0.5) {
                maxScore = score;
                bestMove = pos;
            }

        }

        System.out.println("Fin de l'explortation min/max, meilleur coup -> " + bestMove + ", score -> " + maxScore);
        System.out.println("----------\n");

        return bestMove;

    }

    /**
     * Calcule du max
     * @return mouvement choisi
     */
    private double min(ReversiGame game, int depth) {
        if(depth == 0 || game.isGameOver()) {
            double score = mHeuristic.evaluate(game, game.getCurrentPlayerColor());
            System.out.println("Calculated score is : " + score);
            return score;
        }

        double minScore = Double.POSITIVE_INFINITY;
        ReversiGame virtualGame = game.copy();

        List<MovePosition> moves = virtualGame.getPossibleMoves(virtualGame.getCurrentPlayerColor());

        for(MovePosition pos : moves) {

            // simule le coup
            virtualGame.performMove(pos, virtualGame.getCurrentPlayerColor());

            double score = max(virtualGame, depth - 1);

            if(score < minScore || score == minScore && Math.random() > 0.5)
                minScore = score;
        }

        return minScore;
    }

    /**
     * Calcule du min
     * @return mouvement choisi
     */
    private double max(ReversiGame game, int depth) {
        if(depth == 0 || game.isGameOver()) {
            double score = mHeuristic.evaluate(game, game.getCurrentPlayerColor());
            System.out.println("Calculated score is : " + score);
            return score;
        }

        double maxScore = Double.NEGATIVE_INFINITY;
        ReversiGame virtualGame = game.copy();

        List<MovePosition> moves = virtualGame.getPossibleMoves(virtualGame.getCurrentPlayerColor());

        for(MovePosition pos : moves) {

            // simule le coup
            virtualGame.performMove(pos, virtualGame.getCurrentPlayerColor());

            double score = min(virtualGame, depth - 1);

            if(score > maxScore || score == maxScore && Math.random() > 0.5)
                maxScore = score;
        }

        return maxScore;
    }
}
