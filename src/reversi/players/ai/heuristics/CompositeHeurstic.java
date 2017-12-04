package reversi.players.ai.heuristics;

import reversi.ReversiGame;

import java.util.ArrayList;
import java.util.List;

/**
 * Une heuristique composite est une heuristique composée de plusieurs autres heuristiques,
 * chacune de ces heuristiques est associée à un coefficient qui représentera son influence lors du calcul du score.
 */
public class CompositeHeurstic extends AbstractHeuristic {
    private List<WeightedHeuristic> mHeuristics = new ArrayList<>();

    /**
     * Évalue les heuristiques ajoutées dans cette heuristique composite
     * @param game instance du jeu à évaluer
     * @return score du plateau
     */
    @Override
    public double evaluate(ReversiGame game) {
        if(mHeuristics.isEmpty())
            throw new IllegalArgumentException("Cannot evaluate game : no heuristics added to CompositeHeuristic");

        double score = 0;

        // On calcule chaque heuristique avec le poids associé
        for(WeightedHeuristic heur : mHeuristics) {
            score += (heur.getHeuristic().evaluate(game) * heur.getWeight());
        }

        return score;
    }

    /**
     * Ajoute une nouvelle heuristique à la liste d'heuristiques à évaluer
     * @param heuristic Instance de l'heuristique
     * @param weight Poids de l'heuristique
     * @return vrai si ajoutée, faux sinon
     */
    public boolean addHeuristic(AbstractHeuristic heuristic, double weight) {
        return mHeuristics.add(new WeightedHeuristic(heuristic, weight));
    }

    @Override
    public AbstractHeuristic copy() {
        CompositeHeurstic copied = new CompositeHeurstic();
        copied.mHeuristics = new ArrayList<>(mHeuristics);
        return copied;
    }

    private class WeightedHeuristic {
        private AbstractHeuristic mHeuristic;
        private double mWeight;

        public WeightedHeuristic(AbstractHeuristic heuristic, double weight) {
            if(heuristic == null)
                throw new IllegalArgumentException("Null parameter given to WeightedHeuristic");

            if(weight == 0)
                throw new IllegalArgumentException("Weight cannot be 0");

            mHeuristic = heuristic;
            mWeight = weight;
        }

        public double getWeight() {
            return mWeight;
        }

        public AbstractHeuristic getHeuristic() {
            return mHeuristic;
        }
    }
}
