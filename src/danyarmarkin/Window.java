package danyarmarkin;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Window extends JPanel {

    private JFrame frame;
    private Snake snake;
    private Timer timer;
    private ScoreLabel scoreLabel;
    private JButton restartButton;

    private RestartListener restartListener;

    private int cellSize = 20;
    private int totalSize = 30;
    private int width = 750;
    private int height = 720;
    private int bounce = 50;

    private final int delay = 50;
    private final int inDelay = 5;
    private int delayIndex = 0;
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

        timer = new Timer(delay / inDelay, e -> {
            if (delayIndex == 0) {
                snake.setDirection(direction);
                NextResult result = snake.next();
                switch (result) {
                    case OK -> repaint();
                    case STOP -> {
                        restartButton.setVisible(true);
                        timer.stop();
                    }
                    case FEED -> scoreLabel.add();

                }
            }
            repaint();

            delayIndex ++;
            delayIndex %= inDelay;
        });

        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP, KeyEvent.VK_W -> direction = Direction.UP;
                    case KeyEvent.VK_DOWN, KeyEvent.VK_S -> direction = Direction.DOWN;
                    case KeyEvent.VK_RIGHT, KeyEvent.VK_D -> direction = Direction.RIGHT;
                    case KeyEvent.VK_LEFT, KeyEvent.VK_A -> direction = Direction.LEFT;
                }
            }
        });

        restartButton = new JButton("Restart");
        this.add(restartButton);
        restartButton.addActionListener(e -> {
            snake.restart();
            direction = Direction.RIGHT;
            scoreLabel.setNull();
            restartButton.setVisible(false);
            repaint();
            timer.start();
        });
        restartButton.setVisible(false);
        frame.getRootPane().setDefaultButton(restartButton);

        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.RED);
        g.fillOval(snake.getFood()[0] * cellSize + bounce,
                snake.getFood()[1] * cellSize + bounce,
                cellSize, cellSize);

        g.setColor(Color.LIGHT_GRAY);
        for (int i = 0; i <= cellSize * totalSize; i+= cellSize) {
            g.drawLine(bounce, i + bounce, cellSize * totalSize + bounce, i + bounce);
            g.drawLine(i + bounce, bounce, i + bounce, cellSize * totalSize + bounce);
        }

        for (int i = 0; i < snake.getCoords().length; i++) {
            Color color;
            switch ((int) ((float) i / snake.getCoords().length * 7)) {
                case 0 -> color = Color.RED;
                case 1 -> color = Color.ORANGE;
                case 2 -> color = Color.YELLOW;
                case 3 -> color = Color.GREEN;
                case 4 -> color = Color.CYAN;
                case 5 -> color = Color.BLUE;
                case 6 -> color = Color.MAGENTA;
                default -> throw new IllegalStateException("Unexpected value: " + i % 7);
            }
            g.setColor(color);
            g.fillRect(
                    snake.getCoords()[i][0] * cellSize + bounce,
                    snake.getCoords()[i][1] * cellSize + bounce,
                    cellSize,
                    cellSize
                    );

        }
    }

    public int getTotalSize() {
        return totalSize;
    }

}
