package reversi;

/**
 * Piece
 * Représente une pièce sur le plateau
 */
public class Piece {

	private PieceColor mColor;

	/**
	 * Constructeur
	 * @param c couleur initiale
	 */
	public Piece(PieceColor c) {
		mColor = c;
	}

	/**
	 * Renvoie la couleur courante de le pièce
	 * @return
	 */
	public PieceColor getColor() {
		return mColor;
	}

	/**
	 * Inverse la couleur de la pièce
	 */
	public void flip() {
		mColor = (mColor == PieceColor.White) ? PieceColor.Black : PieceColor.White;
	}

	/**
	 * Affiche un charactère différent en fonction de la couleur de la pièce
	 */
	@Override
	public String toString() {
		return mColor == PieceColor.White ? "●" : "○";
	}
}
