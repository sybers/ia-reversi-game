package reversi.players;

import reversi.MovePosition;
import reversi.ReversiGame;

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
    public MovePosition playTurn(ReversiGame game) {
        int row;
        int column;

        System.out.println(game.toString());

        column = getValueFromInput("Entrez la colonne :");

        row = getValueFromInput("Entrez la ligne :");

        return new MovePosition(row, column);
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
