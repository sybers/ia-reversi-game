package reversi;

/**
 * Game
 * Classe gérant la logique du jeu
 */
public final class ReversiGame {
	// taille du plateau
	private final static int ROWS = 4;
	private final static int COLUMNS = 4;
	
	// offsets pour les directions
	private final static int[] mOffsetsRows = 	{-1, -1, -1, 0, 1, 1,  1,  0};
	private final static int[] mOffsetsColumns = {-1,  0,  1, 1, 1, 0, -1, -1};

	private AbstractPlayer mPlayer1;
	private AbstractPlayer mPlayer2;

	private boolean mIsBlackTurn = false; // par défaut : joueur 1 -> blanc, joueur 2 -> noir

	// plateau de jeu
	private Board mBoard;
	
	/**
	 * Constructeur
	 */
	public ReversiGame(AbstractPlayer player1, AbstractPlayer player2) {
		if(player1 == null || player2 == null)
			throw new IllegalArgumentException("player1 and player 2 must not be null values");

		mPlayer1 = player1;
		mPlayer2 = player2;

		mBoard = new Board(ROWS, COLUMNS);
		initializeBoard();
	}
	
	/**
	 * Initialize le plateau avec les pièces de départ
	 * et attribue les scores correspondants aux deux joueurs
	 */
	public void initializeBoard() {
		int middleRow = (int) Math.ceil(ROWS/2);
		int middleColumn = (int) Math.ceil(COLUMNS/2);

		// pièces du joueur blanc
		this.mBoard.addPiece(middleRow - 1, middleColumn - 1, Piece.Color.White);
		this.mBoard.addPiece(middleRow    , middleColumn	   , Piece.Color.White);

		// pièces du joueur noir
		this.mBoard.addPiece(middleRow - 1, middleColumn    , Piece.Color.Black);
		this.mBoard.addPiece(middleRow    , middleColumn - 1, Piece.Color.Black);

		// score par défaut des deux joueurs
		mPlayer1.setScore(2);
		mPlayer2.setScore(2);
	}

	/**
	 * Commence la boucle du jeu
	 */
	public void startGame() {
		// pour chaque tour
		while(hasValidMoves((mIsBlackTurn ? Piece.Color.Black : Piece.Color.White ))) {
			MovePosition attemptedMove;

			// tant que la position donnée n'eest pas un coup possible
			do {
				System.out.println("Tour du joueur " + (mIsBlackTurn ? "Noir" : "Blanc"));
				System.out.println("Blanc ->" + mPlayer1.getScore() + " | Noir -> " + mPlayer2.getScore());
				attemptedMove = (mIsBlackTurn ? mPlayer2 : mPlayer1).playTurn(this);
			} while(!isPossibleMove(attemptedMove.getRow(), attemptedMove.getColumn(), (mIsBlackTurn ? Piece.Color.Black : Piece.Color.White )));

			performMove(attemptedMove.getRow(), attemptedMove.getColumn(), (mIsBlackTurn ? Piece.Color.Black : Piece.Color.White ));

			mIsBlackTurn = !mIsBlackTurn;

		}

		endGame();
	}
	
	/**
	 * Renvoie le plateau de jeu
	 * @return plateau de jeu
	 */
	public Board getBoard() {
		return mBoard;
	}
	
	/**
	 * Vérifie que le plateau contient des mouvements possibles pour la couleur donnée
	 * @param c couleur à vérifier
	 * @return vrai si des mouvements sont possibles, faux sinon
	 */
	public boolean hasValidMoves(Piece.Color c) {
		for(int i = 0; i < mBoard.getRows(); i++) {
			for(int j = 0; j < mBoard.getColumns(); j++) {
				if(isPossibleMove(i, j, c))
					return true;
			}
		}
		return false;
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

		// si il y a déjà une pièce sur cette case, le mouvement est impossible
		if(mBoard.getPiece(row, column) != null)
			return false;
		
		
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
	
	/**
	 * Effectue un mouvement à la position donnée sur le plateau
	 * @param row numéro de ligne
	 * @param column numéro de colonne
	 * @param color couleur de la pièce à poser
	 * @return vrai si le mouvement a été effectué, faux sinon
	 */
	private boolean performMove(int row, int column, Piece.Color color) {
		boolean isValid = false;

		// si il y a déjà une pièce sur cette case, le mouvement est impossible
		if(mBoard.getPiece(row, column) != null)
			return isValid;
		
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

					// on ajoute un point au joueur ayant posé sa pièce
					if(color == Piece.Color.Black)
						mPlayer2.incrementScore();
					else
						mPlayer1.incrementScore();
					
					while(startRow != currentRow || startColumn != currentColumn) {
						//on retourne la pièce concernée
						Piece flippedPiece = mBoard.getPiece(startRow, startColumn);

						// on retourne la pièce
						flippedPiece.flip();

						// on ajoute autant de points que de pièce que doit retourner la pièce posée
						// et on enlève les points à l'autre joueur
						if(flippedPiece.getColor() == Piece.Color.White) {
							mPlayer2.decrementScore();
							mPlayer1.incrementScore();
						} else {
							mPlayer2.incrementScore();
							mPlayer1.decrementScore();
						}
						
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
		}
		
		return isValid;
	}

	/**
	 * Méthode appelée lorsque la partie est terminée
	 * Pour récapituler les scores et afficher le gagnant
	 */
	private void endGame() {
		int p1Score = mPlayer1.getScore();
		int p2Score = mPlayer2.getScore();

		System.out.println("Blanc ->" + p1Score + " | Noir -> " + p2Score);
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
