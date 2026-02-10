package autonoma.furiaenlacarretera.elements;

import gamebase.elements.Escritor;
import gamebase.elements.EscritorArchivoTextoPlano;
import gamebase.elements.Lector;
import gamebase.elements.LectorArchivoTextoPlano;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Clase que utiliza un lector para leer un puntaje maximo
 *
 * @author Camila Prada
 * @version 1.0.0
 * @since 2025-05-20
 */
public class Score {
    /**
     * Archivo donde se almacenan los puntajes.
     */
    private File archivoPuntajes;

    /**
     * Lector encargado de leer los puntajes desde el archivo.
     */
    private final Lector lector;

    /**
     * Escritor encargado de guardar los puntajes en el archivo.
     */
    private final Escritor escritor;

    /**
     * Constructor que inicializa la clase Score con la ruta de archivo indicada.
     * Si el archivo no existe, lo crea automáticamente.
     *
     * @param rutaArchivo Ruta del archivo donde se almacenarán los puntajes.
     * @throws IOException Si ocurre un error al crear o acceder al archivo.
     */
    public Score(String rutaArchivo) throws IOException {
        this.archivoPuntajes = new File(rutaArchivo);
        if (!archivoPuntajes.exists()) {
            archivoPuntajes.createNewFile(); // Crea el archivo si no existe
        }
        this.lector = new LectorArchivoTextoPlano();
        this.escritor = new EscritorArchivoTextoPlano(rutaArchivo);
    }

    /**
     * Guarda un nuevo puntaje en el archivo.
     * El método lee los puntajes existentes, agrega el nuevo puntaje,
     * ordena la lista de mayor a menor y conserva solo los 10 mejores puntajes.
     *
     * @param nuevoPuntaje Puntaje que se desea guardar.
     * @throws IOException Si ocurre un error al leer o escribir en el archivo.
     */
    public void guardarPuntaje(int nuevoPuntaje) throws IOException {
        ArrayList<String> lineas = lector.leer(archivoPuntajes.getPath());
        ArrayList<Integer> puntajes = new ArrayList<>();

        for (String linea : lineas) {
            if (!linea.isBlank()) {
                puntajes.add(Integer.parseInt(linea.trim()));
            }
        }

        puntajes.add(nuevoPuntaje);
        Collections.sort(puntajes, Collections.reverseOrder()); // Mayor a menor

        if (puntajes.size() > 10) {
            puntajes = new ArrayList<>(puntajes.subList(0, 10));
        }

        ArrayList<String> nuevasLineas = new ArrayList<>();
        for (int p : puntajes) {
            nuevasLineas.add(String.valueOf(p));
        }

        escritor.escribir(nuevasLineas, archivoPuntajes.getPath());
    }

    /**
     * Lee todos los puntajes almacenados en el archivo.
     * Convierte cada linea leida en un entero, omitiendo líneas en blanco.
     *
     * @return Una lista con los puntajes almacenados.
     * @throws IOException Si ocurre un error al leer el archivo.
     */
    public ArrayList<Integer> leerPuntajes() throws IOException {
        ArrayList<String> lineas = lector.leer(archivoPuntajes.getPath());
        ArrayList<Integer> puntajes = new ArrayList<>();

        for (String linea : lineas) {
            if (!linea.isBlank()) {
                puntajes.add(Integer.parseInt(linea.trim()));
            }
        }

        return puntajes;
    }

}
