package Adapter;

public class DiscoMusicalLegacy {
    private String nombre;
    private String artista;
    private String generoMusical;
    private int añoEstreno;
    private double precioVenta;

    public DiscoMusicalLegacy(String nombre, String artista, String generoMusical, int añoEstreno, double precioVenta) {
        this.nombre = nombre;
        this.artista = artista;
        this.generoMusical = generoMusical;
        this.añoEstreno = añoEstreno;
        this.precioVenta = precioVenta;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getArtista() {
        return artista;
    }

    public void setArtista(String artista) {
        this.artista = artista;
    }

    public String getGeneroMusical() {
        return generoMusical;
    }

    public void setGeneroMusical(String generoMusical) {
        this.generoMusical = generoMusical;
    }

    public int getAñoEstreno() {
        return añoEstreno;
    }

    public void setAñoEstreno(int añoEstreno) {
        this.añoEstreno = añoEstreno;
    }

    public double getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(double precioVenta) {
        this.precioVenta = precioVenta;
    }

    public String obtenerDetallesCompletos(){
        //aqui va su codigo
        return null;
    }

    

    

    
}
