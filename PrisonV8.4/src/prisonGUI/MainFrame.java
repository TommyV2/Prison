package prisonGUI;

import java.awt.Point;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class MainFrame extends JFrame {

    private static final int X_POS = 50;
    private static final int Y_POS = 50;

    public MainFrame(String title) {
        super(title);
        setLocation(new Point(X_POS, Y_POS));
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

}
