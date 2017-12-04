package reversi.players;

import reversi.MovePosition;
import reversi.Piece;
import reversi.ReversiGame;
import java.util.Scanner;

/**
 * HumanPlayer
 * Cette classe permet de définir les actions d'un joueur humain
 */
public class ConsolePlayer implements PlayerInterface {
    private final Scanner mScanner = new Scanner(System.in);

    private int mScore;
    private Piece.Color mColor;

    /**
     * Créer un joueur avec un score initialisé à 0
     *
     * @param c Couleur du joueur
     */
    public ConsolePlayer(Piece.Color c) {
        mColor = c;
    }

    /**
     * Cette méthode est appelée lorsque le joueur doit jouer son tour
     * Le joueur humain sélectionne arbitrairement la position qu'il souhite jouer
     */
    @Override
    public MovePosition playTurn(ReversiGame game) {
        // on affiche le plateau de jeu dans la console
        System.out.println(game.toString());

        // on récupère les valeurs que le joueur choisit
        int column = getValueFromInput("Entrez la colonne :");
        int row = getValueFromInput("Entrez la ligne :");

        return new MovePosition(row, column);
    }

    @Override
    public void setScore(int score) {
        mScore = score;
    }

    @Override
    public int getScore() {
        return mScore;
    }

    @Override
    public Piece.Color getColor() {
        return mColor;
    }

    /**
     * Recopie de l'objet
     * @return copie de l'objet
     */
    @Override
    public PlayerInterface copy() {
        ConsolePlayer copy = new ConsolePlayer(mColor);
        copy.setScore(mScore);
        return copy;
    }

    /**
     * Permet de récupérer un entier depuis le périphérique d'entrée par défaut
     * @return int value
     */
    private int getValueFromInput(String message) {
        System.out.println(message);

        // On tente d'obtenir un entier
        while(!mScanner.hasNextInt()) {
            mScanner.next();
            System.out.println("Entrez un nombre valide...");
        }

        return mScanner.nextInt();
    }
}
