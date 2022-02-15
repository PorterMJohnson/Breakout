import acm.graphics.GRect;
import java.awt.*;

public class Brick extends GRect{

    public static final int WIDTH = 44;

    public static final int HEIGHT = 20;

    private int brickHealth;

    public int brickPoints;

    public int getBrickHealth(){
        return brickHealth;
    }

    public void doOuchie(){
        this.brickHealth -= 1;
    }

    public Brick(double x, double y, Color color,int row){
        super(x,y, WIDTH, HEIGHT);
        this.setFilled(true);
        this.setFillColor(color);
        switch (row){
            case 0:
            case 1:
                brickHealth = 5;
                brickPoints = 600;
                break;

            case 2:
            case 3:
                brickHealth = 4;
                brickPoints = 475;
                break;
            case 4:
            case 5:
                brickHealth = 3;
                brickPoints = 350;
                break;
            case 6:
            case 7:
                brickHealth = 2;
                brickPoints = 225;
                break;
            case 8:
            case 9:
                brickHealth = 1;
                brickPoints = 100;
                break;
        }
    }



}