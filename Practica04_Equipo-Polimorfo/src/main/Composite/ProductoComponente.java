package Composite;
public abstract class ProductoComponente {

    protected String nombre;
    protected String genero;
    protected double precio;

    public ProductoComponente(String nombre, String genero, double precio) {
        this.nombre = nombre;
        this.genero = genero;
        this.precio = precio;
    }

    public String getNombre() {
        return nombre;
    }

    public String getGenero() {
        return genero;
    }

    public double getPrecio() {
        return precio;
    }

    public abstract int getMinutosDuracion();  
    public abstract String getSinopsis();  
    public abstract void reproducir();  
}
