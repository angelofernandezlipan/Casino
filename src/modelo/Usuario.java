package modelo;

import java.io.Serializable;

public class Usuario implements Serializable {

    // Atributos privados (Encapsulamiento V4)
    private String username;
    private String password;
    private String nombre;

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
}