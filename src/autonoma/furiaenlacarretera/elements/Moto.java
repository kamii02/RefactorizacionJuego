/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package autonoma.furiaenlacarretera.elements;

import autonoma.furiaenlacarretera.elements.Gasolina;
import com.sun.source.tree.Tree;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

/**
 *@author Valerie Moreno
 * @since 21/5/2025
 * @version 1.0.0
 */
public class Moto {
    /**
     * atributos
     */
    /**
     * atributo timer
     */
    private Timer timerCombustible;
    private int fuel;
    private Thread contadorGasolina;
    private boolean estaSinConbustible;
    /**
     * constructor
     */
    public Moto() {    
        this.fuel=100;
        this.estaSinConbustible = false;
    }

    public int getFuel() {
        return fuel;
    }

    public void setFuel(int fuel) {
        this.fuel = fuel;
    }

    public boolean isEstaSinConbustible() {
        return estaSinConbustible;
    }

    public void setEstaSinConbustible(boolean estaSinConbustible) {
        this.estaSinConbustible = estaSinConbustible;
    }
    

    
    /**
     * metodo de disminuir comburtible
     */
    public Thread disminuirCombustible() {
        contadorGasolina = new Thread(()->{
            while (!estaSinConbustible){
                try {
                    Thread.sleep(3000);
                    if(fuel>0){
                        fuel-=10;
                        System.out.println(fuel);
                    }
                    else{
                        estaSinConbustible=true;
                    }
                } catch (InterruptedException e) {
                    break;
                }
            }
        });
        contadorGasolina.start();
        return contadorGasolina;
    }

    /**
     * metodo de recargar comburtible
     */
    public void recargarCombustible(int cantidad){
        if (cantidad >= 5) {
            this.fuel = 100;
            estaSinConbustible = false;
        }
    }
}
