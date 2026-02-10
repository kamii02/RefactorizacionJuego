package autonoma.furiaenlacarretera.elements;

import autonoma.furiaenlacarretera.views.GameWindow;

/**
 *
 * @author Maria Camila Prada Cortes
 * @version 1.0.0
 * @since 2025-05-20
 */
public class RefreshProgram implements Runnable {

    private GameField gameField;
    private GameWindow gameWindow;
    private boolean running;
    private Thread thread;

    /**
     * Constructor
     */
    public RefreshProgram(GameField gameField, GameWindow gameWindow) {
        this.gameField = gameField;
        this.gameWindow = gameWindow;
        running = false;
    }

    @Override
    public void run() {
        running = true;
        int carSpawnTimer = 0;
        int coneSpawnTimer = 0;
        int personSpawnTimer = 0;
        int currencySpawnTimer = 0;

        while (running) {
            gameField.update();

            carSpawnTimer += 30;
            coneSpawnTimer += 30;
            personSpawnTimer += 30;
            currencySpawnTimer += 30;

            if (carSpawnTimer >= 2000) {  // cada 1 segundo
                gameField.addCar();
                carSpawnTimer = 0;
            }
            if (personSpawnTimer >= 5000) {  // cada 5 segundos
                gameField.addPerson();
                personSpawnTimer = 0;
            }
            if (currencySpawnTimer >= 3000) {  // cada 3 segundos
                gameField.addCurrency();
                currencySpawnTimer = 0;
            }

            try {
                Thread.sleep(30);
            } catch (InterruptedException ex) {
                // manejar excepción si quieres
            }
        }
    }

    public void start() {
        thread = new Thread(this);
        thread.start();
    }

    public void stop() {
        running = false;
    }

}
