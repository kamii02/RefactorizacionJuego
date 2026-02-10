/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gamebase.elements;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;

/**
 *Implementación de Lector para archivos de texto plano
 * @author marti
 */
public class LectorArchivoTextoPlano implements Lector {
    /**
     * Archivo guardado por lineas
     */
    private ArrayList<String> archivo;
    /**
     * charset en el cual se desea leer el archivo
     */
    private Charset charset = Charset.forName("UTF-8");

    ////////////////////////////////////////////////////////////////////////
    /**
     * Contructor de la clase
     */
    public LectorArchivoTextoPlano() {
        this.archivo = new ArrayList<>();
    }

    /////////////////////////////////////////////////////////////////////////
    /**
     * Lee el archivo de texto dónde tenemos el programa que deseamos
     * interpretar
     *
     * @param localizacionArchivo ruta del archivo
     * @return arreglo con el archivo de texto
     * @throws IOException: si el archivo no existe
     */
    @Override
    public ArrayList<String> leer(String localizacionArchivo) throws IOException {
        archivo.clear();
        File file = new File(localizacionArchivo);

        // Verifica si el archivo existe antes de intentar leerlo
        if (!file.exists()) {
            // Si el archivo no existe, creamos uno vacío
            file.createNewFile();
            return archivo;
        }

        FileReader reader = new FileReader(file, charset);
        BufferedReader buffer = new BufferedReader(reader);

        String linea;
        while ((linea = buffer.readLine()) != null) {
            archivo.add(linea);
        }

        reader.close();
        return archivo;
    }
}
