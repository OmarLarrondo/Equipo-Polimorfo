package Modelo;

import java.util.List;
import java.util.Scanner;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.IntStream;

import Composite.ProductoComponente;

/**
 * Clase que maneja la interfaz de usuario interactiva para el catalogo de RockBuster.
 * <p>
 * Proporciona un menu principal que permite a los usuarios navegar por el catalogo,
 * aplicar filtros y ver informacion detallada de productos. Implementa separacion
 * entre logica de negocio pura y operaciones de entrada/salida.
 * </p>
 *
 * @author Equipo-Polimorfo
 * @version 1.0
 * @since 2025-09-28
 */
public class MenuInteractivo {

    private final Catalogo catalogo;
    private final Scanner scanner;

    /**
     * Constructor que inicializa el menu interactivo con un catalogo.
     *
     * @param catalogo el catalogo de productos a gestionar
     */
    public MenuInteractivo(Catalogo catalogo) {
        this.catalogo = catalogo;
        this.scanner = new Scanner(System.in);
    }

    /**
     * Muestra el menu principal y maneja la navegacion del usuario.
     * <p>
     * Presenta las opciones disponibles y ejecuta las acciones correspondientes
     * basandose en la seleccion del usuario hasta que decida salir.
     * </p>
     */
    public void mostrarMenuPrincipal() {
        boolean continuar = true;

        while (continuar) {
            mostrarOpcionesMenu();
            String opcion = leerEntrada();

            switch (opcion) {
                case "1":
                    manejarOpcionCatalogo();
                    break;
                case "2":
                    manejarOpcionFiltroGenero();
                    break;
                case "3":
                    manejarOpcionFiltroPrecio();
                    break;
                case "4":
                    continuar = false;
                    System.out.println("Gracias por usar RockBuster! Hasta pronto!");
                    break;
                default:
                    System.out.println("Opcion no valida. Por favor, seleccione 1-4.");
            }

            if (continuar) {
                pausarParaContinuar();
            }
        }
    }

    /**
     * Maneja la opcion de mostrar el catalogo completo.
     * <p>
     * Obtiene todos los productos del catalogo y permite al usuario
     * seleccionar uno para ver informacion detallada.
     * </p>
     */
    public void manejarOpcionCatalogo() {
        List<ProductoComponente> productos = catalogo.verCatalogoCompleto();
        procesarListaProductos(productos, "=== CATALOGO COMPLETO ===");
    }

    /**
     * Maneja la opcion de filtrar productos por genero.
     * <p>
     * Solicita al usuario un genero y muestra los productos que coinciden
     * con el criterio especificado.
     * </p>
     */
    public void manejarOpcionFiltroGenero() {
        System.out.print("Ingrese el genero a buscar: ");
        String genero = leerEntrada();

        List<ProductoComponente> productosFiltrados = catalogo.filtrarPorGenero(genero);
        String titulo = String.format("=== PRODUCTOS DE GENERO: %s ===", genero.toUpperCase());

        procesarListaProductos(productosFiltrados, titulo);
    }

    /**
     * Maneja la opcion de filtrar productos por precio maximo.
     * <p>
     * Solicita al usuario un precio maximo y muestra los productos
     * cuyo precio sea menor o igual al especificado.
     * </p>
     */
    public void manejarOpcionFiltroPrecio() {
        System.out.print("Ingrese el precio maximo: $");
        String precioStr = leerEntrada();

        validarYProcesarPrecio(precioStr)
            .ifPresentOrElse(
                precio -> {
                    List<ProductoComponente> productosFiltrados = catalogo.filtrarPorPrecioMaximo(precio);
                    String titulo = String.format("=== PRODUCTOS HASTA $%.2f ===", precio);
                    procesarListaProductos(productosFiltrados, titulo);
                },
                () -> System.out.println("Precio invalido. Por favor ingrese un numero valido.")
            );
    }

    /**
     * Permite al usuario seleccionar un producto de una lista y ver informacion completa.
     * <p>
     * Muestra la lista numerada de productos y permite la seleccion por indice.
     * Si la seleccion es valida, muestra la informacion detallada del producto.
     * </p>
     *
     * @param productos la lista de productos para seleccionar
     */
    public void seleccionarProducto(List<ProductoComponente> productos) {
        if (esListaVacia(productos)) {
            System.out.println("No hay productos para seleccionar.");
            return;
        }

        System.out.print("Seleccione el numero del producto para ver detalles completos (0 para volver): ");
        String seleccionStr = leerEntrada();

        validarYProcesarSeleccion(seleccionStr, productos.size())
            .ifPresentOrElse(
                indice -> {
                    if (indice == 0) {
                        return;
                    }
                    ProductoComponente productoSeleccionado = productos.get(indice - 1);
                    mostrarInformacionCompleta(productoSeleccionado);
                },
                () -> System.out.println("Seleccion invalida. Por favor ingrese un numero valido.")
            );
    }

