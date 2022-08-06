package danyarmarkin;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;

public class RecordMessage extends JPanel {

    private int score;
    private RestartListener listener;

    public RecordMessage(int score) {
        this.score = score;
    }

    public void release() {
        JLabel label = new JLabel("Your Score: " + score);
        this.add(label);

        JButton button = new JButton("Restart");
        button.setSize(100, 50);
        this.add(button);

        JFrame frame = new JFrame("Score");
        frame.setSize(200, 100);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(this);

        button.addActionListener(e -> {
            if (listener != null) listener.restart();
            frame.setVisible(false);
            frame.dispose();
        });

        frame.getRootPane().setDefaultButton(button);
    }

    public void addRestartListener(RestartListener listener) {
        this.listener = listener;
    }
}
