import java.awt.Color;
import java.awt.Graphics2D;

public class Obstacle {
    int x, y, width, height;
    Color color;

    public Obstacle(int x, int y, int width, int height, Color color) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
    }
}
