import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SimpleCalculator extends JFrame {

    private JTextField textField;
    private double firstNumber = 0;
    private String operator = "";
    private boolean startNewInput = true;

    public SimpleCalculator() {
        // Set up the JFrame
        setTitle("Simple Calculator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 400);
        setLocationRelativeTo(null); // Center the frame

        // Create and set up components
        textField = new JTextField();
        textField.setHorizontalAlignment(JTextField.RIGHT);
        textField.setEditable(false);

        // Create number buttons (0-9)
        JButton[] numberButtons = new JButton[10];
        for (int i = 0; i < 10; i++) {
            final int number = i; // Required for the ActionListener
            numberButtons[i] = new JButton(String.valueOf(i));
            numberButtons[i].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    numberButtonClicked(number);
                }
            });
        }

        // Create operator buttons (+, -, *, /, =)
        JButton addButton = createOperatorButton("+");
        JButton subtractButton = createOperatorButton("-");
        JButton multiplyButton = createOperatorButton("*");
        JButton divideButton = createOperatorButton("/");
        JButton equalsButton = new JButton("=");
        equalsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                equalsButtonClicked();
            }
        });

        // Create a panel for the number buttons
        JPanel numberPanel = new JPanel(new GridLayout(4, 3));
        for (int i = 1; i <= 9; i++) {
            numberPanel.add(numberButtons[i]);
        }
        numberPanel.add(new JPanel()); // Empty panel
        numberPanel.add(numberButtons[0]);
        numberPanel.add(new JPanel()); // Empty panel

        // Create a panel for the operator buttons
        JPanel operatorPanel = new JPanel(new GridLayout(5, 1));
        operatorPanel.add(addButton);
        operatorPanel.add(subtractButton);
        operatorPanel.add(multiplyButton);
        operatorPanel.add(divideButton);
        operatorPanel.add(equalsButton);

        // Create a main panel to hold all components
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(textField, BorderLayout.NORTH);
        mainPanel.add(numberPanel, BorderLayout.CENTER);
        mainPanel.add(operatorPanel, BorderLayout.EAST);

        // Set the main panel as the content pane
        setContentPane(mainPanel);
    }

    private JButton createOperatorButton(String text) {
        JButton button = new JButton(text);
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                operatorButtonClicked(text);
            }
        });
        return button;
    }

    private void numberButtonClicked(int number) {
        if (startNewInput) {
            textField.setText(String.valueOf(number));
            startNewInput = false;
        } else {
            textField.setText(textField.getText() + number);
        }
    }

    private void operatorButtonClicked(String op) {
        startNewInput = true;
        firstNumber = Double.parseDouble(textField.getText());
        operator = op;
    }

    private void equalsButtonClicked() {
        double secondNumber = Double.parseDouble(textField.getText());
        double result = 0;

        switch (operator) {
            case "+":
                result = firstNumber + secondNumber;
                break;
            case "-":
                result = firstNumber - secondNumber;
                break;
            case "*":
                result = firstNumber * secondNumber;
                break;
            case "/":
                if (secondNumber != 0) {
                    result = firstNumber / secondNumber;
                } else {
                    JOptionPane.showMessageDialog(this, "Cannot divide by zero", "Error", JOptionPane.ERROR_MESSAGE);
                    return; // Do not update the text field
                }
                break;
        }

        textField.setText(String.valueOf(result));
        startNewInput = true;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new SimpleCalculator().setVisible(true);
            }
        });
    }
}
