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

    private int cellSize = 25;
    private int totalSize = 30;
    private int width = 1000;
    private int height = 1000;
    private int bounds = 50;

    private final int delay = 50;
    private final int inDelay = 5;
    private int delayIndex = 0;
    public Direction direction = Direction.RIGHT;

    public Window() {
        frame = new JFrame("snake");

        scoreLabel = new ScoreLabel();
        this.add(scoreLabel);

        this.setBackground(new Color(60, 63, 65));

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

    private Color getColor(int i, Color[] g) {
        if (i >= g.length) return Color.BLACK;
        return g[i];
    }

    private Color[] rainbow = {
            Color.RED,
            Color.ORANGE,
            Color.YELLOW,
            Color.GREEN,
            Color.CYAN,
            Color.BLUE,
            Color.MAGENTA,
    };

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(snake.apple.color);
        g.fillOval(snake.apple.x * cellSize + bounds,
                snake.apple.y * cellSize + bounds,
                cellSize, cellSize);

        g.setColor(Color.LIGHT_GRAY);
        for (int i = 0; i <= cellSize * totalSize; i+= totalSize * cellSize) {
            g.drawLine(bounds, i + bounds, cellSize * totalSize + bounds, i + bounds);
            g.drawLine(i + bounds, bounds, i + bounds, cellSize * totalSize + bounds);
        }

        Color[] gradient = rainbow;

        for (int i = 0; i < snake.getCoords().length; i++) {
            int colNum = i * (gradient.length - 1) / snake.getCoords().length;
            Color color0 = getColor(colNum, gradient);
            Color color1 = getColor(colNum + 1, gradient);
            int i0 = colNum * snake.getCoords().length / (gradient.length - 1);
            int i1 = (colNum + 1) * snake.getCoords().length / (gradient.length - 1);

            int rgb0 = color0.getRGB();
            int rgb1 = color1.getRGB();

            int b0 = rgb0 & 0xFF, b1 = rgb1 & 0xFF;
            rgb0 >>= 8;
            rgb1 >>= 8;
            int g0 = rgb0 & 0xFF, g1 = rgb1 & 0xFF;
            rgb0 >>= 8;
            rgb1 >>= 8;
            int r0 = rgb0 & 0xFF, r1 = rgb1 & 0xFF;

            int blue = b0, green = g0, red = r0;
            try {
                blue = (b1 - b0) * (i - i0) / (i1 - i0) + b0;
                green = (g1 - g0) * (i - i0) / (i1 - i0) + g0;
                red = (r1 - r0) * (i - i0) / (i1 - i0) + r0;
            } catch (Exception e) {}

            g.setColor(new Color(red, green, blue, 255));
            g.fillRect(
                    snake.getCoords()[i][0] * cellSize + bounds,
                    snake.getCoords()[i][1] * cellSize + bounds,
                    cellSize,
                    cellSize
                    );

        }
    }

    public int getTotalSize() {
        return totalSize;
    }

}
