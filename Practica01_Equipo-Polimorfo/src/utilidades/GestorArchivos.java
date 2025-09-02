package utilidades;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


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


    public void escribirTransaccionesCompletas(List<String> transacciones, String nombreArchivo){

        Path path = Paths.get(nombreArchivo);
        try {
            
            if (Files.exists(path)) {
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
                    Files.write(path, new byte[0]); // deja el archivo vacío
                    }else{
                        return;
                    }
                }else{
                    return;
                }
            //Si no existe el archivo, lo crea 
            } else {
                // Si no existe, lo crea
                Files.createFile(path);
                System.out.println("Se creo el archivo correctamente");
            }
        } catch (IOException e) {
            System.out.println("Error al crear o limpiar el archivo: " + e.getMessage());
        }
    }


    public void escribirResumenMnesual(int mes, List<String> transacciones, String nombreArchivo){
        Path path = Paths.get(nombreArchivo);

        String mesFormato = String.format("-%02d-", mes);

        // Filtrar transacciones que contienen ese mes
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




