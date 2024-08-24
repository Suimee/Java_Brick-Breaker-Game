import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.Timer;
public class Gameplay extends JPanel implements KeyListener, ActionListener {
    boolean play = false;
    int score = 0;
    int totalBricks = 21;
    int playerX = 310;
    int ballposX = 120;
    int ballposY = 350;
    int ballXdir = -1;
    int ballYdir = -2;
    Timer timer;
    int delay = 8;
    MapGenerator map;
    public Gameplay() {//constructor
        map= new MapGenerator(3,7);
        addKeyListener(this);
        setFocusable(true);
        timer = new Timer(delay, this);
        timer.start();
    }

    public void paint(Graphics g) {//background
        g.setColor(Color.gray);
        g.fillRect(1,1,692,592);
        map.draw((Graphics2D)g);
        //borders
        g.setColor(Color.orange);
        g.fillRect(0, 0, 3, 592);
        g.fillRect(0, 0, 692, 3);
        g.fillRect(691, 0, 3, 592);
        //scores
        g.setColor(Color.black);
        g.setFont(new Font("serif",Font.BOLD,25));
        g.drawString("Score:"+score,590,30);
        //the paddle
        g.setColor(Color.orange);
        g.fillRect(playerX, 550, 100, 8);
        //the ball
        g.setColor(Color.magenta);
        g.fillOval(ballposX, ballposY, 20, 20);
        if(ballposY>570){
            play=false;
            ballXdir=0;
            ballYdir=0;
            g.setColor(Color.black);
            g.setFont(new Font("serif",Font.BOLD,30));
            g.drawString("Game Over!!!",190,300);
        }
    }
    public void actionPerformed(ActionEvent e) {
        timer.start();
        if(play){
            if(new Rectangle(ballposX,ballposY,20,20).intersects(new Rectangle(playerX,550,100,8))){
                ballYdir= -ballYdir;
            }
           for(int i=0;i<map.map.length;i++) {
               for(int j=0;j<map.map[0].length;j++){
                   if(map.map[i][j]>0){
                       int brickX = j*map.brickWidth+80;
                       int brickY= i* map.brickHeight+50;
                       int brickWidth = map.brickWidth;
                       int brickHeight= map.brickHeight;
                       Rectangle rect = new Rectangle(brickX,brickY,brickWidth,brickHeight);
                       Rectangle ballRect = new Rectangle(ballposX,ballposY,20,20);
                       Rectangle brickRect= rect;
                       if(ballRect.intersects(brickRect)){
                           map.setBrickValue(0,i,j);
                           totalBricks--;
                           score +=5;
                           if(ballposX + 19<= brickRect.x || ballposX +1 >= brickRect.x +brickRect.width){
                              ballXdir= -ballXdir;
                           }
                           else{
                               ballYdir= - ballYdir;
                           }
                           break;
                       }
                   }
               }
           }
          ballposX += ballXdir;
          ballposY+= ballYdir;
          if(ballposX<0){
              ballXdir= -ballXdir;
          }
          if(ballposY<0){
              ballYdir= -ballYdir;
          }
          if(ballposX>670){
              ballXdir= -ballXdir;
          }
        }
        repaint();
    }

    public void keyTyped(KeyEvent e) {
    }

    public void keyReleased(KeyEvent e) {
    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            if (playerX <=600) {
                playerX = 600;
            } else {
                moveRight();
            }
        }
            if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                if (playerX < 10) {
                    playerX = 10;
                } else {
                    moveLeft();
                }
            }

    }
        public void moveRight(){
            play= true;
            playerX +=20;
        }
        public void moveLeft(){
        play= true;
        playerX -=20;
        }

}


