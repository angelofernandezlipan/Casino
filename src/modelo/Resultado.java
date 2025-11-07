package modelo;

import java.io.Serializable; // Necesario para guardar en archivos (V7)

// Implementa Serializable para poder guardarse
public class Resultado implements Serializable {

    private final int numero;
    private final String tipoApuesta; // Se guarda la etiqueta (String)
    private final int montoApostado;
    private final boolean acierto;
    private final int saldoFinal;

    // Constructor completo (V7)
    // Recibe el objeto ApuestaBase completo
    public Resultado(int numero, ApuestaBase apuesta, boolean acierto, int saldoFinal) {
        this.numero = numero;
        this.acierto = acierto;
        this.saldoFinal = saldoFinal;

        // Llama a los getters de ApuestaBase para obtener los datos
        this.tipoApuesta = apuesta.getEtiqueta();
        this.montoApostado = apuesta.getMontoApostado();
    }

    // --- GETTERS ---

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