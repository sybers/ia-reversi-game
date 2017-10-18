package reversi;

/**
 * Piece
 * Représente une pièce sur le plateau
 */
public class Piece {
	public enum Color { White, Black }
	
	private Color mColor;
	
	/**
	 * Constructeur
	 * @param c couleur initiale
	 */
	public Piece(Color c) {
		mColor = c;
	}
	
	/**
	 * Renvoie la couleur courante de le pièce
	 * @return
	 */
	public Color getColor() {
		return mColor;
	}
	
	/**
	 * Inverse la couleur de la pièce
	 */
	public void flip() {
		mColor = (mColor == Color.White) ? Color.Black : Color.White;
	}
}
