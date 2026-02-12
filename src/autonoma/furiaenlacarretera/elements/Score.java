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
     * Número máximo de puntajes almacenados.
     */
    private static final int MAX_PUNTAJES = 10;
    
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
            archivoPuntajes.createNewFile();
        }

        this.lector = new LectorArchivoTextoPlano();
        this.escritor = new EscritorArchivoTextoPlano(rutaArchivo);
    }

    /**
     * Guarda un nuevo puntaje manteniendo solo los mejores MAX_PUNTAJES.
     *
     * @param nuevoPuntaje Puntaje a guardar.
     * @throws IOException Si ocurre un error de lectura o escritura.
     */
    public void guardarPuntaje(int nuevoPuntaje) throws IOException {
        ArrayList<Integer> puntajes = obtenerPuntajesDesdeArchivo();
        agregarYOrdenarPuntaje(puntajes, nuevoPuntaje);
        guardarEnArchivo(puntajes);
    }

    /**
     * Lee todos los puntajes almacenados.
     *
     * @return Lista de puntajes.
     * @throws IOException Si ocurre un error al leer el archivo.
     */
    public ArrayList<Integer> leerPuntajes() throws IOException {
        ArrayList<String> lineas = lector.leer(archivoPuntajes.getPath());
        return convertirLineasAPuntajes(lineas);
    }

    /* ============================= */
    /* MÉTODOS PRIVADOS REFACTORIZADOS */
    /* ============================= */

    private ArrayList<Integer> obtenerPuntajesDesdeArchivo() throws IOException {
        ArrayList<String> lineas = lector.leer(archivoPuntajes.getPath());
        return convertirLineasAPuntajes(lineas);
    }

    private ArrayList<Integer> convertirLineasAPuntajes(ArrayList<String> lineas) {
        ArrayList<Integer> puntajes = new ArrayList<>();

        for (String linea : lineas) {
            if (!linea.isBlank()) {
                puntajes.add(Integer.parseInt(linea.trim()));
            }
        }

        return puntajes;
    }

    private void agregarYOrdenarPuntaje(ArrayList<Integer> puntajes, int nuevoPuntaje) {
        puntajes.add(nuevoPuntaje);
        Collections.sort(puntajes, Collections.reverseOrder());

        if (puntajes.size() > MAX_PUNTAJES) {
            puntajes.subList(MAX_PUNTAJES, puntajes.size()).clear();
        }
    }

    private void guardarEnArchivo(ArrayList<Integer> puntajes) throws IOException {
        ArrayList<String> nuevasLineas = convertirPuntajesALineas(puntajes);
        escritor.escribir(nuevasLineas, archivoPuntajes.getPath());
    }

    private ArrayList<String> convertirPuntajesALineas(ArrayList<Integer> puntajes) {
        ArrayList<String> lineas = new ArrayList<>();

        for (int puntaje : puntajes) {
            lineas.add(String.valueOf(puntaje));
        }

        return lineas;
    }
}