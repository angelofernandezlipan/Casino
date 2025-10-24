package modelo;

public class Resultado {

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

    // Getters para el acceso controlado
    public int getNumero() { return numero; }
    public TipoApuesta getTipoApuesta() { return tipoApuesta; }
    public boolean isAcierto() { return acierto; }
    public int getMontoApostado() { return montoApostado; }
    public int getSaldoFinal() { return saldoFinal; }

    // Para historial legible
    @Override
    public String toString() {
        String res = isAcierto() ? "GANÓ" : "PERDIÓ";
        return String.format("Nº: %d (%s) - Apuesta: %s - Monto: $%d - Saldo Final: $%d",
                numero, res, tipoApuesta.name(), montoApostado, saldoFinal);
    }
}