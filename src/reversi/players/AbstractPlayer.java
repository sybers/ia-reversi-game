package reversi.players;

import reversi.MovePosition;
import reversi.Piece;
import reversi.ReversiGame;

public abstract class AbstractPlayer {
    protected int mScore = 0;
    protected Piece.Color mColor;

    /**
     * Créer un joueur avec un score initialisé à 0
     */
    public AbstractPlayer(Piece.Color c) {
        mColor = c;
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
     * Renvoie la couleur du joueur
     * @return color couleur courante
     */
    public Piece.Color getColor() {
        return mColor;
    }

    /**
     * Méthode appelée lorsque le joueur doit jouer son tour
     * @param game Instance du jeu sur lequel le joueur va jouer
     */
    public abstract MovePosition playTurn(ReversiGame game);

    /**
     * Renvoie une nouvelle instance, copie du joueur
     * @return nouvelle instance
     */
    public abstract AbstractPlayer copy();
}
