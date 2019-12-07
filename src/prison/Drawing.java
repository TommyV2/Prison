package prison;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;

public class Drawing extends JPanel{
 
    
    public void paintComponent(Graphics g, int x, int y) {
        super.paintComponent(g);        
        g.setColor(Color.BLACK);
        g.drawRect(x, y, 10, 20);
        g.setColor(Color.RED);
        g.drawRect(x+1, y+1, 8, 18);
    }
    
    
    
}
