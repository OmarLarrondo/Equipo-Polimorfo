package main.estado.pedido;

import main.sistema.Sucursal;

public interface EstadoPedido {

    //en el diagrama de clases, no entendi la parte de -pedido: Pedido
    //NO SE SI SEA FINAL O COMO XD

    public void procesarAgregadoArticulo();

    public void procesarEnvioOrden(Sucursal sucursal);
    
    public void procesarPeticionEntrega(Sucursal sucursal);


}