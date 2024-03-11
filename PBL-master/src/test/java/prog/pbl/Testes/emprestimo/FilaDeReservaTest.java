package prog.pbl.Testes.emprestimo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import prog.pbl.LibraryException.emprestimoException.ReservarException;
import prog.pbl.LibraryException.usersexcepitions.LeitorException;
import prog.pbl.model.emprestimo.FilaDeReserva;
import prog.pbl.model.usuarios.Leitor;

import java.util.LinkedList;
import java.util.Queue;

import static org.junit.jupiter.api.Assertions.*;

class FilaDeReservaTest {

    private Leitor l;
    private FilaDeReserva f;
    private Queue<Leitor> reservas;

    FilaDeReservaTest() throws LeitorException {
    }

    @BeforeEach
    void criar() throws Exception {
        l = new Leitor("Armando","123","57130521090","Uefs","0000");
        f =  new FilaDeReserva("2");
        reservas = new LinkedList<>();
    }

    @Test
    void getIsbn() {
        assertEquals("2",f.getIsbn());
    }

    @Test
    void getReservas() {
        reservas.add(l);
        f.setReservas(reservas);
        assertEquals(1,f.getReservas().size());
    }

    @Test
    void setIsbn() {
        f.setIsbn("3");
        assertEquals("3",f.getIsbn());
    }

    @Test
    void setReservas() {
        reservas.add(l);
        f.setReservas(reservas);
        assertEquals(1,f.getReservas().size());
    }

    @Test
    void addOnReservas() throws ReservarException {
        f.addOnReservas(l);
        assertEquals(1,f.getReservas().size());

    }

    @Test
    void removeOnReservas() throws ReservarException{
        f.addOnReservas(l);
        f.removeOnReservas();
        assertEquals(0,f.getReservas().size());
    }

    @Test
    void testEquals() {
        FilaDeReserva f1 = new FilaDeReserva("23");
        FilaDeReserva f2 = new FilaDeReserva("24");
        assertFalse(f1.equals(f2));

    }
}