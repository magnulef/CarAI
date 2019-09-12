package game;


import java.awt.Canvas;
import java.awt.Dimension;
import java.util.List;
import javax.swing.JFrame;

public class Window extends Canvas {

    public Window(int width, int height, String title, List<Game> games) {
        JFrame frame = new JFrame(title);
        frame.setPreferredSize(new Dimension(width, height));
        frame.setMaximumSize(new Dimension(width, height));
        frame.setMinimumSize(new Dimension(width, height));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(true);
        frame.requestFocus();
        frame.setLocationRelativeTo(null);

        for (Game game : games) {
            frame.add(game);
        }

        frame.setVisible(true);
    }
}
