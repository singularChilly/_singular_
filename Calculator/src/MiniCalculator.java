import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class MiniCalculator extends JFrame {

    private String operationLabel = "+";
    private JTextField eq = new JTextField(3);

    public MiniCalculator() {
        super("Mini Calculator");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        getRootPane().setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Кнопки операции
        JButton operationPlus = new JButton("+");
        JButton operationMinus = new JButton("-");
        JButton operationMultiply = new JButton("*");
        JButton operationDivide = new JButton("/");
        JButton operationPower = new JButton("^");

        // Помещаем кнопки на панель
        JPanel buttonOperation = new JPanel(new GridLayout(5, 1, 0, 5));
        buttonOperation.add(operationPlus);
        buttonOperation.add(operationMinus);
        buttonOperation.add(operationMultiply);
        buttonOperation.add(operationDivide);
        buttonOperation.add(operationPower);

        // Вычисление
        JPanel computation = new JPanel();
        computation.setLayout(new BoxLayout(computation, BoxLayout.Y_AXIS));
        computation.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JTextField arg1 = new JTextField(20);
        computation.add(arg1);

        JLabel operation = new JLabel();
        operation.setText(operationLabel);
        computation.add(operation);

        JTextField arg2 = new JTextField(7);
        computation.add(arg2);

        JLabel equally = new JLabel("=");
        computation.add(equally);

        eq.setEditable(false);
        computation.add(eq);

        // Кнопка "РЕШИТЬ"
        JButton solve = new JButton("РЕШИТЬ");
        solve.setAlignmentX(Component.CENTER_ALIGNMENT);
        computation.add(solve);

        JPanel flowComputation = new JPanel(new FlowLayout(FlowLayout.CENTER));
        flowComputation.add(computation);


        // Слушатели
        ActionListener plus = e -> {
            operationLabel = "+";
            operation.setText(operationLabel);
            checkFields(arg1.getText(), arg2.getText());
        };

        ActionListener minus = e -> {
            operationLabel = "-";
            operation.setText(operationLabel);
            checkFields(arg1.getText(), arg2.getText());
        };

        ActionListener multiply = e -> {
            operationLabel = "*";
            operation.setText(operationLabel);
            checkFields(arg1.getText(), arg2.getText());
        };

        ActionListener divide = e -> {
            operationLabel = "/";
            operation.setText(operationLabel);
            checkFields(arg1.getText(), arg2.getText());
        };

        ActionListener power = e -> {
            operationLabel = "^";
            operation.setText(operationLabel);
            checkFields(arg1.getText(), arg2.getText());
        };

        ActionListener listenerArg1 = e -> checkFields(arg1.getText(), arg2.getText());

        ActionListener listenerArg2 = e -> checkFields(arg1.getText(), arg2.getText());

        ActionListener listenerSolve = e -> checkFields(arg1.getText(), arg2.getText());

        operationPlus.addActionListener(plus);
        operationMinus.addActionListener(minus);
        operationMultiply.addActionListener(multiply);
        operationDivide.addActionListener(divide);
        operationPower.addActionListener(power);
        arg1.addActionListener(listenerArg1);
        arg2.addActionListener(listenerArg2);
        solve.addActionListener(listenerSolve);

        setMinimumSize(new Dimension(320, 200));
        add(buttonOperation, BorderLayout.WEST);
        add(computation, BorderLayout.CENTER);

        setSize(320, 200);
        setVisible(true);
    }

    private void checkFields(String arg1, String arg2) {
        String pattern = "^[-+]?[0-9]*\\.?[0-9]+(?:[eE][-+]?[0-9]+)?$";
        if (!arg1.isEmpty() && !arg2.isEmpty() && arg1.matches(pattern) && arg2.matches(pattern)) {
            doneOperation(new BigDecimal(arg1), new BigDecimal(arg2));
        } else if (arg1.contains(",") || arg2.contains(",")) {
            eq.setText("Используйте \".\" вместо \",\"");
        } else {
            eq.setText("Некорректные знчения!");
        }
    }

    private void doneOperation(BigDecimal x, BigDecimal y) {

        switch (operationLabel) {
            case "+" :
                eq.setText(String.valueOf((x.add(y))));
                break;
            case "-" :
                eq.setText(String.valueOf(x.subtract(y)));
                break;
            case "*" :
                eq.setText(String.valueOf(x.multiply(y)));
                break;
            case "/" :
                if (y.intValue() == 0) {
                    eq.setText("На 0 делить нельзя.");
                } else {
                    BigDecimal result = x.divide(y, 2, RoundingMode.DOWN);
                    String[] str = String.valueOf(result).split("\\.");
                    if (str[1].equals("00")) {
                        eq.setText(str[0]);
                    } else {
                        eq.setText(str[0] + "." + str[1]);
                    }
                }
                break;
            case "^" :
                if (String.valueOf(y).matches("\\d+")) {
                    eq.setText(String.valueOf(x.pow(Integer.parseInt(String.valueOf(y)))));
                } else {
                    eq.setText("Некорректные знчения!");
                }
                break;
        }
    }

    public static void main(String[] args) {
        new MiniCalculator();
    }
}
