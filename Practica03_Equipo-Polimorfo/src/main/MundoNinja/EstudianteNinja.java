package main.MundoNinja;
import main.MundoNinja.ClanesDeProcedencia;

public class EstudianteNinja {
    private String nombre;
    private int edad;
    private ClanesDeProcedencia clan;
    private int nivelHabilidad;

    private EstudianteNinja(){}

    public static class Builder{
        private EstudianteNinja estudiante = new EstudianteNinja();

        public Builder nombre(String nombreBuilder){
            estudiante.nombre = nombreBuilder;
            return this;
        }

        public Builder edad(int edadBuilder){
            estudiante.edad = edadBuilder;
            return this;
        }

        public Builder clanesDeProcedencia(ClanesDeProcedencia clanBuilder){
            estudiante.clan = clanBuilder;
            return this;
        }

        public Builder nivelHabilidad(int nivelHabilidadBuilder){
            estudiante.nivelHabilidad = nivelHabilidadBuilder;
            return this;
        }

        public EstudianteNinja build(){
            return estudiante;
        }
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public ClanesDeProcedencia getClan() {
        return clan;
    }

    public void setClan(ClanesDeProcedencia clan) {
        this.clan = clan;
    }

    public int getNivelHabilidad() {
        return nivelHabilidad;
    }

    public void setNivelHabilidad(int nivelHabilidad) {
        this.nivelHabilidad = nivelHabilidad;
    }
    
    @Override
    public String toString() {
        return "EstudianteNinja {" +
            "nombre='" + nombre + '\'' +
            ", edad=" + edad +
            ", clan=" + clan +
            ", nivelHabilidad=" + nivelHabilidad +
            '}';
    }


    
}
