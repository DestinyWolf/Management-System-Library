package prog.pbl.Testes.usuarios;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import prog.pbl.LibraryException.usersexcepitions.AdministradorException;
import prog.pbl.model.usuarios.Administrador;

import static org.junit.jupiter.api.Assertions.*;

class AdministradorTest {

    private Administrador a;
    @BeforeEach
    void criar() throws AdministradorException {
        a = new Administrador("123","Ken","Maximo","57130521090");
    }



    @Test
    void getCargo() {
        a.setCargo("maximo");
        assertEquals("maximo",a.getCargo());
    }

    @Test
    void setCargo() {
        a.setCargo("maximo");
        assertEquals("maximo",a.getCargo());
    }
}