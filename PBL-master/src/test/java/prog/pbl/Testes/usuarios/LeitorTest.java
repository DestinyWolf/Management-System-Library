package prog.pbl.Testes.usuarios;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import prog.pbl.LibraryException.usersexcepitions.LeitorException;
import prog.pbl.model.usuarios.Leitor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LeitorTest {

    private Leitor l;

    @BeforeEach
    void setUp() throws LeitorException {
        l = new Leitor("Maike","123","62909475085","UEFS",
                "75 9 88888888");
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getId() {
    }

    @Test
    void isBloqueio() {
    }

    @Test
    void getDiasRestantesMulta() {
        l.setDiasRestantesMulta(3);
        assertEquals(3,l.getDiasRestantesMulta());
    }

    @Test
    void getDiasRestantesRenovacao() {
        //Emprestimo em = new Emprestimo();
        //assertEquals(12,l.getDiasRestantesMulta());
    }

    @Test
    void getMaximoDeLivros() {
        l.setMaximoDeLivros(2);
        assertEquals(2,l.getMaximoDeLivros());
    }

    @Test
    void getNumEmprestimos() {
        l.setNumEmprestimos(2);
        assertEquals(2,l.getNumEmprestimos());
    }

    @Test
    void getEndereco() {
        assertEquals("UEFS",l.getEndereco());
    }

    @Test
    void getTelefone() {
        assertEquals("75 9 88888888", l.getTelefone());
    }

    @Test
    void setDiasRestantesMulta() {
        l.setDiasRestantesMulta(2);
        assertEquals(2,l.getDiasRestantesMulta());
    }

    @Test
    void setMaximoDeLivros() {
        l.setMaximoDeLivros(2);
        assertEquals(2,l.getMaximoDeLivros());
    }

    @Test
    void setBloqueio() {
        l.setBloqueio(true);
        assertTrue(l.isBloqueio());
    }

    @Test
    void setEndereco() {
        l.setEndereco("UEFS");
        assertEquals("UEFS",l.getEndereco());
    }

    @Test
    void setNumEmprestimos() {
        l.setNumEmprestimos(2);
        assertEquals(2,l.getNumEmprestimos());
    }

    @Test
    void setTelefone() {
        l.setTelefone("75 9 88888888");
        assertEquals("75 9 88888888",l.getTelefone());
    }

    @Test
    void getEmprestimos() {
    }

    @Test
    void testEquals() {
    }

    @Test
    void testToString() {
    }
}