    /**
     * Lee una linea de entrada del usuario.
     *
     * @return la cadena ingresada por el usuario, sin espacios al inicio y final
     */
    private String leerEntrada() {
        return scanner.nextLine().trim();
    }

    /**
     * Muestra una lista de productos en formato numerado.
     * <p>
     * Presenta cada producto con un numero secuencial, mostrando
     * nombre y precio de cada uno.
     * </p>
     *
     * @param productos la lista de productos a mostrar
     */
    private void mostrarProductos(List<ProductoComponente> productos) {
        if (esListaVacia(productos)) {
            System.out.println("No se encontraron productos.");
            return;
        }

        IntStream.range(0, productos.size())
            .forEach(i -> {
                ProductoComponente producto = productos.get(i);
                System.out.printf("%d. %s - $%.2f%n",
                    i + 1,
                    producto.getNombre(),
                    producto.getPrecio());
            });
    }

    /**
     * Muestra las opciones del menu principal.
     */
    private void mostrarOpcionesMenu() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("           ROCKBUSTER - CATALOGO ");
        System.out.println("=".repeat(50));
        System.out.println("1. Ver catalogo completo");
        System.out.println("2. Filtrar por genero");
        System.out.println("3. Filtrar por precio maximo");
        System.out.println("4. Salir");
        System.out.println("=".repeat(50));
        System.out.print("Seleccione una opcion (1-4): ");
    }

    /**
     * Procesa una lista de productos mostrandola y permitiendo seleccion.
     *
     * @param productos la lista de productos a procesar
     * @param titulo el titulo a mostrar antes de la lista
     */
    private void procesarListaProductos(List<ProductoComponente> productos, String titulo) {
        System.out.println("\n" + titulo);
        System.out.println("-".repeat(titulo.length()));

        mostrarProductos(productos);

        if (!esListaVacia(productos)) {
            System.out.println();
            seleccionarProducto(productos);
        }
    }

    /**
     * Valida y convierte una cadena de precio a double.
     *
     * @param precioStr la cadena que representa el precio
     * @return Optional con el precio si es valido, empty si no lo es
     */
    private Optional<Double> validarYProcesarPrecio(String precioStr) {
        return esCadenaVacia(precioStr) ?
            Optional.empty() :
            convertirStringADouble(precioStr).filter(precio -> precio >= 0);
    }

    /**
     * Valida y convierte una cadena de seleccion a indice valido.
     *
     * @param seleccionStr la cadena que representa la seleccion
     * @param tamano el tamano maximo valido para la seleccion
     * @return Optional con el indice si es valido, empty si no lo es
     */
    private Optional<Integer> validarYProcesarSeleccion(String seleccionStr, int tamano) {
        return esCadenaVacia(seleccionStr) ?
            Optional.empty() :
            convertirStringAEntero(seleccionStr)
                .filter(indice -> indice >= 0 && indice <= tamano);
    }

    /**
     * Convierte una cadena a double de forma segura.
     *
     * @param str la cadena a convertir
     * @return Optional con el double si la conversion es exitosa
     */
    private Optional<Double> convertirStringADouble(String str) {
        try {
            return Optional.of(Double.parseDouble(str));
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }

    /**
     * Convierte una cadena a entero de forma segura.
     *
     * @param str la cadena a convertir
     * @return Optional con el entero si la conversion es exitosa
     */
    private Optional<Integer> convertirStringAEntero(String str) {
        try {
            return Optional.of(Integer.parseInt(str));
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }

    /**
     * Verifica si una lista esta vacia o es null.
     *
     * @param lista la lista a verificar
     * @return true si la lista esta vacia o es null
     */
    private boolean esListaVacia(List<?> lista) {
        return lista == null || lista.isEmpty();
    }

    /**
     * Verifica si una cadena esta vacia o es null.
     *
     * @param cadena la cadena a verificar
     * @return true si la cadena esta vacia o es null
     */
    private boolean esCadenaVacia(String cadena) {
        return cadena == null || cadena.trim().isEmpty();
    }

    /**
     * Muestra la informacion completa de un producto.
     *
     * @param producto el producto del cual mostrar informacion
     */
    private void mostrarInformacionCompleta(ProductoComponente producto) {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("           INFORMACION COMPLETA DEL PRODUCTO");
        System.out.println("=".repeat(60));

        String informacionCompleta = catalogo.mostrarProductoCompleto(producto);
        System.out.println(informacionCompleta);

        System.out.println("=".repeat(60));
    }

    /**
     * Pausa la ejecucion hasta que el usuario presione Enter.
     */
    private void pausarParaContinuar() {
        System.out.println("\nPresione Enter para continuar...");
        scanner.nextLine();
    }
}
