package reversi;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Board
 * Classe gérant le plateau de jeu
 */
public class Board {
	private Piece[][] mPlaces;
	
	public Board(int rows, int columns) {
		if(rows <= 0 || columns <= 0)
			throw new IllegalArgumentException("Rows or collumns cannot be less or equal than 0");
		
		mPlaces = new Piece[rows][columns];
	}
	
	/**
	 * Initialize le plateau dans son état initial
	 */
	public void initialize() {
		this.addPiece(4, 4, Piece.Color.White);
		this.addPiece(4, 5, Piece.Color.Black);
		this.addPiece(5, 4, Piece.Color.Black);
		this.addPiece(5, 5, Piece.Color.White);
	}
	
	/**
	 * Ajouter une pièce au plateau à la position [row, column]
	 */
	public boolean addPiece(int row, int column, Piece.Color c) {
		// on vérifie que la position donnée existe
		if(row <= 0)
			throw new IllegalArgumentException("row cannot be negative");
		if(column <= 0)
			throw new IllegalArgumentException("column cannot be negative");
		
		if(row > mPlaces.length)
			throw new IllegalArgumentException("row cannot be higher than rows board size");
		
		if(row > mPlaces[0].length)
			throw new IllegalArgumentException("column cannot be higher than columns board size");
		
		// TODO : on vérifie si la pièce peut être posée à cette position
		mPlaces[row-1][column-1] = new Piece(c);
		return true;
	}
	
	/**
	 * Gère l'affichage du plateau en mode console
	 */
	@Override
	public String toString() {
		String ret = "";
		for(int i = 0; i < mPlaces.length; i++) {
			for(int j = 0; j < mPlaces[0].length; j++) {
				if(mPlaces[i][j] == null) {
					ret += " -";
				} else {
					ret += " " + (mPlaces[i][j].getColor() == Piece.Color.White ? "O" : "X");
				}
			}
			ret += "\n";
		}
		
		return ret;
	}
}
