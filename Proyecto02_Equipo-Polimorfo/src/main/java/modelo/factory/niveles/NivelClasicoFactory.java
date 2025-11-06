package modelo.factory.niveles;

import modelo.Bloques;
import modelo.factory.ConfigNivel;

public class NivelClasicoFactory implements NivelFactory {

    @Override
    public Nivel crearNivel(ConfigNivel conf) {
        // Crear el objeto Nivel
        Nivel nivelClasico = new Nivel();

        // Configurar propiedades básicas
        nivelClasico.setNombre("Nivel Clásico");
        nivelClasico.setDificultad(conf.obtenerDificultad());
        nivelClasico.setMapaPersonalizado(false); 
        nivelClasico.setCreador("Sistema-Equipo-polimorfo");

        String patron = conf.obtenerPatron();
        if (patron != null && !patron.isEmpty()) {
            agregarBloquesDesdePatron(patron, nivelClasico);
        } else {

            for (int i = 0; i < 5; i++) {
                // Suponiendo que Nivel tiene método agregarBloque(Bloque)
                Bloques bloque = new Bloques(50 + i * 55, 50, 50, 20, 1);
                nivelClasico.agregarBloque(bloque);
            }
        }

        return nivelClasico;
    }

    //FALTA IMPLEMNTAR
    private void agregarBloquesDesdePatron(String patron, Nivel nivel) {
        // Interpretar patron y agregar bloques al nivel
        //falta implemnetar
    }
}
