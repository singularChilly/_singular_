import javax.swing.*;

public class Buttons extends JButton {
    public Buttons(int x, int y) {
        addActionListener(new GameActionListener(x, y, this));
    }
}
