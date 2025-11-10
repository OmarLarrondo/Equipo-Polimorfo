package modelo.nucleo_juego.patron_composite.bloque;

import modelo.nucleo_juego.patron_composite.Pelota;

/**
 * Alias de Bloques para compatibilidad con código existente.
 *
 * @author Equipo-polimorfo
 * @version 1.0
 */
public class Niebla extends TemplateBloque{

    public Niebla(int x, int y) {
        super(x,y);
    }

    @Override
    public ResultadoRebote recibirGolpe(Pelota pelota){
        pelota.neblina();
        return ResultadoRebote.ENGULLIDO;
    }
}
