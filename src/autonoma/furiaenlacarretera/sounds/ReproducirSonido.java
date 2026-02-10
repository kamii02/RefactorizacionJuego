package autonoma.furiaenlacarretera.sounds;

import java.io.InputStream;
import java.util.Random;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

/**
 * Clase utilitaria para reproducir sonidos en formato WAV. Utiliza la API
 * javax.sound.sampled para cargar y reproducir clips de audio.
 *
 * Permite reproducir un sonido de fondo en loop, detenerlo, y reproducir
 * efectos de sonido aleatorios o específicos.
 */
public class ReproducirSonido {

    /**
     * Clip estático que almacena el sonido que se está reproduciendo
     * actualmente. Al ser estático, se comparte entre todas las llamadas a los
     * métodos de reproducción continua.
     */
    private static Clip clip;

    /**
     * Reproduce un archivo de sonido WAV desde la ruta de recursos indicada. Si
     * ya hay un sonido en reproducción, lo detiene antes de iniciar uno nuevo.
     * El sonido se reproduce en loop continuo hasta que se llame a
     * {@link #detener()}.
     *
     * @param nombreArchivo Nombre del archivo de sonido WAV ubicado en
     * "/autonoma/furiaenlacarretera/sounds/"
     */
    public static void playSound(String nombreArchivo) {
        try {
            detener();
            InputStream inputStream = ReproducirSonido.class.getResourceAsStream("/autonoma/furiaenlacarretera/sounds/" + nombreArchivo);
            if (inputStream == null) {
                System.out.println("Archivo no encontrado: " + nombreArchivo);
                return;
            }

            AudioInputStream audioStream = AudioSystem.getAudioInputStream(inputStream);
            clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            clip.start();
        } catch (Exception e) {
            System.out.println("Error al reproducir sonido: " + e.getMessage());
        }
    }

    /**
     * Detiene la reproducción del sonido que está en loop continuo, liberando
     * los recursos asociados. Si no hay sonido en reproducción, no hace nada.
     */
    public static void detener() {
        try {
            if (clip != null) {
                clip.stop();
                clip.flush();
                clip.close();
                clip = null;
            }
        } catch (Exception e) {
        }
    }

    /**
     * Reproduce aleatoriamente uno de los efectos de sonido disponibles.
     * Selecciona un archivo de sonido de efecto de la lista y lo reproduce una
     * vez.
     */
    public static void playRandomEffectSound() {
        String[] sonidos = {"personSound.wav", "sonidoAccidente.wav"};
        Random rand = new Random();
        String sonidoSeleccionado = sonidos[rand.nextInt(sonidos.length)];

        playEffectSound(sonidoSeleccionado);
    }

    /**
     * Reproduce un efecto de sonido WAV desde la ruta de recursos indicada.
     * Este sonido se reproduce una sola vez sin loop.
     *
     * @param nombreArchivo Nombre del archivo de sonido WAV ubicado en
     * "/autonoma/furiaenlacarretera/sounds/"
     */
    public static void playEffectSound(String nombreArchivo) {
        try {
            InputStream inputStream = ReproducirSonido.class.getResourceAsStream("/autonoma/furiaenlacarretera/sounds/" + nombreArchivo);

            AudioInputStream audioStream = AudioSystem.getAudioInputStream(inputStream);
            Clip efecto = AudioSystem.getClip();
            efecto.open(audioStream);
            efecto.start();
        } catch (Exception e) {
        }
    }

}
