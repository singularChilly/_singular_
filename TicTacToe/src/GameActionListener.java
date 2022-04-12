import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameActionListener implements ActionListener {
    private int row;
    private int column;
    private Buttons button;

    public GameActionListener(int row, int column, Buttons button) {
        this.row = row;
        this.column = column;
        this.button = button;
    }

    public static java.awt.event.ActionListener selectDifficultyLevelsEasy = e -> {
        TicTacToe.levelOfDifficulty = 1;
        GUIGame.showDialogGame();
    };

    public static java.awt.event.ActionListener selectDifficultyLevelsMedium = e -> {
        TicTacToe.levelOfDifficulty = 2;
        GUIGame.showDialogGame();
    };

    public static java.awt.event.ActionListener selectDifficultyLevelsComplicated = e -> {
        TicTacToe.levelOfDifficulty = 3;
        GUIGame.showDialogGame();
    };

    public static java.awt.event.ActionListener newGame = e -> {
        TicTacToe.newGame();
        TicTacToe.clearFields();
    };

    @Override
    public void actionPerformed(ActionEvent e) {
        button.setText("<html><h1>X</h1>");
        button.setEnabled(false);
        TicTacToe.humanRunning(row, column);
    }
}
