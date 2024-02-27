package danyarmarkin;

import java.awt.*;

public class Apple {

    int x = 15;
    int y = 5;
    Color color = Color.RED;

    public Apple(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Apple() {
    }

    void nextColor() {
        int r = (int) (Math.random() * 255);
        int g = (int) (Math.random() * 255);
        int b = (int) (Math.random() * 255);
        if (r * r + g * g + b * b < 65 * 65 * 3) {
            nextColor();
        } else {
            color = new Color(r, g, b, 255);
        }
    }
}
