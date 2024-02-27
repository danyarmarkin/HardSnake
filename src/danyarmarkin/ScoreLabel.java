package danyarmarkin;

import javax.swing.*;

public class ScoreLabel extends JLabel {

    private int score = 0;
    private int bestScore;

    private int total = 0;


    public ScoreLabel() {
        int[] r = Database.getResult();
        bestScore = r[0];
        total = r[1];
        this.setText(genText());
    }

    public void add() {
        score++;
        total++;
        bestScore = Math.max(score, bestScore);
        this.setText(genText());
        Database.saveResult(new int[] {bestScore, total});
    }

    public void setNull() {
        score = 0;
        this.setText(genText());
    }

    public int getScore() {
        return score;
    }

    private String genText() {
        return "Score: " + score + "  Best score: " + bestScore + "  Total: " + total;
    }
}
