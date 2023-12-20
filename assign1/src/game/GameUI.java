package game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;


public class GameUI {
    private static JLabel colorLabel;
    private static JLabel retryCount;
    private static List<Colors> pickedColors = new ArrayList<>();
    private static List<Colors> selectedColors;
    private static int attemptCount = 0;
    private static JFrame frame;
    private static JPanel buttonPanel;
    private static JPanel mainPanel;

    public static void main(String[] args) {
        selectedColors = MasterMind.generateRandomizedColors();
        frame = new JFrame("Button Grid Example");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.add(mainPanel= generateScreenLayout(), BorderLayout.NORTH);
        frame.add(buttonPanel = generateButtonLayout(), BorderLayout.CENTER);
        frame.setVisible(true);
    }

    private static JPanel generateScreenLayout() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        colorLabel = new JLabel();
        colorLabel.setPreferredSize(new Dimension(600, 50)); // 60% of the width
        colorLabel.setHorizontalAlignment(JLabel.LEFT);
        colorLabel.setFont(new Font("Arial", Font.BOLD, 16));
        colorLabel.setText("Picked Colors : ");

        retryCount = new JLabel();
        retryCount.setPreferredSize(new Dimension(200, 50)); // 40% of the width
        retryCount.setHorizontalAlignment(JLabel.RIGHT);
        retryCount.setFont(new Font("Arial", Font.BOLD, 16));
        retryCount.setText("Attempt Count : " + attemptCount);

        mainPanel.add(colorLabel, BorderLayout.WEST);
        mainPanel.add(retryCount, BorderLayout.EAST);
        return mainPanel;
    }

    private static JPanel generateButtonLayout() {
        JPanel buttonPanel = new JPanel(new GridLayout(4, 3, 10, 10)); // 4 rows, 3 columns, with spacing


        ArrayList<Colors> colors = new ArrayList<>(List.of(Colors.values()));

        for (int i = 0; i < colors.size() - 2; i++) {
            JButton button = new JButton(String.valueOf(colors.get(i)));
            button.setBackground(Color.decode(colors.get(i).getHexCode()));
            button.addActionListener(new ColorButtonActionListener(colors.get(i)));
            buttonPanel.add(button);
        }
        return buttonPanel;
    }

    private static void showCorrectAnswers(){
        frame.remove(buttonPanel);
        frame.remove(mainPanel);
        JPanel mainPanel = new JPanel(new BorderLayout());
        colorLabel = new JLabel();
        colorLabel.setPreferredSize(new Dimension(600, 50)); // 60% of the width
        colorLabel.setHorizontalAlignment(JLabel.LEFT);
        colorLabel.setFont(new Font("Arial", Font.BOLD, 16));
        colorLabel.setText("The System Generated Colors are : ");

        JPanel buttonPanel = new JPanel(new GridLayout(4, 3, 10, 10)); // 4 rows, 3 columns, with spacing

        for (int i = 0; i < selectedColors.size(); i++) {
            JButton button = new JButton(String.valueOf(selectedColors.get(i)));
            button.setBackground(Color.decode(selectedColors.get(i).getHexCode()));
            button.addActionListener(new ColorButtonActionListener(selectedColors.get(i)));
            button.setEnabled(false);
            buttonPanel.add(button);
        }
        mainPanel.add(colorLabel, BorderLayout.WEST);
        frame.add(mainPanel , BorderLayout.NORTH);
        frame.add(buttonPanel , BorderLayout.CENTER);
        frame.revalidate();

    }
    private static void  refreshScreen(){
        pickedColors.clear();
        frame.remove(buttonPanel);
        frame.add(buttonPanel = generateButtonLayout(), BorderLayout.CENTER);
        retryCount.setText("Attempt Count : " + attemptCount);
        colorLabel.setText("Picked Colors :");
    }

    private static void showStatus(Status status, List<Match> matches) {
        frame.remove(buttonPanel);
        buttonPanel = new JPanel(new GridLayout(6, 1, 10, 10)); // 4 rows, 3 columns, with spacing

        addTextLayout();
        addMatchButtons(matches);
        addActionPanel();

        frame.add(buttonPanel, BorderLayout.CENTER);
    }

    private static void addTextLayout() {
        JPanel textLayout = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JTextArea textBox = new JTextArea("Matching with respective guesses : ");
        textBox.setEditable(false);
        textBox.setBackground(frame.getBackground());
        textBox.setFont(new Font("Arial", Font.BOLD, 16));
        textLayout.add(textBox);
        buttonPanel.add(textLayout, BorderLayout.CENTER);
    }

    private static void addMatchButtons(List<Match> matches) {
        for (Match match : matches) {
            JButton button = new JButton(String.valueOf(match));
            setButtonStyle(button, match);
            button.setEnabled(false);
            buttonPanel.add(button);
        }
    }

    private static void setButtonStyle(JButton button, Match match) {
        if (match == Match.EXACT) {
            button.setBackground(Color.decode(Colors.BLACK.getHexCode()));
            button.setForeground(Color.decode(Colors.WHITE.getHexCode()));
        } else if (match == Match.PARTIAL) {
            button.setBackground(Color.decode(Colors.SILVER.getHexCode()));
            button.setForeground(Color.decode(Colors.WHITE.getHexCode()));
        }
    }

    private static void addActionPanel() {
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        if (attemptCount < 20) {
            JButton retryButton = new JButton("Retry");
            JButton giveUpButton = new JButton("Give Up");

            retryButton.addActionListener(e -> refreshScreen());
            giveUpButton.addActionListener(e -> showCorrectAnswers());

            actionPanel.add(retryButton);
            actionPanel.add(giveUpButton);
        } else {
            JButton answersButton = new JButton("View Correct Colors");
            answersButton.addActionListener(e -> showCorrectAnswers());
            actionPanel.add(answersButton);
        }

        buttonPanel.add(actionPanel, BorderLayout.SOUTH);
    }
    private static class ColorButtonActionListener implements ActionListener {
        private final Colors color;

        ColorButtonActionListener(Colors color) {
            this.color = color;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            pickedColors.add(color);
            if (pickedColors.size() < 6) {
                // Set the label text to the picked color
                colorLabel.setText(colorLabel.getText() + color + ", ");
                JButton sourceButton = (JButton) e.getSource();
                sourceButton.setEnabled(false);

            }

            else if (pickedColors.size() == 6) {
                colorLabel.setText(colorLabel.getText() + color);
                JButton sourceButton = (JButton) e.getSource();
                sourceButton.setEnabled(false);
                Result status = MasterMind.playGame(selectedColors, pickedColors, attemptCount++);
                showStatus(status.getStatus(), status.getMatches());

            }
        }
    }
}
