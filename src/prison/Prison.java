package prison;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.List;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;



public class Prison extends JPanel {
    
    JFrame frame;   
    Graphics g;
    int cellSize=80; //cela to kwadrat
    int start=20; //start rysowania x=start, y=start
    int elementX;
    int elementY;
    int bx1,bx2,by1,by2;
    Random r = new Random();
    Color camColor = new Color(255,204,204);
    Color cellColor = new Color(178,255,102);
    Color doorColor = new Color(153,76,0);
    Color colorDefault = UIManager.getColor ( "Panel.background" );
    
    ArrayList<Rectangle> beds = new ArrayList<>();
    
    enum Corner  
    {
        topL,
        topR,
        botL,
        botR
    }
    
    public class Rectangle {
        private Point bottomLeft;
        private Point topRight;
          
        boolean isOverlapping(Rectangle other) {
            if (this.topRight.y < other.bottomLeft.y 
        || this.bottomLeft.y > other.topRight.y) {
            return false;
        }
        if (this.topRight.x < other.bottomLeft.x 
        || this.bottomLeft.x > other.topRight.x) {
            return false;
        }
        return true;
        }
    }
    
    public class Point {
        public int x;
        public int y;
        
    }
    
    public Prison()
    {
        frame = new JFrame();
    }
    
    public void launchFrame()
    {
               
        frame.setSize(600,800);
        frame.setTitle("Prison");
        frame.setResizable(false); 
        frame.setBackground(Color.WHITE);  
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);                
        JPanel optionsPanel = new JPanel();
        JPanel drawPanel = new JPanel();        
        optionsPanel.setBackground(Color.gray.brighter());        
        frame.setLayout(new GridLayout(2, 0));
        frame.add(new Prison());        
        frame.add(optionsPanel);
        drawPanel.repaint();
        frame.setVisible(true);
    }
    @Override
    public void paintComponent(Graphics g) {
        
        super.paintComponent(g); 
        Graphics2D g2d = (Graphics2D) g;
        
        
        paintBedVertical(g2d, 50, 100);
        paintBedHorizontal(g2d, 100, 100);
        
        paintCell(g2d,350,100);     
       // paintWalls(g2d,Corner.topL,300,300,200,200); 
             
        for(int i=0;i<20;i++)
        {
          placeForBed(300, 300);
          paintCamera(g2d, elementX, elementY);  
         // paintBedVertical( g2d, elementX, elementY);
        
        }
        paintWalls(g2d,Corner.botR,300,300,100,100); 
    }
    
    static boolean insideRect(int x1, int y1, int x2, int y2, int x, int y) 
    { 
        if (x > x1 && x < x2 &&  y > y1 && y < y2) return true; 
        else
        return false;        
    } 
    
    public void placeForBed(int x1, int h1)
    {
        elementX=start+r.nextInt(x1);
        elementY=start+r.nextInt(h1);
        
        while(insideRect(bx1,by1,bx2,by2,elementX,elementY)){
        elementX=start+r.nextInt(x1);
        elementY=start+r.nextInt(h1);
        }
        
    }
    
    public void paintBedHorizontal(Graphics g2d, int x, int y) 
    {
        g2d.setColor(Color.BLACK);
        g2d.drawRect(x, y, 20, 10);
        g2d.setColor(Color.RED);
        g2d.fillRect(x+1, y+1, 15, 9);                                          
               
    }
    public void paintBedVertical(Graphics g2d, int x, int y) 
    {
        g2d.setColor(Color.BLACK);
        g2d.drawRect(x, y, 10, 20);
        g2d.setColor(Color.RED);
        g2d.fillRect(x+1, y+5, 9, 15);                                          
               
    }
    public void paintCamera(Graphics g2d, int x, int y) 
    {
        
        g2d.setColor(camColor); 
        g2d.fillOval(x-45, y-45, 100, 100);  
       // g2d.drawOval(x-45, y-45, 100, 100);  
        g2d.setColor(Color.RED);
        g2d.fillOval(x+1, y+1, 10, 10);
        g2d.setColor(Color.BLACK);           
        g2d.drawOval(x+1, y+1, 10, 10);                
    }
    
    public void paintCell(Graphics g2d, int x, int y) 
    {
        Graphics2D pom = (Graphics2D) g2d;        
        
        
        g2d.setColor(cellColor); 
        g2d.fillRect(x, y, cellSize, cellSize);
        g2d.setColor(Color.BLACK); 
        g2d.drawRect(x+cellSize-11, y+cellSize-11, 10, 10);
        g2d.setColor(Color.CYAN); 
        g2d.fillRect(x+cellSize-10, y+cellSize-10, 10, 10);
        pom.setStroke(new BasicStroke(2)); 
        pom.setColor(Color.BLACK); 
        pom.drawRect(x, y, cellSize, cellSize);       
        pom.setColor(doorColor); 
        pom.setStroke(new BasicStroke(7));
        pom.drawLine(x+cellSize/2-5, y, x+cellSize/2-5+10, y);  
        pom.setStroke(new BasicStroke(1));
    }
    
    public void paintWalls(Graphics g2d,Corner cutP, int x1, int h1, int x2, int h2)
    {
        Graphics2D pom = (Graphics2D) g2d;
        pom.setStroke(new BasicStroke(5));       
        pom.setColor(Color.BLACK); 
        pom.drawRect(start, start, x1, h1);
        pom.setColor(colorDefault); 
        
        
        if(cutP==Corner.topR){
            pom.drawRect(start+x1-x2, start, x2, h2);
            pom.setColor(Color.BLACK); 
            pom.drawLine(start+x1-x2, start, start+x1-x2, start+h2);
            pom.drawLine(start+x1-x2, start+h2, start+x1, start+h2);
            
        }
        if(cutP==Corner.topL){
            pom.drawRect(start, start, x2, h2);
            pom.setColor(Color.BLACK);
            pom.drawLine(start+x2, start, start+x2, start+h2);
            pom.drawLine(start, start+h2, start+x2, start+h2);
            bx1=start; by1=start; bx2=start+x2; by2=start+h2;
                       
        }
        if(cutP==Corner.botR){
            pom.drawRect(start+x1-x2, start+h1-h2, x2, h2);
            pom.setColor(Color.BLACK);
            pom.drawLine(start+x1-x2, start+h1-h2, start+x1, start+h1-h2);
            pom.drawLine(start+x1-x2, start+h1-h2, start+x1-x2, start+h1);
        }
        if(cutP==Corner.botL){
            pom.drawRect(start, start+h1-h2, x2, h2);
            pom.setColor(Color.BLACK);
            pom.drawLine(start, start+h1-h2, start+x2, start+h1-h2);
            pom.drawLine(start+x2, start+h1-h2, start+x2, start+h1);
        }
        pom.setStroke(new BasicStroke(1));   
    }
     
    
    public static void main(String[] args) {
   
      Prison p1 = new Prison();
      p1.launchFrame();
      
      
    }
    
}
