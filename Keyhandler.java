import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Keyhandler implements KeyListener {

    public boolean upPressed = false;
    public boolean downPressed = false;

    @Override
    public void keyPressed(KeyEvent e) {
        int keycode = e.getKeyCode();

        if (keycode == KeyEvent.VK_UP) {
            upPressed = true;
        } else if (keycode == KeyEvent.VK_DOWN) {
            downPressed = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int keycode = e.getKeyCode();

        if (keycode == KeyEvent.VK_UP) {
            upPressed = false;
        } else if (keycode == KeyEvent.VK_DOWN) {
            downPressed = false;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'keyTyped'");
    }
}
