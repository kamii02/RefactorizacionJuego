package autonoma.furiaenlacarretera.elements;

import autonoma.furiaenlacarretera.sounds.ReproducirSonido;
import autonoma.furiaenlacarretera.views.GameWindow;
import gamebase.elements.EscritorArchivoTextoPlano;
import gamebase.elements.LectorArchivoTextoPlano;
import gamebase.elements.Sprite;
import gamebase.elements.SpriteContainer;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Timer;

/**
 *
 * @author Kamii
 */
public class GameField extends SpriteContainer {

    /**
     * Atributos
     */
    private EscritorArchivoTextoPlano escritor;
    private LectorArchivoTextoPlano lector;
    private Jugador jugador;
    private Police police;

    private int offsetX = 0;
    private int offsetY = 0;
    private static int movimiento = 5;
    private boolean partidaTerminada = false;
    private Timer gameTimer;
    private Thread contadorTiempo;
    private Thread ponerGasolina;
    public int maxScore = 0;
    private String mensajePantalla = "";
    private long tiempoMensaje = 0;

    public GameField(int x, int y, int height, int width, String mapaSeleccionado) {
        super(x, y, height, width);
        setImage(mapaSeleccionado);
    }

    /**
     * Metodos de acceso
     */
//    public Player getPlayer() {
//        return player;
//    }
    public boolean getPartidaTerminada() {
        return partidaTerminada;
    }

    public void setPartidaTerminada(boolean partidaTerminada) {
        this.partidaTerminada = partidaTerminada;
    }

    public List<Sprite> getSprites() {
        return sprites;
    }
    /**
     * metodo para validar si hay colision
     */
    private boolean hayColision(Rectangle nuevoRect) {
        for (Sprite sprite : sprites) {
            if (sprite.getBoundaries().intersects(nuevoRect)) {
                return true;
            }
        }
        return false;
    }
     
    public Jugador getJugador() {
        return jugador;
    }

    /**
     * Metodo para agregar el carro a la pista
     */
    public void addCar() {
        int minX = 165; // Por ejemplo, límite izquierdo del carril
        int maxX = 360; // Límite derecho del carril
        int intentosMaximos = 100;
        int intentos = 0;

        int width = Car.WIDTH_CAR;
        int height = Car.HEIGH_CAR;
        int jugadorY = jugador != null ? jugador.getY() : getHeight();
        while (intentos < intentosMaximos) {
            int x = minX + (int) (Math.random() * (maxX - minX - width));
            int y = (int) (Math.random() * (jugadorY - height - 50));

            Rectangle nuevoRect = new Rectangle(x, y, width, height);

            if (!hayColision(nuevoRect)) {
                Car carro = new Car(x, y, width, height, this);
                this.sprites.add(carro);
                break;
            }

            intentos++;
        }

        if (intentos == intentosMaximos) {
            //System.out.println("No se pudo colocar el carro sin superposición tras " + intentosMaximos + " intentos.");
        }
    }

    /**
     * Metodo para agregar la gasolina a la pista
     */
    public void addGasolina() {
        int minX = 165; // Por ejemplo, límite izquierdo del carril
        int maxX = 360;
        int width = 50; // tamaño estimado, ajusta según tu sprite
        int height = 50;
        int intentosMaximos = 100;
        int intentos = 0;

        int jugadorY = jugador != null ? jugador.getY() : getHeight();

        while (intentos < intentosMaximos) {
            int x = minX + (int) (Math.random() * (maxX - minX - width));
            int y = (int) (Math.random() * (jugadorY - height - 50));

            Rectangle nuevoRect = new Rectangle(x, y, width, height);

            if (!hayColision(nuevoRect)) {
                Gasolina gasolina = new Gasolina(x, y, width, height, this);
                this.sprites.add(gasolina);
                // System.out.println("Gasolina agregada en (" + x + ", " + y + ")");
                break;
            }

            intentos++;
        }

        if (intentos == intentosMaximos) {
            //System.out.println("No se pudo colocar la gasolina sin superposición tras " + intentosMaximos + " intentos.");
        }

    }

