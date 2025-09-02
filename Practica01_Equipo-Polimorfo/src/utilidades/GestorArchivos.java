package utilidades;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Scanner;

/**
 * Clase  para manejar la generación de reportes. 
 * Esta clase debe crear archivos de texto con todas las transacciones 
 * del sistema, organizadas de manera clara y legible. 
 * La implementación de GestorArchivos debe ser robusta para manejar 
 * la escritura de archivos y el formateo adecuado de la información..
 * 
 */

public class GestorArchivos {


    /**
     * Debe escribir las transacciones en <code>nombreArchivo</code>. 
     * Si <code>nombreArchivo</code>,existe se debe confirmar que se borrara todo el contenido 
     * del archivo y no se podra recuperar. 
     * Si <code>nombreArchivo</code> NO existe, se crea un txt con el nombre de {@link nombreArchivo} 
     * 
     * Escribe linea por linea todas las transacciones en <code>nombreArchivo</code>
     * 
     * @param transacciones todas las transacciones hechas
     * @param nombreArchivo El nombre del archico.txt en donde se guardan todas
     *  las transacciones hechas en cada mes y para cada cliente 
     */
    public void escribirTransaccionesCompletas(List<String> transacciones, String nombreArchivo){
        Path rutaArchivo = Paths.get(nombreArchivo);
        try {
            
            if (Files.exists(rutaArchivo)) {
                // Si existe, limpia el contenido PREGUNTAR PORQUE ES PELIGROSO
                Scanner sc = new Scanner(System.in);
                System.out.println(String.format(
        "PRECAUCION!!!!. Se limpiara el contenido de %s, Estas segur@ que desea limpiar el contenido? s/n: "
                    ,nombreArchivo));
                String desicion = sc.nextLine();
                desicion = desicion.trim().toLowerCase();
                if(desicion.equals("si") || desicion.equals("s")){
                    System.out.println(String.format(
            "Se BORRARA definitivamente %s, No hay forma de recuperarlo, Desea continuar? s/n"
                    ,nombreArchivo));
                    String desicionDos = sc.nextLine();
                    desicionDos = desicionDos.trim().toLowerCase();
                    if(desicionDos.equals("si") || desicionDos.equals("s")){
                    Files.write(rutaArchivo, new byte[0]); // deja el archivo vacío
                    }else{
                        return;
                    }
                }else{
                    return;
                }
            //Si no existe el archivo, lo crea 
            } else {
                // Si no existe, lo crea
                Files.createFile(rutaArchivo);
                System.out.println("Se creo el archivo correctamente");
            }
            try (BufferedWriter writer = Files.newBufferedWriter(rutaArchivo, StandardCharsets.UTF_8)) {
                for (String transaccion : transacciones) {
                    writer.write(transaccion);
                    writer.newLine(); // agrega salto de línea
                }
            }

        } catch (IOException e) {
            System.out.println("Error al crear o limpiar el archivo: " + e.getMessage());
        }


    }


    /**
     * 
     * @param mes el mes para obtener el resumen de transacciones
     * @param transacciones 
     * @param nombreArchivo
     */
    public void escribirResumenMensual(int mes, List<String> transacciones, String nombreArchivo){
        Path nombreArchiivo = Paths.get(nombreArchivo);
        String mesFormato = String.format("-%02d-", mes);

        List<String> filtradas = transacciones.stream()
                .filter(t -> t.contains(mesFormato))
                .toList();

        try {
            if (!Files.exists(path)) {
                Files.createFile(path);
            }

            if (filtradas.isEmpty()) {
                Files.write(path, List.of("No hay transacciones registradas en el mes " + mes));
            } else {
                Files.write(path, filtradas);
            }

            System.out.println("Resumen mensual escrito en: " + nombreArchivo);

        } catch (IOException e) {
            System.out.println("Error al escribir el resumen mensual: " + e.getMessage());
        }
    }

    public void escribirEstadoFinalUsuarios(List<String> usuarios, String nombreArchiivo){

    }
}




