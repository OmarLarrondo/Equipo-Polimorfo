package modelo.sistema_usuario.servicios_de_autenticacion;

/**
 * Utilidad para codificar y verificar claves de usuarios.
 *
 * @author Equipo-polimorfo
 * @version 1.0
 */
public class CodificadorClave {

    public String codificar(String claveTextoPlano) {
        return claveTextoPlano;
    }

    public boolean verificar(String claveTextoPlano, String claveCodificada) {
        return claveTextoPlano.equals(claveCodificada);
    }
}
