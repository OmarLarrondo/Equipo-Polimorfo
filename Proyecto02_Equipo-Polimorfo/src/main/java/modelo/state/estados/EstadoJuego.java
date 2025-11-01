package main.java.modelo.state.estados;

//clase base (superclase) para representar cualquier tipo de evento de entrada del usuario(teclado, el ratón, o cualquier otro dispositivo de entrada.)
import java.awt.event.InputEvent;

/**
 * La interfaz {@code EstadoJuego} define el comportamiento general de los estados
 * dentro del ciclo de vida de un juego. Cada estado representa una etapa o modo
 * del juego (por ejemplo: menú, en juego, pausa, fin del juego, etc.).
 *
 * <p>Implementaciones concretas de esta interfaz deben definir cómo se comporta el
 * juego al entrar en un estado, durante su actualización y al salir de él, así
 * como cómo responde a las entradas del usuario.</p>
 *
 * @author Equipo-polimorfo
 * @version 1.0
 */
public interface EstadoJuego {

    /**
     * Se ejecuta una sola vez al entrar en este estado del juego.
     *
     * @param contexto el contexto del juego que administra los estados.
     */
    void entrar(ContextoJuego contexto);

    /**
     * Actualiza la lógica del estado del juego según el tiempo transcurrido.
     * 
     * @param tiempoDelta el tiempo (en segundos) transcurrido desde la última actualización.
     */
    void actualizar(double tiempoDelta);

    /**
     * Se ejecuta una sola vez al salir de este estado del juego.
     *
     * @param contexto el contexto del juego que administra los estados.
     */
    void salir(ContextoJuego contexto);

    /**
     * Maneja las entradas del usuario relevantes para este estado del juego.
     *
     * @param entrada el evento de entrada recibido (por ejemplo, pulsaciones de teclas o clics del ratón).
     */
    void manejarEntrada(InputEvent entrada);
}
