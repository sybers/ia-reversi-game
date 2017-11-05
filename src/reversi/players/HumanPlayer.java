package reversi.players;

import reversi.MovePosition;
import reversi.Piece;
import reversi.ReversiGame;

import java.util.List;
import java.util.Scanner;

/**
 * HumanPlayer
 * Cette classe permet de définir les actions d'un joueur humain
 */
public class HumanPlayer extends AbstractPlayer {
    private Scanner mScanner = new Scanner(System.in);

    /**
     * Cette méthode est appelée lorsque le joueur doit jouer son tour
     * Le joueur humain sélectionne arbitrairement la position qu'il souhite jouer
     */
    @Override
    public MovePosition playTurn(ReversiGame game, List<MovePosition> possibleMoves) {
        // on affiche le plateau de jeu dans la console
        System.out.println(game.toString());

        // on récupère les valeurs que le joueur choisit
        int column = getValueFromInput("Entrez la colonne :");
        int row = getValueFromInput("Entrez la ligne :");

        return new MovePosition(row, column);
    }

    /**
     * Recopie de l'objet
     * @return copie de l'objet
     */
    @Override
    public AbstractPlayer copy() {
        HumanPlayer copy = new HumanPlayer();
        copy.setScore(mScore);
        copy.setColor(mColor);
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
