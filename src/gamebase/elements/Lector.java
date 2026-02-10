/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gamebase.elements;

import java.io.IOException;
import java.util.ArrayList;

/**
 *Interfaz para la lectura de archivos
 * @author marti
 */
public interface Lector {
    /**
     * Lee un archivo y devuelve su contenido como una lista de líneas
     * 
     * @param localizacionArchivo Ruta del archivo
     * @return Lista de líneas del archivo
     * @throws IOException Si ocurre un error al leer el archivo
     */
    public abstract ArrayList<String> leer(String localizacionArchivo) throws IOException;
}
