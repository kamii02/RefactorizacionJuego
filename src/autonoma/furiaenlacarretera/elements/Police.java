package autonoma.furiaenlacarretera.elements;

import gamebase.elements.Sprite;
import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author Kamii
 */
public class Police extends Sprite implements Runnable {
    // atributos que definen el tamaño
    public static final int WIDTH = 40;
    public static final int HEIGHT = 40;
    // Atributo de el delay para el police
    protected long delay;
    private boolean running;
    private boolean paused;
    protected Thread thread;
    // Cantidad de píxeles que el carro avanza en cada movimiento
    private int step;
    // tiempo de inicio desde cuando sale
    private long tiempoInicio;
    private long duracionPersecucion = 5000;
    /**Se agrega el gamefield para poder perseguir la moto, saber donde esta  (su refrencia)
    No se hizo en gamefiel porque el movimiento de la moto  ocurre de forma independiente
    */
     private GameField gamefield;
     /**
     * Constructor de la clase Police.
     *
     * @param x
     * @param y
     * @param width
     * @param height
     * @param g
     */
    public Police(int x, int y, int width, int height) {
        super(x, y, width, height);

        setImage("police.png");

        this.step = 1;
        this.delay = 150;
        running = false;
        paused = false;

        thread = new Thread(this);
        thread.start();
    }
    /**
     * se Crea el metodo para inicira la persecucion del policia.
     *
     */
    public void iniciarPersecucion() {
        this.tiempoInicio = System.currentTimeMillis();
    }
    /**
     * se Crea el metodo para la movilidad de el police
     *
     */
    public boolean mover() {
        int direccion = (int) (Math.random() * 4);

        int nx = x;
        int ny = y;
        switch (direccion) {
            case 0:   //ARRIBA

                ny -= step;

                break;

            case 1:  //ABAJO
                ny += step;
                break;

            case 2: // IZQUIERDA
                nx -= step;
                break;

            case 3:  // DERECHA
                nx += step;
                break;
        }
        if (!fueraContenedor(nx, ny)) {
            x = nx;
            y = ny;
            if (gameContainer != null) {
                gameContainer.refresh();
            }

            return true;
        }

        return false;
    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(getImage(), getX(), getY(), getWidth(), getHeight(), null);
    }

    @Override
    public void run() {
        running = true;

        while (isRunning()) {
            try {
                Thread.sleep(delay);
            } catch (InterruptedException ex) {
            }

            if (isPaused()) {
                continue;
            }

            if (gamefield != null) {
                gamefield.moverPoliceJugador();    // Ya no se mueve aleatorio si no que persigue al jugador automaticamente
                gamefield.PoliceCaughtProcess(); // verifica colision y muestra mensaje
                gamefield.refresh();
            }
            if (System.currentTimeMillis() - tiempoInicio >= duracionPersecucion) {
                stop(); // deja de ejecutarse
                if (gamefield != null) {
                    gamefield.eliminarPolice(); // lo quitamos del juego
                }
                break;
            }
        }
    }

    public boolean isRunning() {
        return running;
    }

    public void stop() {
        this.running = false;
    }

    public boolean isPaused() {
        return paused;
    }

    public void pause() {
        this.paused = true;
    }

    public void unpause() {
        this.paused = false;
    }

    public long getDelay() {
        return delay;
    }

    public void setDelay(long delay) {
        this.delay = delay;
    }

    public int getStep() {
        return step;
    }

    public void setGamefield(GameField gamefield) {
        this.gamefield = gamefield;
    }
}
