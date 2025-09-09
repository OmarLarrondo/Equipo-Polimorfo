package main.productos.helados;

import main.enums.SaborHelado;

public class HeladoSimple implements ComponenteHelado {

    private SaborHelado sabor;

    public HeladoSimple(SaborHelado sabor) {
        this.sabor = sabor;
    }

    @Override
    public String getDescripcion() {
        //aqui va codigo
        return null; 
    }

    @Override
    public double getPrecio() {
        //aqui va codigo
        return 0;
    }

}