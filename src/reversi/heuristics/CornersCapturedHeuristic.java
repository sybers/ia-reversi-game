package reversi.heuristics;

import reversi.Board;
import reversi.Piece;
import reversi.PieceColor;
import reversi.ReversiGame;

import java.util.ArrayList;
import java.util.List;

public class CornersCapturedHeuristic extends AbstractHeuristic {

    @Override
    public double evaluate(ReversiGame game) {

        List<Piece> cornerPieces = new ArrayList<>();
        Board board = game.getBoard();

        // coin nord-ouest
        cornerPieces.add(board.getPiece(0, 0));

        // coin nord-est
        cornerPieces.add(board.getPiece(0, game.getColumns()));

        // coin sud-est
        cornerPieces.add(board.getPiece(game.getRows(), game.getColumns()));

        // coin sud-ouest
        cornerPieces.add(board.getPiece(game.getRows(), 0));

        PieceColor maxPlayerColor = game.getCurrentPlayer().getColor();
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
