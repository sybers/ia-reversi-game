package reversi.players;

import reversi.MovePosition;
import reversi.PieceColor;
import reversi.ReversiGame;

public interface PlayerInterface {

    /**
     * Change la valeur du score pour le joueur
     * @param score nouvelle valeur de score
     */
    void setScore(int score);

    /**
     * Renvoie le score courant du joueur
     * @return valeur du score
     */
    int getScore();

    /**
     * Renvoie la couleur du joueur
     * @return color couleur courante
     */
    PieceColor getColor();

    /**
     * Méthode appelée lorsque le joueur doit jouer son tour
     * @param game Instance du jeu sur lequel le joueur va jouer
     */
    MovePosition playTurn(ReversiGame game);

    /**
     * Renvoie une nouvelle instance, copie du joueur
     * @return nouvelle instance
     */
    PlayerInterface copy();
}
