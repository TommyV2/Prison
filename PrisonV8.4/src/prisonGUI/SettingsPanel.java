package prisonGUI;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class SettingsPanel extends JPanel {

    private final int WIDTH = 1000;
    private final int HEIGHT = 700;
    private final int btnWidth = 100;
    private final int btnHeight = 30;
    private int popMax;
    private int budget;
    private int popSize;
    private double crossingRate;
    private double mutationRate;
    private double acceptedQuality;

    private JTextField budgetTextField;
    private JTextField crossingRateTextField;
    private JTextField mutationRateTextField;
    private JTextField popSizeTextField;
    private JLabel budgetLabel;
    private JLabel crossingRateLabel;
    private JLabel mutationRateLabel;
    private JLabel popSizeLabel;

    public SettingsPanel() {
        setFocusable(true);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setLayout(null);

        popMax = 100000;
        budget = 1000000;
        popSize = 1000;
        crossingRate = 80;
        mutationRate = 2;
        acceptedQuality = 2.9;

        budgetLabel = new JLabel("Budget: ");
        budgetLabel.setBounds(50, 100, 200, 30);
        add(budgetLabel);
        budgetTextField = new JTextField("1000000");
        budgetTextField.setBounds(270, 100, 200, 25);
        add(budgetTextField);
        createButton("Confirm", 500, 100, 100, 25, event -> {
            String text = budgetTextField.getText();
            budgetTextField.setText(text);
            int b = -1;
            try{
            b = Integer.parseInt(text);}
            catch(NumberFormatException ex){
                throw new NumberFormatException("Wrong number!");
                        
            }
            if (Integer.parseInt(text) > 1000000 || Integer.parseInt(text) < 0) {
                budget = 1000000;
            } else {
                budget = Integer.parseInt(text);
            }

            System.out.println(budget);
        });

        crossingRateLabel = new JLabel("Szansa na krzyzowanie: (0 - 100)");
        crossingRateLabel.setBounds(50, 150, 200, 30);
        add(crossingRateLabel);
        crossingRateTextField = new JTextField("80");
        crossingRateTextField.setBounds(270, 150, 200, 25);
        add(crossingRateTextField);
        createButton("Confirm", 500, 150, 100, 25, event -> {
            String text = crossingRateTextField.getText();
            crossingRateTextField.setText(text);
            double cR = -1;
            try{
            cR = Double.parseDouble(text);}
            catch(NumberFormatException ex){
                throw new NumberFormatException("Wrong number!");
                        
            }
            if (cR > 100 || cR < 0) {
                crossingRate = 80;
            } else {
                crossingRate = cR;
            }

        });

        mutationRateLabel = new JLabel("Szansa na mutacje (0 - 100)");
        mutationRateLabel.setBounds(50, 200, 200, 30);
        add(mutationRateLabel);
        mutationRateTextField = new JTextField("2");
        mutationRateTextField.setBounds(270, 200, 200, 25);
        add(mutationRateTextField);
        createButton("Confirm", 500, 200, 100, 25, event -> {
            String text = mutationRateTextField.getText();
            mutationRateTextField.setText(text);
            double mR = -1;
            try{
            mR = Double.parseDouble(text);}
            catch(NumberFormatException ex){
                throw new NumberFormatException("Wrong number!");
                        
            }
            if (Double.parseDouble(text) > 100 || Double.parseDouble(text) < 0) {
                mutationRate = 2;
            } else {
                mutationRate = Double.parseDouble(text);
            }

        });

        createButton("Back", WIDTH - btnWidth, 0, btnWidth, btnHeight, event -> {
            setVisible(false);
            displayPrisonGeneratorPanel();
        });

        //popSize
        popSizeLabel = new JLabel("Liczba osobnikow w populacji");
        popSizeLabel.setBounds(50, 250, 200, 30);
        add(popSizeLabel);
        popSizeTextField = new JTextField("1000");
        popSizeTextField.setBounds(270, 250, 200, 25);
        add(popSizeTextField);
        createButton("Confirm", 500, 250, 100, 25, event -> {
            String text = popSizeTextField.getText();
            popSizeTextField.setText(text);
            int pS = -1;
            try{
            pS = Integer.parseInt(text);}
            catch(NumberFormatException ex){
                throw new NumberFormatException("Wrong number!");
                        
            }
            if (Integer.parseInt(text) > 10000 || Integer.parseInt(text) < 5) {
                popSize = 1000;
            } else {
                popSize = Integer.parseInt(text);
            }

        });

        createButton("Back", WIDTH - btnWidth, 0, btnWidth, btnHeight, event -> {
            setVisible(false);
            displayPrisonGeneratorPanel();
        });
    }

    private void createButton(String title, int x, int y, int width, int height, ActionListener listener) {
        JButton button = new JButton(title);
        button.setBounds(x, y, width, height);
        button.addActionListener(listener);
        add(button);
    }

    public void displayPrisonGeneratorPanel() {

        setVisible(false);
        EventQueue.invokeLater(()
                -> {
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
            PrisonGeneratorPanel prisonPanel = new PrisonGeneratorPanel(popMax, budget, popSize, crossingRate, mutationRate, acceptedQuality);
            frame.add(prisonPanel);
            prisonPanel.requestFocus();
            frame.pack();
        });

    }

}
