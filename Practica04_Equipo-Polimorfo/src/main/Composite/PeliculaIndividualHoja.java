package Composite;

import java.net.Socket;

/**
 * Clase que representa una hoja, en el patron de diseño Composite. es el elemento mas paqueño.
 * Esta clase extiende de {@code ProductoComponente}, por ende debe implemntar los metodos abstractos y 
 * tendra las mismas propiedas y aparte tendra director, sinopsis, minutosDuracion.
 * 
 */
public class PeliculaIndividualHoja extends ProductoComponente {
    /**El nombre del director de la pelicula (ejemplo: Eugenio Derbez) */
    private String director;
    /**La sinopsis de la pelicula.(ejemplo: Wilson es un hombre de mediana edad, huraño y misántropo, que se siente más solo que nunca tras morir su padre, por lo que decide retomar el contacto con su exesposa. Ella le informa de que es el padre de una muchacha que ahora tiene 17 años y a la que dio en adopción. Entusiasmado con la idea de conocerla, el hombre parte en su busca.) */
    private String sinopsis;
    /** La duracion de la pelicula en minutos(ejemplo: 90.) */
    private int minutosDuracion;


    /**
     * Constructor para inicializar los atributos de ({@code PeliculaIndividualHoja}).
     * @param nombre El nombre de la pelicula.
     * @param genero EL genero de la pelicula.
     * @param precio EL precio de la pelicula.
     * @param director EL nombre del director de la pelicula.
     * @param sinopsis La sinopsis de la pelicula.
     * @param minutosDuracion Minutos de duracion de la pelicula.
     */
    public PeliculaIndividualHoja(String nombre, String genero, double precio,
                String director, String sinopsis, int minutosDuracion){
        super(nombre, genero, precio);
        this.director = director;
        this.sinopsis = sinopsis;
        this. minutosDuracion = minutosDuracion;
    }

    /**
     * @return El nombre del director de la pelicula.
     */
    public String getDirector() {
        return director;
    }

    /**
     * @param director EL nombre del director a establecer.
     */
    public void setDirector(String director) {
        this.director = director;
    }

    /**
     * @param minutosDuracion Los minutos de duracion a establecer.
     */
    public void setMinutosDuracion(int minutosDuracion) {
        this.minutosDuracion = minutosDuracion;
    }
    /**
     * @param sinopsis La sinopsis a establecer.
     */
    public void setSinopsis(String sinopsis) {
        this.sinopsis = sinopsis;
    }
    
    /**
     * Devuelve la duracion en minutos de la pelicula.
     * @return Los minutos de duracion de la pelicula.
     */
    @Override
    public int getMinutosDuracion() {
        return minutosDuracion;
    }

    /**
     * Devuelve la sinopsis de la pelicula.
     * @return La sinopsis de al pelicula.
     */
    @Override
    public String getSinopsis() {
        return sinopsis;
    }

    /**
     * Reproduce la película mostrando la información en consola.
     * <p>No devuelve ningún valor, simplemente simula la acción de reproducir.</p> //CHECAR NO SE
     */
    @Override
    public String reproducir() {
        //NO SE BIEN SI ES DIRECTO EN LA TERMINAL. ES QUE ES VOID, SEGUN YO IGUAL DEBERIA DEVOLVER UN STRING PEROOOOOO NO SEEEEEEE LOL.
        String salida = String.format("▶ Reproduciendo película: %s \nDuracion: %d\n Director: %s \n Genero: %s \n Sinopsis: %s\n Precio de renta: %.2f ", nombre, minutosDuracion,director,genero,sinopsis,precio );
        System.out.println(salida);
        return salida;
    }
    /**
     * Devuelve una representacion en cadena de esta Pelicula.<p></>
     * El formato es Nombre: [nombre], Director: [director] etc...
     * @return Una cadena que representa el objeto PeliculaIndividual.
     */

    @Override
    public String toString() {
        return "Pelicula [nombre=" + nombre + ", director=" + director + ", genero=" + genero
                + ", sinopsis=" + sinopsis + ", precio=" + precio + ", minutosDuracion=" + minutosDuracion + "]";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        PeliculaIndividualHoja other = (PeliculaIndividualHoja) obj;
        if (director == null) {
            if (other.director != null)
                return false;
        } else if (!director.equals(other.director))
            return false;
        if (sinopsis == null) {
            if (other.sinopsis != null)
                return false;
        } else if (!sinopsis.equals(other.sinopsis))
            return false;
        if (minutosDuracion != other.minutosDuracion)
            return false;
        return true;
    }
    
}
