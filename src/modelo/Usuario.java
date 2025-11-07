package modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.io.Serializable;

public class Usuario implements Serializable {

    // Atributos privados (Encapsulamiento V4)
    private String username;
    private String password;
    private String nombre;

    // Asociación 1 a Muchos con Resultado (V5)
    private final List<Resultado> historial = new ArrayList<>();

    public Usuario(String username, String password, String nombre) {
        this.username = username;
        this.password = password;
        this.nombre = nombre;
    }

    public boolean validarCredenciales(String u, String p) {
        return this.username.equals(u) && this.password.equals(p);
    }

    // Getters
    public String getNombre() { return nombre; }
    public String getUsername() { return username; }

    // Métodos para manejar la Asociación (V5)
    public void agregarResultado(Resultado r) {
        historial.add(r);
    }

    public List<Resultado> getHistorial() {
        // Retorna lista inmutable (Encapsulamiento)
        return Collections.unmodifiableList(historial);
    }
}