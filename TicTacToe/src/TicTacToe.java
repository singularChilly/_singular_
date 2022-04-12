import javax.swing.*;

/**
 * Главный класс игры "Крестики нолики"
 */
public class TicTacToe {

    /** Карта игры*/
    protected static char[][] MAP_PLAY_FIELD;
    /** Размер карты в формате SIZE * SIZE */
    public static final int SIZE = 3;
    /** Уровень сложности */
    public static int levelOfDifficulty = 0;

    /** Маркер пустой клетки */
    protected static final char MARKER_EMPTY = '•';
    /** Маркер игрока */
    protected static final char MARKER_X = 'X';
    /** Маркер компьютера */
    protected static final char MARKER_O = 'O';

    /** Результаты партий */
    public static int[] resultParts = {0, 0, 0};

    /** Флвг отвечающий за различие хода игрока и компьютера */
    public static boolean isUser;

    /** Имя игрока */
    public static final String USER = "ИГРОК";
    /** Имя компьютера */
    protected static final String COMPUTER = "КОМПЬЮТЕР";

    static int statusGame = -1;

    /**
     * Зпуска процесса игры
     * @param player игрок
     * @param marker марке игрока
     */
    public static void starGame(String player, char marker) {
        isUser = USER.equals(player);
        statusGame = endGame(marker);
        if (statusGame > -1) {
            resultParts[statusGame == 1 ? 2 : player.equals(USER) ? 0 : 1]++;
            GUIGame.outputField.setText(statusGame == 0 ? player + " победил!" : "Ничья...");
            GUIGame.updateScore("Игрок победил - " + resultParts[0] + ", Компьютер победил - " + resultParts[1] + ", Ничьих - " + resultParts[2]);
            int stillPlay = GUIGame.showInfoMessage(statusGame, player);
            if (stillPlay == 0) {
                statusGame = -1;
                clearFields();
                newGame();
            } else {
                GUIGame.outputField.setText("Игра окончена!");
                disableFields();
            }
        }
    }

    /**
     * Задизейблить поле
     */
    private static void disableFields() {
        for (JButton[] buttons : GUIGame.mapPlayField) {
            for (JButton button : buttons) {
                button.setEnabled(false);
            }
        }
    }

    /**
     * Очистить поле
     */
    public static void clearFields() {
        for (JButton[] buttons : GUIGame.mapPlayField) {
            for (JButton button : buttons) {
                button.setText("");
                button.setEnabled(true);
            }
        }
    }

    /**
     * Запус новой игры
     */
    public static void newGame() {
        GUIGame.outputField.setText("Игра начинается!");
        initPlayField();
    }

    /**
     * Инициализация иггрового поля
     */
    private static void initPlayField() {
        MAP_PLAY_FIELD = new char[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                MAP_PLAY_FIELD[j][i] = MARKER_EMPTY;
            }
        }
    }

    /**
     * Ход игрока
     */
    public static void humanRunning(int x, int y) {
        MAP_PLAY_FIELD[x][y] = MARKER_X;
        starGame(USER, MARKER_X);
        if (statusGame == -1) {
            new ComputerRunning();
            starGame(COMPUTER, MARKER_O);
        }
    }

    /**
     * Проверка валидности координат
     * @param x координата по х
     * @param y координата по у
     * @return {@code true}, если если координаты не валидны, иначе {@code true}
     */
    protected static boolean isCoordinateNotValidate(int x, int y) {
        if ((x < 0 || x > SIZE - 1) || (y < 0 || y > SIZE - 1)) {
            return true;
        }
        return MAP_PLAY_FIELD[x][y] != MARKER_EMPTY;
    }

    /**
     * Проверка окончания игры
     * @param marker маркер игрока
     * @return {@code 0}, если игрок с маркером {@code marker} победил,
     * {@code 1}, если ничья, {@code -1} если игрок не победил (игра продолжается)
     */
    public static int endGame(char marker) {
        if (isVictory(marker)) {
            return 0;
        }
        if (moveImpossible()) {
            return 1;
        }
        return -1;
    }

    /**
     * Проверка выиграшных комбинаций
     * @param marker маркер игрока
     * @return {@code true} если игрок победил, иначе {@code false}
     */
    public static boolean isVictory(char marker) {
        return isVerticalVictory(marker) || isHorizontalVictory(marker) ||
                isVictoryDiagonallyFromLeftToRight(marker) || isVictoryDiagonallyFromRightToLeft(marker);
    }

    /**
     * Проверка победы с комбинацией по горизонтали
     * @param marker маркер игрока
     * @return {@code true}, если игрок победил, иначе {@code false}
     */
    private static boolean isHorizontalVictory(char marker) {
        int count = 0;
        for (int i = 0; i < SIZE; i ++) {
            for (int j = 0; j < SIZE; j++) {
                if (MAP_PLAY_FIELD[i][j] == marker) {
                    count++;
                }
            }
            if (count == SIZE) {
                return true;
            }
            count = 0;
        }
        return false;
    }

    /**
     * Проверка победы с комбинацией по вертикали
     * @param marker маркер игрока
     * @return {@code true}, если игрок победил, иначе {@code false}
     */
    private static boolean isVerticalVictory(char marker) {
        int count = 0;
        for (int i = 0; i < SIZE; i ++) {
            for (int j = 0; j < SIZE; j++) {
                if (MAP_PLAY_FIELD[j][i] == marker) {
                    count++;
                }
            }
            if (count == SIZE) {
                return true;
            }
            count = 0;
        }
        return false;
    }

    /**
     * Проверка победы с комбинацией по диагонали (слева на право)
     * @param marker маркер игрока
     * @return {@code true}, если игрок победил, иначе {@code false}
     */
    private static boolean isVictoryDiagonallyFromLeftToRight(char marker) {
        int count = 0;
        for (int i = 0; i < SIZE; i ++) {
            for (int j = 0; j < SIZE; j++) {
                if (i == j && MAP_PLAY_FIELD[i][j] == marker) {
                    count++;
                }
            }
            if (count == SIZE) {
                return true;
            }
        }
        return false;
    }

    /**
     * Проверка победы с комбинацией по диагонали с права на лево
     * @param marker маркер игрока
     * @return {@code true}, если игрок победил, иначе {@code false}
     */
    private static boolean isVictoryDiagonallyFromRightToLeft(char marker) {
        int count = 0;
        for (int i = 0; i < SIZE; i++) {
            for (int j = SIZE - 1; j >= 0; j--) {
                if (j == (SIZE - 1) - i && MAP_PLAY_FIELD[i][j] == marker) {
                    count++;
                }
            }
            if (count == SIZE) {
                return true;
            }
        }
        return false;
    }

    /**
     * Проверить возможен ли ещё ход
     * @return {@code true}, если ход не возможен, иначе {@code false}
     */
    private static boolean moveImpossible() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (MAP_PLAY_FIELD[i][j] == MARKER_EMPTY) {
                    return false;
                }
            }
        }
        return true;
    }
}
