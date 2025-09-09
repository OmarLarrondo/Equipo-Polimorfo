package main.productos;

import main.productos.helados.ComponenteHelado;

public class Helado extends Producto{
    private ComponenteHelado componenteHelado;

    public Helado(String id, String nombre, String descripcion, double precio
                ,ComponenteHelado componenteHelado ) {
        super(id, nombre, descripcion, precio);
        this.componenteHelado = componenteHelado;
    }



}