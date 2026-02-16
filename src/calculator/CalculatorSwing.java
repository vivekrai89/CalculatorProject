import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class CalculatorSwing extends JFrame implements ActionListener {

    private JTextField display;
    private String operator = "";
    private double result = 0;
    private boolean startNewNumber = true;

    public CalculatorSwing() {
        setTitle("Calculator");
        setSize(350, 500); // Bigger window
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.DARK_GRAY); // Background color

        // Display field
        display = new JTextField("0");
        display.setEditable(false);
        display.setFont(new Font("Arial", Font.BOLD, 40)); // Bigger font
        display.setHorizontalAlignment(JTextField.RIGHT);
        display.setBackground(Color.BLACK);
        display.setForeground(Color.GREEN);
        display.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(display, BorderLayout.NORTH);

        // Buttons panel
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 4, 5, 5));
        panel.setBackground(Color.DARK_GRAY);

        String[] buttons = {
                "7", "8", "9", "/",
                "4", "5", "6", "*",
                "1", "2", "3", "-",
                "0", ".", "=", "+",
                "C"
        };

        for (String text : buttons) {
            JButton button = new JButton(text);
            button.setFont(new Font("Arial", Font.BOLD, 24));
            button.setForeground(Color.WHITE);
            button.setBackground(getButtonColor(text));
            button.setFocusPainted(false);
            button.addActionListener(this);
            panel.add(button);
        }

        add(panel, BorderLayout.CENTER);
        setVisible(true);
    }

    private Color getButtonColor(String text) {
        if ("0123456789.".contains(text)) return new Color(50, 50, 50); // dark gray for numbers
        if ("C".equals(text)) return new Color(200, 50, 50); // red for clear
        return new Color(30, 144, 255); // blue for operators
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();

        if ("0123456789.".contains(cmd)) {
            if (startNewNumber) {
                display.setText(cmd);
                startNewNumber = false;
            } else {
                display.setText(display.getText() + cmd);
            }
        } else if ("+-*/".contains(cmd)) {
            if (!operator.isEmpty()) {
                double secondNumber = Double.parseDouble(display.getText());
                result = calculate(result, secondNumber, operator);
                display.setText(String.valueOf(result));
            } else {
                result = Double.parseDouble(display.getText());
            }
            operator = cmd;
            startNewNumber = true;
        } else if (cmd.equals("=")) {
            if (!operator.isEmpty()) {
                double secondNumber = Double.parseDouble(display.getText());
                result = calculate(result, secondNumber, operator);
                display.setText(String.valueOf(result));
                operator = "";
                startNewNumber = true;
            }
        } else if (cmd.equals("C")) {
            display.setText("0");
            result = 0;
            operator = "";
            startNewNumber = true;
        }
    }

    private double calculate(double a, double b, String op) {
        switch (op) {
            case "+": return a + b;
            case "-": return a - b;
            case "*": return a * b;
            case "/": return b != 0 ? a / b : 0;
            default: return b;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(CalculatorSwing::new);
    }
}
