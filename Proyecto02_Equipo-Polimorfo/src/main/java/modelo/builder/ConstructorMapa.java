package modelo.builder;

import java.util.ArrayList;
import java.util.List;
import modelo.factory.Nivel;
import modelo.Bloques;

/**
 * Clase que aplica el patrón Builder para construir niveles
 * 
 * @author Equipo-polimorfo
 * @version 1.0
 */
public class ConstructorMapa {
    private Nivel nivel;
    private List<Bloques> bloques;
    
    /**
     * Reinicia el estado del constructor, creando un nuevo nivel y 
     * vaciando la lista de bloques.
     * 
     * @return el mismo constructor (para encadenamiento)
     */
    public ConstructorMapa reiniciar() {
        this.nivel = new Nivel();
        this.bloques = new ArrayList<>();
        return this;
    }

    /**
     * Asigna el nombre del nivel.
     * 
     * @param nombre nombre del nivel
     * @return el mismo constructor (para encadenamiento)
     */
    public ConstructorMapa establecerNombre(String nombre) {
        this.nivel.setNombres(nombre);
        return this;
    }

    /**
     * Establece la dificultad del nivel.
     * 
     * @param dificultad valor de dificultad (ej. 1 = fácil, 2 = medio, 3 = difícil) o sino como sea
     * @return el mismo constructor (para encadenamiento)
     */
    public ConstructorMapa establecerDificultad(int dificultad) {
        this.nivel.setDificultad(dificultad);
        return this;
    }

    /**
     * Agrega un bloque al nivel en construcción, dependiendo del tipo.
     * 
     * @param x posición X
     * @param y posición Y
     * @param tipo tipo de bloque (destructible, indestructible, bonus)
     * @return el mismo constructor (para encadenamiento)
     */
    public ConstructorMapa agregarBloque(double x, double y, TipoBloque tipo) {
        Bloques bloque;

        //NO SE SI SEA ASI :C
        switch (tipo) {
            case DESTRUCTIBLE:
                bloque = new Bloques(x, y, 50, 20, 1);
                break;
            case INDESTRUCTIBLE:
                bloque = new Bloques(x, y, 50, 20, 999);
                break;
            case BONUS:
                bloque = new Bloques(x, y, 50, 20, 1);
                break;
            default:
                bloque = new Bloques(x, y, 50, 20, 1);
                break;
        }

        bloques.add(bloque);
        return this;
    }

    /**
     * Agrega un bloque destructible (atajo de agregarBloque con tipo DESTRUCTIBLE).
     */
    public ConstructorMapa agregarBloqueDestructible(double x, double y) {
        return agregarBloque(x, y, TipoBloque.DESTRUCTIBLE);
    }

    /**
     * Agrega un bloque indestructible (atajo de agregarBloque con tipo INDESTRUCTIBLE).
     */
    public ConstructorMapa agregarBloqueIndestructible(double x, double y) {
        return agregarBloque(x, y, TipoBloque.INDESTRUCTIBLE);
    }

    /**
     * Agrega un patrón de bloques predefinido
     */
    public ConstructorMapa agregarPatronBloques(double x, double y) {
        for (int i = 0; i < 5; i++) {
            agregarBloqueDestructible(x + i * 55, y);
        }
        return this;
    }

    /**
     * Construye el nivel y devuelve
     * 
     * @return el nivel completo con sus bloques configurados
     */
    public Nivel construir() {
        this.nivel.setBloques(bloques);
        return this.nivel;
    }
}
