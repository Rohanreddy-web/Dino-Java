import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable {

    final int originalTileSize = 16;
    final int scale = 3;
    final int tileSize = originalTileSize * scale;
    final int maxCol = 12;
    final int maxRow = 16;
    final int screenWidth = tileSize * maxRow;
    final int screenHeight = tileSize * maxCol;

    BufferedImage playerImage;

    Thread gameThread;
    Keyhandler kh = new Keyhandler();

    int playerX = 50;
    int playerY;
    int playerWidth = tileSize;
    int playerHeight = tileSize;

    int groundY = screenHeight - tileSize * 2;
    int velocityY = 0;
    int gravity = 1;
    int jumpStrength = -20;
    boolean isJumping = false;
    int cameraX = 0;
    int obstacleWidth = tileSize - 20;
    int obstacleHeight = tileSize - 20;
    int startX = 300;
    int spacing = 600;
    ArrayList<Obstacle> obstacles;
    boolean gameOver = false; // Track game over state

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(kh);
        this.setFocusable(true);

        playerY = groundY - playerHeight;

        try {
            playerImage = ImageIO.read(getClass().getResourceAsStream("/images/DINOB.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        obstacles = new ArrayList<>();
        for (int i = 0; i <= 1000; i++) {
            int xPos = startX + i * spacing;
            obstacles.add(new Obstacle(xPos, groundY - obstacleHeight, obstacleWidth, obstacleHeight, Color.WHITE));
        }
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        while (gameThread != null) {
            update();
            repaint();

            try {
                Thread.sleep(16); // ~60 FPS
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void update() {
        if (gameOver) {
            return; // Stop updating if game is over
        }

        // Move forward
        playerX += 4;

        // Handle jump
        if (kh.upPressed && !isJumping) {
            velocityY = jumpStrength;
            isJumping = true;
        }

        // Apply gravity
        velocityY += gravity;
        playerY += velocityY;

        // Hit the ground
        if (playerY >= groundY - playerHeight) {
            playerY = groundY - playerHeight;
            velocityY = 0;
            isJumping = false;
        }

        // Move camera with player
        cameraX = playerX - 100;

        // Check for collisions
        for (Obstacle obstacle : obstacles) {
            if (isColliding(playerX, playerY, playerWidth, playerHeight, 
                            obstacle.x, obstacle.y, obstacle.width, obstacle.height)) {
                gameOver = true;
                gameThread = null; // Stop the game thread
                break;
            }
        }
    }

    // Collision detection method
    private boolean isColliding(int x1, int y1, int w1, int h1, int x2, int y2, int w2, int h2) {
        return x1 < x2 + w2 &&
               x1 + w1 > x2 &&
               y1 < y2 + h2 &&
               y1 + h1 > y2;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        int drawX = playerX - cameraX;

        // Draw dino image
        if (playerImage != null) {
            g2.drawImage(playerImage, drawX, playerY, playerWidth, playerHeight, null);
        }

        // Draw ground
        g2.setColor(Color.WHITE);
        g2.drawLine(0, groundY, getWidth(), groundY);

        // Draw score
        g2.setColor(Color.WHITE);
        g2.drawString("Score: " + (int) (playerX / 100), 10, 20);

        // Draw obstacles
        for (Obstacle obstacle : obstacles) {
            g2.setColor(obstacle.color);
            g2.fillRect(obstacle.x - cameraX, obstacle.y, obstacle.width, obstacle.height);

        }

        // Draw game over message
        if (gameOver) {
            g2.setColor(Color.RED);
            g2.drawString("Game Over! Score: " + (int) (playerX / 100), screenWidth / 2 - 50, screenHeight / 2);
        }

        g2.dispose();
    }
}