package reversi;

/**
 * Board
 * Classe gérant le plateau de jeu
 */
public class Board {
	private Piece[][] mPlaces;
	
	/**
	 * Constructeur
	 * @param rows
	 * @param columns
	 */
	public Board(int rows, int columns) {
		if(rows < 0 || columns < 0)
			throw new IllegalArgumentException("Rows or collumns cannot be less or equal than 0");
		
		mPlaces = new Piece[rows][columns];
	}
	
	/**
	 * Ajouter une pièce au plateau à la position [row, column]
	 */
	public boolean addPiece(int row, int column, Piece.Color c) {
		checkCoordinatesInput(row, column);
		
		mPlaces[row][column] = new Piece(c);
		return true;
	}
	
	/**
	 * Renvoie l'instance de pièce à la position demandée
	 * @param row
	 * @param column
	 * @return
	 */
	public Piece getPiece(int row, int column) {
		checkCoordinatesInput(row, column);
		
		return mPlaces[row][column];
	}
	
	/**
	 * Vérifie que les coordonnées données sont cohérentes
	 * @param row
	 * @param column
	 */
	private void checkCoordinatesInput(int row, int column) {
		if(row < 0)
			throw new IllegalArgumentException("row cannot be negative");
		if(column < 0)
			throw new IllegalArgumentException("column cannot be negative");
		
		if(row >= mPlaces.length)
			throw new IllegalArgumentException("row cannot be higher than rows board size");
		
		if(row >= mPlaces[0].length)
			throw new IllegalArgumentException("column cannot be higher than columns board size");
	}
	
	/**
	 * Gère l'affichage du plateau en mode console
	 */
	@Override
	public String toString() {
		String ret = "   ";
		// affiche la ligne avec les coordonnées des colonnes
		for(int i = 0; i < mPlaces.length; i++) {
			ret += i + " ";
		}
		
		ret +="\n";
		
		// affiche le plateau de jeu et les numéros de ligne
		for(int i = 0; i < mPlaces.length; i++) {
			ret += i + " ";
			for(int j = 0; j < mPlaces[0].length; j++) {
				if(mPlaces[i][j] == null) {
					ret += " -";
				} else {
					ret += " " + mPlaces[i][j].toString();
				}
			}
			ret += "\n";
		}
		
		return ret;
	}
}
