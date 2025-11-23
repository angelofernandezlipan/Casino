package modelo;

import java.util.List;
import java.util.Arrays;
import java.util.Random;

public class Ruleta {

    private final List<Integer> numerosRojos = Arrays.asList(1, 3, 5, 7, 9, 12, 14, 16, 18, 19, 21, 23, 25, 27, 30, 32, 34, 36);
    private final Random rng = new Random();

    private int saldo;

    // Constructor por defecto (Saldo 0)
    public Ruleta() {
        this(0);
    }

    public Ruleta(int saldoInicial) {
        if (saldoInicial < 0) {
            throw new IllegalArgumentException("Saldo inicial inválido");
        }
        this.saldo = saldoInicial;
    }

    public int girarRuleta() {
        return rng.nextInt(37);
    }

    public String colorDe(int numero) {
        if (numero == 0) return "Verde";
        return numerosRojos.contains(numero) ? "Rojo" : "Negro";
    }

    public void depositar(int monto) {
        if (monto <= 0) {
            throw new IllegalArgumentException("El monto debe ser positivo");
        }
        this.saldo += monto;
    }

    public int getSaldo() {
        return saldo;
    }

    /**
     * Lógica central del juego con validaciones.
     * (Para Test Casos 3 y 4)
     */
    public Resultado jugar(ApuestaBase apuesta) {
        // Validación Caso 3: Apuesta nula
        if (apuesta == null) {
            throw new IllegalArgumentException("Apuesta requerida");
        }

        // Validación Caso 4: Saldo insuficiente
        if (apuesta.getMontoApostado() > this.saldo) {
            throw new IllegalArgumentException("Saldo insuficiente");
        }

        // Lógica del juego
        int numero = girarRuleta();
        String color = colorDe(numero);
        boolean acierto = apuesta.acierta(numero, color);

        // Actualizar saldo interno
        if (acierto) {
            this.saldo += apuesta.getMontoApostado();
        } else {
            this.saldo -= apuesta.getMontoApostado();
        }

        return new Resultado(numero, apuesta, acierto, this.saldo);
    }
}