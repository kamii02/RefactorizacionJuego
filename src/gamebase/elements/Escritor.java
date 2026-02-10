/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gamebase.elements;

import java.io.IOException;
import java.util.ArrayList;

/**
 *Interfaz que define el comportamiento para escribir datos en un archivo.
 * @author marti
 * @version 1.0.0
 * @since 2025-05-02
 */

public interface Escritor {
     /**
     * Escribe el archivo de memoria interna por lineas
     *Método abstracto que escribe el contenido de una lista en un archivo en
     * la ruta especificada.
     * 
     * @param archivo
     * @throws java.io.IOException si el archivo no existe
     */
    public abstract void escribir(ArrayList<String> archivo, String ruta) throws IOException ;
}