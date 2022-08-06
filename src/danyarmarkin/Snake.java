package danyarmarkin;

public class Snake {
    private int[][] coords = {{2, 5}, {1, 5}, {0, 5}};
    private Direction direction = Direction.RIGHT;
    private int[] food = {15, 5};

    private Window window;

    public NextResult next() {
        int[][] newCoords = new int[coords.length][2];
        switch (direction) {
            case UP -> newCoords[0] = new int[] {coords[0][0], coords[0][1] - 1};
            case DOWN -> newCoords[0] = new int[] {coords[0][0], coords[0][1] + 1};
            case LEFT -> newCoords[0] = new int[] {coords[0][0] - 1, coords[0][1]};
            case RIGHT -> newCoords[0] = new int[] {coords[0][0] + 1, coords[0][1]};
        }

        if (newCoords[0][0] >= window.getTotalSize() || newCoords[0][0] < 0 ||
            newCoords[0][1] >= window.getTotalSize() || newCoords[0][1] < 0)
            return NextResult.STOP;

        for (int i = 0; i < coords.length - 1; i++)
            newCoords[i + 1] = coords[i];

        for (int i = 1; i < coords.length; i++) {
            if (newCoords[0][0] == newCoords[i][0] && newCoords[0][1] == newCoords[i][1]) return NextResult.STOP;
        }

        if (newCoords[0][0] == food[0] && newCoords[0][1] == food[1]) {
            int x = newCoords[0][0];
            int y = newCoords[0][1];

            while (contains(newCoords, new int[] {x, y})) {
                x = (int) (Math.random() * window.getTotalSize());
                y = (int) (Math.random() * window.getTotalSize());
            }

            food = new int[] {x, y};

            int[] last = coords[coords.length - 1];

            coords = new int[coords.length + 1][2];
            System.arraycopy(newCoords, 0, coords, 0, newCoords.length);
            coords[coords.length - 1] = last;

            return NextResult.FEED;
        }

        coords = newCoords;

        return NextResult.OK;
    }

    public void restart() {
        coords = new int[][] {{2, 5}, {1, 5}, {0, 5}};
        direction = Direction.RIGHT;
        food = new int[] {15, 5};
    }

    private boolean contains(int[][] array, int[] val) {
        for (int[] v : array) {
            if (v[0] == val[0] && v[1] == val[1]) return true;
        }
        return false;
    }

    public void setDirection(Direction direction) {
        if ((this.direction == Direction.LEFT && direction == Direction.RIGHT) ||
                (this.direction == Direction.RIGHT && direction == Direction.LEFT) ||
                (this.direction == Direction.UP && direction == Direction.DOWN) ||
                (this.direction == Direction.DOWN && direction == Direction.UP))
            return;
        this.direction = direction;
    }

    public void setWindow(Window window) {
        this.window = window;
    }

    public int[][] getCoords() {
        return coords;
    }

    public int[] getFood() {
        return food;
    }

}
