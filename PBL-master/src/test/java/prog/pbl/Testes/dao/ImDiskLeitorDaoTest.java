package prog.pbl.Testes.dao;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import prog.pbl.LibraryException.usersexcepitions.LeitorException;
import prog.pbl.dao.MasterDao;
import prog.pbl.model.estoque.Livro;
import prog.pbl.model.usuarios.Leitor;

import static org.junit.jupiter.api.Assertions.*;

class ImDiskLeitorDaoTest {

    private Leitor leitor;



    @BeforeEach
    void setUp () throws Exception{
        leitor = new Leitor("Maike","123","62909475085","UEFS",
                "75 9 88888888");
        MasterDao.getLivroDao().save(new Livro("1","bla", "horror", "pat", "saraiva",2000,"as aventuras de creitin", 1));
        MasterDao.getLeitorDao().save(leitor);
    }

    @AfterEach
    void setDown() throws Exception{
        MasterDao.getLeitorDao().clearAll();
    }

    @Test
    void save() throws LeitorException, Exception {

        assertEquals(leitor,MasterDao.getLeitorDao().findById(leitor.getId()));


    }

    @Test
    void delete() throws LeitorException, Exception{
        Leitor leitor1 = new Leitor("Carlinhos", "123", "18011808060","sua casa", "75 9 92323232");
        MasterDao.getLeitorDao().save(leitor1);
        MasterDao.getLeitorDao().delete(leitor);

        assertEquals(1,MasterDao.getLeitorDao().findAll().size());


    }

    @Test
    void update() throws LeitorException, Exception{
        Leitor leitor2 = new Leitor("Pedro","222","21780640005","SAP","000");
        MasterDao.getLeitorDao().Update(leitor2,leitor);
        assertNotEquals(leitor, MasterDao.getLeitorDao().findById(leitor2.getId()));
    }

    @Test
    void findById() throws LeitorException, Exception{
        assertEquals(leitor,MasterDao.getLeitorDao().findById(leitor.getId()));
    }


    @Test
    void findAll() throws LeitorException, Exception{

        assertEquals(1,MasterDao.getLeitorDao().findAll().size());
    }

    @Test
    void testFindLogin() throws LeitorException, Exception {

        Leitor f = MasterDao.getLeitorDao().findLogin(leitor.getId(), leitor.getSenha());
        assertEquals(leitor, f);
    }
}