package reversi;

import reversi.players.AbstractPlayer;
import reversi.players.HumanPlayer;
import reversi.players.AIPlayer;
import reversi.players.ai.heuristics.MobilityHeuristic;

public class Program {

	public static void main(String[] args) {
		//AbstractPlayer whitePlayer = new HumanPlayer(Piece.Color.White);
		//AbstractPlayer blackPlayer = new HumanPlayer(Piece.Color.Black);

        AbstractPlayer whitePlayer = new AIPlayer(Piece.Color.White, new MobilityHeuristic(), 1);
        AbstractPlayer blackPlayer = new AIPlayer(Piece.Color.Black, new MobilityHeuristic(), 4);

		ReversiGame g = new ReversiGame(whitePlayer, blackPlayer);

		g.init();

		// play until the game is over
		while(!g.isGameOver()) {
			g.play();
		}

		g.resumeScores();
	}
}
