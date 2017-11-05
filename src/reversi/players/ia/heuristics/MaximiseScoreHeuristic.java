package reversi.players.ia.heuristics;

import reversi.Piece;
import reversi.ReversiGame;
import reversi.players.ia.AbstractHeuristic;

public class MaximiseScoreHeuristic extends AbstractHeuristic {

    /**
     * Cette heuristique maximise le score du joueur MAX et minimise le score du joueur MIN
     * @param game instance du jeu
     * @param playerColor couleur du joueur MAX
     * @return score de la position à évaluer
     */
    @Override
    public double evaluate(ReversiGame game, Piece.Color playerColor) {
        int MaxScore = game.getPlayerByColor(playerColor).getScore();
        int MinScore = game.getPlayerByColor(playerColor == Piece.Color.White ? Piece.Color.Black : Piece.Color.White).getScore();
        return 100* (MaxScore - MinScore) / (MaxScore + MinScore);
    }
}
