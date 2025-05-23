import javax.swing.JFrame;

public class Main{
    public static void main(String[] args) {
        JFrame window=new JFrame();
        GamePanel gamepanal=new GamePanel ();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Game-2D");
        window.add(gamepanal);
        window.setVisible(true);
        gamepanal.startGameThread();
        window.pack();
    }
}