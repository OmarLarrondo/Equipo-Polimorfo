package mvc.controlador;

import io.vavr.collection.List;
import io.vavr.control.Option;
import io.vavr.control.Try;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import mvc.modelo.ModeloJuego;
import mvc.modelo.enums.Direccion;
import mvc.modelo.enums.ModoJuego;
import mvc.vista.VistaJuego;
import patrones.adapter.AdaptadorEntradaTeclado;
import patrones.factory.ia.DificultadIA;
import patrones.factory.ia.ServiciioIA;
import patrones.observer.ObservadorUI;
import util.ParticleEmitter;
import util.RenderizadorJuego;
import mvc.modelo.items.ItemNeblina;

/**
 * Controlador del panel del juego principal.
 * Gestiona la interfaz del juego y la lógica de actualización del estado.
 * Implementa el game loop con AnimationTimer y renderizado usando programación funcional pura.
 * Implementa el componente Controlador del patrón MVC.
 *
 * @author Equipo-polimorfo
 * @version 1.0
 */
public class ControladorJuego extends ControladorBase {

    private static final double NANOSEGUNDOS_POR_SEGUNDO = 1_000_000_000.0;

    @FXML
    private StackPane contenedorJuego;

    private VistaJuego vistaJuego;
    private ModeloJuego modeloJuego;
    private Option<AnimationTimer> gameLoop;
    private Option<ObservadorUI> observadorUI;
    private ParticleEmitter.SistemaParticulas sistemaParticulas;
    private Option<ServiciioIA> servicioIA;
    private AdaptadorEntradaTeclado adaptadorEntrada;
    private long ultimoTiempo;
    private boolean pausado;
    private boolean juegoIniciado;

    /**
     * Constructor por defecto requerido por FXML.
     */
    public ControladorJuego() {
        this.gameLoop = Option.none();
        this.observadorUI = Option.none();
        this.sistemaParticulas = ParticleEmitter.SistemaParticulas.vacio();
        this.servicioIA = Option.none();
        this.pausado = false;
        this.juegoIniciado = false;
    }

    /**
     * Inicializa el controlador después de cargar el FXML.
     */
    @FXML
    @Override
    public void initialize() {
        Try.run(() -> {
            if (contenedorJuego != null) {
                configurarAtajosTecladoPantallaCompleta(contenedorJuego);
            }
            inicializarVista();
            inicializarModelo();
            configurarEntrada();
            crearGameLoop();
        }).onFailure(e -> System.err.println("Error inicializando controlador: " + e.getMessage()));
    }

    /**
     * Inicializa la vista del juego.
     */
    private void inicializarVista() {
        vistaJuego = new VistaJuego();
        contenedorJuego.getChildren().add(vistaJuego.obtenerContenedor());
    }

    /**
     * Inicializa el modelo del juego.
     */
    private void inicializarModelo() {
        modeloJuego = new ModeloJuego();

        final Option<Runnable> callbackDetener = Option.of(this::detenerGameLoop);
        final Option<Runnable> callbackMenu = Option.of(this::navegarAlMenu);

        observadorUI = Option.of(new ObservadorUI(vistaJuego, callbackDetener, callbackMenu));
        observadorUI.forEach(obs -> modeloJuego.agregarObservador(obs));
    }

    /**
     * Navega al menu principal de forma segura.
     */
    private void navegarAlMenu() {
        Option.of(gestorEscenas)
            .peek(mvc.vista.GestorEscenas::mostrarMenu);
    }

    private void configurarEntrada() {
        System.out.println("DEBUG: Configurando entrada de teclado...");
        adaptadorEntrada = new AdaptadorEntradaTeclado();
        contenedorJuego.setFocusTraversable(true);
        contenedorJuego.setOnKeyPressed(event -> {
            adaptadorEntrada.teclaPresionada(event.getCode());
            manejarTeclaPresionada(event);
        });
        contenedorJuego.setOnKeyReleased(event -> {
            adaptadorEntrada.teclaLiberada(event.getCode());
            manejarTeclaSoltada(event);
        });
        contenedorJuego.requestFocus();
        System.out.println("DEBUG: Event handlers registrados. FocusTraversable=true, ¿Tiene foco? " + contenedorJuego.isFocused());
    }

    private void manejarTeclaPresionada(final KeyEvent event) {
        System.out.println("DEBUG: Tecla presionada: " + event.getCode() + ", juegoIniciado=" + juegoIniciado + ", pausado=" + pausado);
        
        Try.run(() -> {
            if (event.getCode() == KeyCode.ALT) {
                System.out.println("DEBUG: Alternando pausa...");
                alternarPausa();
                event.consume();
                return;
            }

            if (event.getCode() == KeyCode.SPACE && !juegoIniciado) {
                System.out.println("DEBUG: Iniciando juego con ESPACIO...");
                iniciarJuego();
                event.consume();
                return;
            }

            if (!pausado && juegoIniciado) {
                procesarMovimiento(event.getCode(), true);
            }
        }).onFailure(e -> System.err.println("Error manejando tecla presionada: " + e.getMessage()));
    }

