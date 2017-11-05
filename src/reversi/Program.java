package reversi;

import reversi.players.AbstractPlayer;
import reversi.players.AIPlayer;
import reversi.players.ia.heuristics.MaximiseScoreHeuristic;
import reversi.players.ia.heuristics.MobilityHeuristic;

public class Program {

	public static void main(String[] args) {
		AbstractPlayer whitePlayer = new AIPlayer(new MaximiseScoreHeuristic());
		AbstractPlayer blackPlayer = new AIPlayer(new MobilityHeuristic());

		ReversiGame g = new ReversiGame(whitePlayer, blackPlayer);
		
		g.play();
		
		System.out.println("Fin de la partie");
	}

}
