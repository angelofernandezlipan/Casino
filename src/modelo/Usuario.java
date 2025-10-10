package modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Usuario {

    // Atributos existentes (Encapsulados)
    private String username;
    private String password;
    private String nombre;

    // <<< CÓDIGO NUEVO (V5): Asociación 1 a muchos con Resultado
    private final List<Resultado> historial = new ArrayList<>();

    // Constructor
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

    // <<< CÓDIGO NUEVO (V5): Méthod para agregar el resultado
    public void agregarResultado(Resultado r) {
        historial.add(r);
    }

    // <<< CÓDIGO NUEVO (V5): Méthod para recuperar el historial
    public List<Resultado> getHistorial() {
        // Devuelve una lista inmodificable para proteger los datos
        return Collections.unmodifiableList(historial);
    }
}