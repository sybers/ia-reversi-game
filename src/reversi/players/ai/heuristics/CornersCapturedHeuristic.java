package reversi.players.ai.heuristics;

import reversi.Piece;
import reversi.ReversiGame;

import java.util.ArrayList;
import java.util.List;

public class CornersCapturedHeuristic extends AbstractHeuristic {

    @Override
    public double evaluate(ReversiGame game) {

        List<Piece> cornerPieces = new ArrayList<>();

        // coin nord-ouest
        cornerPieces.add(game.getPieceAt(0, 0));

        // coin nord-est
        cornerPieces.add(game.getPieceAt(0, game.getColumns()));

        // coin sud-est
        cornerPieces.add(game.getPieceAt(game.getRows(), game.getColumns()));

        // coin sud-ouest
        cornerPieces.add(game.getPieceAt(game.getRows(), 0));

        Piece.Color maxPlayerColor = game.getCurrentPlayer().getColor();
        int maxCorners, minCorners;
        maxCorners = minCorners = 0;

        for(Piece p : cornerPieces) {
            if(p != null) {
                if(p.getColor() == maxPlayerColor)
                    maxCorners++;
                else
                    minCorners++;
            }
        }

        if(maxCorners + minCorners != 0)
            return 100 * (maxCorners - minCorners) / (maxCorners + minCorners);
        else
            return 0;
    }

    @Override
    public AbstractHeuristic copy() {
        return new CornersCapturedHeuristic();
    }
}
