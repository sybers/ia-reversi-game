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

        double maxScore = Double.NEGATIVE_INFINITY;
        MovePosition bestMove = null;

        List<MovePosition> moves = game.getPossibleMoves(game.getCurrentPlayer());
        ReversiGame virtualGame;

        System.out.println("Il y a " + moves.size() + " coups disponibles à partir de l'état de base.");

        for(MovePosition pos : moves) {

            virtualGame = game.copy();

            // simule le coup
            virtualGame.play(pos);

            double score = min(virtualGame, depth - 1);

            System.out.println("Score du coup : " + pos.toString() + " -> " + score);

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

        // pas de coups pour le joueur min mais la partie n'ets pas terminée, on passe au joueur max
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

        // pas de coups pour le joueur min mais la partie n'ets pas terminée, on passe au joueur max
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
