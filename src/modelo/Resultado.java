package modelo;

import java.io.Serializable;

public class Resultado implements Serializable{

    // Atributos privados y finales (Inmutabilidad y Encapsulamiento)
    private final int numero;
    private final String tipoApuesta; // (V7)
    private final boolean acierto;
    private final int montoApostado;
    private final int saldoFinal;

    // Constructor completo
    public Resultado(int numero, ApuestaBase apuesta, boolean acierto, int saldoFinal) {
        this.numero = numero;
        this.tipoApuesta = apuesta.getEtiqueta();
        this.acierto = acierto;
        this.montoApostado = apuesta.getMontoApostado;
        this.saldoFinal = saldoFinal;
    }

    // Getters
    public int getNumero() { return numero; }
    public String getTipoApuesta() { return tipoApuesta; }
    public boolean isAcierto() { return acierto; }
    public int getMontoApostado() { return montoApostado; }
    public int getSaldoFinal() { return saldoFinal; }

    @Override
    public String toString() {
        String res = isAcierto() ? "GANÓ" : "PERDIÓ";
        return String.format("Nº: %d (%s) - Apuesta: %s - Monto: $%d - Saldo Final: $%d",
                numero, res, tipoApuesta, montoApostado, saldoFinal);
    }
}