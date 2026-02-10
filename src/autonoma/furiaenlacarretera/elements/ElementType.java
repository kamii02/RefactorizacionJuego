package autonoma.furiaenlacarretera.elements;


import gamebase.elements.GraphicContainer;
import gamebase.elements.Sprite;
import gamebase.elements.SpriteMobile;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.List;

/**
 *
 * @author Maria Camila Prada Cortes
 * @version 1.0.0
 * @since 2025-05-20
 */
public abstract class ElementType extends SpriteMobile {

    public static final int WIDTH = 30;
    public static final int HEIGHT = 30;

    public ElementType(int x, int y, int width, int height) {
        super(x, y, width, height);
        setStep(step);
    }

    public boolean checkCollision(Point punto) {
        return punto.x >= this.getX() && punto.x <= this.getX() + this.getWidth()
                && punto.y >= this.getY() && punto.y <= this.getY() + this.getHeight();
    }

    public static ElementType create(Class type, int width, int height, List<Sprite> sprites, GraphicContainer gc)
            throws InstantiationException, IllegalAccessException {
        int x, y;
        boolean overlaps;

        do {
            x = (int) (Math.random() * (width - ElementType.WIDTH));
            y = (int) (Math.random() * (height - ElementType.HEIGHT));
            overlaps = false;

            Rectangle newFleaRect = new Rectangle(x, y, ElementType.WIDTH, ElementType.HEIGHT);

            for (Sprite sprite : sprites) {
                if (sprite instanceof ElementType) {
                    Rectangle existingFleaRect = new Rectangle(sprite.getX(), sprite.getY(), sprite.getWidth(), sprite.getHeight());
                    if (newFleaRect.intersects(existingFleaRect)) {
                        overlaps = true;
                        break;
                    }
                }
            }
        } while (overlaps);

        ElementType e = null;

        if (type.equals(Car.class)) {
            e = new Car(x, y, ElementType.WIDTH, ElementType.HEIGHT, gc);

        } else if (type.equals(Person.class)) {
            e = new Person(x, y, ElementType.WIDTH, ElementType.HEIGHT, gc);

        } else if (type.equals(Cone.class)) {
            e = new Cone(x, y, ElementType.WIDTH, ElementType.HEIGHT, gc);

        } else if (type.equals(Currency.class)) {
            e = new Currency(x, y, ElementType.WIDTH, ElementType.HEIGHT, gc);

        } else {
            System.out.println("ERROR: ElementType.create unknown type of elements");
        }

        return e;
    }

    /**
     * metodo esta diseñado para que cada clase hija defina su propia logica de que hacer
     * cuando recibe un golpe o colision en el GameField.
     * @param gameField 
     */
    public abstract void registerHitGameField(GameField gameField);
    /**
     * Método que debe implementar cada tipo de pulga (normal o mutante)
     */
    public abstract void delete(GameField gameField);
}
