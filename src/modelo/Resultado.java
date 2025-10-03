package modelo;

public class Resultado {

    // Atributos para una ronda de juego
    private final int numero;
    private final TipoApuesta tipoApuesta;
    private final boolean acierto;
    private final int montoApostado;
    private final int saldoFinal; // útil para el historial

    // Constructor que inicializa el objeto Resultado
    public Resultado(int numero, TipoApuesta tipoApuesta, boolean acierto, int montoApostado, int saldoFinal) {
        this.numero = numero;
        this.tipoApuesta = tipoApuesta;
        this.acierto = acierto;
        this.montoApostado = montoApostado;
        this.saldoFinal = saldoFinal;
    }

    // Getters para el historial

    public int getNumero() {
        return numero;
    }

    public TipoApuesta getTipoApuesta() {
        return tipoApuesta;
    }

    public boolean isAcierto() {
        return acierto;
    }

    public int getMontoApostado() {
        return montoApostado;
    }

    public int getSaldoFinal() {
        return saldoFinal;
    }

    // Gemini: Para una mejor visualización en consola o debugging
    @Override
    public String toString() {
        String resultado = acierto ? "GANÓ" : "PERDIÓ";
        return String.format("Nº: %d (%s) - Apuesta: %s - Monto: $%d - Saldo Final: $%d",
                numero, resultado, tipoApuesta.name(), montoApostado, saldoFinal);
    }
}
