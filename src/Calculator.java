import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import javax.swing.*;
import javax.swing.border.LineBorder;

public class Calculator {
    
    int boardWidth = 360;
    int boardHeight = 540;

    Color lightGrey = new Color(212, 212, 210);
    Color darkGrey = new Color(80, 80, 80);
    Color orange = new Color(255, 149, 0);
    Color black = new Color(28, 28, 28);
    
    JFrame frame = new JFrame("Calculator");
    JLabel displayLabel = new JLabel();
    JPanel displayPanel = new JPanel();
    JPanel buttonPanel = new JPanel();

    String[] buttonValues = {
        "AC", "+/-", "%", "÷", 
        "7", "8", "9", "×", 
        "4", "5", "6", "-",
        "1", "2", "3", "+",
        "0", ".", "√", "="
    };
    String[] rightSymbols = {"÷", "×", "-", "+", "="};
    String[] topSymbols = {"AC", "+/-", "%"};

    String A = "0";
    String operator = null;
    String B = null;

    Calculator(){

        frame.setSize(boardWidth, boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        displayLabel.setBackground(black);
        displayLabel.setForeground(Color.white);
        displayLabel.setFont(new Font("Arial", Font.PLAIN, 80));
        displayLabel.setHorizontalAlignment(JLabel.RIGHT);
        displayLabel.setText("0");
        displayLabel.setOpaque(true);

        displayPanel.setLayout(new BorderLayout());
        displayPanel.add(displayLabel);
        frame.add(displayPanel, BorderLayout.NORTH);
        

        buttonPanel.setLayout(new GridLayout(5, 4));
        buttonPanel.setBackground(black);

        for (int i=0; i< buttonValues.length; i++){
            JButton button = new JButton();
            String buttonValue = buttonValues[i];
            button.setText(buttonValue);
            button.setFont(new Font("Arial", Font.PLAIN, 30));
            button.setBackground(darkGrey);
            button.setFocusable(false);
            button.setForeground(Color.white);
            button.setBorder(new LineBorder(black));
            
            if (Arrays.asList(topSymbols).contains(buttonValue)) {
                button.setBackground(lightGrey);
                button.setForeground(black);
            }
            else if (Arrays.asList(rightSymbols).contains(buttonValue)) {
                button.setBackground(orange);
                button.setForeground(Color.white);
            }
            else {
                button.setBackground(darkGrey);
                button.setForeground(Color.white);
            }
            buttonPanel.add(button);
            button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    JButton button = (JButton)e.getSource();
                    String buttonText = button.getText(); 
                    if (Arrays.asList(topSymbols).contains(buttonValue)){
                        if (buttonText.equals("AC")){
                            clearAll();
                            displayLabel.setText("0");
                        }
                        if (buttonText.equals("+/-")){
                            double currentValue = Double.parseDouble(displayLabel.getText());
                            currentValue = currentValue * -1;
                            displayLabel.setText(removeZeroDecimal(currentValue));
                        }
                        if (buttonText.equals("%")){
                            if (displayLabel.getText() != "0"){
                                double currentValue = Double.parseDouble(displayLabel.getText());
                                currentValue = currentValue / 100;
                                displayLabel.setText(removeZeroDecimal(currentValue));
                            }
                            else {
                                // do nothing
                            }
                        }
                    }
                    else if (Arrays.asList(rightSymbols).contains(buttonText)){
                        if (buttonText.equals("=")){
                            if (A != null){
                                B = displayLabel.getText();
                                double numA = Double.parseDouble(A);
                                double numB = Double.parseDouble(B);
                                switch(operator) {
                                case "÷":
                                    displayLabel.setText(removeZeroDecimal(numA/numB));
                                    break;
                                case "×":
                                    displayLabel.setText(removeZeroDecimal(numA*numB));
                                    break;
                                case "-":
                                    displayLabel.setText(removeZeroDecimal(numA-numB));
                                    break;
                                case "+":   
                                    displayLabel.setText(removeZeroDecimal(numA+numB));
                                    break;
                                default:
                                    displayLabel.setText("Error");
                                    break;
                                }
                            }
                            if (!displayLabel.getText().equals("Error")) {
                                A = displayLabel.getText();
                            } else {
                                A = "0"; 
                            }
                            operator = null; 
                            B = null;
                        }
                        else if ("÷×-+".contains(buttonText)){
                            if (operator == null){
                                A = displayLabel.getText();
                                displayLabel.setText("0");
                                B = "0";
                            }
                            operator = buttonValue;
                        }
                    }
                    else {
                        if (buttonText.equals(".")){
                            if (!displayLabel.getText().contains(".")){
                                displayLabel.setText(displayLabel.getText() + buttonText);
                            }
                            else {
                                // do nothing
                            }
                        }
                        else if ("0123456789".contains(buttonText)){
                            if (displayLabel.getText().equals("0")){
                                displayLabel.setText(buttonText);
                            }
                            else {
                                displayLabel.setText(displayLabel.getText() + buttonText);
                            }
                        }
                    }
                };   
            });
        };    
        frame.add(buttonPanel);
        frame.setVisible(true);
        


    };
    void clearAll(){
        A = "0";
        B = null;
        operator = null;
    };
    String removeZeroDecimal(double value){
            if (value % 1 == 0){
                return Integer.toString((int)value);
            }
            return Double.toString(value);

    };
    public static void main(String[] args) {
        new Calculator();
    };
};  