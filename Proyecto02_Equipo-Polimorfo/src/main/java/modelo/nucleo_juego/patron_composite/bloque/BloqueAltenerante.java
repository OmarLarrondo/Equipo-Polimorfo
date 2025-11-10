package modelo.nucleo_juego.patron_composite.bloque;

import modelo.nucleo_juego.patron_composite.Pelota;
import modelo.nucleo_juego.patron_strategy_colision.estrategias.EstrategiaColisionPelotaBloque;

/**
 * Alias de Bloques para compatibilidad con código existente.
 *
 * @author Equipo-polimorfo
 * @version 1.0
 */
public class BloqueAltenerante extends TemplateBloque{

    private boolean tangible;

    public BloqueAltenerante(int x, int y, boolean tangible) {
        super(x,y);
        this.tangible = tangible;
    }

    @Override
    public ResultadoRebote recibirGolpe(Pelota pelota){
        this.tangible = !this.tangible;
        if(tangible){
            EstrategiaColisionPelotaBloque.manejarColision(this, pelota);
            return ResultadoRebote.SUSPENDIDO;
        }

        return ResultadoRebote.REACTIVADO;
    }
}
