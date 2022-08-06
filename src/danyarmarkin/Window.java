package danyarmarkin;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Window extends JPanel {

    private JFrame frame;
    private Snake snake;
    private Timer timer;
    private ScoreLabel scoreLabel;

    private RestartListener listener;

    private int cellSize = 20;
    private int totalSize = 30;
    private int width = 750;
    private int height = 720;
    private int bounce = 50;
    public Direction direction = Direction.RIGHT;

    public Window() {
        frame = new JFrame("snake");

        scoreLabel = new ScoreLabel();
        this.add(scoreLabel);

        frame.add(this);

        frame.setSize(width, height);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        snake = new Snake();
        snake.setWindow(this);

        timer = new Timer(50, e -> {
            snake.setDirection(direction);
            NextResult result = snake.next();
            switch (result) {
                case OK -> repaint();
                case STOP -> {
                    RecordMessage message = new RecordMessage(scoreLabel.getScore());
                    message.addRestartListener(listener);
                    message.release();
                    timer.stop();
                }
                case FEED -> scoreLabel.add();
            }
        });

        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_W -> direction = Direction.UP;
                    case KeyEvent.VK_S -> direction = Direction.DOWN;
                    case KeyEvent.VK_D -> direction = Direction.RIGHT;
                    case KeyEvent.VK_A -> direction = Direction.LEFT;
                }
            }
        });

        listener = () -> {
            snake.restart();
            direction = Direction.RIGHT;
            scoreLabel.setNull();
            repaint();
            timer.start();
        };

        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.BLACK);

        for (int i = 0; i <= cellSize * totalSize; i+= cellSize) {
            g.drawLine(bounce, i + bounce, cellSize * totalSize + bounce, i + bounce);
            g.drawLine(i + bounce, bounce, i + bounce, cellSize * totalSize + bounce);
        }

        for (int[] c : snake.getCoords()) {
            g.fillRect(
                    c[0] * cellSize + bounce,
                    c[1] * cellSize + bounce,
                    cellSize,
                    cellSize
                    );

        }

        g.setColor(Color.RED);

        g.fillRect(snake.getFood()[0] * cellSize + bounce,
                snake.getFood()[1] * cellSize + bounce,
                cellSize, cellSize);
    }

    public int getTotalSize() {
        return totalSize;
    }

}
