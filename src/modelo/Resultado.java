package modelo;

public class Resultado {

    // Atributos privados y finales (Inmutabilidad y Encapsulamiento)
    private final int numero;
    private final TipoApuesta tipoApuesta;
    private final boolean acierto;
    private final int montoApostado;
    private final int saldoFinal;

    // Constructor completo
    public Resultado(int numero, TipoApuesta tipoApuesta, boolean acierto, int montoApostado, int saldoFinal) {
        this.numero = numero;
        this.tipoApuesta = tipoApuesta;
        this.acierto = acierto;
        this.montoApostado = montoApostado;
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