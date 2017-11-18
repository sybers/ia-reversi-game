package reversi;

import reversi.players.AbstractPlayer;
import reversi.players.AIPlayer;
import reversi.players.HumanPlayer;
import reversi.players.ai.heuristics.MobilityHeuristic;

public class Program {

	public static void main(String[] args) {
		AbstractPlayer whitePlayer = new AIPlayer(new MobilityHeuristic(), 3);
		AbstractPlayer blackPlayer = new AIPlayer(new MobilityHeuristic(), 1);

		ReversiGame g = new ReversiGame(whitePlayer, blackPlayer);

		g.start();
	}
}
