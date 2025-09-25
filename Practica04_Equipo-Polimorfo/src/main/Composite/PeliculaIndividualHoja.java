package Composite;
public class PeliculaIndividualHoja extends ProductoComponente {
    private String director;
    private String sinopsis;
    private int minutosDuracion;

    public PeliculaIndividualHoja(String nombre, String genero, double precio,
                String director, String sinopsis, int minutosDuracion){
        super(nombre, genero, precio);
        this.director = director;
        this.sinopsis = sinopsis;
        this. minutosDuracion = minutosDuracion;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public void setMinutosDuracion(int minutosDuracion) {
        this.minutosDuracion = minutosDuracion;
    }

    public void setSinopsis(String sinopsis) {
        this.sinopsis = sinopsis;
    }
    
    @Override
    public int getMinutosDuracion() {
        //aqui va su codigo 
        return 0;
    }

    @Override
    public String getSinopsis() {
        //aqui va su codigo 
        return null;
    }

    @Override
    public void reproducir() {
        //aqui va su codigo 
    }

    @Override
    public String toString() {
        return "Pelicula [nombre=" + nombre + ", director=" + director + ", genero=" + genero
                + ", sinopsis=" + sinopsis + ", precio=" + precio + ", minutosDuracion=" + minutosDuracion + "]";
    }


    



    
    
    
}
