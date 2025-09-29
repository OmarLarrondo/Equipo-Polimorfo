import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

import Composite.ProductoComponente;
import Composite.PeliculaIndividualHoja;
import Composite.Saga;
import Adapter.DiscoMusicalLegacy;
import Adapter.DiscoMusicalAdapter;
import Modelo.Catalogo;
import Modelo.MenuInteractivo;
import Visualizar.VisualizadorConsola;

/**
 * Clase principal que inicializa y ejecuta la aplicacion de RockBuster.
 * <p>
 * Esta clase se encarga de crear todos los productos iniciales del catalogo,
 * incluyendo peliculas individuales, sagas anidadas y discos musicales adaptados
 * del sistema legacy. Sigue los principios de programacion funcional para la
 * creacion e inicializacion de componentes.
 * </p>
 *
 * @author Equipo-Polimorfo
 * @version 1.0
 * @since 2025-09-28
 */
public class Main {

    /**
     * Punto de entrada principal de la aplicacion.
     * <p>
     * Inicializa el catalogo con productos predefinidos y ejecuta la aplicacion
     * interactiva de RockBuster.
     * </p>
     *
     * @param args argumentos de linea de comandos (no utilizados)
     */
    public static void main(String[] args) {
        ejecutarAplicacion(inicializarCatalogo());
    }

    /**
     * Crea la lista inicial de productos para el catalogo de RockBuster.
     * <p>
     * Genera una variedad de productos incluyendo peliculas individuales
     * de Star Wars, sagas anidadas y discos musicales adaptados del sistema
     * legacy. Cumple con el requisito de tener al menos 8 productos variados.
     * </p>
     *
     * @return una lista inmutable que contiene todos los productos iniciales
     */
    private static List<ProductoComponente> crearProductosIniciales() {
        return Arrays.asList(
            crearPeliculasIndividuales(),
            crearSagasAnidadas(),
            crearDiscosMusicaleasAdaptados()
        ).stream()
            .flatMap(List::stream)
            .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }

    /**
     * Crea las peliculas individuales de Star Wars.
     *
     * @return lista de peliculas individuales
     */
    private static List<ProductoComponente> crearPeliculasIndividuales() {
        return Arrays.asList(
            new PeliculaIndividualHoja(
                "Star Wars Episodio IV: Una Nueva Esperanza",
                "Ciencia Ficcion",
                45.00,
                "George Lucas",
                "Una joven princesa es rescatada por un granjero y un viejo caballero Jedi, " +
                "quienes descubren que ella tiene informacion vital para destruir una estacion espacial.",
                121
            ),
            new PeliculaIndividualHoja(
                "Star Wars Episodio V: El Imperio Contraataca",
                "Ciencia Ficcion",
                47.00,
                "Irvin Kershner",
                "Los rebeldes huyen del Imperio mientras Luke entrena con Yoda y descubre " +
                "una terrible verdad sobre su pasado.",
                124
            ),
            new PeliculaIndividualHoja(
                "Star Wars Episodio VI: El Retorno del Jedi",
                "Ciencia Ficcion",
                46.00,
                "Richard Marquand",
                "Luke debe enfrentar a Darth Vader y al Emperador para salvar a la galaxia " +
                "y redimir a su padre.",
                131
            ),
            new PeliculaIndividualHoja(
                "Rogue One: Una Historia de Star Wars",
                "Ciencia Ficcion",
                55.00,
                "Gareth Edwards",
                "Un grupo de rebeldes se sacrifica para robar los planos de la Estrella de la Muerte.",
                133
            )
        );
    }

    /**
     * Crea las sagas anidadas de Star Wars.
     *
     * @return lista de sagas compuestas
     */
    private static List<ProductoComponente> crearSagasAnidadas() {
        Saga trilogiaOriginal = new Saga(
            "Saga: Trilogia Original",
            "Ciencia Ficcion",
            138.00,
            0.05
        );

        crearPeliculasTrilogiaOriginal().forEach(trilogiaOriginal::agregarComponente);

        Saga trilogiaPrecuelas = new Saga(
            "Saga: Trilogia de Precuelas",
            "Ciencia Ficcion",
            135.00,
            0.05
        );

        crearPeliculasTrilogiaPrecuelas().forEach(trilogiaPrecuelas::agregarComponente);

        Saga trilogiaSecuelas = new Saga(
            "Saga: Trilogia de Secuelas",
            "Ciencia Ficcion",
            141.00,
            0.05
        );

        crearPeliculasTrilogiaSecuelas().forEach(trilogiaSecuelas::agregarComponente);

        Saga sagaCompleta = new Saga(
            "Saga: Coleccion Completa Star Wars",
            "Ciencia Ficcion",
            500.00,
            0.05
        );

        Arrays.asList(trilogiaPrecuelas, trilogiaOriginal, trilogiaSecuelas)
            .forEach(sagaCompleta::agregarComponente);

        return Arrays.asList(trilogiaOriginal, trilogiaPrecuelas, trilogiaSecuelas, sagaCompleta);
    }

