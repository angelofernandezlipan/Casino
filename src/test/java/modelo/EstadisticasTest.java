package modelo;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class EstadisticasTest {

    @Test
    @DisplayName("Caso 5: Estadísticas calculan correctamente victorias y porcentaje")
    void testCalculosEstadisticas() {
        // 1. Crear un repositorio falso (Mock) con datos controlados
        IRepositorioResultados repoMock = new IRepositorioResultados() {
            @Override
            public List<Resultado> obtenerTodos() {
                return Arrays.asList(
                        new Resultado(1, new ApuestaRojo(100), true, 1100),  // Ganó
                        new Resultado(2, new ApuestaNegro(100), true, 1200), // Ganó
                        new Resultado(3, new ApuestaPar(100), false, 1100),  // Perdió
                        new Resultado(4, new ApuestaImpar(100), false, 1000) // Perdió
                );
            }
            // Métodos no usados en la prueba
            @Override public void guardar(Resultado r) {}
            @Override public void cargar() {}
            @Override public void persistir() {}
        };

        // 2. Instanciar Estadísticas inyectando el Mock
        Estadisticas stats = new Estadisticas(repoMock);

        // 3. Validar resultados esperados
        assertEquals(4, stats.getTotalJugadas(), "Debe contar 4 jugadas");
        assertEquals(2, stats.getTotalAciertos(), "Debe contar 2 victorias");
        assertEquals(50.0, stats.getPorcentajeAciertos(), 0.01, "Porcentaje debe ser 50%");
        assertEquals(0, stats.getGananciaNeta(), "Ganancia debe ser 0");
    }
}