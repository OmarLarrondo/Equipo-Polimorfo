package modelo.componente;

/**
 * Representa una fuente de alimentación (PSU) de computadora como componente hoja.
 * 
 * <p>Extiende {@link ComponenteHoja} e incluye atributos específicos de las fuentes,
 * como la potencia máxima en vatios y la certificación de eficiencia (ejemplo: 80 PLUS Gold).
 * Esta clase permite crear instancias de fuentes con nombre, marca, precio, tipo, 
 * potencia máxima y certificación.
 * 
 * Ejemplos de potencia: 500 W, 800 W, 1000 W, 1500 W.
 * Ejemplos de certificación: "80 PLUS Bronze", "80 PLUS Gold", "80 PLUS Platinum".
 * 
 * @author Equipo-polimorfo
 * @version 1.0
 */
public class FuenteAlimentacion extends ComponenteHoja {
    /** La potencia maxima de la fuente(ejemplo:500W , 700W, 15000W ) */
    private int potenciaMaxima;
    /** La certificacion de la fuente (ejemplo: "Gold", "Platinum")*/
    private String certificacion;

    /**
     * COsntructor para incializar cada uno de los atributos de la fuente,
     * @param nombre         el nombre de la fuente (ejemplo: "EVGA 1000 G5")
     * @param precio         el precio de la fuente(ejemplo: 1000.00 )
     * @param marca          la marca de la fuente(ejemplo: "EVGA", "XPG")
     * @param tipo           el tipo de componente (siempre "Fuente de alimentación")
     * @param potenciaMaxima la potencia máxima en vatios (ejemplo: 800, 1000, 1500)
     * @param certificacion  la eficiencia certificada (ejemplo: "80 PLUS Bronze", "80 PLUS Gold")
     */
    public FuenteAlimentacion(String nombre, double precio, String marca, String tipo, int potenciaMaxima,
            String certificacion) {
        super(nombre, precio, marca, tipo);
        this.potenciaMaxima = potenciaMaxima;
        this.certificacion = certificacion;
    }

    /**
     * la potencia maxima.
     * @return la potenci amaxima que soporta
     */
    public int getPotenciaMaxima() {
        return potenciaMaxima;
    }

    /**
     * La certificacion dela fuente.
     * @return la certificacion de la fuente 
     */
    public String getCertificacion() {
        return certificacion;
    }

    //SEGUN YO NO HACE FALTA DOC ESTO PORQUE YA ESTA EN LA CLASE ABSTRACT
    @Override
    public String mostrarDetalles() {
        return String.format(
            "Fuente: %s | Marca: %s | Potencia: %d W | Certificación: %s | Precio: $%.2f",
            obtenerNombre(), obtenerMarca(), potenciaMaxima, certificacion, obtenerPrecio()
        );
    }

}
