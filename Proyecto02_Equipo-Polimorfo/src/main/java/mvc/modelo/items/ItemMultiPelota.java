package mvc.modelo.items;

import java.util.ArrayList;
import java.util.List;

import mvc.modelo.entidades.ObjetoJuego;
import mvc.modelo.entidades.ObjetoJuegoCompuesto;
import mvc.modelo.entidades.Pelota;

public class ItemMultiPelota implements Item{
    private int cantidadPelotas;
    private double duracion;
    private boolean activo;

    private List<Pelota> pelotasCreadas;



    public ItemMultiPelota(int cantidadPelotas, double duracion, boolean activo) {
        this.cantidadPelotas = cantidadPelotas;
        this.duracion = duracion;
        this.activo = activo;
        this.pelotasCreadas = new ArrayList<>();

    }


    @Override
    public void aplicar(ObjetoJuego objeto) {
        if(objeto instanceof ObjetoJuegoCompuesto contenedor){
            List<ObjetoJuego> hijosConter = contenedor.obtenerHijos();
            int creadas = 0;
            for(int i = 0; i < hijosConter.size(); i++){
                if(hijosConter.get(i) instanceof Pelota p){
                    if(creadas>= cantidadPelotas) break;
                    //crear una copia de la pelota
                    Pelota copiaPelota = p.clonar();
                    double aleatorio = 0.8 + Math.random() * 0.4; // [0.8, 1.2]
                    copiaPelota.establecerVelocidadX(p.obtenerVelocidadX()* aleatorio);
                    copiaPelota.establecerVelocidadY(p.obtenerVelocidadY()* aleatorio);
                    
                    //VER SI HAY GRADOS DE MOVIMIENTO DE LA PELOTA, SI SI, MODIFICARLOS.

                    contenedor.agregar(copiaPelota);
                    pelotasCreadas.add(copiaPelota);
                    creadas++;
                    activo = true;
                }
            }
        }    

    }
    @Override
    public double obtenerDuracion() {
        return duracion;
    }
    @Override
    public boolean estaActivo() {
        return activo;
    }
    @Override
    public void desactivar(ObjetoJuego objeto) {
        if(objeto instanceof ObjetoJuegoCompuesto contenedor){
            for (Pelota p : pelotasCreadas) {
                contenedor.eliminar(p);
            }
            activo = false;
        }
    }

}
