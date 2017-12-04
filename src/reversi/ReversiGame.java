package reversi;

import reversi.players.AbstractPlayer;

import java.util.ArrayList;
import java.util.List;

/**
 * Game
 * Classe gérant la logique du jeu
 */
public final class ReversiGame {
    // offsets pour les directions
    private final static int[] mOffsetsRows = 	{-1, -1, -1, 0, 1, 1,  1,  0};
    private final static int[] mOffsetsColumns = {-1,  0,  1, 1, 1, 0, -1, -1};

	private AbstractPlayer mWhitePlayer;
	private AbstractPlayer mBlackPlayer;

	private List<MovePosition> mNextPossibleMoves;

	// par défaut le joueur blanc commence
	private boolean mIsBlackTurn = false;
	private boolean mIsGameOver = false;

	// plateau de jeu
	private Board mBoard;

	/**
	 * Constructeur
	 * @param player1 instance du joueur 1
	 * @param player2 instance du joueur 2
	 */
	public ReversiGame(AbstractPlayer player1, AbstractPlayer player2) {
		this(player1, player2, 8, 8);
	}

	/**
	 * Construteur
	 * @param player1 instance du joueur 1
	 * @param player2 instance du joueur 2
	 * @param rows nombre de lignes
	 * @param columns nombre de colonnes
	 */
	public ReversiGame(AbstractPlayer player1, AbstractPlayer player2, int rows, int columns) {
		if(player1 == null || player2 == null)
			throw new IllegalArgumentException("player1 and player 2 must not be null values");

		if(player1.getColor() == player2.getColor())
			throw new IllegalArgumentException("player1 and player 2 cannot have the same colour");

		if(rows <= 0 || columns <= 0)
            throw new IllegalArgumentException("rows and columns cannot be negative or zero");

		if(player1.getColor() == Piece.Color.White) {
			mWhitePlayer = player1;
			mBlackPlayer = player2;
		} else {
			mWhitePlayer = player2;
			mBlackPlayer = player1;
		}

		mNextPossibleMoves = new ArrayList<>();

		mBoard = new Board(rows, columns);
	}

