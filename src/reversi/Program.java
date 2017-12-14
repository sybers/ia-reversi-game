package reversi;

import reversi.players.AIPlayer;
import reversi.players.PlayerInterface;
import reversi.heuristics.CompositeHeurstic;
import reversi.heuristics.CornersCapturedHeuristic;
import reversi.heuristics.MaximiseScoreHeuristic;
import reversi.heuristics.MobilityHeuristic;

public class Program {

	public static void main(String[] args) {
	    int gamesToPlay = 100;

	    for(int i = 0; i < gamesToPlay; i++) {
            //AbstractPlayer whitePlayer = new HumanPlayer(Piece.Color.White);
            //AbstractPlayer blackPlayer = new HumanPlayer(Piece.Color.Black);

            CompositeHeurstic compositeHeurstic = new CompositeHeurstic();
            compositeHeurstic.addHeuristic(new MaximiseScoreHeuristic(), 10);
            compositeHeurstic.addHeuristic(new MobilityHeuristic(), 78.922);
            compositeHeurstic.addHeuristic(new CornersCapturedHeuristic(), 801.724);

            PlayerInterface whitePlayer = new AIPlayer(PieceColor.White, new MobilityHeuristic(), 2);
            PlayerInterface blackPlayer = new AIPlayer(PieceColor.Black, compositeHeurstic, 2);

            ReversiGame g = new ReversiGame(whitePlayer, blackPlayer);

            g.init();

            // play until the game is over
            while(!g.isGameOver()) {
                g.play(g.getCurrentPlayer().playTurn(g));
            }

            System.out.println(g.toCSV());
        }
	}
}
