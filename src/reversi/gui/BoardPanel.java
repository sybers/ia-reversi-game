package reversi.gui;

import reversi.MovePosition;
import reversi.Piece;
import reversi.PieceColor;
import reversi.ReversiGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

public class BoardPanel extends JPanel {
    private int mWidth;
    private int mHeight;
    private ReversiGame mGame = null;
    private BoardPanelListener mPanelListener = null;
    private boolean enableAvailableMoves = false;

    /**
     * Constructor
     * @param width panel width
     * @param height panel height
     */
    public BoardPanel(int width, int height) {
        if(width <= 0 || height <= 0)
            throw new IllegalArgumentException("Height and Width must be positive integers.");

        mHeight = height;
        mWidth = width;

        setBorder(BorderFactory.createLineBorder(Color.BLACK));

        setBackground(new Color(60, 133, 26));
        setPreferredSize(new Dimension(mWidth, mHeight));

        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(mPanelListener != null && mGame != null)
                    mPanelListener.boardClicked(coordinatesToMovePosition(e.getX(), e.getY()), e);
            }

            @Override
            public void mousePressed(MouseEvent e) {}

            @Override
            public void mouseReleased(MouseEvent e) {}

            @Override
            public void mouseEntered(MouseEvent e) {
                if(mPanelListener != null)
                    mPanelListener.boardMouseEntered(e);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if(mPanelListener != null)
                    mPanelListener.boardMouseExited(e);

            }
        });
    }

    /**
     * Set the board to render
     * @param game instance
     */
    public void setBoard(ReversiGame game) {
        mGame = game;
    }

    public void setBoardListener(BoardPanelListener listener) {
        mPanelListener = listener;
    }

    /**
     * Enable or disable display of available moves for the next player
     * @param flag status
     */
    public void enableAvailableMoves(boolean flag) {
        enableAvailableMoves = flag;
    }

    /**
     * Renders a board
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        ((Graphics2D)g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        if(mGame != null) {
            makeGrid(g);
            makePieces(g);
            if(enableAvailableMoves)
                makeAvailableMoves(g);
        } else {
            System.out.println("No board bound to BoardPanel...");
        }
    }

    /**
     * Draw the grid
     */
    private void makeGrid(Graphics g) {
        int rows = mGame.getBoard().getRows();
        int cols = mGame.getBoard().getColumns();
        int rowSize = mHeight / mGame.getBoard().getRows();
        int colSize = mWidth / mGame.getBoard().getColumns();

        // draw lines separator
        for(int i = 1; i <= rows; i++) {
            g.drawLine(0, rowSize*i, mWidth, rowSize*i);
        }

        // draw columns separator
        for(int i = 1; i <= cols; i++) {
            g.drawLine(colSize*i, 0, colSize*i, mHeight);
        }
    }

    /**
     * Draw the pieces
     */
    private void makePieces(Graphics g) {
        int rows = mGame.getBoard().getRows();
        int cols = mGame.getBoard().getColumns();
        int rowSize = mHeight / mGame.getBoard().getRows();
        int colSize = mWidth / mGame.getBoard().getColumns();

        int pieceRadius = rowSize / 2;

        // draw lines separator
        for(int row = 0; row < cols; row++) {
            for(int col = 0; col < rows; col++) {
                int x = colSize * col + pieceRadius - pieceRadius/2;
                int y = rowSize * row + pieceRadius - pieceRadius/2;

                Piece p = mGame.getBoard().getPiece(row, col);
                if(p != null) {
                    if(p.getColor() == PieceColor.White) {
                        g.setColor(Color.WHITE);
                        g.fillOval(x, y, pieceRadius, pieceRadius);
                    }
                    else {
                        g.setColor(Color.BLACK);
                        g.fillOval(x, y, pieceRadius, pieceRadius);
                    }

                    g.setColor(Color.black);
                    g.drawArc(x, y, pieceRadius, pieceRadius, 0, 360);
                }
            }
        }
    }

    /**
     * Draws the available moves
     */
    private void makeAvailableMoves(Graphics g) {
        int rows = mGame.getBoard().getRows();
        int cols = mGame.getBoard().getColumns();
        int rowSize = mHeight / mGame.getBoard().getRows();
        int colSize = mWidth / mGame.getBoard().getColumns();

        int pieceRadius = rowSize / 2;

        List<MovePosition> availableMoves = mGame.getPossibleMoves(mGame.getCurrentPlayer());

        for(MovePosition move : availableMoves) {
            int x = colSize * move.getColumn() + pieceRadius - pieceRadius/8;
            int y = rowSize * move.getRow() + pieceRadius - pieceRadius/8;

            g.setColor(Color.RED);
            g.drawRect(x, y, pieceRadius/4, pieceRadius/4);
        }
    }

    /**
     * Converts mouse click position to grid coordinates
     * @param x mouse X position
     * @param y mouse Y position
     * @return instance of desired mouse position
     */
    private MovePosition coordinatesToMovePosition(int x, int y) {
        int rowSize = mHeight / mGame.getBoard().getRows();
        int colSize = mWidth / mGame.getBoard().getColumns();

        return new MovePosition((int) Math.floor(y / colSize), (int) Math.floor(x / rowSize));
    }

    public interface BoardPanelListener {
        void boardClicked(MovePosition movePosition, MouseEvent e);

        void boardMouseEntered(MouseEvent e);

        void boardMouseExited(MouseEvent e);
    }
}
