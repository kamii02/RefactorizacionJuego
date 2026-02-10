/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package autonoma.furiaenlacarretera.elements;

import static autonoma.furiaenlacarretera.elements.Car.HEIGH_CAR;
import static autonoma.furiaenlacarretera.elements.Car.WIDTH_CAR;
import com.sun.source.tree.Tree;
import gamebase.elements.GraphicContainer;
import gamebase.elements.Sprite;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

/**
 *
 * @author Valerie Moreno
 * @since 21/5/2025
 * @version 1.0.0
 */
public class Jugador extends Sprite {

    /**
     * Atributos
     *
     */
    /**
     * atributos de tamaño
     */
    public static final int WIDTH_JUGADOR = 50;
    public static final int HEIGH_JUGADOR = 50;
    /**
     * atributo de puntaje
     */
    private int puntaje;
    /**
     * atributo de instancia de moto
     */
    private Moto moto;
    private int monedas = 3;

    /**
     * atributos de vidas
     */
    private int cantidadVidas = 2;
    /**
     * atributos de imagen
     */
    private BufferedImage imagenBuffer;
    private Graphics g_imagenBuffer;
    private static final int STEP = 5;
    Thread hilo;

    /**
     * constructor
     */
    public Jugador(int x, int y, int width, int height) {
        super(x, y, height, width);
        this.puntaje = 0;
        this.moto = new Moto();
        setImage("chicaMoto.png");
        imagenBuffer = new BufferedImage(WIDTH_JUGADOR,
                HEIGH_JUGADOR,
                BufferedImage.TYPE_INT_RGB
        );
        //obtenemos los graficos
        g_imagenBuffer = imagenBuffer.getGraphics();
    }

    public void setMoto(Moto moto) {
        this.moto = moto;
    }

    public Moto getMoto() {
        return moto;
    }

    public int getCantidadVidas() {
        return cantidadVidas;
    }

    public void setCantidadVidas(int cantidadVidas) {
        this.cantidadVidas = cantidadVidas;
    }
    

    public int getPuntaje() {
        return puntaje;
    }

    public void setPuntaje(int score) {
        this.puntaje = score;
    }
    public void recogerMoneda() {
        monedas++;
    }
    

    public int getMonedas() {
        return monedas;
    }

    public void setMonedas(int monedas) {
        this.monedas = monedas;
    }
    

    @Override
    public void paint(Graphics g) {
        g.drawImage(getImage(), x, y, width, height, null);
    }

    public void aumentarPuntaje(int score) {
        this.puntaje += score;
    }

    public Thread consumirConbustible() {
        if (moto != null) {
            hilo=moto.disminuirCombustible();
        }
        return hilo;
    }

    public void recargarConbustible(int cantindad) {
        this.moto.recargarCombustible(cantindad);
    }

    public void mover(int direction) {
        switch (direction) {
            
            case KeyEvent.VK_LEFT:
                if (getX() - STEP >= 165) {
                    setX(getX() - STEP);
                }
                break;

            case KeyEvent.VK_RIGHT:
                if (getX() + width + STEP <= 360) {
                    setX(getX() + STEP);
                }
                break;
        }
    }
    
    /**
     * Disminuye una vida al jugador.
     * Si se queda sin vidas, podrías lanzar un evento o manejarlo desde GameField.
     */
    public void eliminarVida() {
        if (cantidadVidas > 0) {
            cantidadVidas--;
        }
    }

    /**
     * Verifica si el jugador perdió todas sus vidas.
     */
    public boolean estaMuerto() {
        return cantidadVidas <= 0;
    }

    

}
