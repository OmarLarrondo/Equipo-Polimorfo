package main.herramientas;

/**
 * Representa una Herramienta con nobre y un peso.
 * Se utiliza el patron Factory aqui , permite añadir nuevos tipos de productos 
 * sin modificar el código de los Ninjas
 * 
 */
public abstract class  Herramienta {
    /**Nombre de la herramienta (ejemplo: kunai,botiquin,etc) */
    protected String nombre;
    /**Peso de la herramienta (mas de 0.) */
    protected double peso;
    /**Cantidad de esta herramienta */
    protected int cantidad;

    /**
     * Constructor para inicializar un objeto de tipo Herramienta.
     * Crea una nueva Herramienta con nombre, peso y cantidad.
     *
     * @param nombre   El nombre de la herramienta.
     * @param peso     El peso de la herramienta (debe ser mayor que 0).
     * @param cantidad La cantidad inicial de herramientas (debe ser >= 0).
     * @throws IllegalArgumentException si el peso o la cantidad no cumplen las condiciones.
     */
    public Herramienta(String nombre, double peso, int cantidad) {
        this.nombre = nombre;
        if (peso <= 0) {
            throw new IllegalArgumentException("El peso debe ser mayor que 0");
        }
        if (cantidad < 0) {
            throw new IllegalArgumentException("La cantidad no puede ser negativa");
        }
        this.peso = peso;
        this.cantidad = cantidad;
    }

    /** @return Nombre de la Herramienta*/    
    public String getNombre() {
        return nombre;
    }

    /**@param nombre Nombre de la herramienta */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /** @return Peso de la Herramienta */
    public double getPeso() {
        return peso;
    }

    /**@param peso Peso de la herramienta */
    public void setPeso(double peso) {
        if (peso <= 0) {
            throw new IllegalArgumentException("El peso debe ser mayor que 0");
        }
        this.peso = peso;
    }

    /** @return Cantidad de herramientas de este tipo */
    public int getCantidad() {
        return cantidad;
    }

    /**@param cantidad Cantidad de las herramientas */
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
    
    @Override
    public String toString() {
        return String.format("Herramienta [nombre= %s, peso= %.2f, cantidad= %d]"
                            , nombre, peso, cantidad);
    }
    
    
    

}