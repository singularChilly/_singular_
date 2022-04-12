import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Класс хода компьютера
 */
public class ComputerRunning extends TicTacToe {

    /** Рейтинг */
    private static int rating = 0;
    /** Индекс по X */
    private static int indexX;
    /** индекс по Y */
    private static int indexY;

    /**
     * Конструктор
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
     * Напечатать результат хода компьютера
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
     * Сходить
     * @param x координата по х
     * @param y координата по у
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
     * Обработать данные
     * @param map карта с данными
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
     * Проверить границы для конкретного хода
     * @param x координата по х
     * @param y координата по у
     * @return массив с границами ({@code true} - граница естьб иначе {@code false})
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
     * На основе массива с границами проверить, есть ли маркер противника в доступных ходах
     * @param isBorder массив с границами
     * @param x координата по х
     * @param y координата по у
     * @return масисив с границами ({@code true} - граница естьб иначе {@code false})
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
     * Устанавливает рейтинг хода
     * @param position какую позицию проверяем
     * @param x координата по х
     * @param y координата по у
     * @return карту с рейтингом и ходами по данному рейтингу
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
     * Узнать лучший ретинг
     * @param position какую позицию проверяем
     * @param x координата по х
     * @param y координата по у
     * @return карту с рейтингом и ходами по данному рейтингу
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
     * Изменить значение переменных на основе {@code position}
     * @param position какую позицию проверяем
     * @param x координата по х
     * @param y координата по у
     * @return массив с новыми координатами
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
     * Проверить, победит ли игрок маркер которого передали в аргуменах по заданным координатам
     * @param x координата по х
     * @param y координата по у
     * @param marker маркер игрока
     * @return {@code true} если переданный маркер установленный по переданным координатам побелит, иначе {@code false}
     */
    private boolean isCoordinateVictoryPlayer(int x, int y, char marker) {
        MAP_PLAY_FIELD[x][y] = marker;
        boolean result = isVictory(marker);
        MAP_PLAY_FIELD[x][y] = MARKER_EMPTY;
        return result;
    }

    /**
     * Инициализация карты
     * @param rating рейтинг
     * @param x координата по х
     * @param y координата по у
     * @return карту с рейтингом и ходами по данному рейтингу
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
