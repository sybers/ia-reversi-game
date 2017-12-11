package reversi.gui;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import reversi.MovePosition;
import reversi.Piece;
import reversi.ReversiGame;
import reversi.players.AIPlayer;
import reversi.players.ConsolePlayer;
import reversi.players.PlayerInterface;
import reversi.players.ai.heuristics.CompositeHeurstic;
import reversi.players.ai.heuristics.MaximiseScoreHeuristic;
import reversi.players.ai.heuristics.MobilityHeuristic;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
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
        $$$setupUI$$$();
        mPlayer1Type.addActionListener(e -> {
            if (String.valueOf(mPlayer1Type.getSelectedItem()).equals("Human"))
                mPlayer1Difficulty.setEnabled(false);
            else
                mPlayer1Difficulty.setEnabled(true);
        });

        mPlayer2Type.addActionListener(e -> {
            if (String.valueOf(mPlayer2Type.getSelectedItem()).equals("Human"))
                mPlayer2Difficulty.setEnabled(false);
            else
                mPlayer2Difficulty.setEnabled(true);
        });

        mNewGameButton.addActionListener(e -> newGame());
    }

    /**
     * Application entry point
     *
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
        MovePosition position = pos;

        if (mGame.getCurrentPlayer() instanceof AIPlayer) {
            position = mGame.getCurrentPlayer().playTurn(mGame);
        }

        System.out.println(position);

        switch (mGame.play(position)) {
            case ReversiGame.PLAYER_PLAYED:
                mStatusBar.setInfoMessage("Player played");
                break;
            case ReversiGame.INVALID_MOVE:
                mStatusBar.setErrorMessage("Invalid move");
                break;
            case ReversiGame.SKIPPED_TURN:
                mStatusBar.setInfoMessage("Skipped player's turn");
                break;
            case ReversiGame.GAME_FINISHED:
                mStatusBar.setSuccessMessage("Game over !");
                break;
        }

        refreshUI();

        Timer t = new Timer(1500, e -> mStatusBar.setInfoMessage(mGame.getCurrentPlayer().getColor() + " Player's turn"));
        t.setRepeats(false);
        t.start();

    }

    /**
     * Start a new Game
     */
    private void newGame() {
        System.out.println("StartiTng new game");

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

        mBoardPanel.setBoard(mGame);
        mBoardPanel.enableAvailableMoves(true);

        mGame.getPossibleMoves(mGame.getCurrentPlayer());

        mStatusBar.setInfoMessage(mGame.getCurrentPlayer().getColor() + " Player's turn");

        refreshUI();
    }


    /**
     * Create a player according to its type and difficulty (if IA)
     *
     * @param type       "Human" or "Computer"
     * @param difficulty "Easy", "Medium" or "Hard"
     * @return New player instance
     */
    private PlayerInterface createPlayer(String type, String difficulty, Piece.Color c) {
        switch (type) {
            case "Human":
                return new ConsolePlayer(c);

            case "Computer":
                switch (difficulty) {
                    case "Easy":
                        MaximiseScoreHeuristic easyHeuristic = new MaximiseScoreHeuristic();
                        return new AIPlayer(c, easyHeuristic, 1);

                    case "Medium":
                        MobilityHeuristic mediumHeuristic = new MobilityHeuristic();
                        return new AIPlayer(c, mediumHeuristic, 2);

                    case "Hard":
                    default:
                        CompositeHeurstic hardHeuristic = new CompositeHeurstic();
                        hardHeuristic.addHeuristic(new MobilityHeuristic(), 10);
                        return new AIPlayer(c, hardHeuristic, 4);
                }

            default:
                throw new IllegalArgumentException("Invalid options given to createPlayer");
        }
    }

    /**
     * Refresh Interface after data changed
     */
    private void refreshUI() {
        // refresh scores
        mP1ScoreLabel.setText("" + mPlayer1.getScore());
        mP2ScoreLabel.setText("" + mPlayer2.getScore());

        // refresh board
        mPanel.repaint();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        createUIComponents();
        mPanel = new JPanel();
        mPanel.setLayout(new BorderLayout(0, 0));
        mPanel.setBackground(new Color(-13421773));
        mPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5), null));
        mPanel.add(mBoardPanel, BorderLayout.CENTER);
        mStatusBar.setBackground(new Color(-1115905));
        mStatusBar.setForeground(new Color(-1));
        mPanel.add(mStatusBar, BorderLayout.SOUTH);
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(5, 1, new Insets(0, 4, 0, 4), -1, -1));
        panel1.setBackground(new Color(-5221824));
        mPanel.add(panel1, BorderLayout.EAST);
        panel1.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(-16777216)), null));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel2.setBackground(new Color(-5221824));
        panel2.setForeground(new Color(-723724));
        panel1.add(panel2, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        panel2.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "P1 (White)", TitledBorder.LEFT, TitledBorder.DEFAULT_POSITION));
        mPlayer1Type = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel1 = new DefaultComboBoxModel();
        defaultComboBoxModel1.addElement("Human");
        defaultComboBoxModel1.addElement("Computer");
        mPlayer1Type.setModel(defaultComboBoxModel1);
        panel2.add(mPlayer1Type, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        mPlayer1Difficulty = new JComboBox();
        mPlayer1Difficulty.setEnabled(false);
        final DefaultComboBoxModel defaultComboBoxModel2 = new DefaultComboBoxModel();
        defaultComboBoxModel2.addElement("Easy");
        defaultComboBoxModel2.addElement("Medium");
        defaultComboBoxModel2.addElement("Hard");
        mPlayer1Difficulty.setModel(defaultComboBoxModel2);
        panel2.add(mPlayer1Difficulty, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel3.setBackground(new Color(-5221824));
        panel3.setForeground(new Color(-723724));
        panel1.add(panel3, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        panel3.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "P2 (Black)", TitledBorder.LEFT, TitledBorder.DEFAULT_POSITION));
        mPlayer2Type = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel3 = new DefaultComboBoxModel();
        defaultComboBoxModel3.addElement("Human");
        defaultComboBoxModel3.addElement("Computer");
        mPlayer2Type.setModel(defaultComboBoxModel3);
        panel3.add(mPlayer2Type, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        mPlayer2Difficulty = new JComboBox();
        mPlayer2Difficulty.setEnabled(false);
        final DefaultComboBoxModel defaultComboBoxModel4 = new DefaultComboBoxModel();
        defaultComboBoxModel4.addElement("Easy");
        defaultComboBoxModel4.addElement("Medium");
        defaultComboBoxModel4.addElement("Hard");
        mPlayer2Difficulty.setModel(defaultComboBoxModel4);
        panel3.add(mPlayer2Difficulty, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel4.setBackground(new Color(-5221824));
        panel1.add(panel4, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        mNewGameButton = new JButton();
        mNewGameButton.setText("New Game");
        panel4.add(mNewGameButton, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer();
        panel1.add(spacer1, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final JPanel panel5 = new JPanel();
        panel5.setLayout(new GridLayoutManager(2, 3, new Insets(0, 0, 0, 0), -1, -1));
        panel5.setBackground(new Color(-5221824));
        panel1.add(panel5, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        mP1ScoreLabel = new JLabel();
        Font mP1ScoreLabelFont = this.$$$getFont$$$(null, -1, 16, mP1ScoreLabel.getFont());
        if (mP1ScoreLabelFont != null) mP1ScoreLabel.setFont(mP1ScoreLabelFont);
        mP1ScoreLabel.setForeground(new Color(-723724));
        mP1ScoreLabel.setHorizontalAlignment(0);
        mP1ScoreLabel.setText("-");
        panel5.add(mP1ScoreLabel, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        mP2ScoreLabel = new JLabel();
        Font mP2ScoreLabelFont = this.$$$getFont$$$(null, -1, 16, mP2ScoreLabel.getFont());
        if (mP2ScoreLabelFont != null) mP2ScoreLabel.setFont(mP2ScoreLabelFont);
        mP2ScoreLabel.setForeground(new Color(-723724));
        mP2ScoreLabel.setHorizontalAlignment(0);
        mP2ScoreLabel.setText("-");
        panel5.add(mP2ScoreLabel, new GridConstraints(1, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer2 = new Spacer();
        panel5.add(spacer2, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final JLabel label1 = new JLabel();
        label1.setForeground(new Color(-723724));
        label1.setText("P1 Score");
        panel5.add(label1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setForeground(new Color(-723724));
        label2.setText("P2 Score");
        panel5.add(label2, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    private Font $$$getFont$$$(String fontName, int style, int size, Font currentFont) {
        if (currentFont == null) return null;
        String resultName;
        if (fontName == null) {
            resultName = currentFont.getName();
        } else {
            Font testFont = new Font(fontName, Font.PLAIN, 10);
            if (testFont.canDisplay('a') && testFont.canDisplay('1')) {
                resultName = fontName;
            } else {
                resultName = currentFont.getName();
            }
        }
        return new Font(resultName, style >= 0 ? style : currentFont.getStyle(), size >= 0 ? size : currentFont.getSize());
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return mPanel;
    }
}
