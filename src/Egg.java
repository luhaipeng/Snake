import java.awt.*;

/**
 * Created by lhp_mac on 2019/5/7.
 */
public class Egg {
    int x;
    int y;


    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Egg(int x,int y){
       this.x = x ;
       this.y = y ;
    }

    public void drawEgg(Graphics g) {
        Color color = g.getColor();
        g.setColor(Color.BLUE);
        g.fillRect(x * Yard.BLOCK_SIZE, y * Yard.BLOCK_SIZE,
                Yard.BLOCK_SIZE, Yard.BLOCK_SIZE);
        g.setColor(color);
    }
}
