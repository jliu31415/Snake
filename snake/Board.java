package snake;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.awt.event.KeyEvent;

public class Board{
    public static final int WIDTH = 500;
    public static final int HEIGHT = 500;
    private static final int SLEEPTIME = 100;
    private static Point direction;
    private static ArrayList<Point> directionQueue;
    private static boolean gameOver;
    
    public static void main(String[] args){
        JFrame frame = new JFrame();
        MyListener listener = new MyListener();
        frame.addKeyListener(listener);
        Snake snake = new Snake();
        frame.add(snake);
        initialize(frame);
        
        while(true){
            snake.reset(new Color((float) Math.random(), (float) Math.random(), (float) Math.random()));
            gameOver = false;
            direction = new Point(1, 0);    //snake starts off to the right
            directionQueue = new ArrayList<Point>();
            directionQueue.add(new Point(direction));
            
            while(!gameOver){
                try {
                    Thread.sleep(SLEEPTIME);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                
                gameOver = directionQueue.size() > 0 ? snake.update(directionQueue.remove(0)) : snake.update(direction);
                snake.repaint();
                //snake.printBody(true);
            }
            
            System.out.println(snake.printBody(false)); //print score
        }
    }

    public static void initialize(JFrame frame){
        frame.setTitle("Snake");
        frame.getContentPane().setPreferredSize(new Dimension(WIDTH, HEIGHT));
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.pack();
    }

    public static class MyListener implements KeyListener{
        public void keyPressed(KeyEvent e){
            switch(e.getKeyCode()) {
                case 37:    //left
                    if(direction.x != 1)
                        direction.setLocation(-1, 0);
                    break;
                case 38:    //up
                    if(direction.y != 1)
                        direction.setLocation(0, -1);
                    break;
                case 39:    //right
                    if(direction.x != -1)
                        direction.setLocation(1, 0);
                    break;
                case 40:    //down
                    if(direction.y != -1)
                        direction.setLocation(0, 1);
                    break;
                case 27:    //escape key
                    System.exit(0);
                    break;
            }
            
            directionQueue.add(new Point(direction));
        }

        public void keyReleased(KeyEvent e){}

        public void keyTyped(KeyEvent e){}
    }
}