    /**
     * Maneja eventos de tecla soltada.
     *
     * @param event Evento de teclado
     */
    private void manejarTeclaSoltada(final KeyEvent event) {
        Try.run(() -> {
            if (!pausado && juegoIniciado) {
                procesarMovimiento(event.getCode(), false);
            }
        }).onFailure(e -> System.err.println("Error manejando tecla soltada: " + e.getMessage()));
    }

    /**
     * Procesa movimiento de paletas según las teclas presionadas.
     *
     * @param code      Código de la tecla
     * @param presionada true si la tecla fue presionada, false si fue soltada
     */
    private void procesarMovimiento(final KeyCode code, final boolean presionada) {
        if (!presionada) return;

        final ModoJuego modo = modeloJuego.obtenerModoActual();

        switch (code) {
            case W:
                modeloJuego.obtenerJugador1().moverEnDireccion(Direccion.ARRIBA, 0.016);
                break;
            case S:
                modeloJuego.obtenerJugador1().moverEnDireccion(Direccion.ABAJO, 0.016);
                break;
            case UP:
                if (modo == ModoJuego.DOS_JUGADORES) {
                    modeloJuego.obtenerJugador2().moverEnDireccion(Direccion.ARRIBA, 0.016);
                }
                break;
            case DOWN:
                if (modo == ModoJuego.DOS_JUGADORES) {
                    modeloJuego.obtenerJugador2().moverEnDireccion(Direccion.ABAJO, 0.016);
                }
                break;
            default:
                break;
        }
    }

    /**
     * Alterna el estado de pausa del juego.
     */
    private void alternarPausa() {
        pausado = !pausado;
        if (pausado) {
            vistaJuego.actualizarInfo("PAUSA - ALT: Reanudar");
            vistaJuego.mostrarMensajeCentral("PAUSA");
        } else {
            vistaJuego.actualizarInfo("ESPACIO: Iniciar | ALT: Pausa");
            ultimoTiempo = System.nanoTime();
        }
    }

    private void iniciarJuego() {
        System.out.println("DEBUG: Entrando a iniciarJuego()...");
        Try.run(() -> {
            juegoIniciado = true;
            modeloJuego.establecerActivo(true);
            vistaJuego.actualizarInfo("W/S: Jugador 1 | Flechas: Jugador 2 | ALT: Pausa");
            vistaJuego.mostrarMensajeCentral("¡COMIENZA!");
            ultimoTiempo = System.nanoTime();
            System.out.println("DEBUG: Juego iniciado correctamente. juegoIniciado=" + juegoIniciado);
        }).onFailure(e -> System.err.println("Error iniciando juego: " + e.getMessage()));
    }

    private void crearGameLoop() {
        System.out.println("DEBUG: Creando game loop...");
        final AnimationTimer timer = new AnimationTimer() {
            private long contadorFrames = 0;

            @Override
            public void handle(final long ahora) {
                contadorFrames++;
                if (contadorFrames == 1) {
                    System.out.println("DEBUG: Game loop handle() ejecutándose por primera vez");
                }
                if (contadorFrames % 60 == 0) {
                    System.out.println("DEBUG: Game loop activo (frame " + contadorFrames + "), pausado=" + pausado + ", juegoIniciado=" + juegoIniciado);
                }

                if (!modeloJuego.estaActivo()) {
                    return;
                }

                if (!pausado && juegoIniciado) {
                    final double delta = calcularDelta(ahora);
                    actualizar(delta);
                    renderizar();
                    ultimoTiempo = ahora;
                } else if (juegoIniciado) {
                    renderizar();
                }
            }
        };

        gameLoop = Option.of(timer);
        gameLoop.forEach(loop -> {
            loop.start();
            System.out.println("DEBUG: Game loop iniciado con start()");
        });
        ultimoTiempo = System.nanoTime();
    }

    /**
     * Calcula el tiempo delta entre frames.
     *
     * @param ahora Tiempo actual en nanosegundos
     * @return Delta en segundos
     */
    private double calcularDelta(final long ahora) {
        return Math.min((ahora - ultimoTiempo) / NANOSEGUNDOS_POR_SEGUNDO, 0.1);
    }