    /**
     * Metodo para agregar la persona a la pista
     */
    public void addPerson() {
        int minX = 165;
        int maxX = 360;

        int intentos = 0;
        int maxIntentos = 50;
        int jugadorY = jugador != null ? jugador.getY() : getHeight();
        while (intentos < maxIntentos) {
            int xt = minX + (int) (Math.random() * (maxX - minX - Cone.WIDTH_CONE));
            int yt = (int) (Math.random() * (jugadorY - HEIGHT - 50));
            Rectangle nuevoRect = new Rectangle(xt, yt, Cone.WIDTH_CONE, Cone.HEIGH_CONE);
            if (!hayColision(nuevoRect)) {
                Person person = new Person(xt, yt, Cone.WIDTH_CONE, Cone.HEIGH_CONE, this);
                this.sprites.add(person);
                break;
            }
            intentos++;
        }
    }
    /**
     * Metodo para agregar el jugador a la pista
     */
    public void addJugador() {
        int startX = width / 2 - 25;  // centrado horizontalmente, restando la mitad del ancho del jugador
        int startY = height - 100;    // cerca de la parte inferior

        this.jugador = new Jugador(startX, startY, 50, 40);
        this.jugador.setMoto(new Moto());
        this.sprites.add(jugador);
    }

    /**
     * Metodo para agregar la moneda a la pista
     */
    public void addCurrency() {
        int minX = 165;
        int maxX = 360;

        int intentos = 0;
        int maxIntentos = 50;
        int jugadorY = jugador != null ? jugador.getY() : getHeight();
        while (intentos < maxIntentos) {
            int xt = minX + (int) (Math.random() * (maxX - minX - Currency.WIDTH_CURRENCY));
            int yt = (int) (Math.random() * (jugadorY - HEIGHT - 50));

            Rectangle nuevoRect = new Rectangle(xt, yt, Currency.WIDTH_CURRENCY, Currency.HEIGH_CURRENCY);
            if (!hayColision(nuevoRect)) {
                Currency currency = new Currency(xt, yt, Currency.WIDTH_CURRENCY, Currency.HEIGH_CURRENCY, this);
                add(currency);
                break;
            }
            intentos++;
        }
    }

    /**
     * Metodo para agregar el policia a la pista
     */
    public void addPolice() {
        if (this.police == null) {
            // Tomamos la posición X actual del jugador
            int startX = jugador.getX();

            // Posición Y debajo del campo de juego (para que suba persiguiendo)
            int startY = this.height + 50;
            this.police = new Police(startX, startY, Police.WIDTH, Police.HEIGHT);
            this.police.setGamefield(this);  //referencia del gamefield donde esta
            this.police.setDelay(50);
            this.police.iniciarPersecucion();
            this.sprites.add(police);
            //System.out.println("Policía agregado a la pista");
        }
    }
    /**
     * Metodo para eliminar el policia a la pista
     */
    public void eliminarPolice() {
        if (police != null) {
            sprites.remove(police);
            police.stop();
            police = null;
            //System.out.println("Policía eliminado ");
        }
    }
    /**
     * Metodo para iniciar el contador del tiempo
     */
    public void iniciarContadorTiempo() {
        contadorTiempo = new Thread(() -> {
            while (!partidaTerminada) {
                try {
                    Thread.sleep(2000);
                    if (jugador != null) {
                        jugador.aumentarPuntaje(1);
                        //System.out.println("Puntaje: " + jugador.getPuntaje());
                        refresh();
                    }
                } catch (InterruptedException e) {
                    break;
                }
            }
        });
        contadorTiempo.start();
    }
    /**
     * Metodo para iniciar la gasolina del jugador
     */
    public Thread iniciarGasolina() {
        ponerGasolina = new Thread(() -> {
            while (!partidaTerminada) {
                try {
                    Thread.sleep(7000); // 20 segundos
                    if (!partidaTerminada) {
                        addGasolina();
                        refresh();
                    }
                } catch (InterruptedException e) {
                    break;
                }
            }
        });
        ponerGasolina.start();
        return ponerGasolina;
    }
    
    public void eliminarElement(ElementType element) {
        sprites.remove(element);
    }

    /**
     * calcula como moverse el policia hacia el jugador
     */
    public void moverPoliceJugador() {
        if (police == null || jugador == null) {
            return;
        }

        int speed = police.getStep();

        // Movimiento horizontal: seguir al jugador en X
        int dx = jugador.getX() - police.getX();
        if (Math.abs(dx) > speed) {
            if (dx > 0) {
                police.setX(police.getX() + speed);
            } else {
                police.setX(police.getX() - speed);
            }
        }

        // Movimiento vertical: acercarse pero detenerse a cierta distancia
        int distanciaDeseadaY = 50; // la distancia vertical que debe mantener
        int objetivoY = jugador.getY() + distanciaDeseadaY;
        int dy = objetivoY - police.getY();
        //dice qué tan lejos está verticalmente el policía de su objetivo.
        if (Math.abs(dy) > speed) {
            // Solo se mueve si aún no está cerca de la distancia deseada
            if (dy > 0) {
                police.setY(police.getY() + speed);
            } else {
                police.setY(police.getY() - speed);
            }
        }

    }
    /**
     * metodo que valida si el policia atrapo a el jugador
     */
    public void PoliceCaughtProcess() {
        for (int i = 0; i < sprites.size(); i++) {
            if (sprites.get(i) instanceof Police) {
                Police police = (Police) sprites.get(i);

                if (jugador.checkCollision(police)) {
                    if (gameContainer instanceof GameWindow) {
                        ((GameWindow) gameContainer).atrapadoPolice();
                    }

                }
            }
        }
    }

