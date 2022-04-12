import javax.swing.*;
import java.awt.*;

import static javax.swing.JOptionPane.INFORMATION_MESSAGE;
import static javax.swing.JOptionPane.YES_NO_OPTION;

public class GUIGame extends JFrame {

    public static JTextField outputField = new JTextField();
    public static JButton[][] mapPlayField = new JButton[TicTacToe.SIZE][TicTacToe.SIZE];
    public static JFrame frame = new JFrame();
    static JPanel difficultySelection;
    static JLabel score = new JLabel();

    public GUIGame () {
        frame.setTitle("Tic Tac Toe");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        frame.getRootPane().setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        difficultySelection = showDisplayDifficultySelection();
        frame.add(difficultySelection, BorderLayout.CENTER);

        frame.setSize(500, 400);
        frame.setMinimumSize(new Dimension(500, 400));
        frame.setVisible(true);
    }

    public static void showDialogGame() {
        frame.remove(difficultySelection);
        outputField.setEditable(false);
        JButton newGame = new JButton("Новая игра");
        newGame.addActionListener(GameActionListener.newGame);
        JPanel newGamePanel = new JPanel(new GridLayout(1, 2, 2, 2));
        newGamePanel.add(outputField);
        newGamePanel.add(newGame);
        frame.add(newGamePanel, BorderLayout.NORTH);
        frame.add(makeButtonsField(), BorderLayout.CENTER);
        updateScore("Игрок победил - 0, Компьютер победил - 0, Ничьих - 0");
        frame.add(score, BorderLayout.SOUTH);
        frame.setVisible(true);
        TicTacToe.newGame();
    }

    public static int showInfoMessage(int statusGame, String player) {
        return JOptionPane.showConfirmDialog(frame, "<html><h2> " + (statusGame == 0 ? player + " победил!" : "Ничья...") +
                "</h2><i>Сыграть ещё?</i>", "Окно подтверждения", YES_NO_OPTION, INFORMATION_MESSAGE);
    }

    public static void updateScore(String str) {
        score.setText(str);
    }

    public static JPanel makeButtonsField() {
        JPanel buttonsPanel = new JPanel(new GridLayout(TicTacToe.SIZE, TicTacToe.SIZE, 2, 2));
        for (int i = 0; i < TicTacToe.SIZE; i++) {
            for (int j = 0; j < TicTacToe.SIZE; j++) {
                mapPlayField[i][j] = new Buttons(i, j);
                buttonsPanel.add(mapPlayField[i][j]);
            }
        }
        return buttonsPanel;
    }

    public JPanel showDisplayDifficultySelection() {
        JButton difficultyLevelsEasy = new JButton("Легко");
        JButton difficultyLevelsMedium = new JButton("Средне");
        JButton difficultyLevelsComplicated = new JButton("Сложно");

        difficultyLevelsEasy.addActionListener(GameActionListener.selectDifficultyLevelsEasy);
        difficultyLevelsMedium.addActionListener(GameActionListener.selectDifficultyLevelsMedium);
        difficultyLevelsComplicated.addActionListener(GameActionListener.selectDifficultyLevelsComplicated);

        JPanel difficultySelection = new JPanel(new GridLayout(4, 1, 0, 5));
        difficultySelection.add(new JLabel("Выберите сложность"));
        difficultySelection.add(difficultyLevelsEasy);
        difficultySelection.add(difficultyLevelsMedium);
        difficultySelection.add(difficultyLevelsComplicated);

        return difficultySelection;
    }

    public static void main(String[] args) {
        new GUIGame();
    }
}
