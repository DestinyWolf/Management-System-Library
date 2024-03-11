package prog.pbl.Testes.dao;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import prog.pbl.LibraryException.emprestimoException.EmprestimoException;
import prog.pbl.LibraryException.estoqueExceptions.LivroException;
import prog.pbl.LibraryException.usersexcepitions.LeitorException;
import prog.pbl.dao.MasterDao;
import prog.pbl.dao.emprestimo.ImMemoryEmprestimoDao;
import prog.pbl.model.emprestimo.Emprestimo;
import prog.pbl.model.estoque.Livro;
import prog.pbl.model.usuarios.Leitor;
import prog.pbl.util.Data;

import static org.junit.jupiter.api.Assertions.*;

class ImMemoryEmprestimoDaoTest {

    Emprestimo emprestimo;
    Leitor leitor;
    Livro livro;
    ImMemoryEmprestimoDao emprestimoDao;
    @BeforeEach
    void criar() throws LeitorException, EmprestimoException, LivroException, Exception {
        leitor = new Leitor("Maike","123","62909475085","UEFS",
                "75 9 88888888");
        livro = new Livro("12","Mikey","Diversao","endereco","Canaviais",2023,"Bolsonaro", 1);
        MasterDao.getLivroDao().save(livro);
        MasterDao.getLeitorDao().save(leitor);
        emprestimo = new Emprestimo(leitor, livro);
        emprestimoDao = new ImMemoryEmprestimoDao();
        emprestimo.setDevolvido(true);

    }

    @AfterEach
    void limpar() throws Exception{
        MasterDao.getLeitorDao().delete(leitor);
        emprestimoDao = new ImMemoryEmprestimoDao();
        MasterDao.getLivroDao().delete(livro);
    }

    @Test
    void findById() throws Exception {
        emprestimoDao.save(emprestimo);
        assertEquals(emprestimo, emprestimoDao.findById(livro.getIsbn() + new Data().getDia() + leitor.getId()));
    }

    @Test
    void save() throws EmprestimoException {
        emprestimoDao.save(emprestimo);
    }

    @Test
    void deleteById() throws Exception{

        emprestimoDao.save(emprestimo);
        emprestimoDao.delete(emprestimo);
        assertEquals(0, emprestimoDao.findAll().size());

    }

    @Test
    void update() throws Exception {
        emprestimoDao.save(emprestimo);
        Leitor leitor2 = new Leitor("Armando","123","57130521090","Uefs","0000");
        Emprestimo emprestimo2 = new Emprestimo(leitor2, livro);
        emprestimoDao.Update(emprestimo2, emprestimo);
        assertEquals(emprestimo2, emprestimoDao.findById(emprestimo2.getId()));
    }

    @Test
    void findAll() throws Exception{
        emprestimoDao.save(emprestimo);
        Leitor leitor2 = new Leitor("Armando","123","57130521090","Uefs","0000");
        Emprestimo emprestimo2 = new Emprestimo(leitor2, livro);
        emprestimoDao.save(emprestimo2);
        assertEquals(2, emprestimoDao.findAll().size());

    }

    @Test
    void findByUser() throws Exception{
        emprestimoDao.save(emprestimo);
        Leitor leitor2 = new Leitor("Armando","123","57130521090","Uefs","0000");
        Emprestimo emprestimo2 = new Emprestimo(leitor2, livro);
        emprestimoDao.save(emprestimo2);
        assertEquals(1,  emprestimoDao.findByUser(leitor.getId()).size());
    }

    @Test
    void findByLivro() throws Exception{
        emprestimoDao.save(emprestimo);
        Leitor leitor2 = new Leitor("Armando","123","57130521090","Uefs","0000");
        Emprestimo emprestimo2 = new Emprestimo(leitor2, livro);
        emprestimoDao.save(emprestimo2);
        assertEquals(2,  emprestimoDao.findByLivro(livro.getIsbn()).size());

    }

    @Test
    void findByUserAndLivro() throws Exception{
        emprestimoDao.save(emprestimo);
        Leitor leitor2 = new Leitor("Armando","123","57130521090","Uefs","0000");
        Emprestimo emprestimo2 = new Emprestimo(leitor2, livro);
        emprestimoDao.save(emprestimo2);
        assertEquals(emprestimo, emprestimoDao.findByUserAndLivro(livro.getIsbn(), leitor.getId()));
    }

    @Test
    void findEmprestimosAtivosPorUsuario() throws Exception {
        emprestimoDao.save(emprestimo);
        emprestimo.setDevolvido(false);
        emprestimoDao.Update(emprestimo, emprestimo);
        Leitor leitor2 = new Leitor("Armando","123","57130521090","Uefs","0000");
        Emprestimo emprestimo2 = new Emprestimo(leitor2, livro);
        emprestimoDao.save(emprestimo2);
        assertEquals(1, emprestimoDao.findEmprestimosAtivosPorUsuario(leitor.getId()).size());
    }

    @Test
    void findEmprestimosAtivos() throws Exception {
        emprestimoDao.save(emprestimo);
        emprestimo.setDevolvido(false);
        emprestimoDao.Update(emprestimo, emprestimo);
        Leitor leitor2 = new Leitor("Armando","123","57130521090","Uefs","0000");
        Emprestimo emprestimo2 = new Emprestimo(leitor2, livro);
        emprestimoDao.save(emprestimo2);
        assertEquals(2, emprestimoDao.findEmprestimosAtivos().size());
    }

    @Test
    void findEmprestimosEncerrados() throws Exception{
        emprestimoDao.save(emprestimo);
        emprestimo.setDevolvido(true);
        emprestimoDao.Update(emprestimo, emprestimo);
        Leitor leitor2 = new Leitor("Armando","123","57130521090","Uefs","0000");
        MasterDao.getLeitorDao().save(leitor2);
        Emprestimo emprestimo2 = new Emprestimo(leitor2, livro);
        emprestimoDao.save(emprestimo2);
        emprestimo2.setDevolvido(true);
        emprestimoDao.Update(emprestimo2, emprestimo2);
        assertEquals(2, emprestimoDao.findEmprestimosEncerrados().size());
    }
}