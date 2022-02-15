import acm.graphics.GLabel;
import acm.graphics.GObject;
import acm.program.GraphicsProgram;
import svu.csc213.Dialog;

import java.awt.*;
import java.awt.event.MouseEvent;

public class Breakout extends GraphicsProgram {

    /*

    1) What do I do about lives.
    // done
    2) All the bricks the take the same number of hits
    //done
    3) How do I know how many lives I have left
    //done
    4) How do I know how many points I have
    //done
    5) What happens if I run out of lives completely
    //done
    6) How can I tell that a brick has been hit?

    7) Power-ups in some bricks
    8) Multiple levels
    9) An animation for a broken brick?
     */

    private Ball ball;
    private Paddle paddle;
    private Brick brick;

    private int points = 0;
    private int numBricksInRow;
    private int lives = 3;
    private GLabel showLives;
    private GLabel showPoints;
    private int signal;
    private int rowNumber;

    private Color[] rowColors = {new Color(255,255,255), Color.GRAY, Color.BLACK, new Color(50,0,102), new Color(238,130,238), Color.BLUE, Color.GREEN, Color.YELLOW,new Color(255,165,0), Color.RED};

    @Override
    public void init(){

        labels();

        bricks();

        objects();

    }

    @Override
    public void run(){
        addMouseListeners();
        waitForClick();
        gameLoop();
    }

    @Override
    public void mouseMoved(MouseEvent me){
        // make sure that the paddle doesn't go offscreen
        if((me.getX() < getWidth() - paddle.getWidth()/2)&&(me.getX() > paddle.getWidth() / 2)){
            paddle.setLocation(me.getX() - paddle.getWidth()/2, paddle.getY());
        }
    }

    private void livesLost(){
        if(lives == 0){

            Dialog.showMessage("You lost!");

            points = 0;

            lives += 3;

            signal += 1;

            restart();

        }
    }

    public void labels(){

        //Shows how many lives are left
        showLives = new GLabel("Lives Remaining: " + lives);
        add(showLives,getWidth()/2 - showLives.getWidth()/2,15);

        //Shows current number of points
        showPoints = new GLabel("Points: " + points);

        add(showPoints,getWidth()*.8-showPoints.getWidth()/2, 15);
    }

    public void bricks(){

        numBricksInRow = getWidth() / (Brick.WIDTH + 5);

        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < numBricksInRow; col++) {

                double brickX = 10 + col * (Brick.WIDTH + 5);
                double brickY = Brick.HEIGHT + row * (Brick.HEIGHT + 5);

                Brick brick = new Brick(brickX,brickY,rowColors[row], row);
                add(brick);

            }
        }

    }

    public void objects(){

        ball = new Ball(getWidth()/2, 350, 10, this.getGCanvas());
        add(ball);

        paddle = new Paddle(230, 430, 50 ,10);
        add(paddle);

    }

    private void gameLoop(){
        while(true){
            // move the ball
            ball.handleMove();

            // handle collisions
            handleCollisions();

            // handle losing the ball
            if(ball.lost){
                handleLoss();
            }

            pause(5);
        }
    }

    private void handleCollisions(){
        // obj can store what we hit
        GObject obj = null;

        // check to see if the ball is about to hit something

        if(obj == null){
            // check the top right corner
            obj = this.getElementAt(ball.getX()+ball.getWidth(), ball.getY());
        }

        if(obj == null){
            // check the top left corner
            obj = this.getElementAt(ball.getX(), ball.getY());
        }

        if(obj == null){
            // check the bottom right corner
            obj = this.getElementAt(ball.getX() + ball.getWidth(), ball.getY()+ball.getHeight());
        }

        if(obj == null){
            // check the bottom left corner
            obj = this.getElementAt(ball.getX(), ball.getY() + ball.getHeight());
        }

            // if we hit something...
        if(obj != null){

            //what did I hit?
            if(obj instanceof Paddle){

              if(ball.getX() <= (paddle.getX() + (paddle.getWidth()*.2))){
                  //hit left side of the paddle
                  ball.bounceLeft();
              } else if(ball.getX() >= (paddle.getWidth() + (paddle.getWidth()*.8))){
                  //hit right of the paddle
                  ball.bounceRight();
              } else {
                  ball.bounce();
              }

            }

            if(obj instanceof Brick){
                //ball bounces
                ball.bounce();
                //destroy brick
                ((Brick) obj).doOuchie();
                ((Brick) obj).setFillColor(((Brick) obj).getFillColor().darker());
                if(((Brick) obj).getBrickHealth() <= 0){
                    this.remove(obj);
                    points += ((Brick) obj).brickPoints;
                }

                showPoints.setLabel("Points: " + points);

            }

        }

        // if by the end of the method obj is still null, we hit nothing
    }

    private void handleLoss(){
        ball.lost = false;
        lives-=1;
        showLives.setLabel("Lives Remaining: " + lives);
        livesLost();
        reset();
    }

    private void reset(){
        ball.setLocation(getWidth()/2, 350);
        paddle.setLocation(230, 430);
        waitForClick();
    }

    private void restart(){
        removeAll();
        labels();
        bricks();
        objects();
    }

    //fuck joe biden. 8===D

    public static void main(String[] args) {
        new Breakout().start();
    }

}