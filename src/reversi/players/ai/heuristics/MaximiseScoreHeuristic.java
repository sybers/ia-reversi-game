package reversi.players.ai.heuristics;

import reversi.ReversiGame;

public class MaximiseScoreHeuristic extends AbstractHeuristic {

    /**
     * Cette heuristique maximise le score du joueur MAX et minimise le score du joueur MIN
     * @param game instance du jeu
     * @return score de la position à évaluer
     */
    @Override
    public double evaluate(ReversiGame game) {
        int MaxScore = game.getCurrentPlayer().getScore();
        int MinScore = game.getOpponentPlayer().getScore();
        return 100 * (MaxScore - MinScore) / (MaxScore + MinScore);
    }

    @Override
    public AbstractHeuristic copy() {
        return new MaximiseScoreHeuristic();
    }
}
