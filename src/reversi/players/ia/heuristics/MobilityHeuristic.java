package reversi.players.ia.heuristics;

import reversi.Piece;
import reversi.ReversiGame;
import reversi.players.ia.AbstractHeuristic;

public class MobilityHeuristic extends AbstractHeuristic {

    /**
     * Cette heuristique maximise le score du joueur MAX et minimise le score du joueur MIN
     * @param game instance du jeu
     * @param playerColor couleur du joueur MAX
     * @return score de la position à évaluer
     */
    @Override
    public double evaluate(ReversiGame game, Piece.Color playerColor) {
        int maxPlayerMoves = game.getPossibleMoves(playerColor).size();
        int minPlayerMoves = game.getPossibleMoves(playerColor == Piece.Color.White ? Piece.Color.Black : Piece.Color.White).size();

        if(maxPlayerMoves + minPlayerMoves != 0) {
            return 100 * (maxPlayerMoves - minPlayerMoves) / (maxPlayerMoves + minPlayerMoves);
        } else {
            return 0;
        }
    }
}
