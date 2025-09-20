package main.MundoNinja;


/**
 * Representa un estudiante ninja con nombre, edad, clan de procedencia y nivel de habilidad.
 * Se utiliza el patrón Builder aqui para crearlos de forma mas flexible
 */
public class EstudianteNinja {

    /** Nombre del estudiante ninja */
    private String nombre;
    /** Edad del estudiante (0-100) */
    private int edad;
    /** Clan de procedencia del estudiante */
    private ClanesDeProcedencia clan;
    /** Nivel de habilidad del estudiante (1-3)*/
    private int nivelHabilidad;
    
    /** Constructor privado para forzar el uso del Builder */
    private EstudianteNinja() {

    }

    /**
     * Builder para crear instancias de EstudianteNinja.
     */
    public static class Builder {
        /** Instancia interna que se construye paso a paso */
        private EstudianteNinja estudiante = new EstudianteNinja();

        /**
         * Asigna el nombre del estudiante.
         * @param nombreBuilder Nombre del estudiante
         * @return El Builder actual
         */
        public Builder nombre(String nombreBuilder) {
            estudiante.nombre = nombreBuilder;
            return this;
        }

        /**
         * Asigna la edad del estudiante.
         * @param edadBuilder Edad del estudiante
         * @return El Builder actual
         */
        public Builder edad(int edadBuilder) {
            estudiante.edad = edadBuilder;
            return this;
        }

        /**
         * Asigna el clan de procedencia del estudiante.
         * @param clanBuilder Clan del estudiante
         * @return El Builder actual
         */
        public Builder clanesDeProcedencia(ClanesDeProcedencia clanBuilder) {
            estudiante.clan = clanBuilder;
            return this;
        }

        /**
         * Asigna el nivel de habilidad del estudiante. (1-3)
         * @param nivelHabilidadBuilder Nivel de habilidad
         * @return El Builder actual
         */
        public Builder nivelHabilidad(int nivelHabilidadBuilder) {
            estudiante.nivelHabilidad = nivelHabilidadBuilder;
            return this;
        }

        /**
         * Construye y devuelve la instancia de EstudianteNinja.
         * @return EstudianteNinja construido
         */
        public EstudianteNinja build() {
            return estudiante;
        }
    }

    /** Getters y Setters */

    /** @return Nombre del estudiante */
    public String getNombre() { 
        return nombre; 
    }

    /** @param nombre Nombre del estudiante */
    public void setNombre(String nombre) {
        this.nombre = nombre; 
    }

    /** @return Edad del estudiante */
    public int getEdad() { 
        return edad; 
    }

    /** @param edad Edad del estudiante */
    public void setEdad(int edad) { 
        this.edad = edad; 
    }

    /** @return Clan del estudiante */
    public ClanesDeProcedencia getClan() { 
        return clan; 
    }

    /** @param clan Clan del estudiante */
    public void setClan(ClanesDeProcedencia clan) { 
        this.clan = clan; 
    }

    /** @return Nivel de habilidad del estudiante */
    public int getNivelHabilidad() { 
        return nivelHabilidad; 
    }

    /** @param nivelHabilidad Nivel de habilidad del estudiante */
    public void setNivelHabilidad(int nivelHabilidad) { 
        this.nivelHabilidad = nivelHabilidad; 
    }

    
    /**
     * Devuelve una representación en texto del estudiante ninja.
     * @return String con los datos del estudiante
     */
    @Override
    public String toString() {
        return String.format(
            "Estudiante Ninja {nombre: %s \nedad: %d \nclan: %s \nnivelHabilidad: %d}",
            nombre, edad, clan, nivelHabilidad
        );
    }
}
