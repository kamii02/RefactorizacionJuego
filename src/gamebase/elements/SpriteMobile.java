package gamebase.elements;

/**
 *
 * @author marti
 */
public abstract class SpriteMobile extends Sprite implements Runnable {

    protected boolean running;
    protected boolean pausa;

    protected long delay;
    protected Thread hilo;
    protected int step;

    public SpriteMobile(int x, int y, int height, int width) {
        super(x, y, height, width);
        hilo = new Thread(this);

        delay = 500;
        running = false;
        pausa = false;
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public abstract boolean mover();

    public boolean isRunning() {
        return running;
    }

    public void parar() {
        this.running = false;
    }

    public boolean isPausa() {
        return pausa;
    }

    public void pausar() {
        this.pausa = true;
    }

    public void dePausar() {
        this.pausa = false;
    }
}
