package reversi;

public abstract class AbstractPlayer {
    public int mScore;

    /**
     * Créer un joueur avec un score initialisé à 0
     */
    public AbstractPlayer() {
        this(0);
    }

    /**
     * Créer un joueur avec un score initial
     * @param score valeur du score
     */
    public AbstractPlayer(int score) {
        mScore = score;
    }

    /**
     * Change la valeur du score pour le joueur
     * @param score nouvelle valeur de score
     */
    public void setScore(int score) {
        mScore = score;
    }

    /**
     * Renvoie le score courant du joueur
     * @return valeur du score
     */
    public int getScore() {
        return mScore;
    }

    /**
     * Ajoute un point au score du joueur
     */
    public void incrementScore() {
        this.mScore += 1;
    }

    /**
     * Enlève un point au score du joueur
     */
    public void decrementScore() {
        this.mScore -= 1;
    }

    /**
     * Méthode appelée lorsque le joueur doit jouer son tour
     * @param game Instance du jeu sur lequel le joueur va jouer
     */
    public abstract MovePosition playTurn(ReversiGame game);
}
