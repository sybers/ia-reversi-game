package reversi;

import reversi.players.AbstractPlayer;

import java.util.ArrayList;
import java.util.List;

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

	private AbstractPlayer mWhitePlayer;
	private AbstractPlayer mBlackPlayer;

	private boolean mIsBlackTurn = true; // par défaut : joueur 1 -> blanc, joueur 2 -> noir

	// plateau de jeu
	private Board mBoard;
	
	/**
	 * Constructeur
	 */
	public ReversiGame(AbstractPlayer whitePlayer, AbstractPlayer blackPlayer) {
		if(whitePlayer == null || blackPlayer == null)
			throw new IllegalArgumentException("player1 and player 2 must not be null values");

		mWhitePlayer = whitePlayer;
		mBlackPlayer = blackPlayer;

		mWhitePlayer.setColor(Piece.Color.White);
		mBlackPlayer.setColor(Piece.Color.Black);


		mBoard = new Board(ROWS, COLUMNS);
		initializeBoard();
	}

	/**
	 * Copy constructor
	 * @param other
	 */
	public ReversiGame(ReversiGame other) {
		if(other == null)
			throw new IllegalArgumentException("Cannot create copy object from null value");

		mWhitePlayer = other.mWhitePlayer.copy();
		mBlackPlayer = other.mBlackPlayer.copy();

		mIsBlackTurn = other.mIsBlackTurn;

		mBoard = new Board(other.mBoard);
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

		// score initial des deux joueurs
		mWhitePlayer.setScore(2);
		mBlackPlayer.setScore(2);
	}

	/**
	 * Commence la boucle du jeu
	 */
	public void play() {
		// pour chaque tour
		boolean hasValidWhiteMoves = true;
		boolean hasValidBlackMoves = true;

		while(hasValidBlackMoves || hasValidWhiteMoves) {
			mIsBlackTurn = !mIsBlackTurn;

			Piece.Color currentColor = (mIsBlackTurn ? Piece.Color.Black : Piece.Color.White );

			List<MovePosition> possibleMoves = getPossibleMoves(currentColor);

			// si un joueur n'a pas de mouvement possible, on passe son tour
			if(possibleMoves.isEmpty()) {
				if(mIsBlackTurn)
					hasValidBlackMoves = false;
				else
					hasValidWhiteMoves = false;

				System.out.println("Joueur " + currentColor + " passe son tour. pas de coups possibles pour lui");
			}

			// sinon on le fait jouer
			else {
				MovePosition attemptedMove;

				// tant que la position donnée n'eest pas un coup possible
				do {
					attemptedMove = (mIsBlackTurn ? mBlackPlayer : mWhitePlayer).playTurn(this, possibleMoves);
				} while(!isPossibleMove(attemptedMove.getRow(), attemptedMove.getColumn(), currentColor));

				performMove(attemptedMove.getRow(), attemptedMove.getColumn(), currentColor);
			}
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
	 * Renvoie le joueur de la couleur demandée
	 * @param color couleur du joueur
	 * @return instance du joueur
	 */
	public AbstractPlayer getPlayerByColor(Piece.Color color) {
		if(color == Piece.Color.Black)
			return mBlackPlayer;
		else
			return mWhitePlayer;
	}

	/**
	 * Renvoie l'instance du joueur blanc
	 * @return joueur blanc
	 */
	public AbstractPlayer getWhitePlayer() {
		return mWhitePlayer;
	}

	/**
	 * Renvoie l'instance du joueur noir
	 * @return joueur noir
	 */
	public AbstractPlayer getBlackPlayer() {
		return mBlackPlayer;
	}

	/**
	 * Vérifie que le plateau contient des mouvements possibles pour la couleur donnée
	 * @param c couleur à vérifier
	 * @return vrai si des mouvements sont possibles, faux sinon
	 */
	public List<MovePosition> getPossibleMoves(Piece.Color c) {
		List<MovePosition> possibleMoves = new ArrayList<>();

		for(int i = 0; i < mBoard.getRows(); i++) {
			for(int j = 0; j < mBoard.getColumns(); j++) {
				if(isPossibleMove(i, j, c))
					possibleMoves.add(new MovePosition(i, j));
			}
		}
		return possibleMoves;
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
	public boolean performMove(int row, int column, Piece.Color color) {
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
				// si on a une pièce de sa couleur avec une pièce de la couleur adverse entre, on retourne les pièces entre la position de départ et cette pièce
				else if(hasOpponentPieceBetween) {
					int startRow = row + mOffsetsRows[i];
					int startColumn = column + mOffsetsColumns[i];
					
					// on ajoute la pièce à la position voulue
					mBoard.addPiece(row, column, color);

					// on ajoute un point au joueur ayant posé sa pièce
					if(color == Piece.Color.Black)
						mBlackPlayer.incrementScore();
					else
						mWhitePlayer.incrementScore();
					
					while(startRow != currentRow || startColumn != currentColumn) {
						// mark the move as valid
						isValid = true;

						//on retourne la pièce concernée
						Piece flippedPiece = mBoard.getPiece(startRow, startColumn);

						// on retourne la pièce
						flippedPiece.flip();

						// on ajoute autant de points que de pièce que doit retourner la pièce posée
						// et on enlève les points à l'autre joueur
						if(flippedPiece.getColor() == Piece.Color.White) {
							mBlackPlayer.decrementScore();
							mWhitePlayer.incrementScore();
						} else {
							mBlackPlayer.incrementScore();
							mWhitePlayer.decrementScore();
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
		int whiteScore = mWhitePlayer.getScore();
		int blackScore = mBlackPlayer.getScore();

		System.out.println(toString());

		if(whiteScore > blackScore) {
			System.out.println("Le joueur Blanc remporte la partie !");
		}
		else if(whiteScore < blackScore) {
			System.out.println("Le joueur Noir remporte la partie !");
		}
		else {
			System.out.println("les deux joueurs finnissent à égalité.");
		}
	}
	
	/**
	 * Affiche le plateau et l'état courant du jeu
	 */
	@Override
	public String toString() {
		String ret = "";
		ret += "Tour du joueur " + (mIsBlackTurn ? "Noir" : "Blanc") + "\n";
		ret += "Blanc ->" + mWhitePlayer.getScore() + " | Noir -> " + mBlackPlayer.getScore() + "\n";
		ret += mBoard.toString();
		return ret;
	}
}
