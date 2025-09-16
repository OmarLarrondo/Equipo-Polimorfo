package main.MundoNinja;

public class NinjaVoluntario {
    private String nombre;
    private int edad;
    private ClanesDeProcedencia clan;
    private Rangos rango;
    private int nivelHabilidad;

    private NinjaVoluntario(){}

    public static class Builder{
        private NinjaVoluntario voluntario = new NinjaVoluntario();

        public Builder nombre(String nombreBuilder){
            voluntario.nombre = nombreBuilder;
            return this;
        }

        public Builder edad(int edadBuilder){
            voluntario.edad = edadBuilder;
            return this;
        }

        public Builder clanesDeProcedencia(ClanesDeProcedencia clanBuilder){
            voluntario.clan = clanBuilder;
            return this;
        }

        public Builder rango(Rangos rangoBuilder){
            voluntario.rango = rangoBuilder;
            return this;
        }

        public Builder nivelHabilidad(int nivelHabilidadBuilder){
            voluntario.nivelHabilidad = nivelHabilidadBuilder;
            return this;
        }

        public NinjaVoluntario build(){
            return voluntario;
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

    public Rangos getRango() {
        return rango;
    }

    public void setRango(Rangos rango) {
        this.rango = rango;
    }

    public int getNivelHabilidad() {
        return nivelHabilidad;
    }

    public void setNivelHabilidad(int nivelHabilidad) {
        this.nivelHabilidad = nivelHabilidad;
    }
    
    private int getCapacidadvoluntarios(){
        //aqui va codifgo 
        return 0;
    }

    @Override
    public String toString() {
        return "NinjaVoluntario {" +
            "nombre='" + nombre + '\'' +
            ", edad=" + edad +
            ", clan=" + clan +
            ", rango=" + rango +
            ", nivelHabilidad=" + nivelHabilidad +
            '}';
    }
}
