package modelo.componente;

public abstract class ComponenteHoja implements ComponentePC {

    private String nombre;
    private double precio;
    private String marca;
    private String tipo;

    public ComponenteHoja(String nombre, double precio, String marca, String tipo) {
        this.nombre = nombre;
        this.precio = precio;
        this.marca = marca;
        this.tipo = tipo;
    }


    public String obtenerNombre(){
        return nombre;
    }
    public double obtenerPrecio(){
        return precio;
    }
    public String obtenerMarca(){
        return marca;
    }
    public String obtenerTipo(){
        return tipo;
    }
    public String mostrarDetalles(){
        return toString();
    }
    //CREO QUE ES ABSTRACTO PORQUE NE EL DIAGRMA, MOTHERBOARD CPU NO IMPLEMNETAR Y OTRAS NO. no se xd 
    public abstract boolean esCompatibleConComponentePC();
}
