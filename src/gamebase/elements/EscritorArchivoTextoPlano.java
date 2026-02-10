/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gamebase.elements;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.util.ArrayList;

/**
 *
 * @author marti
 */
public class EscritorArchivoTextoPlano implements Escritor{
        

    /**
     * charset en el cual se desea leer el archivo
     */
    private final Charset charset = Charset.forName("UTF-8");
    
    private String filePath;

    public EscritorArchivoTextoPlano(String filePath) {
        this.filePath = filePath;
    }
   ////////////////////////////////////////////////////////////////////////////
    /**
     * Sobreescribe el archivo de texto
     * 
     * @throws IOException 
     */
    @Override
    public void escribir(ArrayList<String> archivo, String ruta) throws IOException {
        File fichero = new File(ruta);
        // Usamos 'true' en el segundo parámetro de FileWriter para añadir contenido
        FileWriter writer = new FileWriter(fichero, true); // true significa append
        PrintWriter pw = new PrintWriter(writer);

        for (String linea : archivo) {
            pw.println(linea);
        }

        pw.close();
        writer.close();
    }

}
