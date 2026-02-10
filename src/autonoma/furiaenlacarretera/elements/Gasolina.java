package autonoma.furiaenlacarretera.elements;

import static autonoma.furiaenlacarretera.elements.Cone.HEIGH_CONE;
import static autonoma.furiaenlacarretera.elements.Cone.WIDTH_CONE;
import autonoma.furiaenlacarretera.sounds.ReproducirSonido;
import gamebase.elements.GraphicContainer;
import gamebase.elements.Sprite;
import static gamebase.elements.Sprite.jugando;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * @author Valerie Moreno
 * @since 21/5/2025
 * @version 1.0.0
 */
public class Gasolina extends ElementType {
    // atributos que definen el tamaño
    public static final int WIDTH_CURRENCY = 48;
    public static final int HEIGH_CURRENCY = 80;
    // Imagen en memoria
    private BufferedImage imagenBuffer;
    private Graphics g_imagenBuffer;
    // Cantidad de píxeles que el carro avanza en cada movimiento
    private int paso;
    /**
     * Constructor de la clase Gasolina.
     *
     * @param x
     * @param y
     * @param width
     * @param height
     * @param g
     */
    public Gasolina(int x, int y, int height, int width, GraphicContainer g) {
        super(x, y, height, width);
        setImage("gasolina.png");
        gameContainer = g;
        paso = (int) ((Math.random() * 10) + 5);
        //paso = 1;
        if (gameContainer != null) {
            hilo.start();
        }

        //creamos la imagen en memoria
        imagenBuffer = new BufferedImage(WIDTH_CURRENCY,
                HEIGH_CURRENCY,
                BufferedImage.TYPE_INT_RGB
        );
        //obtenemos los graficos
        g_imagenBuffer = imagenBuffer.getGraphics();
    }
    /**
     * Registra el sprite actual en el campo de juego.
     *
     * Este método agrega la instancia actual (this) a la lista de sprites del
     * objeto GameField recibido como parámetro. De esta forma, el sprite podrá
     * ser gestionado y actualizado dentro del ciclo del juego.
     *
     * @param gameField el campo de juego donde se desea registrar este sprite.
     */
    @Override
    public void registerHitGameField(GameField gameField) {
        if (gameField != null) {
            gameField.getSprites().add(this);
        }
    }
    /**
     * Crea y posiciona un objeto de tipo Gasolina en el campo de juego.
     *
     * Este método intenta colocar una nueva instancia de `Person` en una
     * posición aleatoria dentro de los límites, evitando que se superponga con
     * otros sprites que ya existen. Se realizan varios intentos hasta encontrar
     * una ubicación libre o alcanzar un número máximo de intentos.
     *
     
     *
     * @param gameField
     * @param container
     * @param rand
     */
    public static void create(GameField gameField, GraphicContainer container, Random rand) {
        int intentosMaximos = 100;
        int intentos = 0;
        boolean seSobrepone = true;

        int x = 0;
        int y = 0;

        while (intentos < intentosMaximos && seSobrepone) {
            x = rand.nextInt(container.getBordes().width - WIDTH_CONE);
            y = rand.nextInt(container.getBordes().height - HEIGH_CONE);

            Rectangle nuevo = new Rectangle(x, y, WIDTH_CONE, HEIGH_CONE);
            seSobrepone = false;

            for (Sprite elemento : gameField.getSprites()) {
                Rectangle existente = new Rectangle(
                        elemento.getX(),
                        elemento.getY(),
                        elemento.getWidth(),
                        elemento.getHeight()
                );

                if (nuevo.intersects(existente)) {
                    seSobrepone = true;
                    break;
                }
            }

            intentos++;
        }

        if (!seSobrepone) {
            Gasolina gasolina = new Gasolina(x, y, HEIGH_CURRENCY, WIDTH_CURRENCY, container);
            gasolina.registerHitGameField(gameField);

        } else {
            System.out.println("No se pudo colocar el cono sin superposición tras " + intentosMaximos + " intentos.");
        }
    }

    @Override
    public void update(Graphics g) {
        g.drawImage(imagenBuffer, 0, 0, this);
        //paint(g);
    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(getImage(), getX(), getY(), getWidth(), getHeight(), null);
    }

    public int getPaso() {
        return paso;
    }

    public void setPaso(int paso) {
        this.paso = paso;
    }

    @Override
    public boolean mover() {

        int direccion = (int) (Math.random() * 3);

        int nx = x;
        int ny = y;
        switch (direccion) {
            case 0:   //ARRIBA

                ny -= paso;

                break;

            case 1:  //ABAJO
                ny += paso;
                break;

            case 2: // IZQUIERDA
                nx -= paso;
                break;

            case 3:  // DERECHA
                nx += paso;
                break;
        }
        if (!fueraContenedor(nx, ny)) {
            x = nx;
            y = ny;
            //System.out.println("Me movi");
            if (gameContainer != null) {
                gameContainer.refresh();
            }

            return true;
        }

        return false;

    }

    @Override
    public void run() {
        running = true;
        while (running) {
            try {
                Thread.sleep(delay);
            } catch (InterruptedException ex) {
            }
            if (!jugando) {
                pausar();
            } else {
                dePausar();
            }

            if (isPausa()) {
                continue;
            }

            mover();
        }
    }

    public void playGasolinaSound() {
        ReproducirSonido.playEffectSound("soundFuel.wav");
    }

    @Override
    public void delete(GameField gameField) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
