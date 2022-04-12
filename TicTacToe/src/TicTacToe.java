import javax.swing.*;

/**
 * ������� ����� ���� "�������� ������"
 */
public class TicTacToe {

    /** ����� ����*/
    protected static char[][] MAP_PLAY_FIELD;
    /** ������ ����� � ������� SIZE * SIZE */
    public static final int SIZE = 3;
    /** ������� ��������� */
    public static int levelOfDifficulty = 0;

    /** ������ ������ ������ */
    protected static final char MARKER_EMPTY = '�';
    /** ������ ������ */
    protected static final char MARKER_X = 'X';
    /** ������ ���������� */
    protected static final char MARKER_O = 'O';

    /** ���������� ������ */
    public static int[] resultParts = {0, 0, 0};

    /** ���� ���������� �� �������� ���� ������ � ���������� */
    public static boolean isUser;

    /** ��� ������ */
    public static final String USER = "�����";
    /** ��� ���������� */
    protected static final String COMPUTER = "���������";

    static int statusGame = -1;

    /**
     * ������ �������� ����
     * @param player �����
     * @param marker ����� ������
     */
    public static void starGame(String player, char marker) {
        isUser = USER.equals(player);
        statusGame = endGame(marker);
        if (statusGame > -1) {
            resultParts[statusGame == 1 ? 2 : player.equals(USER) ? 0 : 1]++;
            GUIGame.outputField.setText(statusGame == 0 ? player + " �������!" : "�����...");
            GUIGame.updateScore("����� ������� - " + resultParts[0] + ", ��������� ������� - " + resultParts[1] + ", ������ - " + resultParts[2]);
            int stillPlay = GUIGame.showInfoMessage(statusGame, player);
            if (stillPlay == 0) {
                statusGame = -1;
                clearFields();
                newGame();
            } else {
                GUIGame.outputField.setText("���� ��������!");
                disableFields();
            }
        }
    }

    /**
     * ������������ ����
     */
    private static void disableFields() {
        for (JButton[] buttons : GUIGame.mapPlayField) {
            for (JButton button : buttons) {
                button.setEnabled(false);
            }
        }
    }

    /**
     * �������� ����
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
     * ����� ����� ����
     */
    public static void newGame() {
        GUIGame.outputField.setText("���� ����������!");
        initPlayField();
    }

    /**
     * ������������� ��������� ����
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
     * ��� ������
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
     * �������� ���������� ���������
     * @param x ���������� �� �
     * @param y ���������� �� �
     * @return {@code true}, ���� ���� ���������� �� �������, ����� {@code true}
     */
    protected static boolean isCoordinateNotValidate(int x, int y) {
        if ((x < 0 || x > SIZE - 1) || (y < 0 || y > SIZE - 1)) {
            return true;
        }
        return MAP_PLAY_FIELD[x][y] != MARKER_EMPTY;
    }

    /**
     * �������� ��������� ����
     * @param marker ������ ������
     * @return {@code 0}, ���� ����� � �������� {@code marker} �������,
     * {@code 1}, ���� �����, {@code -1} ���� ����� �� ������� (���� ������������)
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
     * �������� ���������� ����������
     * @param marker ������ ������
     * @return {@code true} ���� ����� �������, ����� {@code false}
     */
    public static boolean isVictory(char marker) {
        return isVerticalVictory(marker) || isHorizontalVictory(marker) ||
                isVictoryDiagonallyFromLeftToRight(marker) || isVictoryDiagonallyFromRightToLeft(marker);
    }

    /**
     * �������� ������ � ����������� �� �����������
     * @param marker ������ ������
     * @return {@code true}, ���� ����� �������, ����� {@code false}
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
     * �������� ������ � ����������� �� ���������
     * @param marker ������ ������
     * @return {@code true}, ���� ����� �������, ����� {@code false}
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
     * �������� ������ � ����������� �� ��������� (����� �� �����)
     * @param marker ������ ������
     * @return {@code true}, ���� ����� �������, ����� {@code false}
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
     * �������� ������ � ����������� �� ��������� � ����� �� ����
     * @param marker ������ ������
     * @return {@code true}, ���� ����� �������, ����� {@code false}
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
     * ��������� �������� �� ��� ���
     * @return {@code true}, ���� ��� �� ��������, ����� {@code false}
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
