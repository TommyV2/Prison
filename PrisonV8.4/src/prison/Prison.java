package prison;

import prisonGUI.MainFrame;

import prisonGUI.PrisonGeneratorPanel;

public class Prison {

    public static void main(String[] args) {

        MainFrame frame = new MainFrame("Prison");

        frame.add(new PrisonGeneratorPanel(100000, 1000000, 20, 80, 80, 2.9));
        frame.pack();
        frame.setVisible(true);

    }

}