    /**
     * Finaliza la partida actual.
     *
     *
     * elimina todos los sprites del campo de juego, refresca la pantalla y
     * muestra un mensaje de finalización en la consola. Si el contenedor del
     * juego es una ventana la cierra automáticamente.
     */
    public void finalizarPartida() {
        this.partidaTerminada = true; // Detener la lógica del juego
        if (contadorTiempo != null && contadorTiempo.isAlive()) {
            contadorTiempo.interrupt();  // Detener el hilo correctamente
        }
        Thread hiloCombustible = jugador.consumirConbustible();
        if (hiloCombustible != null && hiloCombustible.isAlive()) {
            hiloCombustible.interrupt(); // Detener el hilo correctamente
        }

        Thread hiloGasolina = iniciarGasolina();
        if (hiloGasolina != null && hiloGasolina.isAlive()) {
            hiloGasolina.interrupt(); // Detener el hilo correctamente
        }

        this.sprites.clear();
        this.refresh();
        //System.out.println("Partida finalizada.");
        if (gameContainer instanceof GameWindow) {
            ((GameWindow) gameContainer).terminarPartida();
        }
    }

    public void menejarCombustible() {
        jugador.consumirConbustible();
    }

    /**
     * Actualiza el estado del campo de juego en cada ciclo del juego.
     *
     * Este método realiza varias operaciones: - Consume combustible de la moto
     * del jugador. - Verifica si el jugador se queda sin vidas o sin
     * combustible, y finaliza la partida si es necesario. - Mueve los elementos
     * (carros, conos, personas, monedas, etc.) hacia abajo, simulando avance. -
     * Elimina los elementos que salen de la pantalla. - Desplaza la imagen de
     * fondo para crear un efecto de desplazamiento continuo. - Refresca la
     * pantalla para mostrar los cambios.
     */
    public void update() {

        if (jugador.getCantidadVidas() <= 0 || jugador.getMoto().isEstaSinConbustible()) {
            finalizarPartida();
            return;
        }

        //Mover obstaculos, monedas y demás elementos hacia abajo (simulando avance)
        // 1. Mover obstáculos, monedas y demás elementos hacia abajo (simulando avance)
        for (int i = 0; i < sprites.size(); i++) {
            Sprite sprite = sprites.get(i);
            if (sprite instanceof ElementType && !(sprite instanceof Police)) {
                ElementType element = (ElementType) sprite;

                element.setY(element.getY() + movimiento);

                // Elimina el elemento si ya pasó la parte inferior del campo
                if (element.getY() > height) {
                    eliminarElement(element);
                }
            }
        }
        // Movimiento automático del fondo
        Thread acelerador = new Thread(() -> {
            while (jugando) {
                try {
                    Thread.sleep(6000); // cada 15 segundos
                    if (movimiento < 15) {
                        movimiento++; // acelera el fondo
                    }
                } catch (InterruptedException e) {
                    break;
                }
            }
        });
        acelerador.start();

        offsetY -= movimiento;
        if (offsetY <= 0) {
            offsetY = getImage().getHeight(null);
        }
        processCollisionMotobike();
        if (police != null) {
            moverPoliceJugador();
        }
        refresh();  // Refresca pantalla
    }

