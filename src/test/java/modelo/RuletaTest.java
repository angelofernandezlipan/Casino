package modelo;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

class RuletaTest {

    @Test
    @DisplayName("Caso 1: Constructor rechaza saldo inicial negativo")
    void testConstructorSaldoNegativo() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Ruleta(-100);
        });
        assertEquals("Saldo inicial inválido", exception.getMessage());
    }

    @Test
    @DisplayName("Caso 2: Depósito incrementa el saldo correctamente")
    void testDepositar() {
        Ruleta ruleta = new Ruleta(100);
        ruleta.depositar(50);
        assertEquals(150, ruleta.getSaldo(), "El saldo debería ser 150 tras depositar 50");
    }

    @Test
    @DisplayName("Caso 3: Jugar con apuesta nula es rechazada")
    void testJugarApuestaNula() {
        Ruleta ruleta = new Ruleta(1000);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            ruleta.jugar(null);
        });
        assertEquals("Apuesta requerida", exception.getMessage());
    }

    @Test
    @DisplayName("Caso 4: Apuesta con monto mayor al saldo es rechazada")
    void testJugarSaldoInsuficiente() {
        Ruleta ruleta = new Ruleta(100); // Saldo 100
        ApuestaBase apuesta = new ApuestaRojo(200); // Apuesta 200 (Excede saldo)

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            ruleta.jugar(apuesta);
        });
        assertEquals("Saldo insuficiente", exception.getMessage());
    }
}