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
import patrones.factory.ia.DificultadIA;
import patrones.factory.ia.ServiciioIA;
import patrones.observer.ObservadorUI;
import util.ParticleEmitter;
import util.RenderizadorJuego;

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

        observadorUI = Option.of(new ObservadorUI(vistaJuego));
        observadorUI.forEach(obs -> modeloJuego.agregarObservador(obs));
    }

    /**
     * Configura el manejo de entrada de teclado.
     */
    private void configurarEntrada() {
        contenedorJuego.setFocusTraversable(true);
        contenedorJuego.setOnKeyPressed(this::manejarTeclaPresionada);
        contenedorJuego.setOnKeyReleased(this::manejarTeclaSoltada);
        contenedorJuego.requestFocus();
    }

    /**
     * Maneja eventos de tecla presionada.
     *
     * @param event Evento de teclado
     */
    private void manejarTeclaPresionada(final KeyEvent event) {
        Try.run(() -> {
            if (event.getCode() == KeyCode.ALT) {
                alternarPausa();
                event.consume();
                return;
            }

            if (event.getCode() == KeyCode.SPACE && !juegoIniciado) {
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

    /**
     * Inicia el juego.
     */
    private void iniciarJuego() {
        Try.run(() -> {
            juegoIniciado = true;
            modeloJuego.establecerActivo(true);
            vistaJuego.actualizarInfo("W/S: Jugador 1 | Flechas: Jugador 2 | ALT: Pausa");
            vistaJuego.mostrarMensajeCentral("¡COMIENZA!");
            ultimoTiempo = System.nanoTime();
        }).onFailure(e -> System.err.println("Error iniciando juego: " + e.getMessage()));
    }

    /**
     * Crea el game loop usando AnimationTimer.
     */
    private void crearGameLoop() {
        final AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(final long ahora) {
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
        gameLoop.forEach(AnimationTimer::start);
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
            modeloJuego.actualizar(delta);
            sistemaParticulas = sistemaParticulas.actualizar(delta);
            actualizarHUD();
        }).onFailure(e -> System.err.println("Error actualizando juego: " + e.getMessage()));
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
            RenderizadorJuego.renderizarBloques(gc, List.ofAll(modeloJuego.obtenerBloques()));
            RenderizadorJuego.renderizarPaleta(gc, modeloJuego.obtenerJugador1());
            RenderizadorJuego.renderizarPaleta(gc, modeloJuego.obtenerJugador2());
            RenderizadorJuego.renderizarPelota(gc, modeloJuego.obtenerPelota());
            RenderizadorJuego.renderizarItems(gc, List.ofAll(modeloJuego.obtenerItems()));
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

    /**
     * Reinicia el estado del controlador al estado inicial.
     */
    public void reiniciarEstado() {
        Try.run(() -> {
            pausado = false;
            juegoIniciado = false;
            sistemaParticulas = ParticleEmitter.SistemaParticulas.vacio();
            modeloJuego.reiniciar();
            vistaJuego.actualizarInfo("ESPACIO: Iniciar | ALT: Pausa");
            vistaJuego.actualizarPuntaje(1, 0);
            vistaJuego.actualizarPuntaje(2, 0);
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