    /**
     * Actualiza el estado del juego.
     *
     * @param delta Tiempo transcurrido en segundos
     */
    private void actualizar(final double delta) {
        Try.run(() -> {
            procesarEntradaContinua(delta);
            modeloJuego.actualizar(delta);
            sistemaParticulas = sistemaParticulas.actualizar(delta);
            actualizarHUD();
        }).onFailure(e -> System.err.println("Error actualizando juego: " + e.getMessage()));
    }

    /**
     * Procesa la entrada de teclado de forma continua en cada frame.
     *
     * @param delta Tiempo transcurrido en segundos
     */
    private void procesarEntradaContinua(final double delta) {
        if (adaptadorEntrada == null) return;

        final ModoJuego modo = modeloJuego.obtenerModoActual();

        if (adaptadorEntrada.esTeclaPresionada(KeyCode.W)) {
            modeloJuego.obtenerJugador1().moverEnDireccion(Direccion.ARRIBA, delta);
        }
        if (adaptadorEntrada.esTeclaPresionada(KeyCode.S)) {
            modeloJuego.obtenerJugador1().moverEnDireccion(Direccion.ABAJO, delta);
        }

        if (modo == ModoJuego.DOS_JUGADORES) {
            if (adaptadorEntrada.esTeclaPresionada(KeyCode.UP)) {
                modeloJuego.obtenerJugador2().moverEnDireccion(Direccion.ARRIBA, delta);
            }
            if (adaptadorEntrada.esTeclaPresionada(KeyCode.DOWN)) {
                modeloJuego.obtenerJugador2().moverEnDireccion(Direccion.ABAJO, delta);
            }
        }
    }

    /**
     * Actualiza el HUD con información del juego.
     */
    private void actualizarHUD() {
        final double tiempoRestante = modeloJuego.obtenerDuracionPartida() - modeloJuego.obtenerTiempoTranscurrido();
        vistaJuego.actualizarTiempo(Math.max(0, tiempoRestante));
    }

    /**
     * Renderiza el juego en el canvas.
     */
    private void renderizar() {
        Try.run(() -> {
            final GraphicsContext gc = vistaJuego.obtenerContextoGrafico();
            final double ancho = vistaJuego.obtenerAncho();
            final double alto = vistaJuego.obtenerAlto();

            RenderizadorJuego.limpiarCanvas(gc, ancho, alto);
            RenderizadorJuego.renderizarFondo(gc, ancho, alto);
            
            // Renderizar bloques
            RenderizadorJuego.renderizarBloques(gc, List.ofAll(modeloJuego.obtenerBloques()));
            
            // Renderizar paletas solo si no son null
            Option.of(modeloJuego.obtenerJugador1())
                .forEach(paleta -> RenderizadorJuego.renderizarPaleta(gc, paleta));
            
            Option.of(modeloJuego.obtenerJugador2())
                .forEach(paleta -> RenderizadorJuego.renderizarPaleta(gc, paleta));
            
            // Renderizar pelota principal solo si no es null
            Option.of(modeloJuego.obtenerPelota())
                .forEach(pelota -> RenderizadorJuego.renderizarPelota(gc, pelota));
            
            // Renderizar items
            final List<mvc.modelo.items.Item> items = List.ofAll(modeloJuego.obtenerItems());
            RenderizadorJuego.renderizarItems(gc, items);

            // Renderizar neblina si hay algún ItemNeblina activo
            final boolean neblinaActiva = items
                .filter(item -> item instanceof ItemNeblina)
                .exists(item -> item.estaActivo());

            if (neblinaActiva) {
                RenderizadorJuego.renderizarNeblina(gc, ancho, alto, 1.0);
            }
            
            // Renderizar sistema de partículas
            sistemaParticulas.renderizar(gc);
        }).onFailure(e -> System.err.println("Error renderizando: " + e.getMessage()));
    }

    /**
     * Establece el modelo del juego.
     *
     * @param modelo Modelo del juego a establecer
     */
    public void establecerModelo(final ModeloJuego modelo) {
        Try.run(() -> {
            this.modeloJuego = modelo;
            observadorUI.forEach(obs -> {
                modeloJuego.agregarObservador(obs);
            });
        }).onFailure(e -> System.err.println("Error estableciendo modelo: " + e.getMessage()));
    }

