package patrones.factory.niveles;

import mvc.modelo.entidades.Bloque;
import patrones.factory.ia.ConfigNivel;
import mvc.modelo.entidades.Paleta;

public class NivelClasicoFactory implements NivelFactory {

    //falta dcc, NO OLVIDAR AAAAAAAAAAAAAAAAAAAAAAAA
    @Override
    public Nivel crearNivel(ConfigNivel conf) {
        // Crear el objeto Nivel CLASICO
        Nivel nivelClasico = new Nivel();

        // prop basicas del nivel clasico(1vs1)
        nivelClasico.setNombre("Nivel Clásico(1vs1)");
        //CHECAR, SI ES NIVEL CLASICO(1VS1) PUES QUE DIFICULTAD TENDRIA? POR EL 
        //MOMENTO ESTA BIEN ASIGNNARLE EL DE CONF.
        nivelClasico.setDificultad(conf.obtenerDificultad());
        //OBVIO NO ES PERSONALIZADO
        nivelClasico.setMapaPersonalizado(false); 
        //DEFAULT
        nivelClasico.setCreador("Sistema-Equipo-polimorfo");

        //checar donde meter resto
        Paleta paletaPlayer1 = new Paleta(50,300, 20,100);
        Paleta paletaPLayer2 = new Paleta(50,300, 20,100);

        
        //CHECAR COMO ES ESTA PARTE, SAUL DIJO QUE UNA MATRIZ(CREO)
        //SUPONGAMOS QUE EL PATRON ES TIPO: [XXXX-XX, NO SE]
        String patron = conf.obtenerPatron();
        if (patron != null && !patron.isEmpty()) {
            //FALTA IMPLEMNETAR ESTA PARTE, DEPENDIENDO COMO ESTE EL PATRON ESCRITO, NO SE COMO ESTARA. ENTONCES NO ES POSIBLE HACERLO.
            agregarBloquesDesdePatron(patron, nivelClasico);
        } else {
            //DEFAULT
            for (int i = 0; i < 5; i++) {
                // Suponia agregar 5 bloques, no se coo seira
                Bloque bloque = new Bloque(50 + i * 55, 50, 50, 20, 1);
                nivelClasico.agregarBloque(bloque);
            }
        }

        return nivelClasico;
    }

    //FALTA IMPLEMNTAR
    private void agregarBloquesDesdePatron(String patron, Nivel nivel) {
        // Interpretar patron y agregar bloques al nivel
        //falta implemnetar de acuerdo como este el patron string, ya sea
        //filtrar, cuando detetcte un _. 
    }
}
