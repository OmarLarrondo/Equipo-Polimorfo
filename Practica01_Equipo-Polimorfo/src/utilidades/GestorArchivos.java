package utilidades;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import modelo.ResultadoCobro;
import modelo.Usuario;

/**
 * Clase para manejar la generación de reportes de la simulación de servicios de streaming.
 * Esta clase crea archivos de texto con todas las transacciones del sistema,
 * organizadas de manera clara y legible utilizando objetos ResultadoCobro
 * para estructurar correctamente la información.
 * 
 * La implementación es robusta para manejar la escritura de archivos
 * y el formateo adecuado de la información según los requerimientos de la práctica.
 * 
 * @author Equipo Polimórfo
 * @version 1.0
 */
public class GestorArchivos {

    /**
     * Escribe un reporte completo de todas las transacciones realizadas durante la simulación.
     * El archivo incluye todas las transacciones organizadas cronológicamente,
     * mostrando tanto cobros exitosos como fallidos para cada usuario y servicio.
     * 
     * @param transacciones lista de objetos ResultadoCobro que contienen la información
     *                     detallada de cada transacción realizada
     * @param nombreArchivo nombre del archivo donde se escribirá el reporte completo
     */
    public void escribirTransaccionesCompletas(List<ResultadoCobro> transacciones, String nombreArchivo) {
        try {
            Path path = Paths.get(nombreArchivo);
            
            if (path.getParent() != null) {
                Files.createDirectories(path.getParent());
            }
            
            List<String> contenido = new ArrayList<>();
            
            contenido.add("==========================================================");
            contenido.add("           REPORTE COMPLETO DE TRANSACCIONES");
            contenido.add("              Simulación de Servicios de Streaming");
            contenido.add("==========================================================");
            contenido.add("Generado: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            contenido.add("Total de transacciones: " + transacciones.size());
            contenido.add("");
            
            if (transacciones.isEmpty()) {
                contenido.add("No se registraron transacciones durante la simulación.");
            } else {
                for (int mes = 1; mes <= 12; mes++) {
                    final int mesActual = mes;
                    List<ResultadoCobro> transaccionesMes = transacciones.stream()
                        .filter(t -> contieneMes(t.getDescripcion(), mesActual))
                        .collect(Collectors.toList());
                    
                    if (!transaccionesMes.isEmpty()) {
                        contenido.add("▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬");
                        contenido.add(String.format("                        MES %02d", mes));
                        contenido.add("▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬");
                        contenido.add("");
                        
                        for (ResultadoCobro transaccion : transaccionesMes) {
                            contenido.add(formatearTransaccion(transaccion));
                        }
                        contenido.add("");
                    }
                }
            }
            
            contenido.add("==========================================================");
            contenido.add("                    FIN DEL REPORTE");
            contenido.add("==========================================================");
            
            Files.write(path, contenido);
            System.out.println("Reporte completo generado en: " + nombreArchivo);
            
        } catch (IOException e) {
            System.err.println("Error al escribir las transacciones completas: " + e.getMessage());
        }
    }

    /**
     * Escribe un resumen de las transacciones realizadas en un mes específico.
     * El archivo contiene únicamente las transacciones del mes solicitado,
     * con estadísticas resumidas del período.
     * 
     * @param mes número del mes del cual generar el resumen (1-12)
     * @param transacciones lista de objetos ResultadoCobro de toda la simulación
     * @param nombreArchivo nombre del archivo donde se escribirá el resumen mensual
     */
    public void escribirResumenMensual(int mes, List<ResultadoCobro> transacciones, String nombreArchivo) {
        try {
            Path path = Paths.get(nombreArchivo);
            
            if (path.getParent() != null) {
                Files.createDirectories(path.getParent());
            }
            
            List<ResultadoCobro> transaccionesMes = transacciones.stream()
                .filter(t -> contieneMes(t.getDescripcion(), mes))
                .collect(Collectors.toList());
            
            List<String> contenido = new ArrayList<>();
            
            contenido.add("==========================================================");
            contenido.add(String.format("             RESUMEN MENSUAL - MES %02d", mes));
            contenido.add("              Simulación de Servicios de Streaming");
            contenido.add("==========================================================");
            contenido.add("Generado: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            contenido.add("");
            
            if (transaccionesMes.isEmpty()) {
                contenido.add("No hay transacciones registradas en el mes " + mes);
            } else {
                long exitosas = transaccionesMes.stream().filter(ResultadoCobro::getExitoso).count();
                long fallidas = transaccionesMes.size() - exitosas;
                double totalCobrado = transaccionesMes.stream()
                    .filter(ResultadoCobro::getExitoso)
                    .mapToDouble(ResultadoCobro::getMonto)
                    .sum();
                
                contenido.add(String.format("Total de transacciones: %d", transaccionesMes.size()));
                contenido.add(String.format("Transacciones exitosas: %d", exitosas));
                contenido.add(String.format("Transacciones fallidas: %d", fallidas));
                contenido.add(String.format("Monto total cobrado: $%.2f", totalCobrado));
                contenido.add("");
                contenido.add("DETALLE DE TRANSACCIONES:");
                contenido.add("__________________________________________________________");
                contenido.add("");
                
                for (ResultadoCobro transaccion : transaccionesMes) {
                    contenido.add(formatearTransaccion(transaccion));
                }
            }
            
            contenido.add("");
            contenido.add("==========================================================");
            contenido.add("                 FIN RESUMEN MENSUAL");
            contenido.add("==========================================================");
            
            Files.write(path, contenido);
            System.out.println(String.format("Resumen del mes %02d generado en: %s", mes, nombreArchivo));
            
        } catch (IOException e) {
            System.err.println("Error al escribir el resumen mensual: " + e.getMessage());
        }
    }

    /**
     * Escribe el estado final de todos los usuarios al terminar la simulación.
     * El archivo contiene información sobre el saldo final, servicios activos,
     * y resumen de gastos de cada usuario.
     * 
     * @param usuarios lista de todos los usuarios que participaron en la simulación
     * @param nombreArchivo nombre del archivo donde se escribirá el estado final
     */
    public void escribirEstadoFinalUsuarios(List<Usuario> usuarios, String nombreArchivo) {
        try {
            Path path = Paths.get(nombreArchivo);
            
            if (path.getParent() != null) {
                Files.createDirectories(path.getParent());
            }
            
            List<String> contenido = new ArrayList<>();
            
            contenido.add("==========================================================");
            contenido.add("              ESTADO FINAL DE USUARIOS");
            contenido.add("              Simulación de Servicios de Streaming");
            contenido.add("==========================================================");
            contenido.add("Generado: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            contenido.add("");
            
            if (usuarios.isEmpty()) {
                contenido.add("No hay usuarios registrados en la simulación.");
            } else {
                for (Usuario usuario : usuarios) {
                    contenido.addAll(formatearUsuario(usuario));
                    contenido.add("");
                }
                
                double totalSaldoFinal = usuarios.stream()
                    .mapToDouble(u -> u.obtenerDineroDisponible())
                    .sum();
                
                contenido.add("▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬");
                contenido.add("                   RESUMEN GENERAL");
                contenido.add("▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬");
                contenido.add(String.format("Total de usuarios: %d", usuarios.size()));
                contenido.add(String.format("Saldo total final: $%.2f", totalSaldoFinal));
            }
            
            contenido.add("");
            contenido.add("==========================================================");
            contenido.add("               FIN ESTADO FINAL");
            contenido.add("==========================================================");
            
            Files.write(path, contenido);
            System.out.println("Estado final de usuarios generado en: " + nombreArchivo);
            
        } catch (IOException e) {
            System.err.println("Error al escribir el estado final de usuarios: " + e.getMessage());
        }
    }
    
    /**
     * Formatea la información de una transacción para su presentación en reportes.
     * 
     * @param transaccion el objeto ResultadoCobro a formatear
     * @return cadena formateada con la información de la transacción
     */
    private String formatearTransaccion(ResultadoCobro transaccion) {
        String estado = transaccion.getExitoso() ? "✓ EXITOSO" : "✗ FALLIDO";
        return String.format("[%s] %s - $%.2f - %s",
            estado,
            transaccion.getUsuario().obtenerNombre(),
            transaccion.getMonto(),
            transaccion.getDescripcion());
    }
    
    /**
     * Formatea la información completa de un usuario para el reporte final.
     * 
     * @param usuario el objeto Usuario a formatear
     * @return lista de cadenas con la información formateada del usuario
     */
    private List<String> formatearUsuario(Usuario usuario) {
        List<String> info = new ArrayList<>();
        
        info.add("▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬");
        info.add(String.format("USUARIO: %s", usuario.obtenerNombre().toUpperCase()));
        info.add("▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬");
        info.add(String.format("Saldo final: $%.2f", usuario.obtenerDineroDisponible()));
        
        if (usuario.obtenerCuentaBanco() != null) {
            info.add(String.format("Transacciones realizadas: %d", 
                usuario.obtenerCuentaBanco().obtenerTransacciones().size()));
        }
        
        return info;
    }
    
    /**
     * Verifica si una descripción de transacción corresponde a un mes específico.
     * 
     * @param descripcion descripción de la transacción
     * @param mes número del mes a verificar
     * @return true si la descripción corresponde al mes especificado
     */
    private boolean contieneMes(String descripcion, int mes) {
        String patron1 = String.format("mes %02d", mes);
        String patron2 = String.format("mes %d", mes);
        String patron3 = String.format("Mes %02d", mes);
        String patron4 = String.format("Mes %d", mes);
        
        String descripcionLower = descripcion.toLowerCase();
        
        return descripcionLower.contains(patron1) || 
               descripcionLower.contains(patron2) ||
               descripcionLower.contains(patron3.toLowerCase()) ||
               descripcionLower.contains(patron4.toLowerCase());
    }
}