    /**
     * Procesa las colisiones entre la motocicleta del jugador y los distintos
     * elementos del campo de juego.
     *
     * Recorre una copia de la lista de sprites para evitar
     * ConcurrentModificationException. Según el tipo de elemento con el que se
     * colisiona, se ejecutan distintas acciones:
     *
     * - Si colisiona con un Car o una Person: - Reproduce un efecto de sonido
     * aleatorio. - Resta una vida al jugador. - Elimina el objeto colisionado.
     * - Si el jugador queda sin vidas, finaliza la partida. - Si no, añade una
     * policía y ejecuta el proceso PoliceCaughtProcess.
     *
     * - Si colisiona con una Currency: - Reproduce el sonido de recogida de
     * moneda. - Incrementa la cantidad de monedas del jugador. - Elimina la
     * moneda recogida.
     *
     * - Si colisiona con una Gasolina: - Verifica si el jugador tiene al menos
     * 5 monedas. - Si tiene, reproduce sonido de carga, recarga combustible y
     * descuenta monedas. - Si no, muestra un mensaje temporal en pantalla
     * indicando que no tiene monedas suficientes. - Elimina la gasolina del
     * campo de juego.
     *
     * La copia de la lista se utiliza para evitar errores al modificar la
     * colección original durante la iteración.
     */
    private void processCollisionMotobike() {
        List<Sprite> copiaSprites = new ArrayList<>(sprites);  // Copia para evitar ConcurrentModificationException

        for (Sprite s : copiaSprites) {
            if (s instanceof ElementType) {
                ElementType element = (ElementType) s;

                if (jugador.checkCollision(element)) {
                    if (element instanceof Car || element instanceof Person) {
                        ReproducirSonido.playRandomEffectSound();
                        // Reducir vida al colisionar con Car o Person
                        jugador.eliminarVida();

                        // Eliminar el elemento tras la colisión
                        sprites.remove(element);

                        // Verificar si el jugador se quedó sin vidas
                        if (jugador.getCantidadVidas() <= 0) {
                            finalizarPartida();
                            return;
                        }

                        // Añadir policía tras chocar con Car o Person
                        addPolice();
                        PoliceCaughtProcess();

                    } else if (element instanceof Currency) {
                        // Sumar monedas y eliminar moneda
                        ((Currency) element).playCurrencySound();
                        jugador.recogerMoneda(); // acumula monedas en el jugador
                        sprites.remove(element);

                    } else if (element instanceof Gasolina) {
                        int cantidadMonedas = jugador.getMonedas();
                        if (cantidadMonedas >= 5) {
                            ((Gasolina) element).playGasolinaSound();
                            jugador.recargarConbustible(cantidadMonedas);
                            cantidadMonedas -= 5;
                            jugador.setMonedas(cantidadMonedas);
                        } else {
                            mensajePantalla = "Monedas insuficientes, no se recarga.";
                            tiempoMensaje = System.currentTimeMillis(); //
                            repaint();
                        }
                        sprites.remove(element);
                    }
//                    else {
//                        System.out.println("ERROR: GameField.processCollisionMotorbike. Tipo desconocido de ElementType");
//                    }
                }
            }
        }
    }

    @Override
    public void paint(Graphics g) {
        Image fondo = getImage();
        int fondoAlto = fondo.getHeight(null);

        // Calcula la posición vertical del fondo para hacer scroll infinito
        int y1 = -offsetY % fondoAlto;

        // Dibuja dos veces la imagen para rellenar todo el panel
        g.drawImage(fondo, 0, y1, getWidth(), fondoAlto, null);
        g.drawImage(fondo, 0, y1 + fondoAlto, getWidth(), fondoAlto, null);
        if (!partidaTerminada) {
            g.setFont(new Font("Arial", Font.BOLD, 20));
            g.drawString("Puntaje: " + jugador.getPuntaje(), 10, 20);
            g.drawString("Vidas: " + jugador.getCantidadVidas(), 10, 45);
            g.drawString("Gasolina: " + jugador.getMoto().getFuel(), 10, 70);
            g.drawString("Monedas: " + jugador.getMonedas(), 10, 90);
            if (!mensajePantalla.isEmpty()) {
                long ahora = System.currentTimeMillis();
                if (ahora - tiempoMensaje < 3000) {
                    g.drawString(mensajePantalla, 140, 70);
                } else {
                    mensajePantalla = ""; // borrar mensaje después de 3 segundos
                }
            }
        }

        // Copiar la lista para evitar problemas de concurrencia
        List<Sprite> copiaSprites = new ArrayList<>(sprites);

        for (Sprite sprite : copiaSprites) {
            if (sprite != null) {
                sprite.paint(g);
            }
        }
        //jugador.paint(g);
    }

    /**
     * Maneja los eventos de teclado para mover la motocicleta del jugador hacia
     * la izquierda o derecha.
     *
     * Si se presiona la tecla de flecha izquierda o derecha y el jugador
     * existe, realiza las siguientes acciones:
     *
     * - Mueve la motocicleta del jugador en la dirección correspondiente. -
     * Procesa las colisiones que puedan haberse producido tras el movimiento. -
     * Refresca el área de juego para actualizar la pantalla.
     *
     * @param code Código de la tecla presionada, correspondiente a las
     * constantes de KeyEvent.
     */
    public void keyPressed(int code) {
        if (code == KeyEvent.VK_LEFT
                | code == KeyEvent.VK_RIGHT) {
            if (jugador != null) {
                jugador.mover(code);
                processCollisionMotobike();
                refresh();
            }
        }
    }

    @Override
    public Rectangle getBordes() {
        return new Rectangle(x, y, width, height);
    }

    @Override
    public void refresh() {
        if (gameContainer != null) {
            gameContainer.refresh();
        }
    }

}
