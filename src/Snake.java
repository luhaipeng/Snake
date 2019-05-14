import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Random;

/**
 * Created by lhp_mac on 2019/5/6.
 */
public class Snake {
    //方向 默认为右边
    public Direction direction = Direction.R;

    public Node head = null;
    public Node tail = null;

    /**
     * 初始化 蛇头位置为x，y
     *
     * @param x
     * @param y
     */
    public Snake(int x, int y) {
        Node node = new Node(x, y);
        node.next = null;
        head = node;
        tail = node;
    }

    //画蛇
    public void drawSnake(Graphics g) {
        Color color = g.getColor();
        //头结点
        g.setColor(Color.red);
        g.fillRect(head.x * Yard.BLOCK_SIZE, head.y * Yard.BLOCK_SIZE,
                Yard.BLOCK_SIZE, Yard.BLOCK_SIZE);
        g.setColor(color);

        Node p = head.next;
        while (p != null) {
            p.drawNode(g);
            p = p.next;
        }
    }

    /**
     * 监听上下左右键
     */
    public void keyPressed(KeyEvent keyEvent) {
        int keyCode = keyEvent.getKeyCode();
        Snake snake = Yard.snake;
        Egg egg = Yard.egg;
        switch (keyCode) {
            //左键
            case KeyEvent.VK_LEFT:
                //相反方向 不操作
                if (snake.direction == Direction.R) break;
                snake.direction = Direction.L;

                break;
            //右键
            case KeyEvent.VK_RIGHT:
                //相反方向 不操作
                if (snake.direction == Direction.L) break;
                snake.direction = Direction.R;
                updatePosition(egg, snake, "x++");
                //是否吃到蛋蛋
                eatEgg(snake, egg);

                break;
            //下键
            case KeyEvent.VK_DOWN:
                //相反方向 不操作
                if (snake.direction == Direction.U) break;
                snake.direction = Direction.D;
                //更新坐标
                updatePosition(egg, snake, "y++");
                //是否吃到蛋蛋
                eatEgg(snake, egg);

                break;
            //上键
            case KeyEvent.VK_UP:
                //相反方向 不操作
                if (snake.direction == Direction.D) break;
                snake.direction = Direction.U;
                updatePosition(egg, snake, "y--");
                //是否吃到蛋蛋
                eatEgg(snake, egg);
                break;
        }

    }

    /**
     * 更新所有的坐标
     */
    public void updatePosition(Egg egg, Snake snake, String op) {
        Node p = snake.head;
        Node tail = snake.tail;
        //更新除头节点的其他所有节点
        while (tail != head) {
            tail.x = tail.pre.x;
            tail.y = tail.pre.y;
            tail = tail.pre;
        }
        switch (op) {
            case "x++":
                //更新头部
                head.x++;
                if (head.x > Yard.COLS) {
                    System.out.println("游戏结束");
                    System.exit(0);
                }

                break;
            case "x--":
                head.x--;
                if (head.x < 0) {
                    System.out.println("游戏结束");
                    System.exit(0);
                }
                break;
            case "y++":
                head.y++;
                if (head.y > Yard.ROWS) {
                    System.out.println("游戏结束");
                    System.exit(0);
                }
                break;
            case "y--":
                head.y--;
                if (head.x < 0) {
                    System.out.println("游戏结束");
                    System.exit(0);
                }
                break;
        }
    }

    /**
     * 是否吃蛋蛋
     *
     * @param snake
     * @param egg
     */
    public void eatEgg(Snake snake, Egg egg) {
        //碰到了蛋
        if (snake.head.x == egg.getX() && snake.head.y == egg.getY()) {
            //获得尾部坐标
            int x = tail.x;
            int y = tail.y;
            switch (snake.direction) {
                case U:
                    y += 1;
                    break;
                case R:
                    x -= 1;
                    break;
                case L:
                    x += 1;
                    break;
                case D:
                    y -= 1;
            }
            Node node = new Node(x, y);
            node.next = null;
            node.pre = tail;

            tail.next = node;
            tail = node;

            //重新规划蛋蛋
            Random random = new Random();
            egg.setX(random.nextInt(70));
            egg.setY(random.nextInt(70));
        }


    }

    //节点类
    class Node {
        public int x;
        public int y;
        public Node next;
        public Node pre;

        public Node(int x, int y) {
            this.x = x;
            this.y = y;
            next = null;
            pre = null;
        }


        //画点
        public void drawNode(Graphics g) {
            Color color = g.getColor();
            g.setColor(Color.black);
            g.fillRect(x * Yard.BLOCK_SIZE, y * Yard.BLOCK_SIZE,
                    Yard.BLOCK_SIZE, Yard.BLOCK_SIZE);
            g.setColor(color);
        }
    }
}
