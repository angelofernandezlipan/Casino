package modelo;

import java.util.List;

/**
 * Interfaz o contrato que define las operaciones para un repositorio de historial de resultados (Versión 8).
 * Implementa el Principio de Inversión de Dependencias (DIP).
 */
public interface IRepositorioResultados {

    // Guarda un nuevo resultado en el repositorio. El parámetro r es el Resultado de la jugada.
    void guardar(Resultado r);

    // Obtiene todos los resultados almacenados. Retorna una lista de todos los resultados.
    List<Resultado> obtenerTodos();

    // Carga datos en memoria
    void cargar();

    // Persiste datos
    void persistir();
}