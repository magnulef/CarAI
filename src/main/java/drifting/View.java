package drifting;

import utils.Keyboard;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.Timer;
import java.util.TimerTask;

public class View extends javax.swing.JFrame {

    private BufferedImage back1;
    private BufferedImage back2;
    private BufferedImage back3;

    private Graphics2D g1;
    private Graphics2D g2;
    private Graphics2D g3;

    private Car car = new Car();
    private Track track = new Track();

    private int width;
    private int height;

    public View() {
        initComponents();
        width = getWidth()*2;
        height = getHeight()*2;

        back1 = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        back2 = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        back3 = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        g1 = (Graphics2D) back1.getGraphics();
        g2 = (Graphics2D) back2.getGraphics();
        g3 = (Graphics2D) back3.getGraphics();

        g1.drawImage(back1, 0, 0, width, height, null);

        g1.translate(width/2, height/2);
        g1.scale(1, -1);

        g2.setColor(Color.WHITE);
        g2.fillRect(0, 0, width, height);
        g2.translate(width/2, height/2);
        g2.scale(1, -1);


        new Timer().schedule(new MainLoop(), 100, 30);
    }

    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 956, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 599, Short.MAX_VALUE)
        );

        pack();
    }

    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(View.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(View.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(View.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(View.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new View().setVisible(true);
            }
        });
    }

    public void update() {
        car.update();
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        g3.setColor(Color.WHITE);
        g3.fillRect(0, 0, width, height);

        g2.setBackground(new Color(255, 255, 255, 0));
        g2.clearRect(-width/2, -height/2, width, height);

        AffineTransform at = g1.getTransform();
        AffineTransform at2 = g2.getTransform();
        car.draw(g2, g1);
        track.draw(g1);
        g1.setTransform(at);
        g2.setTransform(at2);

        g3.drawImage(back1, 0, 0, null);
        g3.drawImage(back2, 0, 0, null);

        g2d.drawImage(back3, 0, 0, null);
    }

    @Override
    protected void processKeyEvent(KeyEvent e) {
        if (e.getID() == KeyEvent.KEY_PRESSED) {
            Keyboard.keydown[e.getKeyCode()] = true;
        }
        else if (e.getID() == KeyEvent.KEY_RELEASED) {
            Keyboard.keydown[e.getKeyCode()] = false;
        }
    }

    private class MainLoop extends TimerTask {
        @Override
        public void run() {
            update();
            repaint();
        }
    }
}