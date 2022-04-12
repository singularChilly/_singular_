import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * ����� ���� ����������
 */
public class ComputerRunning extends TicTacToe {

    /** ������� */
    private static int rating = 0;
    /** ������ �� X */
    private static int indexX;
    /** ������ �� Y */
    private static int indexY;

    /**
     * �����������
     */
    public ComputerRunning() {
        boolean isRandom = true;
        rating = 0;
        indexX = 0;
        indexY = 0;
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (TicTacToe.levelOfDifficulty >= 3 && MAP_PLAY_FIELD[i][j] == MARKER_EMPTY && isCoordinateVictoryPlayer(i, j, MARKER_O)) {
                    indexX = i;
                    indexY = j;
                    printResult();
                    return;
                }
                if (TicTacToe.levelOfDifficulty >= 2 && MAP_PLAY_FIELD[i][j] == MARKER_EMPTY && isCoordinateVictoryPlayer(i, j, MARKER_X)) {
                    indexX = i;
                    indexY = j;
                    printResult();
                    return;
                }
                if (MAP_PLAY_FIELD[i][j] == MARKER_O) {
                    go(i, j);
                    isRandom = false;
                }
            }
        }

        if (isRandom) {
            int x, y;
            do {
                x = (int) (Math.random() * SIZE);
                y = (int) (Math.random() * SIZE);
            } while (isCoordinateNotValidate(x, y));

            MAP_PLAY_FIELD[x][y] = MARKER_O;
            setMarkerToToButton(x, y);
            return;
        }

        printResult();
    }

    /**
     * ���������� ��������� ���� ����������
     */
    private void printResult() {
        MAP_PLAY_FIELD[indexX][indexY] = MARKER_O;
        setMarkerToToButton(indexX, indexY);
    }

    private void setMarkerToToButton(int x, int y) {
        GUIGame.mapPlayField[x][y].setText("<html><h1>O</h1>");
        GUIGame.mapPlayField[x][y].setEnabled(false);
    }

    /**
     * �������
     * @param x ���������� �� �
     * @param y ���������� �� �
     */
    private void go(int x, int y) {
        boolean[] isBorder = checkMarkerFoe(checkBorders(x, y), x, y);

        for (int i = 0; i < isBorder.length; i++) {
            if (!isBorder[i]) {
                int[] newValue = changeVariablesValue(i, x, y);
                handleData(ratingRunning(i, newValue[0], newValue[1]));
            }
        }
    }

    /**
     * ���������� ������
     * @param map ����� � �������
     */
    private void handleData(Map<Integer, ArrayList<Integer>> map) {
        map.forEach((k, v) -> {
            if (k > rating) {
                rating = k;
                indexX = v.get(0);
                indexY = v.get(1);
            }
        });
    }

    /**
     * ��������� ������� ��� ����������� ����
     * @param x ���������� �� �
     * @param y ���������� �� �
     * @return ������ � ��������� ({@code true} - ������� ����� ����� {@code false})
     */
    private static boolean[] checkBorders(int x, int y) {
        boolean[] isBorder = {false, false, false, false, false, false, false, false};
        for (int count = 0; count < SIZE; count++) {
            if (x == 0 && y == count) {
                isBorder[0] = true;
                isBorder[1] = true;
                isBorder[2] = true;
            }
            if (x == count && y == SIZE - 1) {
                isBorder[2] = true;
                isBorder[3] = true;
                isBorder[4] = true;
            }
            if (x == SIZE - 1 && y == count) {
                isBorder[4] = true;
                isBorder[5] = true;
                isBorder[6] = true;
            }
            if (x == count && y == 0) {
                isBorder[6] = true;
                isBorder[7] = true;
                isBorder[0] = true;
            }
        }
        return isBorder;
    }

    /**
     * �� ������ ������� � ��������� ���������, ���� �� ������ ���������� � ��������� �����
     * @param isBorder ������ � ���������
     * @param x ���������� �� �
     * @param y ���������� �� �
     * @return ������� � ��������� ({@code true} - ������� ����� ����� {@code false})
     */
    private static boolean[] checkMarkerFoe(boolean[] isBorder, int x, int y) {
        for (int i = 0; i < isBorder.length; i++) {
            if (!isBorder[i]) {
                int[] newValue = changeVariablesValue(i, x, y);
                if (MAP_PLAY_FIELD[newValue[0]][newValue[1]] == MARKER_X) {
                    isBorder[i] = true;
                }
            }
        }
        return isBorder;
    }

    /**
     * ������������� ������� ����
     * @param position ����� ������� ���������
     * @param x ���������� �� �
     * @param y ���������� �� �
     * @return ����� � ��������� � ������ �� ������� ��������
     */
    public Map<Integer, ArrayList<Integer>> ratingRunning(int position, int x, int y) {
        if (MAP_PLAY_FIELD[x][y] == MARKER_EMPTY) {
            return initMap(1, x, y);
        }
        if (MAP_PLAY_FIELD[x][y] == MARKER_O) {
            return checkTheBestRunning(position, x, y);
        }
        return initMap(0, x, y);
    }

    /**
     * ������ ������ ������
     * @param position ����� ������� ���������
     * @param x ���������� �� �
     * @param y ���������� �� �
     * @return ����� � ��������� � ������ �� ������� ��������
     */
    private Map<Integer, ArrayList<Integer>> checkTheBestRunning(int position, int x, int y) {
        int tempX = x;
        int tempY = y;
        int temp2X = x;
        int temp2Y = y;
        int lengthArray = checkMarkerFoe(checkBorders(tempX, tempY), tempX, tempY).length;
        int reversePosition = position - (lengthArray / 2) < 0 ? position + (lengthArray / 2) : position - (lengthArray / 2);
        int tempRating = 0;
        while (true) {
            if (!checkMarkerFoe(checkBorders(tempX, tempY), tempX, tempY)[position]) {
                tempRating++;
                int [] newValue = changeVariablesValue(position, tempX, tempY);
                tempX = newValue[0];
                tempY = newValue[1];
                if (MARKER_EMPTY == MAP_PLAY_FIELD[tempX][tempY]) {
                    if (!checkMarkerFoe(checkBorders(tempX, tempY), tempX, tempY)[reversePosition] &&
                            MARKER_O == MAP_PLAY_FIELD[changeVariablesValue(reversePosition, tempX, tempY)[0]][changeVariablesValue(reversePosition, tempX, tempY)[1]]) {
                        tempRating++;
                    }
                    if (tempRating > rating) {
                        return initMap(tempRating, tempX, tempY);
                    }
                    return new HashMap<>();
                }
            } else if (!checkMarkerFoe(checkBorders(temp2X, temp2Y), temp2X, temp2Y)[reversePosition]) {
                tempRating++;
                int [] newValue = changeVariablesValue(reversePosition, temp2X, temp2Y);
                temp2X = newValue[0];
                temp2Y = newValue[1];
                if (MARKER_EMPTY == MAP_PLAY_FIELD[temp2X][temp2Y]) {
                    if (tempRating > rating) {
                        return initMap(tempRating, temp2X, temp2Y);
                    }
                    return new HashMap<>();
                }
            } else {
                return new HashMap<>();
            }
        }
    }

    /**
     * �������� �������� ���������� �� ������ {@code position}
     * @param position ����� ������� ���������
     * @param x ���������� �� �
     * @param y ���������� �� �
     * @return ������ � ������ ������������
     */
    private static int[] changeVariablesValue(int position, int x, int y) {
        switch (position) {
            case 0 :
                x--;
                y--;
                break;
            case 1 :
                x--;
                break;
            case 2 :
                x--;
                y++;
                break;
            case 3 :
                y++;
                break;
            case 4 :
                x++;
                y++;
                break;
            case 5 :
                x++;
                break;
            case 6 :
                x++;
                y--;
                break;
            case 7 :
                y--;
                break;
        }
        return new int[]{x, y};
    }

    /**
     * ���������, ������� �� ����� ������ �������� �������� � ��������� �� �������� �����������
     * @param x ���������� �� �
     * @param y ���������� �� �
     * @param marker ������ ������
     * @return {@code true} ���� ���������� ������ ������������� �� ���������� ����������� �������, ����� {@code false}
     */
    private boolean isCoordinateVictoryPlayer(int x, int y, char marker) {
        MAP_PLAY_FIELD[x][y] = marker;
        boolean result = isVictory(marker);
        MAP_PLAY_FIELD[x][y] = MARKER_EMPTY;
        return result;
    }

    /**
     * ������������� �����
     * @param rating �������
     * @param x ���������� �� �
     * @param y ���������� �� �
     * @return ����� � ��������� � ������ �� ������� ��������
     */
    private Map<Integer, ArrayList<Integer>> initMap(int rating, int x, int y) {
        Map<Integer, ArrayList<Integer>> map = new HashMap<>();
        ArrayList<Integer> array = new ArrayList<>();
        array.add(x);
        array.add(y);
        map.put(rating, array);
        return map;
    }
}
