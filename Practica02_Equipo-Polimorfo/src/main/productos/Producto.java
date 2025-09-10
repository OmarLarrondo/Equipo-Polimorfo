package main.productos;

public abstract class Producto {
    protected String id;
    protected String nombre;
    protected String descripcion;
    protected double precio;

    public Producto(String id, String nombre, String descripcion, double precio) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
    }

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public double getPrecio() {
        return precio;
    }

    /**
     * Cada clase implemnta una dif implemnetacion dle metodo
     */
    public void preparar(){
        
    }
    

}