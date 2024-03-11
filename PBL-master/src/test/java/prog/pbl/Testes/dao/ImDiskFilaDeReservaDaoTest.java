package prog.pbl.Testes.dao;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import prog.pbl.LibraryException.emprestimoException.ReservarException;
import prog.pbl.dao.MasterDao;
import prog.pbl.model.emprestimo.FilaDeReserva;

import static org.junit.jupiter.api.Assertions.*;

class ImDiskFilaDeReservaDaoTest {

    private FilaDeReserva fila;

    @BeforeEach
    void setUp() throws Exception {

        fila = new FilaDeReserva("12");
        MasterDao.getFiladeReservaDao().save(fila);
    }

    @AfterEach
    void setDown() throws Exception{
        MasterDao.getFiladeReservaDao().clearAll();
    }

    @Test
    void findById() throws ReservarException {

        assertEquals(fila, MasterDao.getFiladeReservaDao().findById(fila.getIsbn()));

    }

    @Test
    void save() throws ReservarException {

        assertEquals(1, MasterDao.getFiladeReservaDao().findAll().size());
    }

    @Test
    void delete() throws ReservarException{
        MasterDao.getFiladeReservaDao().delete(fila);
        assertEquals(0,MasterDao.getFiladeReservaDao().findAll().size() );
    }

    @Test
    void update() throws ReservarException{
        FilaDeReserva novaFila = new FilaDeReserva("778");
        MasterDao.getFiladeReservaDao().Update(novaFila,fila);
        assertNotEquals(fila, MasterDao.getFiladeReservaDao().findById(novaFila.getIsbn()));
    }

    @Test
    void findAll() throws ReservarException{
        assertEquals(1,MasterDao.getFiladeReservaDao().findAll().size());
    }
}