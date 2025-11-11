package controlador;

import modelo.Usuario;
import modelo.IRepositorioResultados;
import modelo.RepositorioArchivo;
import modelo.RepositorioEnMemoria; // Opcional, para pruebas

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Gestiona la sesión del usuario y la persistencia de datos.
 * Implementa el patrón Singleton.
 * V8: Desacopla el historial del usuario e inyecta el repositorio.
 */
public class SessionController {

    // --- Singleton ---
    private static final SessionController INSTANCE = new SessionController();

    // --- Estado de Persistencia V7 (Usuarios) ---
    private List<Usuario> usuarios = new ArrayList<>();
    private static final String ARCHIVO_USUARIOS = "usuarios.dat";

    // --- Estado de Sesión V8 (Historial) ---
    private Usuario usuarioActual;
    private IRepositorioResultados repositorioResultados; // Depende de la abstracción

    /**
     * Constructor privado (Singleton).
     * Carga los usuarios al iniciar. Si no existen, crea los de por defecto.
     */
    private SessionController() {
        cargarUsuarios(); // Carga usuarios desde "usuarios.dat"

        if (usuarios.isEmpty()) {
            usuarios.add(new Usuario("donnie", "1234", "Donnie"));
            usuarios.add(new Usuario("admin", "admin", "Administrador"));
            guardarUsuarios(); // Guarda los usuarios iniciales
        }
    }

    public static SessionController getInstance() {
        return INSTANCE;
    }

    /**
     * Valida al usuario e INYECTA el repositorio de historial (V8).
     */
    public Usuario iniciarSesion(String u, String p) {
        for (Usuario usuario : usuarios) {
            if (usuario.validarCredenciales(u, p)) {
                this.usuarioActual = usuario;

                // Inyección de Dependencia (V8):
                // Decide qué implementación de repositorio usar.
                String repoFileName = usuario.getUsername() + "_historial.dat";
                this.repositorioResultados = new RepositorioArchivo(repoFileName);

                // Carga el historial específico de ese usuario
                this.repositorioResultados.cargar();

                return usuario;
            }
        }
        return null; // Credenciales incorrectas
    }

    /**
     * Cierra la sesión, guardando tanto los usuarios como el historial (V8).
     */
    public void cerrarSesion() {
        guardarUsuarios(); // Guarda la lista de usuarios

        // Persiste el historial del usuario actual
        if (repositorioResultados != null) {
            repositorioResultados.persistir();
        }

        usuarioActual = null;
        repositorioResultados = null;
    }

    // --- Getters de Sesión ---

    public Usuario getUsuarioActual() {
        return usuarioActual;
    }

    public IRepositorioResultados getRepositorio() {
        return repositorioResultados;
    }

    // --- Métodos de Persistencia de USUARIOS (V7) ---

    @SuppressWarnings("unchecked")
    private void cargarUsuarios() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ARCHIVO_USUARIOS))) {
            usuarios = (List<Usuario>) ois.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("No se encontró " + ARCHIVO_USUARIOS + ". Se creará uno nuevo.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error al cargar usuarios: " + e.getMessage());
            usuarios = new ArrayList<>();
        }
    }

    private void guardarUsuarios() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARCHIVO_USUARIOS))) {
            oos.writeObject(usuarios);
        } catch (IOException e) {
            System.out.println("Error al guardar usuarios: " + e.getMessage());
        }
    }
}