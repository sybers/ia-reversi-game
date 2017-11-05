package reversi.players;

import reversi.MovePosition;
import reversi.Piece;
import reversi.ReversiGame;

import java.util.List;

public abstract class AbstractPlayer {
    protected int mScore;
    protected Piece.Color mColor;

    /**
     * Créer un joueur avec un score initialisé à 0
     */
    public AbstractPlayer() {
        this(0);
    }

    /**
     * Constructeur par
     * @param other
     */
    public AbstractPlayer(AbstractPlayer other) {
        mScore = other.getScore();
        mColor = other.getColor();
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
     * Renvoie la couleur du joueur
     * @return color couleur courante
     */
    public Piece.Color getColor() {
        return mColor;
    }

    /**
     * Définit la couleur des pièces du joueur
     * @param mColor nouvelle couleur
     */
    public void setColor(Piece.Color mColor) {
        this.mColor = mColor;
    }

    /**
     * Méthode appelée lorsque le joueur doit jouer son tour
     * @param game Instance du jeu sur lequel le joueur va jouer
     */
    public abstract MovePosition playTurn(ReversiGame game, List<MovePosition> possibleMoves);

    /**
     * Renvoie une nouvelle instance, copie du joueur
     * @return nouvelle instance
     */
    public abstract AbstractPlayer copy();
}
