package reversi.gui;

import reversi.MovePosition;
import reversi.Piece;
import reversi.ReversiGame;
import reversi.players.AIPlayer;
import reversi.players.ConsolePlayer;
import reversi.players.PlayerInterface;
import reversi.players.ai.heuristics.MobilityHeuristic;

import javax.swing.*;
import java.awt.event.MouseEvent;

public class GameWindow {
    // UI components
    private BoardPanel mBoardPanel;
    private JPanel mPanel;
    private StatusBar mStatusBar;
    private JComboBox mPlayer1Type;
    private JComboBox mPlayer1Difficulty;
    private JComboBox mPlayer2Type;
    private JComboBox mPlayer2Difficulty;
    private JButton mNewGameButton;
    private JLabel mP1ScoreLabel;
    private JLabel mP2ScoreLabel;

    // Game variables
    private PlayerInterface mPlayer1;
    private PlayerInterface mPlayer2;
    private ReversiGame mGame;

    /**
     * Constructor.
     * Create game instances bind action listeners
     */
    public GameWindow() {
        mPlayer1Type.addActionListener(e -> {
            if(String.valueOf(mPlayer1Type.getSelectedItem()).equals("Human"))
                mPlayer1Difficulty.setEnabled(false);
            else
                mPlayer1Difficulty.setEnabled(true);
        });

        mPlayer2Type.addActionListener(e -> {
            if(String.valueOf(mPlayer2Type.getSelectedItem()).equals("Human"))
                mPlayer2Difficulty.setEnabled(false);
            else
                mPlayer2Difficulty.setEnabled(true);
        });

        mNewGameButton.addActionListener(e -> newGame());
    }

    /**
     * Application entry point
     * @param args
     */
    public static void main(String[] args) {
        JFrame frame = new JFrame("GameWindow");
        frame.setContentPane(new GameWindow().mPanel);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    /**
     * Initialize custom components
     */
    private void createUIComponents() {
        mBoardPanel = new BoardPanel(400, 400);
        mBoardPanel.setBoardListener(new BoardPanel.BoardPanelListener() {
            @Override
            public void boardClicked(MovePosition movePosition, MouseEvent e) {
                play(movePosition);
            }

            @Override
            public void boardMouseEntered(MouseEvent e) {

            }

            @Override
            public void boardMouseExited(MouseEvent e) {

            }
        });
        mStatusBar = new StatusBar();
    }

    /**
     * Play the next turn
     */
    private void play(MovePosition pos) {
        mStatusBar.setInfoMessage(mGame.getCurrentPlayer().getColor() + " Player's turn");

        mGame.play(pos);

        refreshUI();

        if(mGame.isGameOver()) {
            mStatusBar.setSuccessMessage("Game over !");
            refreshUI();
            return;
        }

        if(mGame.getCurrentPlayer() instanceof AIPlayer) {
            play(null);
        }
    }

    /**
     * Start a new Game
     */
    private void newGame() {
        System.out.println("Creating a new game");

        mPlayer1 = createPlayer(
            String.valueOf(mPlayer1Type.getSelectedItem()),
            String.valueOf(mPlayer1Difficulty.getSelectedItem()),
            Piece.Color.White
        );

        mPlayer2 = createPlayer(
            String.valueOf(mPlayer2Type.getSelectedItem()),
            String.valueOf(mPlayer2Difficulty.getSelectedItem()),
            Piece.Color.Black
        );

        mGame = new ReversiGame(mPlayer1, mPlayer2);

        mGame.init();

        mBoardPanel.setBoard(mGame.getBoard());

        refreshUI();
    }


    /**
     * Create a player according to its type and difficulty (if IA)
     * @param type "Human" or "Computer"
     * @param difficulty "Easy", "Medium" or "Hard"
     * @return New player instance
     */
    private PlayerInterface createPlayer(String type, String difficulty, Piece.Color c) {
        switch(type) {
            case "Human":
                return new ConsolePlayer(c);

            case "Computer":
                int depth;
                switch (difficulty) {
                    case "Easy":
                        depth = 1;
                        break;
                    case "Medium":
                        depth = 2;
                        break;
                    default:
                        depth = 4;
                        break;
                }
                return new AIPlayer(c, new MobilityHeuristic(), depth);

            default:
                throw new IllegalArgumentException("Invalid options given to createPlayer");
        }
    }

    /**
     * Refresh Interface after data changed
     */
    private void refreshUI() {
        mP1ScoreLabel.setText("" + mPlayer1.getScore());
        mP2ScoreLabel.setText("" + mPlayer2.getScore());
        mPanel.repaint();
    }
}
