package Composite;
import java.util.ArrayList;
import java.util.List;

public class Saga extends ProductoComponente{
    private List<ProductoComponente> componentes;
    private double descuento;

    public Saga(String nombre, String genero, double precio,
                double descuento) {
        super(nombre, genero, precio);
        this.descuento = descuento;
        this.componentes = new ArrayList<>();
    }

    public List<ProductoComponente> getComponentes() {
        return componentes;
    }



    public void setComponentes(List<ProductoComponente> componentes) {
        this.componentes = componentes;
    }



    public double getDescuento() {
        return descuento;
    }



    public void setDescuento(double descuento) {
        this.descuento = descuento;
    }

    

    @Override
    public int getMinutosDuracion() {
        // aqui va su codigo 
        return 0;
    }

    @Override
    public String getSinopsis() {
        // aqui va su codigo 
        return null;
    }

    @Override
    public void reproducir() {
        // aqui va su codigo 
    }

    @Override
    public String toString() {
        return "Saga [nombre=" + nombre + ", genero=" + genero + ", precio=" + precio + ", componentes=" + componentes
                + ", descuento=" + descuento + "]";
    }

    
    
}
