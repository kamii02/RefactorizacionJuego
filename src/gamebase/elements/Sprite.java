package gamebase.elements;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author marti
 */
public abstract class Sprite extends Canvas {

    protected int x;
    protected int y;
    protected int height;
    protected int width;
    protected BufferedImage image;
    protected Color color;

    protected GraphicContainer gameContainer;
    public static boolean jugando = false;

    public Sprite(int x, int y, int height, int width) {
        this.x = x;
        this.y = y;
        this.height = height;
        this.width = width;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public BufferedImage getImage() {
        return image;
    }

    public BufferedImage setImage(String filename) {
        try {
            var stream = getClass().getResourceAsStream("/autonoma/furiaenlacarretera/images/" + filename);
            System.out.println(getClass().getResource("/autonoma/furiaenlacarretera/images/" + filename));

            if (stream == null) {
                System.out.println("No se encontró el recurso: " + filename);
                return null;
            }
            BufferedImage img = ImageIO.read(stream);
            if (img == null) {
                System.out.println("No se pudo leer la imagen: " + filename);
                return null;
            }
            setImage(img);
            return getImage();
        } catch (IOException e) {
            System.out.println("Error leyendo la imagen: " + filename);
            e.printStackTrace();
        }
        return null;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    // se usa para asignarle al sprite, como el gnomo o el troll, una referencia al contenedor grafico principal
    public void setGraphicContainer(GraphicContainer gContainer) {
        this.gameContainer = gContainer;
    }

    public Rectangle getBoundaries() {
        return new Rectangle(x, y, width, height);
    }

    public boolean fueraContenedor(int x, int y) {
        if (getContenedor() == null) {
            return false;
        }

        Rectangle bounds = getContenedor().getBordes();

        return !(x >= bounds.getX()
                & y >= bounds.getY()
                & x + getWidth() <= bounds.getX() + bounds.getWidth()
                & y + getHeight() <= bounds.getY() + bounds.getHeight());
    }

    public boolean fueraContenedor(int x, int y, int w, int h) {
        if (getContenedor() == null) {
            return false;
        }

        Rectangle bounds = new Rectangle(x, y, w, h);

        return !(getX() >= bounds.getX()
                & getY() >= bounds.getY()
                & getX() + getWidth() <= bounds.getX() + bounds.getWidth()
                & getY() + getHeight() <= bounds.getY() + bounds.getHeight());
    }

    //Este método verifica si dos sprites se están tocando (por ejemplo, el gnomo y un hongo) usando colisión de rectángulos (bounding box):
    //
    //collisionX verifica si se superponen horizontalmente.
    //
    //collisionY verifica si se superponen verticalmente.
    //
    //Si ambas condiciones son verdaderas, significa que hay colisión.
    public boolean checkCollision(Sprite other) {
        // Collision x-axis?
        boolean collisionX = this.getX() + this.getWidth() >= other.getX()
                && this.getX() < other.getX() + other.getWidth();

        // Collision y-axis?
        boolean collisionY = this.getY() + this.getHeight() >= other.getY()
                && this.getY() < other.getY() + other.getHeight();

        // Collision only if on both axes
        return collisionX && collisionY;
    }

    public abstract void paint(Graphics g);

    public GraphicContainer getContenedor() {
        return gameContainer;
    }

    public void setContenedor(GraphicContainer gameContainer) {
        this.gameContainer = gameContainer;
    }
}
