package snake;

import java.util.*;
import javax.swing.*;
import java.awt.*;

@SuppressWarnings("serial")
public class Snake extends JPanel{
    public final int WIDTH = Board.WIDTH;
    public final int HEIGHT = Board.HEIGHT;
    public final int SIZE = 10;
    public final int INITLENGTH = 5;
    
    private ArrayList<Point> body;
    private Point head;
    private Point apple;
    private Color color;
    
    public Snake() {
        this.setBackground(Color.black);
        body = new ArrayList<Point>();
    }

    public void paintComponent(Graphics g){
        Graphics2D gg = (Graphics2D) g;

        //Enable anti-aliasing and pure stroke
        gg.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        gg.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
        super.paintComponent(gg);
        gg.setPaint(color);
        
        for(Point b : body){
            gg.fillOval(b.x, b.y, SIZE, SIZE);
        }

        gg.setPaint(Color.red);
        gg.fillOval(apple.x, apple.y, SIZE, SIZE);
    }

    public boolean update(Point direction){      //returns gameOver
        //update body
        head.translate(SIZE * direction.x, SIZE * direction.y);
        head.setLocation((head.x + WIDTH) % WIDTH, (head.y + HEIGHT) % HEIGHT); //wrap around screen
        
        //check gameOver
        for(Point b : body){
            if(b.equals(head))
                return true;          
        }

        //check if apple is eaten
        if(apple.equals(head)){
            boolean inBody;
            do{
                inBody = false;
                apple.setLocation(SIZE * (int)(WIDTH/SIZE * Math.random()), SIZE * (int)(HEIGHT/SIZE * Math.random()));
                for(Point b : body){
                    if(apple.equals(b)){
                        inBody = true;
                        break;
                    }        
                }
            }while(inBody);
        }else{
            body.remove(0);
        }

        body.add(new Point(head));
        return false;
    }
    
    public void reset(Color c) {
        body.clear();
        for(int i = 0; i < INITLENGTH; i++){
            body.add(new Point(WIDTH/SIZE/2 * SIZE, HEIGHT/SIZE/2 * SIZE));
        }
        head = new Point(WIDTH/SIZE/2 * SIZE, HEIGHT/SIZE/2 * SIZE);
        do{
            apple = new Point(SIZE * (int)(WIDTH/SIZE * Math.random()), SIZE * (int)(HEIGHT/SIZE * Math.random()));
        }while(apple.equals(head));

        color = c;
    }

    public int printBody(boolean print){
        if(print){
            for(Point b : body){
                System.out.print(b.getX() + ", " + b.getY() + " | ");
            }
            System.out.println();
        }
        return body.size();
    }
}