	/**
	 * Initialize le plateau avec les pièces de départ
	 * et attribue les scores correspondants aux deux joueurs
	 */
	public void init() {
		int middleRow = (int) Math.ceil(mBoard.getRows()/2);
		int middleColumn = (int) Math.ceil(mBoard.getColumns()/2);

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
	 * Permet de savoir si la partie est terminée.
	 * Une partie est terminée si aucun des deux joueurs n'a de mouvements possibles
	 * @return Vrai si la partie est terminée, faux sinon
	 */
	public boolean isGameOver() {
		return mIsGameOver;
	}

    /**
     * Renvoie le nombre de lignes du plateau de jeu
     * @return nombre de lignes
     */
	public int getRows() {
	    return mBoard.getRows();
    }

    /**
     * Renvoie le nombre de colonnes du plateau de jeu
     * @return nombre de colonnes
     */
    public int getColumns() {
	    return mBoard.getColumns();
    }

	/**
	 * Renvoie la couleur des pièces du joueur dont c'est le tour
	 * @return
	 */
	public AbstractPlayer getCurrentPlayer() {
		return mIsBlackTurn ? mBlackPlayer : mWhitePlayer;
	}

	/**
	 * Renvoie l'instance du joueur adverse au tour en cours
	 * @return Joueur adverse
	 */
	public AbstractPlayer getOpponentPlayer() {
		return mIsBlackTurn ? mWhitePlayer : mBlackPlayer;
	}

	/**
	 * Renvoie les coups possibles pour le donné
	 * @return liste des mouvements possibles
	 */
	public List<MovePosition> getPossibleMoves(AbstractPlayer player) {
		List<MovePosition> possibleMoves = new ArrayList<>();
		Piece.Color c = player.getColor();

		for(int i = 0; i < mBoard.getRows(); i++) {
			for(int j = 0; j < mBoard.getColumns(); j++) {
				if(isPossibleMove(i, j, c))
					possibleMoves.add(new MovePosition(i, j));
			}
		}

		return possibleMoves;
	}

    /**
     * Joue le coup suivant pour le joueur dont c'est le tour
     */
	public void play() {
	    play(null);
    }

    /**
     * Joue le coup suivant pour le joueur dont c'est le tour avec le coup donné en paramètre
     */
	public void play(MovePosition desiredMove) {

		// calcule les prochains coups possibles
		List<MovePosition> nextAvailableMoves = getPossibleMoves(getCurrentPlayer());

		if(nextAvailableMoves.isEmpty() && mNextPossibleMoves.isEmpty()) {
			mIsGameOver = true;
		}
		else {
			mNextPossibleMoves = nextAvailableMoves;

			if(!mNextPossibleMoves.isEmpty()) {
                MovePosition attemptedMove;

                if(desiredMove != null) {
                    attemptedMove = desiredMove;
                } else {
                    // tant que la position donnée n'eest pas un coup possible
                    do {
                        attemptedMove = getCurrentPlayer().playTurn(this);
                    } while(!isPossibleMove(attemptedMove, getCurrentPlayer().getColor()));
                }

                performMove(attemptedMove);
                updatePoints();
            } else {
                // System.out.println("Pas de coups pour le joueur " + getCurrentPlayer().getColor());
				// TODO : informer que le joueur n'a pas de coups possibles
            }

			switchPlayers();
		}
	}

	/**
	 * Retourne la pièce à la position demandée
	 * @param row ligne
	 * @param col colonne
	 * @return Instance de la pièce
	 */
	public Piece getPieceAt(int row, int col) {
		return mBoard.getPiece(row, col);
	}

	/**
	 * Effectue un mouvement à la position donnée sur le plateau
	 * Change le tour du joueur courant si le mouvement était valide
	 * @param position Position du mouvement
	 * @return vrai si le mouvement a été effectué, faux sinon
	 */
	private void performMove(MovePosition position) {
		boolean isValid = false;

		// si il y a déjà une pièce sur cette case, le mouvement est impossible
		if(mBoard.getPiece(position.getRow(), position.getColumn()) != null)
			return;

		// pour les 8 directions autour de la pièce
		for(int i = 0; i < 8; i++) {
			int currentRow = position.getRow() + mOffsetsRows[i];
			int currentColumn = position.getColumn() + mOffsetsColumns[i];

			// la pièce a t-elle une pièce opposée dans la direction courante
			boolean hasOpponentPieceBetween = false;

			// tant qu'il reste des pièces dans la direction explorée
			while(currentRow >= 0 && currentRow < mBoard.getRows() && currentColumn >= 0 && currentColumn < mBoard.getColumns()) {
				// si la prochaine case dans la direction est vide, on sort de la boucle

				Piece currentPiece = mBoard.getPiece(currentRow, currentColumn);

				if(currentPiece == null)
					break;

				// si on croise une pièce de la couleur adverse, on mets à jour le flag
				if(currentPiece.getColor() != getCurrentPlayer().getColor()) {
					hasOpponentPieceBetween = true;
				}
				// si on a une pièce de sa couleur avec une pièce de la couleur adverse entre, on retourne les pièces entre la position de départ et cette pièce
				else if(hasOpponentPieceBetween) {
					isValid = true;

					int startRow = position.getRow() + mOffsetsRows[i];
					int startColumn = position.getColumn() + mOffsetsColumns[i];

					while(startRow != currentRow || startColumn != currentColumn) {
						//on retourne la pièce concernée
						Piece flippedPiece = mBoard.getPiece(startRow, startColumn);

						// on retourne la pièce
						flippedPiece.flip();

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

		if(isValid)
			mBoard.addPiece(position.getRow(), position.getColumn(), getCurrentPlayer().getColor());
	}

	/**
	 * Vérifie si le mouvement donné est possible sur le plateau de jeu
	 * @param position Position du mouvement à tester
	 * @param color Couleur de la pièce à poser
	 * @return Vrai si le coup est valide, faux sinon
	 */
	private boolean isPossibleMove(MovePosition position, Piece.Color color) {
		if(position == null)
			return false;

		return isPossibleMove(position.getRow(), position.getColumn(), color);
	}

	/**
	 * Vérifie si un mouvement est possible
	 * @param row position de la ligne
	 * @param column position de la colonne
	 * @param color couleur de la pièce à poser
	 * @return boolean vrai si le mouvement est possible, faux sinon
	 */
	private boolean isPossibleMove(int row, int column, Piece.Color color) {

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
			while(currentRow >= 0 && currentRow < mBoard.getRows() && currentColumn >= 0 && currentColumn < mBoard.getColumns()) {
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
	 * Mets à jour les points des joueurs (appelée après un mouvement)
	 */
	private void updatePoints() {
		int blackScore = 0;
		int whitescore = 0;

		for(int i = 0; i < mBoard.getRows(); i++) {
			for(int j = 0; j < mBoard.getColumns(); j++) {
				Piece p = mBoard.getPiece(i, j);
				if(p != null) {
					if(p.getColor() == Piece.Color.Black)
						blackScore++;
					else
						whitescore++;
				}
			}
		}

		// mets à jour les scores
		mWhitePlayer.setScore(whitescore);
		mBlackPlayer.setScore(blackScore);
	}

	/**
	 * Passe au tour du joueur suivant
	 */
	private void switchPlayers() {
		mIsBlackTurn = !mIsBlackTurn;
	}

	public String gameStateToCSV() {
	    String winnerPlayer;
	    if(mWhitePlayer.getScore() > mBlackPlayer.getScore())
	        winnerPlayer = "white";
	    else if(mWhitePlayer.getScore() < mBlackPlayer.getScore())
	        winnerPlayer = "black";
	    else
	        winnerPlayer = "draw";

	    return winnerPlayer + ";" + mWhitePlayer.getScore() + ";" + mBlackPlayer.getScore();
    }

	/**
	 * Copie de l'objet
	 * @return une copie de l'objet
	 */
	public ReversiGame copy() {
		ReversiGame other = new ReversiGame(mWhitePlayer.copy(), mBlackPlayer.copy());
		other.mIsBlackTurn = mIsBlackTurn;
		other.mIsGameOver = mIsGameOver;
		other.mNextPossibleMoves = new ArrayList<>(mNextPossibleMoves);
		other.mBoard = mBoard.copy();

		return other;
	}

	/**
	 * Affiche le plateau et l'état courant du jeu
	 */
	@Override
	public String toString() {
		return "   Tour du joueur " + (mIsBlackTurn ? "Noir" : "Blanc") + "\n" +
				"Blanc -> " + mWhitePlayer.getScore() + " | Noir -> " + mBlackPlayer.getScore() + "\n" +
				mBoard.toString();
	}
}
