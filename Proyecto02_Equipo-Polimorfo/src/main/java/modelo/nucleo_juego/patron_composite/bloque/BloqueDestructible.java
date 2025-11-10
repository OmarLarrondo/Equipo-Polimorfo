package modelo.nucleo_juego.patron_composite.bloque;

import modelo.nucleo_juego.patron_composite.Pelota;
import modelo.nucleo_juego.patron_strategy_colision.estrategias.EstrategiaColisionPelotaBloque;
/**
 * Alias de Bloques para compatibilidad con código existente.
 *
 * @author Equipo-polimorfo
 * @version 1.0
 */
public class BloqueDestructible extends TemplateBloque{

    private int saludRestante;

    public BloqueDestructible(int x, int y, int saludInicial) {
        super(x,y);
        this.saludRestante = saludInicial;
    }

    @Override
    public ResultadoRebote recibirGolpe(Pelota pelota){
        if (saludRestante-- > 0){
            EstrategiaColisionPelotaBloque.manejarColision(this, pelota);
            return ResultadoRebote.SOPORTADO;
        }
        return ResultadoRebote.SUSPENDIDO;
    }

    public int obtenerSalud(){
        return this.saludRestante;
    }

}
