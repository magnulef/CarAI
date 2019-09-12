package game;

import java.awt.Canvas;
import java.awt.Dimension;
import java.util.List;
import javax.swing.JFrame;

public class Window extends Canvas {

    private final JFrame frame;

    public Window(int width, int height, String title, Renderer renderer) {
        this.frame = new JFrame(title);
        this.frame.setPreferredSize(new Dimension(width, height));
        this.frame.setMaximumSize(new Dimension(width, height));
        this.frame.setMinimumSize(new Dimension(width, height));
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setResizable(true);
        this.frame.requestFocus();
        this.frame.setLocationRelativeTo(null);
        this.frame.add(renderer);
        this.frame.setVisible(true);
    }

    public void add(Canvas canvas) {
        this.frame.add(canvas);
    }
}