    /**
     * Crea las peliculas de la trilogia original.
     *
     * @return lista de peliculas de la trilogia original
     */
    private static List<ProductoComponente> crearPeliculasTrilogiaOriginal() {
        return Arrays.asList(
            new PeliculaIndividualHoja(
                "Star Wars Episodio IV: Una Nueva Esperanza",
                "Ciencia Ficcion",
                45.00,
                "George Lucas",
                "Una joven princesa es rescatada por un granjero y un viejo caballero Jedi.",
                121
            ),
            new PeliculaIndividualHoja(
                "Star Wars Episodio V: El Imperio Contraataca",
                "Ciencia Ficcion",
                47.00,
                "Irvin Kershner",
                "Los rebeldes huyen del Imperio mientras Luke entrena con Yoda.",
                124
            ),
            new PeliculaIndividualHoja(
                "Star Wars Episodio VI: El Retorno del Jedi",
                "Ciencia Ficcion",
                46.00,
                "Richard Marquand",
                "Luke debe enfrentar a Darth Vader y al Emperador para salvar a la galaxia.",
                131
            )
        );
    }

    /**
     * Crea las peliculas de la trilogia de precuelas.
     *
     * @return lista de peliculas de la trilogia de precuelas
     */
    private static List<ProductoComponente> crearPeliculasTrilogiaPrecuelas() {
        return Arrays.asList(
            new PeliculaIndividualHoja(
                "Star Wars Episodio I: La Amenaza Fantasma",
                "Ciencia Ficcion",
                42.00,
                "George Lucas",
                "Un joven Anakin Skywalker es descubierto por los Jedi mientras una crisis politica se desarrolla.",
                136
            ),
            new PeliculaIndividualHoja(
                "Star Wars Episodio II: El Ataque de los Clones",
                "Ciencia Ficcion",
                44.00,
                "George Lucas",
                "Anakin y Padme se enamoran mientras la Republica se prepara para la guerra.",
                142
            ),
            new PeliculaIndividualHoja(
                "Star Wars Episodio III: La Venganza de los Sith",
                "Ciencia Ficcion",
                49.00,
                "George Lucas",
                "Anakin se convierte en Darth Vader y el Imperio toma el control de la galaxia.",
                140
            )
        );
    }

    /**
     * Crea las peliculas de la trilogia de secuelas.
     *
     * @return lista de peliculas de la trilogia de secuelas
     */
    private static List<ProductoComponente> crearPeliculasTrilogiaSecuelas() {
        return Arrays.asList(
            new PeliculaIndividualHoja(
                "Star Wars Episodio VII: El Despertar de la Fuerza",
                "Ciencia Ficcion",
                52.00,
                "J.J. Abrams",
                "Una nueva generacion de heroes emerge para enfrentar a la Primera Orden.",
                138
            ),
            new PeliculaIndividualHoja(
                "Star Wars Episodio VIII: Los Ultimos Jedi",
                "Ciencia Ficcion",
                54.00,
                "Rian Johnson",
                "Rey entrena con Luke Skywalker mientras la Resistencia huye de la Primera Orden.",
                152
            ),
            new PeliculaIndividualHoja(
                "Star Wars Episodio IX: El Ascenso de Skywalker",
                "Ciencia Ficcion",
                56.00,
                "J.J. Abrams",
                "La saga concluye con la batalla final entre la Resistencia y la Primera Orden.",
                142
            )
        );
    }

    /**
     * Crea discos musicales adaptados del sistema legacy.
     *
     * @return lista de discos musicales adaptados
     */
    private static List<ProductoComponente> crearDiscosMusicaleasAdaptados() {
        return Arrays.asList(
            new DiscoMusicalAdapter(
                "The Dark Side of the Moon",
                "Rock",
                35.00,
                new DiscoMusicalLegacy(
                    "The Dark Side of the Moon",
                    "Pink Floyd",
                    "Rock Progresivo",
                    1973,
                    35.00
                )
            ),
            new DiscoMusicalAdapter(
                "Thriller",
                "Pop",
                32.00,
                new DiscoMusicalLegacy(
                    "Thriller",
                    "Michael Jackson",
                    "Pop",
                    1982,
                    32.00
                )
            ),
            new DiscoMusicalAdapter(
                "Kind of Blue",
                "Jazz",
                28.00,
                new DiscoMusicalLegacy(
                    "Kind of Blue",
                    "Miles Davis",
                    "Jazz",
                    1959,
                    28.00
                )
            ),
            new DiscoMusicalAdapter(
                "Beethoven's 9th Symphony",
                "Clasica",
                25.00,
                new DiscoMusicalLegacy(
                    "Symphony No. 9",
                    "Ludwig van Beethoven",
                    "Clasica",
                    1824,
                    25.00
                )
            )
        );
    }

    /**
     * Inicializa el catalogo con productos y visualizador.
     * <p>
     * Crea una nueva instancia del catalogo con todos los productos iniciales
     * y configura el visualizador de consola.
     * </p>
     *
     * @return catalogo inicializado y listo para usar
     */
    private static Catalogo inicializarCatalogo() {
        List<ProductoComponente> productos = crearProductosIniciales();
        return new Catalogo(productos, new VisualizadorConsola(productos));
    }

    /**
     * Ejecuta la aplicacion principal de RockBuster.
     * <p>
     * Crea una instancia del menu interactivo e inicia el bucle principal
     * de la aplicacion.
     * </p>
     *
     * @param catalogo el catalogo inicializado con productos
     */
    private static void ejecutarAplicacion(Catalogo catalogo) {
        MenuInteractivo menu = new MenuInteractivo(catalogo);
        menu.mostrarMenuPrincipal();
    }
}