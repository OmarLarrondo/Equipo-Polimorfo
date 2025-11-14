package modelo.editor.builder;

import modelo.editor.niveles.Nivel;

/**
 * Interfaz que define el contrato para un <b>Builder</b> encargado de la
 * construcción paso a paso de objetos {@link Nivel}.
 * 
 * <p>
 * Este patrón permite crear niveles de forma flexible, mediante una secuencia controlada de
 * operaciones. Todas las implementaciones deben permitir la creación de un
 * nivel válido.
 * </p>
 * 
 * @author Equipo-polimorfo
 * @version 1.0
 */
public interface BuilderNivel {

    /**
     * Reinicia el estado interno del builder, para hacer uno nuevo.
     *
     * @return la instancia del builder para permitir encadenamiento.
     */
    BuilderNivel reiniciar();

    /**
     * Establece el nombre del nivel que se está construyendo.
     *
     * @param nombre nombre del nivel.
     * @return la instancia del builder para permitir encadenamiento.
     */
    BuilderNivel establecerNombre(String nombre);

    /**
     * Establece la dificultad del nivel.
     *
     * @param dificultad valor numérico asociado a la dificultad.
     * @return la instancia del builder para permitir encadenamiento.
     */
    BuilderNivel establecerDificultad(int dificultad);



    //CHECAAAAAAAAAAAAAR, Y ADAPTAR DE ACUERDO A SAUL.
    
    /**
     * Agrega un bloque al nivel en construcción.
     *
     * @param x posición X del bloque dentro del nivel.
     * @param y posición Y del bloque dentro del nivel.
     * @param tipo tipo de bloque a crear (por ejemplo, destructible, indestructible, etc.).
     * @return la instancia del builder para permitir encadenamiento.
     */
    BuilderNivel agregarBloque(double x, double y, TipoBloque tipo);

    /**
     * Finaliza la construcción y devuelve el nivel completamente configurado.
     *
     * @return instancia de {@link Nivel} creada por el builder.
     */
    Nivel construir();
}
