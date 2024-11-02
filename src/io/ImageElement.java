package io;
import gui.GraphicalElement;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class ImageElement implements GraphicalElement {
    private int x, y, width, height;
    private BufferedImage image;

    public ImageElement(int x, int y, int width, int height, BufferedImage image) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.image = image;
    }

    
    public void paint(Graphics2D g) {
        g.drawImage(image, x, y, width, height, null);
    }
}