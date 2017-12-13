package reversi.heuristics;

import reversi.ReversiGame;

public class MobilityHeuristic extends AbstractHeuristic {

    /**
     * Cette heuristique maximise le nombre de coups possibles pour le joueur MAX
     * et minimise ceux possible pour le joueur MIN.
     * @param game instance du jeu
     * @return score de la position à évaluer
     */
    @Override
    public double evaluate(ReversiGame game) {
        int maxPlayerMoves = game.getPossibleMoves(game.getCurrentPlayer()).size();
        int minPlayerMoves = game.getPossibleMoves(game.getOpponentPlayer()).size();

        if(maxPlayerMoves + minPlayerMoves != 0)
            return 100 * (maxPlayerMoves - minPlayerMoves) / (maxPlayerMoves + minPlayerMoves);
        else
            return 0;
    }

    @Override
    public AbstractHeuristic copy() {
        return new MobilityHeuristic();
    }
}
