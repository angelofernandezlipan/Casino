package controlador;

import modelo.Usuario;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

class SessionControllerTest {

    @Test
    @DisplayName("Caso 6: Inicio de sesión con usuario no registrado")
    void testLoginUsuarioNoRegistrado() {
        SessionController controller = SessionController.getInstance();
        Usuario resultado = controller.iniciarSesion("usuario_fantasma", "1234");

        assertNull(resultado, "El inicio de sesión debería fallar (retornar null)");
    }

    @Test
    @DisplayName("Caso 7: Inicio de sesión con username nulo")
    void testLoginUsernameNulo() {
        SessionController controller = SessionController.getInstance();
        Usuario resultado = controller.iniciarSesion(null, "1234");
        assertNull(resultado, "El sistema debe rechazar credenciales nulas");
    }
}