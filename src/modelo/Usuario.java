package modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Usuario {
    private String username;
    private String password;
    private String nombre;

    // Implementación de la Asociación: 1 a n con Resultado
    private final List<Resultado> historial = new ArrayList<>();

    public Usuario(String username, String password, String nombre) {
        this.username = username;
        this.password = password;
        this.nombre = nombre;
    }

    public boolean validarCredenciales(String u, String p) {
        return this.username.equals(u) && this.password.equals(p);
    }

    public String getNombre() {
        return nombre;
    }

    public void agregarResultado(Resultado r) {
        historial.add(r);
    }

    public String getUsername() {
        return username;
    }

    public List<Resultado> getHistorial() {
        // Devuelve una versión inmodificable para proteger la integridad del historial
        return Collections.unmodifiableList(historial);
    }
}