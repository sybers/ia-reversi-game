package reversi;

/**
 * Game
 * Classe gérant la logique du jeu
 */
public final class Game {
	private int nb_players;
	private int ROWS = 8;
	private int COLUMNS = 8;
	private Board mBoard;
	
	/**
	 * Constructeur
	 */
	public Game() {
		mBoard = new Board(ROWS, COLUMNS);
		mBoard.initialize();
	}
	
	/**
	 * Retourne le plateau de jeu
	 * @return
	 */
	public Board getBoard() {
		return mBoard;
	}
	
	/**
	 * Affiche le plateau et l'état courant du jeu
	 */
	@Override
	public String toString() {
		// TODO : ajouter les informations relatives à la partie en cours
		return mBoard.toString();
	}
}
