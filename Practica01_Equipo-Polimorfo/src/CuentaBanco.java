import java.util.ArrayList;
import java.util.List;

/**
 * Clase que representa una cuenta bancaria para el sistema de simulación de servicios de streaming.
 * Maneja el saldo del usuario y mantiene un historial completo de todas las transacciones realizadas.
 * Esta clase es fundamental para el procesamiento de pagos y la generación de reportes financieros.
 * 
 * @author Equipo Polimórfo
 * @version 1.0
 */
public class CuentaBanco {
    
    /** Saldo actual de la cuenta bancaria */
    private double saldo;
    
    /** Lista que mantiene el historial completo de transacciones */
    private List<String> transacciones;
    
    /**
     * Constructor que inicializa una nueva cuenta bancaria con un saldo inicial.
     * Registra la transacción inicial en el historial.
     * 
     * @param saldoInicial El monto inicial con el que se crea la cuenta
     * @throws IllegalArgumentException si el saldo inicial es negativo
     */
    public CuentaBanco(double saldoInicial) {
        if (saldoInicial < 0) {
            throw new IllegalArgumentException("El saldo inicial no puede ser negativo");
        }
        this.saldo = saldoInicial;
        this.transacciones = new ArrayList<>();
        this.transacciones.add(String.format("Cuenta creada con saldo inicial: $%.2f", saldoInicial));
    }
    
    /**
     * Realiza un pago descontando el monto del saldo actual.
     * Verifica que haya fondos suficientes antes de procesar el pago.
     * Registra la transacción en el historial independientemente del resultado.
     * 
     * @param monto El monto a descontar de la cuenta
     * @param concepto Descripción del concepto por el cual se realiza el pago
     * @return true si el pago se realizó exitosamente, false si no hay fondos suficientes
     * @throws IllegalArgumentException si el monto es negativo o cero
     */
    public boolean realizarPago(double monto, String concepto) {
        if (monto <= 0) {
            throw new IllegalArgumentException("El monto del pago debe ser mayor a cero");
        }
        
        if (concepto == null || concepto.trim().isEmpty()) {
            concepto = "Pago sin concepto especificado";
        }
        
        if (puedeRealizarPago(monto)) {
            this.saldo -= monto;
            this.transacciones.add(String.format("Pago exitoso: $%.2f - %s (Saldo restante: $%.2f)", 
                                                monto, concepto, this.saldo));
            return true;
        } else {
            this.transacciones.add(String.format("Pago fallido: $%.2f - %s (Saldo insuficiente: $%.2f)", 
                                                monto, concepto, this.saldo));
            return false;
        }
    }
    
    /**
     * Obtiene el saldo actual de la cuenta.
     * 
     * @return El saldo actual de la cuenta
     */
    public double obtenerSaldo() {
        return this.saldo;
    }
    
    /**
     * Obtiene una copia del historial completo de transacciones.
     * 
     * @return Una nueva lista con todas las transacciones realizadas
     */
    public List<String> obtenerTransacciones() {
        return new ArrayList<>(this.transacciones);
    }
    
    /**
     * Verifica si la cuenta tiene fondos suficientes para realizar un pago específico.
     * Este método no modifica el saldo ni registra transacciones.
     * 
     * @param monto El monto que se desea verificar
     * @return true si hay fondos suficientes, false en caso contrario
     * @throws IllegalArgumentException si el monto es negativo o cero
     */
    public boolean puedeRealizarPago(double monto) {
        if (monto <= 0) {
            throw new IllegalArgumentException("El monto a verificar debe ser mayor a cero");
        }
        return this.saldo >= monto;
    }
    
    /**
     * Representación en cadena de la cuenta bancaria.
     * 
     * @return String con información básica de la cuenta
     */
    @Override
    public String toString() {
        return String.format("CuentaBanco{saldo=%.2f, transacciones=%d}", 
                           this.saldo, this.transacciones.size());
    }
}