package main.productos;

import main.enums.TipoMasa;
import main.productos.preparadores.PreparadorPizza;

public class Pizza extends Producto{
    private boolean esVegetariana;
    private TipoMasa tipoMasa;
    private PreparadorPizza preparadorPizza;

    public Pizza(String id, String nombre, String descripcion, double precio,
        boolean esVegetariana, TipoMasa tipoMasa, PreparadorPizza preparadorPizza) {
        super(id, nombre, descripcion, precio);
        this.esVegetariana = esVegetariana;
        this.tipoMasa = tipoMasa;
        this.preparadorPizza = preparadorPizza;
    }

    


    
}