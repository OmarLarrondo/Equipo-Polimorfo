package modelo.editor.niveles;

import modelo.gestion_ia.patron_factory.ConfigNivel;
import modelo.nucleo_juego.patron_composite.Paleta;
import modelo.nucleo_juego.patron_composite.bloque.Bloques;

public class NivelIAFactory implements NivelFactory{

    @Override
    public Nivel crearNivel(ConfigNivel conf) {
        Nivel nivelInteligente = new Nivel();
        nivelInteligente.setNombre("Nivel VS IA. -- Dificultad: "+ conf.obtenerDificultad());
        nivelInteligente.setDificultad(conf.getDificultad());
        //OBVIO NO?
        nivelInteligente.setMapaPersonalizado(false);
        nivelInteligente.setCreador("Sistema- Equipo-polimorfo");

        Paleta paletaJugador = new Paleta(50,300, 20,100);
        Paleta paletaIA = new Paleta(50,300, 20,100);
        //paletaIA.setControlador(new ControlPaletaIA(conf.obtenerDificultad()));
        
        //CHECAR COMO ES ESTA PARTE, SAUL DIJO QUE UNA MATRIZ(CREO)
        //SUPONGAMOS QUE EL PATRON ES TIPO: [XXXX-XX, NO SE]
        String patron = conf.obtenerPatron();
        if (patron != null && !patron.isEmpty()) {
            //FALTA IMPLEMNETAR ESTA PARTE, DEPENDIENDO COMO ESTE EL PATRON ESCRITO, NO SE COMO ESTARA. ENTONCES NO ES POSIBLE HACERLO.
            agregarBloquesDesdePatron(patron, nivelInteligente);
        } else {
            //DEFAULT
            for (int i = 0; i < 5; i++) {
                // Suponia agregar 5 bloques, no se coo seira
                Bloques bloque = new Bloques(50 + i * 55, 50, 50, 20, 1);
                nivelInteligente.agregarBloque(bloque);
            }
        }

        return nivelInteligente;
    }

    //FALTA IMPLEMNTAR
    private void agregarBloquesDesdePatron(String patron, Nivel nivel) {
        // Interpretar patron y agregar bloques al nivel
        //falta implemnetar de acuerdo como este el patron string, ya sea
        //filtrar, cuando detetcte un _. 
    }

}
