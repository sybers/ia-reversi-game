package reversi.gui;

import reversi.Board;
import reversi.MovePosition;
import reversi.Piece;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class BoardPanel extends JPanel {
    private int mWidth;
    private int mHeight;
    private Board mBoard = null;
    private BoardPanelListener mPanelListener = null;

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
                if(mPanelListener != null && mBoard != null)
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
     * @param board boad instance
     */
    public void setBoard(Board board) {
        mBoard = board;
    }

    public void setBoardListener(BoardPanelListener listener) {
        mPanelListener = listener;
    }

    /**
     * Renders a board
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        ((Graphics2D)g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        if(mBoard != null) {
            makeGrid(g);
            makePieces(g);
        } else {
            System.out.println("No board bound to BoardPanel...");
        }
    }

    /**
     * Draw the grid
     */
    private void makeGrid(Graphics g) {
        int rowSize = mHeight / mBoard.getRows();
        int colSize = mWidth / mBoard.getColumns();

        // draw lines separator
        for(int i = 1; i <= mBoard.getColumns(); i++) {
            g.drawLine(0, rowSize*i, mWidth, rowSize*i);
        }

        // draw columns separator
        for(int i = 1; i <= mBoard.getColumns(); i++) {
            g.drawLine(colSize*i, 0, colSize*i, mHeight);
        }
    }

    /**
     * Draw the pieces
     */
    private void makePieces(Graphics g) {
        int rowSize = mHeight / mBoard.getRows();
        int colSize = mWidth / mBoard.getColumns();

        int pieceRadius = rowSize / 2;

        // draw lines separator
        for(int row = 0; row < mBoard.getColumns(); row++) {
            for(int col = 0; col < mBoard.getRows(); col++) {
                int x = colSize * col + pieceRadius - pieceRadius/2;
                int y = rowSize * row + pieceRadius - pieceRadius/2;

                Piece p = mBoard.getPiece(row, col);
                if(p != null) {
                    if(p.getColor() == Piece.Color.White) {
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

    private MovePosition coordinatesToMovePosition(int x, int y) {
        int rowSize = mHeight / mBoard.getRows();
        int colSize = mWidth / mBoard.getColumns();

        return new MovePosition((int) Math.floor(y / colSize), (int) Math.floor(x / rowSize));
    }

    public interface BoardPanelListener {
        void boardClicked(MovePosition movePosition, MouseEvent e);

        void boardMouseEntered(MouseEvent e);

        void boardMouseExited(MouseEvent e);
    }
}
