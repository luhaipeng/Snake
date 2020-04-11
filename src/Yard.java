import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

//贪吃蛇界面
public class Yard extends Frame {

    public static final int ROWS = 70;
    public static final int COLS = 70;
    public static final int BLOCK_SIZE = 10;
    //初始蛇头
    public static Snake snake = new Snake(5, 5);
    //初始阿蛋
    public static Egg egg = new Egg(10, 10);

    //启动类
    public void launch() {
        this.setLocation(300, 100);
        this.setSize(COLS * BLOCK_SIZE, ROWS * BLOCK_SIZE);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        this.setVisible(true);
        //画画线程
        new Thread(new MyPaintThread()).start();
        //监听键
        this.addKeyListener(new MyKeyListener());
    }


    @Override
    public void paint(Graphics g) {
        Color c = g.getColor();
        //画游戏背景
        g.setColor(Color.gray);
        g.fillRect(0, 0, COLS * BLOCK_SIZE, ROWS * BLOCK_SIZE);
        g.setColor(Color.CYAN);
        //画竖线
        for (int i = 1; i < COLS; i++) {
            g.drawLine(i * BLOCK_SIZE, 0, i * BLOCK_SIZE, ROWS * BLOCK_SIZE);
        }
        // 画横线
        for (int i = 1; i < ROWS; i++) {
            g.drawLine(0, i * BLOCK_SIZE, COLS * BLOCK_SIZE, i * BLOCK_SIZE);
        }
        g.setColor(c);
        //画蛇
        snake.drawSnake(g);
        //画蛋
        egg.drawEgg(g);

    }

    public static void main(String[] args) {
        new Yard().launch();
    }

    private class MyPaintThread implements Runnable {

        @Override
        public void run() {
            while (true) {
                switch (snake.direction) {
                    case D:
                        //更新坐标
                        snake.updatePosition(egg, snake, "y++");
                        //吃蛋蛋
                        snake.eatEgg(snake, egg);
                        break;
                    case L:
                        //更新坐标
                        snake.updatePosition(egg, snake, "x--");
                        //吃蛋蛋
                        snake.eatEgg(snake, egg);
                        break;
                    case R:
                        //更新坐标
                        snake.updatePosition(egg, snake, "x++");
                        //吃蛋蛋
                        snake.eatEgg(snake, egg);
                        break;
                    case U:
                        //更新坐标
                        snake.updatePosition(egg, snake, "y--");
                        //吃蛋蛋
                        snake.eatEgg(snake, egg);
                        break;

                }
                //重新画
                repaint();
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private class MyKeyListener extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            snake.keyPressed(e);
        }
    }
}
