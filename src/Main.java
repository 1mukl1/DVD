import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.color.ColorSpace;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        JFrame jFrame = getFrame();
        jFrame.add(new MyComponent());
    }

    static class MyComponent extends JComponent {
        private int x = 1;
        private int y = 1;
        private int kx = 1;
        private int ky = 1;
        private BufferedImage image;
        private BufferedImage imageCol;
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;
            Timer timer = new Timer(100, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    repaint();
                }
            });
            try {
                image = ImageIO.read(new File("dvd-128.png"));
                g2.drawImage(image, x, y, this);
                timer.start();
                if (x == 0 || x == 450){
                    imageCol = colorImage(image);
                    kx *= -1;
                }
                if (y == 0 || y == 312) {
                    imageCol = colorImage(image);
                    ky *= -1;
                }
                    g2.drawImage(imageCol, x, y, this);
                    x = x + 1 * kx;
                    y = y + 1 * ky;
            } catch (IOException ex){
                ex.printStackTrace();
            }
        }
    }

    private static BufferedImage colorImage (BufferedImage image) {
        int color1 = (int) (Math.random() * 255);
        int color2 = (int) (Math.random() * 255);
        int width = image.getWidth();
        int height = image.getHeight();
        WritableRaster raster = image.getRaster();
        for (int xx = 0; xx < width; xx++){
            for (int yy = 0; yy < height; yy++){
                int[] pixels = raster.getPixel(xx, yy, (int[]) null);
                pixels[0] = color1;
                pixels[1] = color2;
                pixels[2] = 255;
                raster.setPixel(xx, yy, pixels);
            }
        }
        return image;
    }

    static JFrame getFrame(){
        JFrame jFrame = new JFrame();
        jFrame.setDefaultCloseOperation(jFrame.EXIT_ON_CLOSE);
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();
        jFrame.setBounds(dimension.width / 2 - 350, dimension.height / 2 - 200, 600, 450);
        jFrame.setTitle("DVD");
        jFrame.getContentPane().setBackground(Color.black);
        try {
            BufferedImage image = ImageIO.read(new File("DVD_EasterEgg-logo-C29B26041B-seeklogo.com.png"));
            jFrame.setIconImage(image);
        } catch (IOException ex){
            ex.printStackTrace();
        }
        jFrame.setVisible(true);
        return jFrame;
    }
}