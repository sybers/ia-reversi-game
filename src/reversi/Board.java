package reversi;

/**
 * Board
 * Classe gérant le plateau de jeu
 */
public class Board {
	private Piece[][] mPlaces;
	
	/**
	 * Constructeur
	 * @param rows nombre de lignes
	 * @param columns nombre de colonnes
	 */
	public Board(int rows, int columns) {
		if(rows < 0 || columns < 0)
			throw new IllegalArgumentException("Rows or collumns cannot be less or equal than 0");
		
		mPlaces = new Piece[rows][columns];
	}
	
	/**
	 * Retourne le nombre de lignes du plateau
	 * @return nombre de lignes
	 */
	public int getRows() {
		return mPlaces.length;
	}
	
	/**
	 * Retourne le nombre de colonnes du plateau
	 * @return nombre de colonnes
	 */
	public int getColumns() {
		return mPlaces[0].length;
	}
	
	/**
	 * Ajouter une pièce au plateau à la position [row, column]
	 */
	public boolean addPiece(int row, int column, Piece.Color c) {
		if(!checkCoordinatesInput(row, column)) {
			return false;
		} else {
			mPlaces[row][column] = new Piece(c);
			return true;
		}
	}
	
	/**
	 * Renvoie l'instance de pièce à la position demandée
	 * @param row numéro de ligne
	 * @param column numéro de colonne
	 * @return Pièce à la position spécifiée
	 */
	public Piece getPiece(int row, int column) {
		if(!checkCoordinatesInput(row, column)){
			return null;
		} else {
			return mPlaces[row][column];
		}
	}
	
	/**
	 * Vérifie que les coordonnées données sont cohérentes
	 * @param row numéro de la ligne
	 * @param column numéro de la colonne
	 */
	private boolean checkCoordinatesInput(int row, int column) {
		if(row < 0 || column < 0 || row >= mPlaces.length || column >= mPlaces[0].length)
			return false;
		else
			return true;
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
