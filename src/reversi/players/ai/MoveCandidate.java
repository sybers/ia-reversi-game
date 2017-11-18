package reversi.players.ai;

import reversi.MovePosition;

/**
 * Mouvement possible associé à son score heuristique
 */
public class MoveCandidate implements Comparable<MoveCandidate> {
    private MovePosition mPosition;
    private double mScore;

    /**
     * MoveCandidate Constructor.
     * @param position
     * @param score
     */
    public MoveCandidate(MovePosition position, double score) {
        if(position == null)
            throw new IllegalArgumentException("Position must not be null value");

        mPosition = position;
        mScore = score;
    }

    /**
     * Renvoie la position du mouvement
     * @return position
     */
    public MovePosition getPosition() {
        return mPosition;
    }

    /**
     * Renvoie le score associé au mouvement
     * @return score
     */
    public double getScore() {
        return mScore;
    }

    /**
     * Compare le mouvement
     * @param other autre mouvement
     * @return résultat de la comparaison
     */
    @Override
    public int compareTo(MoveCandidate other) {
        if(getScore() < other.getScore())
            return -1;
        else if(getScore() > other.getScore())
            return 1;

        return 0;
    }
}
