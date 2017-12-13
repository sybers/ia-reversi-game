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
	public boolean addPiece(int row, int column, PieceColor c) {

		if(validateCoordinates(row, column)) {
			mPlaces[row][column] = new Piece(c);
			return true;
		}

		return false;
	}

	/**
	 * Renvoie l'instance de pièce à la position demandée
	 * @param row numéro de ligne
	 * @param column numéro de colonne
	 * @return Pièce à la position spécifiée
	 */
	public Piece getPiece(int row, int column) {
		if(validateCoordinates(row, column)){
			return mPlaces[row][column];
		}

		return null;
	}

	/**
	 * Vérifie que les coordonnées données sont cohérentes
	 * @param row numéro de la ligne
	 * @param column numéro de la colonne
	 */
	private boolean validateCoordinates(int row, int column) {
		return row >= 0 &&
				column >= 0 &&
				row < mPlaces.length &&
				column < mPlaces[0].length;
	}

    /**
     * Constructeur par recopie
     * @return nouvelle instance
     */
    public Board copy() {
        Board other = new Board(mPlaces.length, mPlaces[0].length);

        // crée un tableau avec les pièces courantes
        for(int i = 0; i < mPlaces.length; i++) {
            for(int j = 0; j < mPlaces[0].length; j++) {
                if(mPlaces[i][j] == null) {
                    other.mPlaces[i][j] = null;
                } else {
                    other.mPlaces[i][j] = new Piece(mPlaces[i][j].getColor());
                }
            }
        }

        return other;
    }

	/**
	 * Gère l'affichage du plateau en mode console
	 */
	@Override
	public String toString() {
		StringBuilder ret = new StringBuilder("   ");
		// affiche la ligne avec les coordonnées des colonnes
		for(int i = 0; i < mPlaces.length; i++) {
			ret.append(i).append(" ");
		}

		ret.append("\n");

		// affiche le plateau de jeu et les numéros de ligne
		for(int i = 0; i < mPlaces.length; i++) {
			ret.append(i).append(" ");
			for(int j = 0; j < mPlaces[0].length; j++) {
				if(mPlaces[i][j] == null) {
					ret.append(" -");
				} else {
					ret.append(" ").append(mPlaces[i][j].toString());
				}
			}
			ret.append("\n");
		}

		return ret.toString();
	}
}