    /**
     * Agrega partículas al sistema para efectos visuales.
     *
     * @param x         Posición X
     * @param y         Posición Y
     * @param cantidad  Cantidad de partículas
     * @param tipo      Tipo de efecto (explosion, chispas, etc.)
     */
    public void agregarEfectoParticulas(final double x, final double y, final int cantidad, final String tipo) {
        Try.run(() -> {
            final io.vavr.collection.List<ParticleEmitter.Particula> nuevasParticulas = switch (tipo) {
                case "explosion" -> ParticleEmitter.crearExplosion(x, y, cantidad, javafx.scene.paint.Color.WHITE, 1.0);
                case "chispas" -> ParticleEmitter.crearChispas(x, y, cantidad, javafx.scene.paint.Color.YELLOW);
                case "confeti" -> ParticleEmitter.crearConfeti(x, y, cantidad);
                default -> io.vavr.collection.List.empty();
            };

            sistemaParticulas = sistemaParticulas.agregar(nuevasParticulas);
        }).onFailure(e -> System.err.println("Error agregando partículas: " + e.getMessage()));
    }

    public void reiniciarEstado() {
        System.out.println("DEBUG: Reiniciando estado del juego...");
        Try.run(() -> {
            pausado = false;
            juegoIniciado = false;
            sistemaParticulas = ParticleEmitter.SistemaParticulas.vacio();
            
            // Inicializar entidades del juego con dimensiones del canvas
            Option.of(vistaJuego)
                .peek(vista -> System.out.println("DEBUG: Dimensiones canvas: " + vista.obtenerAncho() + "x" + vista.obtenerAlto()))
                .flatMap(vista -> Option.of(modeloJuego)
                    .flatMap(modelo -> modelo.inicializarEntidadesJuego(
                        vista.obtenerAncho(),
                        vista.obtenerAlto()
                    )));
            
            // Reiniciar solo los valores del juego sin tocar las entidades recien creadas
            modeloJuego.reiniciarValoresJuego();
            
            vistaJuego.actualizarInfo("ESPACIO: Iniciar | ALT: Pausa");
            vistaJuego.actualizarPuntaje(1, 0);
            vistaJuego.actualizarPuntaje(2, 0);
            
            // Solicitar foco DESPUÉS de que la escena esté completamente renderizada
            javafx.application.Platform.runLater(() -> {
                System.out.println("DEBUG: Solicitando foco en contenedorJuego...");
                Option.of(contenedorJuego)
                    .peek(contenedor -> {
                        contenedor.requestFocus();
                        System.out.println("DEBUG: Foco solicitado. ¿Tiene foco? " + contenedor.isFocused());
                    });
            });
            
            System.out.println("DEBUG: Estado reiniciado correctamente");
        }).onFailure(e -> System.err.println("Error reiniciando estado: " + e.getMessage()));
    }

    /**
     * Establece el nivel que se va a jugar.
     *
     * @param nivel nivel a establecer
     */
    public void establecerNivel(final mvc.modelo.entidades.Nivel nivel) {
        io.vavr.control.Option.of(modeloJuego)
            .peek(modelo -> modelo.establecerNivel(nivel));
    }

    /**
     * Configura la dificultad de la inteligencia artificial.
     * Convierte el nivel numerico a enum DificultadIA y crea el servicio de IA.
     * Utiliza programacion funcional pura con Vavr Option para manejo seguro.
     *
     * @param dificultad nivel de dificultad de la IA (1-10)
     */
    public void configurarDificultadIA(final int dificultad) {
        this.servicioIA = DificultadIA.desdeNumeroNivel(dificultad)
                .map(ServiciioIA::new)
                .peek(servicio -> 
                    System.out.println("IA configurada con dificultad: " + dificultad)
                );

        servicioIA.forEach(servicio ->
                Option.of(modeloJuego)
                        .forEach(modelo -> modelo.establecerServicioIA(servicio))
        );
    }

    /**
     * Establece el modo de juego (CONTRA_IA, DOS_JUGADORES, etc.).
     * Propaga el modo al ModeloJuego para que sepa cómo manejar la lógica del juego.
     *
     * @param modo el modo de juego a establecer
     */
    public void establecerModo(final mvc.modelo.enums.ModoJuego modo) {
        io.vavr.control.Option.of(modeloJuego)
            .peek(modelo -> modelo.establecerModo(modo));
    }

    /**
     * Detiene el game loop del juego de forma segura.
     * <p>
     * Este metodo puede ser llamado cuando el juego termina para detener
     * la actualizacion y renderizado del juego.
     * </p>
     */
    public void detenerGameLoop() {
        gameLoop.forEach(AnimationTimer::stop);
    }

    /**
     * Libera los recursos del controlador.
     */
    public void liberarRecursos() {
        Try.run(() -> {
            gameLoop.forEach(AnimationTimer::stop);
            gameLoop = Option.none();
            observadorUI.forEach(obs -> modeloJuego.eliminarObservador(obs));
            observadorUI = Option.none();
        }).onFailure(e -> System.err.println("Error liberando recursos: " + e.getMessage()));
    }
}
