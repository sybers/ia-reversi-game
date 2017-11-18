package reversi.players.ai.heuristics;

import reversi.Piece;
import reversi.ReversiGame;

public class MobilityHeuristic extends AbstractHeuristic {

    /**
     * Cette heuristique maximise le nombre de coups possibles pour le joueur MAX
     * et minimise ceux possible pour le joueur MIN.
     * @param game instance du jeu
     * @param playerColor couleur du joueur MAX
     * @return score de la position à évaluer
     */
    @Override
    public double evaluate(ReversiGame game, Piece.Color playerColor) {
        int maxPlayerMoves = game.getPossibleMoves(playerColor).size();
        int minPlayerMoves = game.getPossibleMoves(playerColor == Piece.Color.White ? Piece.Color.Black : Piece.Color.White).size();

        if(maxPlayerMoves + minPlayerMoves != 0)
            return 100 * (maxPlayerMoves - minPlayerMoves) / (maxPlayerMoves + minPlayerMoves);
        else
            return 0;
    }
}
