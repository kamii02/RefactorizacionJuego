package autonoma.furiaenlacarretera.views;

import autonoma.furiaenlacarretera.elements.GameField;
import autonoma.furiaenlacarretera.elements.Jugador;
import autonoma.furiaenlacarretera.elements.Score;
import autonoma.furiaenlacarretera.sounds.ReproducirSonido;
import gamebase.elements.GraphicContainer;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import static java.awt.image.ImageObserver.HEIGHT;
import static java.awt.image.ImageObserver.WIDTH;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.JOptionPane;

/**
 *
 * @author Kamii
 */
public class GameWindow extends javax.swing.JFrame implements GraphicContainer {

    public static final int _WIDTH = 850;
    public static final int _HEIGHT = 550;
    private GameField gameField;

    // Double Buffer
    private BufferedImage imagenBuffer;
    private Graphics gImagenBuffer;
    private int maxScore = 0;
    String[] options = {"Sí", "No"};

    /**
     * Creates new form GameWindow
     */
    public GameWindow(GameField g) {
        gameField = g;
        setUndecorated(true);
        initComponents();
        cargarMaximo();
        reiniciarPartida();
        this.setLocationRelativeTo(null);
        ReproducirSonido.playSound("sonidoCars.wav");

        this.setSize(_WIDTH, _HEIGHT);

        // Crear la imagen y cargarla en memoria
        this.imagenBuffer = new BufferedImage(_WIDTH, _HEIGHT,
                BufferedImage.TYPE_INT_RGB);

        this.gImagenBuffer = this.imagenBuffer.getGraphics();
    }

    public void setMaxScore(int maxScore) {
        this.maxScore = maxScore;
        refresh();
    }

    private void manejarFinDePartida() {
        int opcion = JOptionPane.showOptionDialog(
                this,
                "¿Quieres reiniciar la partida?",
                "Confirmación",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]
        );

        boolean continuar = (opcion == JOptionPane.YES_OPTION);
        if (opcion == javax.swing.JOptionPane.YES_OPTION) {
            reiniciarPartida();
        } else {
            JOptionPane.showMessageDialog(null, " Gracias por jugar :)");
            try {
                // Guardamos el puntaje
                Score score = new Score("puntajes.txt");
                int puntajeActual = gameField.getJugador().getPuntaje();

                // Leemos todos los puntajes guardados
                ArrayList<Integer> puntajes = score.leerPuntajes();

                if (puntajeActual > maxScore) {
                    score.guardarPuntaje(puntajeActual);
                    maxScore = puntajeActual;
                }
                // Si el puntaje actual es el más alto, actualizamos el puntaje máximo
                if (!puntajes.isEmpty()) {
                    int nuevoMax = Collections.max(puntajes);
                    if (nuevoMax > maxScore) {
                        maxScore = nuevoMax;
                    }
                }

                // Actualizamos la pantalla con el nuevo puntaje máximo
                this.setMaxScore(gameField.maxScore);

            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Error al guardar puntaje: " + e.getMessage());
            }
            System.exit(0);
        }

    }

    /**
     * Carga el puntaje máximo desde un archivo de texto. Este método lee los
     * puntajes guardados en un archivo llamado "puntajes.txt". Si existen
     * puntajes, se carga el puntaje máximo, y si no, se establece el máximo
     * como 0. Si ocurre algún error durante la lectura del archivo, se muestra
     * un mensaje de error y el puntaje máximo se establece en 0.
     */
    private void cargarMaximo() {
        try {
            Score score = new Score("puntajes.txt");
            ArrayList<Integer> puntajes = score.leerPuntajes();
            if (!puntajes.isEmpty()) {
                maxScore = Collections.max(puntajes); // Carga el puntaje máximo
            } else {
                maxScore = 0;  // Si no hay puntajes, el máximo es 0
            }
        } catch (IOException e) {
            maxScore = 0;  // En caso de error al leer el archivo, el máximo es 0
            JOptionPane.showMessageDialog(this, "Error al cargar el puntaje máximo: " + e.getMessage());
        }
    }

    public void inicioPartida() {

    }

    private void reiniciarPartida() {
        gameField.getSprites().clear();
        gameField.setPartidaTerminada(false);
        gameField.addJugador();
        gameField.refresh();
        gameField.iniciarContadorTiempo();
        gameField.menejarCombustible();
        gameField.iniciarGasolina();
    }

    public void terminarPartida() {
        manejarFinDePartida();
    }

    public void atrapadoPolice() {
        JOptionPane.showMessageDialog(null, "¡Fuiste atrapado por el policia:( ! Has perdido.");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                formKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_Q) {
            System.exit(0);
        }
        if (evt.getKeyCode() == KeyEvent.VK_LEFT
                | evt.getKeyCode() == KeyEvent.VK_RIGHT) {
            gameField.keyPressed(evt.getKeyCode());
        }

    }//GEN-LAST:event_formKeyPressed

    @Override
    public void update(Graphics g) {
        // Pintar el FoodField encima del fondo
        if (gameField != null) {
            gameField.paint(gImagenBuffer);
            if (gameField.getJugador() != null) {
                gImagenBuffer.setColor(Color.YELLOW);
                gImagenBuffer.drawString("Puntaje Máximo: " + maxScore, 10, 110);
            }
        }

        //dibuja la imagen completa en pantalla
        g.drawImage(imagenBuffer, 0, 0, this);
    }

    @Override
    public void paint(Graphics g) {
        update(g);
    }

    @Override
    public Rectangle getBordes() {
        return new Rectangle(WIDTH, HEIGHT);
    }

    /**
     * Refresca la interfaz gráfica de la ventana del juego.
     */
    @Override
    public void refresh() {
        this.repaint();
    }

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
