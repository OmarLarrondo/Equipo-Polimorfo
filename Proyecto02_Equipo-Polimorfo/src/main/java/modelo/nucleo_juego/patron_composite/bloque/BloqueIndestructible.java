package modelo.nucleo_juego.patron_composite.bloque;

import modelo.nucleo_juego.patron_composite.Pelota;
import modelo.nucleo_juego.patron_strategy_colision.estrategias.EstrategiaColisionPelotaBloque;

/**
 * Alias de Bloques para compatibilidad con código existente.
 *
 * @author Equipo-polimorfo
 * @version 1.0
 */
public class BloqueIndestructible extends TemplateBloque{

    public BloqueIndestructible(int x, int y) {
        super(x,y);
    }

    @Override
    public ResultadoRebote recibirGolpe(Pelota pelota){
        EstrategiaColisionPelotaBloque.manejarColision(this, pelota);
        return ResultadoRebote.SOPORTADO;
    }
}
