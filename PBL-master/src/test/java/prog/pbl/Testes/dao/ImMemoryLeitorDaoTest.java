package prog.pbl.Testes.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import prog.pbl.LibraryException.usersexcepitions.LeitorException;
import prog.pbl.dao.MasterDao;
import prog.pbl.dao.usuarios.ImMemoryLeitorDao;
import prog.pbl.model.estoque.Livro;
import prog.pbl.model.usuarios.Leitor;

import static org.junit.jupiter.api.Assertions.*;

class ImMemoryLeitorDaoTest {

    private Leitor leitor;
    private ImMemoryLeitorDao dao;



    @BeforeEach
    void criar () throws Exception{
        leitor = new Leitor("Maike","123","62909475085","UEFS",
                "75 9 88888888");
        dao = new ImMemoryLeitorDao();
        MasterDao.getLivroDao().save(new Livro("1","bla", "horror", "pat", "saraiva",2000,"as aventuras de creitin", 1));
    }

    @Test
    void save() throws LeitorException, Exception {
        dao.save(leitor);
        assertEquals(leitor,dao.findById(leitor.getId()));
    }

    @Test
    void delete() throws LeitorException, Exception{
        dao.save(leitor);
        dao.delete(leitor);


    }

    @Test
    void update() throws LeitorException, Exception{
        dao.save(leitor);
        Leitor leitor2 = new Leitor("Pedro","222","21780640005","SAP","000");
        dao.Update(leitor2,leitor);
        dao.findById(leitor2.getId());
    }

    @Test
    void findById() throws LeitorException, Exception{
        dao.save(leitor);
        assertEquals(leitor,dao.findById(leitor.getId()));
    }


    @Test
    void findAll() throws LeitorException, Exception{

        dao.save(leitor);
        assertEquals(1,dao.findAll().size());
    }

    @Test
    void testFindLogin() throws LeitorException, Exception {
        dao.save(leitor);
        Leitor f = dao.findLogin(leitor.getId(), leitor.getSenha());
        assertNotNull(f);
    }
}