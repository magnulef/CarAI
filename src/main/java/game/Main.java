package game;

import javax.swing.*;
import java.awt.*;

public class Main extends JFrame {

    public Main() {

        initUI();
    }

    private void initUI() {
        add(new Track(1000, 1000));

        setResizable(false);
        pack();

        setTitle("CarAI");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String args[]) {
        EventQueue.invokeLater(() -> {
            JFrame ex = new Main();
            ex.setVisible(true);
        });
    }
}
