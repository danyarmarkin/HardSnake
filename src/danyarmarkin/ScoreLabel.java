package danyarmarkin;

import javax.swing.*;

public class ScoreLabel extends JLabel {

    private int score = 0;
    private int bestScore = 0;

    public ScoreLabel() {
        this.setText(genText());
    }

    public void add() {
        score++;
        bestScore = Math.max(score, bestScore);
        this.setText(genText());
    }

    public void setNull() {
        score = 0;
        this.setText(genText());
    }

    public int getScore() {
        return score;
    }

    private String genText() {
        return "Score: " + score + "  \nBest score: " + bestScore;
    }
}
