package main.MundoNinja;

/**
 * Representa un Voluntario Ninja con nombre, edad, clan de procedencia, rango y nivel de habilidad.
 * Se utiliza el patrón Builder aqui para crearlos de forma mas flexible
 */
public class NinjaVoluntario {
    /**Nombre del NInja voluntario */
    private String nombre;
    /**Edad del ninja voluntario (0-100)*/
    private int edad;
    /** Clan de procedencia del Voluntario*/
    private ClanesDeProcedencia clan;
    /** Rango del ninja voluntario (genin, chunin, jonin)*/
    private Rangos rango;
    /** NIvel de habilidad del Ninja voluntario (4-6)*/
    private int nivelHabilidad;


    /**Constructor privado para forzar el uso de Builder */
    private NinjaVoluntario(){

    }

    /**
     * Builder para crear instancias de NInjaVoluntario
     */
    public static class Builder{
        /** Instancia interna que se construye paso a paso */
        private NinjaVoluntario voluntario = new NinjaVoluntario();
        
        /**
         * Asigna el nombre del voluntarioNinja
         * @param nombreBuilder nombre del voluntario
         * @return El builder actual
         */
        public Builder nombre(String nombreBuilder){
            voluntario.nombre = nombreBuilder;
            return this;
        }

        /**Asigna la edad del VoluntarioNinja
         * @param edadBuilder Edad del Voluntario
         * @return EL builder Actual
         */
        public Builder edad(int edadBuilder){
            voluntario.edad = edadBuilder;
            return this;
        }

        /**Asigna el clan de procedencia del Voluntario
         * @param clanBuilder Clan del voluntario
         * @return El Builder actual
         */
        public Builder clanesDeProcedencia(ClanesDeProcedencia clanBuilder){
            voluntario.clan = clanBuilder;
            return this;
        }

        /**
         * Asigna el nivel de rango (genin, chunin, jonin) del Voluntario
         * @param rangoBuilder Rango del Voluntario
         * @return El Builder actual
         */
        public Builder rango(Rangos rangoBuilder){
            voluntario.rango = rangoBuilder;
            return this;
        }

        /**
         * Asigna el nivel de habilidad del Voluntario (4-6)
         * @param nivelHabilidadBuilder
         * @return El Builder actual
         */
        public Builder nivelHabilidad(int nivelHabilidadBuilder){
            voluntario.nivelHabilidad = nivelHabilidadBuilder;
            return this;
        }

        /**
         * Construye y devuelve la instancia de VoluntarioNinja.
         * @return VolntarioNinjaNinja construido
         */
        public NinjaVoluntario build(){
            return voluntario;
        }
    }

    /** Getters y Setters */


    /** @return Nombre del VolntarioNinja */
    public String getNombre() { 
        return nombre; 
    }

    /** @param nombre Nombre del VolntarioNinja */
    public void setNombre(String nombre) {
        this.nombre = nombre; 
    }

    /** @return Edad del VolntarioNinja */
    public int getEdad() { 
        return edad; 
    }

    /** @param edad Edad del VolntarioNinja */
    public void setEdad(int edad) { 
        this.edad = edad; 
    }

    /** @return Clan del VolntarioNinja */
    public ClanesDeProcedencia getClan() { 
        return clan; 
    }

    /** @param clan Clan del VolntarioNinja */
    public void setClan(ClanesDeProcedencia clan) { 
        this.clan = clan; 
    }

    /** @return Nivel de habilidad del VolntarioNinja */
    public int getNivelHabilidad() { 
        return nivelHabilidad; 
    }

    /** @param nivelHabilidad Nivel de habilidad del VolntarioNinja */
    public void setNivelHabilidad(int nivelHabilidad) { 
        this.nivelHabilidad = nivelHabilidad; 
    }

    /** @return Rango del VoluntarioNinja */
    public Rangos getRango() {
        return rango;
    }

    /**@param rango Rango del Voluntario */
    public void setRango(Rangos rango) {
        this.rango = rango;
    }
    
    /**
     * Obtiene la capacidad máxima de aspirantes que puede liderar este voluntario
     * según su rango: Genin (1), Chunin (2), Jonin (3).
     *
     * @return El número máximo de aspirantes que puede liderar
     */
    public int getCapacidadVoluntarios(){
        switch (rango) {
            case GENIN:
                return 1;
            case CHUNIN:
                return 2;
            case JONIN:
                return 3;
            default:
                return 0;
        }
    }

    @Override
    public String toString() {
        return String.format(
            "Voluntario Ninja \n{nombre: %s \nedad: %d \nclan: %s\n Rango: %s \nnivelHabilidad: %d}",
            nombre, edad, clan, rango, nivelHabilidad
        );
    }
}
