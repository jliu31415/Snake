package snake;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

public class Board{
    public static final int WIDTH = 500;            //window size = width + 6, height + 30
    public static final int HEIGHT = 500;
    private static final int REFRESH = 50;          //millis per loop
    private static double[] direction;
    private static double[] tempDirection;          //prevents backtracking
    private static double gameTime;                 //tracks millis since start of game
    private static boolean gameOver;
    
    public static void main(String[] args){
        JFrame frame = new JFrame();
        initialize(frame);   //initialize frame
        MyListener listener = new MyListener();
        frame.addKeyListener(listener);
        
        while(true){
            Snake snake = new Snake(new Color((float)Math.random(), (float)Math.random(), (float)Math.random()));
            frame.add(snake);
            frame.setVisible(true);
            tempDirection = new double[]{1, 0};    //snake starts by going right
            gameOver = false;
            gameTime = System.currentTimeMillis();
            
            while(!gameOver){
                if(System.currentTimeMillis()>gameTime){
                    direction = tempDirection;
                    gameOver = snake.update(direction);            
                    snake.repaint();
                    gameTime += REFRESH;
                    //snake.printBody(true);
                }
            }
            
            System.out.println(snake.printBody(false));
        }
    }

    public static void initialize(JFrame frame){
        frame.setTitle("Snake Game");
        frame.setSize(WIDTH + 6, HEIGHT + 30);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static class MyListener implements KeyListener{
        public void keyPressed(KeyEvent e){
            switch(e.getKeyCode()) {
                case 37:    //left
                    if(direction[0] != 1)
                        tempDirection = new double[]{-1, 0};
                    break;
                case 38:    //up
                    if(direction[1] != 1)   
                        tempDirection = new double[]{0, -1};
                    break;
                case 39:    //right
                    if(direction[0] != -1)
                        tempDirection = new double[]{1, 0};
                    break;
                case 40:    //down
                    if(direction[1] != -1)
                        tempDirection = new double[]{0, 1};
                    break;
                case 27:    //escape key
                    System.exit(0);
                    break;
            }
        }
        public void keyReleased(KeyEvent e){}
        public void keyTyped(KeyEvent e){}
    }
}
