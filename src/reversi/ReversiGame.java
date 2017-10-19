package reversi;

import java.util.Scanner;

/**
 * Game
 * Classe gérant la logique du jeu
 */
public final class ReversiGame {
	// taille du plateau
	private final static int ROWS = 8;
	private final static int COLUMNS = 8;
	
	// offsets pour les directions
	private final static int[] mOffsetsRows = 	{-1, -1, -1, 0, 1, 1,  1,  0};
	private final static int[] mOffsetsColumns = {-1,  0,  1, 1, 1, 0, -1, -1};
	
	private Board mBoard;
	private Scanner mScanner = new Scanner(System.in);
	
	/**
	 * Constructeur
	 */
	public ReversiGame() {
		mBoard = new Board(ROWS, COLUMNS);
		initializeBoard();
	}
	
	/**
	 * Initialize le plateau avec les pièces de départ
	 */
	public void initializeBoard() {
		int middleRow = (int) Math.ceil(ROWS/2);
		int middleColumn = (int) Math.ceil(COLUMNS/2);
		
		this.mBoard.addPiece(middleRow - 1, middleColumn - 1, Piece.Color.White);
		this.mBoard.addPiece(middleRow - 1, middleColumn    , Piece.Color.Black);
		this.mBoard.addPiece(middleRow    , middleColumn - 1, Piece.Color.Black);
		this.mBoard.addPiece(middleRow    , middleColumn	   , Piece.Color.White);
	}
	
	/**
	 * Jouer un coup est l'action de poser une pièce sur le plateau
	 * c'est une action qui est déléguée au joueur courant (humain ou IA) 
	 * TODO : clean cette fonction et ajouter la délégation aux joueurs (pour l'instant on peut jouer plusieurs fois de suite avec le même joueur)
	 */
	public void setPiece(boolean blackTurn) {
		int row = -1;
		int column = -1;
		boolean possible = false;
		
		while(!possible) {
			System.out.println("Joueur " + (blackTurn ? "Noir" : "Blanc") + " :");
			
			System.out.println("Entrez la colonne :");
			column = mScanner.nextInt();
			
			System.out.println("Entrez la ligne :");
			row = mScanner.nextInt();
			
			possible = isPossibleMove(row, column, blackTurn ? Piece.Color.Black : Piece.Color.White);
			
			if(!possible)
				System.out.println("Mouvement impossible, recommencez...");
		}
		
		System.out.println("J'ajoute la pièce !");
		performMove(row, column, blackTurn ? Piece.Color.Black : Piece.Color.White);
	}
	
	/**
	 * Renvoie le plateau de jeu
	 * @return
	 */
	public Board getBoard() {
		return mBoard;
	}
	
	/**
	 * Vérifie si un mouvement est possible
	 * @param row numéro de ligne
	 * @param column numéro de colonne
	 * @param color couleur de la pièce à poser
	 * @return boolean vrai si le mouvement est possible, faux sinon
	 */
	private boolean isPossibleMove(int row, int column, Piece.Color color) {
		// si pas de pièces autour de moi ou pas de pièces de la couleur opposée -> pas possible
		// pour chaque piece de la couleur opposée autour, on regarde si une pièce de ma couleur se trouve derrière
		// si on tombe sur une pièce de la même couleur, on continue une case derrière dans la direction de la recherche
		// si on trouve une case vide ou si on arrive au bord du plateau -> la direction est invalide
		
		// si aucune direction ne fonctionne alors le coup est impossible
		
		boolean isValid = false;
		
		
		// pour les 8 directions autour de la pièce
		for(int i=0; i<8; i++) {
			int currentRow = row + mOffsetsRows[i];
			int currentColumn = column + mOffsetsColumns[i];
			
			// la pièce a t-elle une pièce opposée dans la direction courante
			boolean hasOpponentPieceBetween = false;
			
			// tant qu'il reste des pièces dans la direction explorée
			while(currentRow >= 0 && currentRow < ROWS && currentColumn >= 0 && currentColumn < COLUMNS) {
				// si la prochaine case dans la direction est vide, on sort de la boucle
				
				Piece currentPiece = mBoard.getPiece(currentRow, currentColumn);
				
				if(currentPiece == null)
					break;
				
				// si on croise une pièce de la couleur adverse, on mets à jour le flag
				if(currentPiece.getColor() != color) {
					hasOpponentPieceBetween = true;
				}
				// si on a une pièce de sa couleur avec une pièce de la couleur adverse entre, on considère que ce mouvement est valide !
				else if(hasOpponentPieceBetween) {
					isValid = true;
					break;
				}
				else
					break;
				
				// on met à jour la position de la case à vérifier (case suivante dans la direction explorée)
				currentRow += mOffsetsRows[i];
				currentColumn += mOffsetsColumns[i];
			}
			
			if(isValid)
				break;
			
		}
		
		return isValid;
	}
	
	private boolean performMove(int row, int column, Piece.Color color) {
		// si pas de pièces autour de moi ou pas de pièces de la couleur opposée -> direction pas possible
		// pour chaque piece de la couleur opposée autour, on regarde si une pièce de ma couleur se trouve derrière
		// si on tombe sur une pièce de la même couleur, on continue une case derrière dans la direction de la recherche
		// si on trouve une case vide ou si on arrive au bord du plateau -> la direction est invalide
		
		// si aucune direction ne fonctionne alors le coup est impossible
		
		boolean isValid = false;
		
		
		// pour les 8 directions autour de la pièce
		for(int i=0; i<8; i++) {
			int currentRow = row + mOffsetsRows[i];
			int currentColumn = column + mOffsetsColumns[i];
			
			// la pièce a t-elle une pièce opposée dans la direction courante
			boolean hasOpponentPieceBetween = false;
			
			// tant qu'il reste des pièces dans la direction explorée
			while(currentRow >= 0 && currentRow < ROWS && currentColumn >= 0 && currentColumn < COLUMNS) {
				// si la prochaine case dans la direction est vide, on sort de la boucle
				
				Piece currentPiece = mBoard.getPiece(currentRow, currentColumn);
				
				if(currentPiece == null)
					break;
				
				// si on croise une pièce de la couleur adverse, on mets à jour le flag
				if(currentPiece.getColor() != color) {
					hasOpponentPieceBetween = true;
				}
				// si on a une pièce de sa couleur avec une pièce de la couleur adverse entre, on retourne les pièces entre la position de départ et cette pièce
				else if(hasOpponentPieceBetween) {
					int startRow = row + mOffsetsRows[i];
					int startColumn = column + mOffsetsColumns[i];
					
					// on ajoute la pièce à la position voulue
					mBoard.addPiece(row, column, color);
					
					while(startRow != currentRow || startColumn != currentColumn) {
						//on retourne la pièce concernée
						mBoard.getPiece(startRow, startColumn).flip();
						
						// on passe à la pièce suivante
						startRow += mOffsetsRows[i];
						startColumn += mOffsetsColumns[i];
					}
					break;
				}
				
				// on met à jour la position de la case à vérifier (case suivante dans la direction explorée)
				currentRow += mOffsetsRows[i];
				currentColumn += mOffsetsColumns[i];
			}
			
			if(isValid)
				break;
			
		}
		
		return isValid;
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
