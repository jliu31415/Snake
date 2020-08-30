package snake;

import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;

public class Snake extends JPanel{
    public static final int WIDTH = Board.WIDTH;    //inherited from Board class
    public static final int HEIGHT = Board.HEIGHT;
    public static final int SIZE = 10;              //size of snake and apple; must be a factor of WIDTH & HEIGHT
    public static final int INITLENGTH = 5;         //must be less than WIDTH * HEIGHT / Math.pow(SIZE, 2)
    
    private static ArrayList<double[]> body;
    private static double[] head;
    private static double[] apple;
    private static Color snakeColor;
    private static double aRange, bRange = SIZE/10;     //used in inRange for apple and body
    
    public Snake(Color c) {
        this.setBackground(Color.black);
        snakeColor = c;
        body = new ArrayList<double[]>();
        for(int i = 0; i < INITLENGTH; i++){
            body.add(new double[]{WIDTH/SIZE/2 * SIZE, HEIGHT/SIZE/2 * SIZE});
        }
        head = new double[]{body.get(INITLENGTH - 1)[0], body.get(INITLENGTH - 1)[1]};
        do{
            apple = new double[]{SIZE * (int)(WIDTH/SIZE * Math.random()), SIZE * (int)(HEIGHT/SIZE * Math.random())};
        }while(inRange(apple, head, aRange));
    }

    public void paintComponent(Graphics g){
        Graphics2D gg = (Graphics2D) g; //cast to 2D graphics to handle doubles

        //Enable anti-aliasing and pure stroke
        gg.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        gg.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
        super.paintComponent(gg);
        gg.setPaint(snakeColor);
        
        for(double[] b : body){
            gg.fill(new Ellipse2D.Double(b[0], b[1], SIZE, SIZE));
        }

        gg.setPaint(Color.red);
        gg.fill(new Ellipse2D.Double(apple[0], apple[1], SIZE, SIZE));
    }

    public boolean update(double[] direction){      //returns gameOver
        //update body
        double x = head[0] + direction[0] * SIZE;
        double y = head[1] + direction[1] * SIZE;
        x -= Math.floor(x/WIDTH) * WIDTH;     //floorMod function for doubles
        y -= Math.floor(y/HEIGHT) * HEIGHT;
        head[0] = x;
        head[1] = y;
        
        //check gameOver
        for(double[] b : body){
            if(inRange(head, b, bRange))
                return true;            
        }

        //check if apple is eaten
        if(inRange(apple, head, aRange)){
            boolean inBody;
            do{
                inBody = false;
                apple = new double[]{SIZE * (int)(WIDTH/SIZE * Math.random()), SIZE * (int)(HEIGHT/SIZE * Math.random())};
                for(double[] b : body){
                    if(inRange(apple, b, aRange)){
                        inBody = true;
                        break;
                    }        
                }
            }while(inBody);
        }else{
            body.remove(0);
        }

        double[] temp = new double[]{head[0], head[1]};   //prevents object reference issues
        body.add(temp);
        return false;
    }
    
    //checks if two points are within a certain distance
    public boolean inRange(double[] a, double[] b, double distance){
        double magnitude = Math.pow(a[0] - b[0], 2) + Math.pow(a[1] - b[1], 2);
        if(Math.pow(magnitude, 0.5) <= distance)
            return true;
        return false;
    }

    public void setRange(double a, double b){
        Snake.aRange = a;
        Snake.bRange = b;
    }

    //prints out body of snake for testing purposes; return size
    public int printBody(boolean print){
        if(print){
            for(double[] b : body){
                System.out.print(Arrays.toString(b) + " ");
            }
            System.out.println();
        }
        return body.size();
    }
}