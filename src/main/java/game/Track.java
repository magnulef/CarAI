package game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Track extends JPanel implements ActionListener {

    private final int width;
    private final int height;
    private final Car car;

    public Track(
            int width,
            int height
    ) {
        this.width = width;
        this.height = height;
        this.car = new Car(500, 500);
        initBoard();
    }

    private void initBoard() {
        addKeyListener(new TAdapter());
        setBackground(Color.BLACK);
        setFocusable(true);
        setPreferredSize(new Dimension(width, height));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //repaint();
    }

    private void doDrawing(Graphics g) {
        Graphics2D gg = (Graphics2D) g.create();
        Rectangle rect2 = new Rectangle(car.getLocationX(), car.getLocationY(), car.getWidth(), car.getHeight());
        gg.rotate(Math.toRadians(car.getOrientation()), car.getLocationX() + car.getWidth()/2,  car.getLocationY() + car.getHeight()/2);
        gg.draw(rect2);
        gg.fill(rect2);
        gg.dispose();

        Toolkit.getDefaultToolkit().sync();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        doDrawing(g);
    }

    private class TAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();

            if (key == KeyEvent.VK_LEFT) {
                car.move(0, 0, 10);
            }

            if (key == KeyEvent.VK_RIGHT) {
                car.move(0, 0, -10);
            }

            if (key == KeyEvent.VK_UP) {
                car.move(0, 10, 0);
            }

            if (key == KeyEvent.VK_DOWN) {
                car.move(0, -10, 0);
            }

            repaint();
        }
    }
}
