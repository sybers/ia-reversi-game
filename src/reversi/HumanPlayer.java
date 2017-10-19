package reversi;

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

        System.out.println("Entrez la colonne :");
        column = mScanner.nextInt();

        System.out.println("Entrez la ligne :");
        row = mScanner.nextInt();

        return new MovePosition(row, column);
    }
}
