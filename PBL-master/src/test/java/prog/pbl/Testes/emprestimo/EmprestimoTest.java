package prog.pbl.Testes.emprestimo;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import prog.pbl.dao.MasterDao;
import prog.pbl.model.emprestimo.Emprestimo;
import prog.pbl.model.emprestimo.FilaDeReserva;
import prog.pbl.model.estoque.Livro;
import prog.pbl.model.usuarios.Leitor;
import prog.pbl.util.Data;

import static org.junit.jupiter.api.Assertions.*;

class EmprestimoTest {

    Emprestimo emprestimo;
    Leitor leitor;
    Livro livro;

    @BeforeEach
    void setUp () throws Exception{
        leitor = new Leitor("Maike","123","62909475085","UEFS",
                "75 9 88888888");
        livro = new Livro("12","Mikey","Diversao","endereco","Canaviais",2023,"Bolsonaro", 1);
        MasterDao.getLivroDao().save(livro);
        MasterDao.getLeitorDao().save(leitor);
        emprestimo = new Emprestimo(leitor, livro);


    }

    @AfterEach
    void tearDown() throws Exception {
        MasterDao.getLeitorDao().delete(leitor);
        MasterDao.getLivroDao().delete(livro);
    }

    @Test
    void getPrazoFinal() {
        Data data= new Data(new Data().getDia()+7, new Data().getMes(), new Data().getAno());

        assertSame(0, data.compareData(emprestimo.getPrazoFinal()));
    }

    @Test
    void getDataEmprestimo() {
        assertSame(0, emprestimo.getDataEmprestimo().compareData(new Data()));
    }

    @Test
    void getLeitor() {
        assertEquals(leitor, emprestimo.getLeitor());
    }

    @Test
    void getLivro() {
        assertEquals(livro, emprestimo.getLivro());
    }

    @Test
    void isDevolvido() {
        assertFalse(emprestimo.isDevolvido());
    }

    @Test
    void getId() {
        assertEquals(livro.getIsbn() + new Data().getDia() + leitor.getId(), emprestimo.getId());
    }

    @Test
    void getRenovacoes() {
        assertEquals(0, emprestimo.getRenovacoes());
    }

    @Test
    void getDataDevolucao() throws Exception{
        Data data = new Data();
        emprestimo.setDevolvido(true);
        assertEquals(0, emprestimo.getDataDevolucao().compareData(data));
    }

    @Test
    void setPrazoFinal() {
        Data data = new Data( new Data().getDia()+1, new Data().getMes(), new Data().getAno());
        emprestimo.setPrazoFinal(new Data( new Data().getDia()+1, new Data().getMes(), new Data().getAno()));
        assertEquals(0, data.compareData(emprestimo.getPrazoFinal()));

    }

    @Test
    void setDataEmprestimo() {
        Data data = new Data( new Data().getDia()+1, new Data().getMes(), new Data().getAno());
        emprestimo.setDataEmprestimo(new Data( new Data().getDia()+1, new Data().getMes(), new Data().getAno()));
        assertEquals(0, data.compareData(emprestimo.getDataEmprestimo()));

    }

    @Test
    void setDevolvido() throws Exception{
        emprestimo.setDevolvido(true);
        assertTrue(emprestimo.isDevolvido());
        assertEquals(2,emprestimo.getLeitor().getNumEmprestimos());

    }

    @Test
    void setLeitor() throws Exception{
        Leitor leitor2 = new Leitor("Armando","123","57130521090","Uefs","0000");
        emprestimo.setLeitor(leitor2);
        assertEquals(leitor2, emprestimo.getLeitor());
    }

    @Test
    void setLivro() {
        Livro livro2 = new Livro("11","Mikey","Diversao","endereco","Canaviais",2023,"Bolsonaro", 1);
        emprestimo.setLivro(livro2);
        assertEquals(livro2, emprestimo.getLivro());

    }

    @Test
    void setId() {
        emprestimo.setId("abrobinha");
        assertEquals("abrobinha", emprestimo.getId());
    }

    @Test
    void setRenovacoes() {
        emprestimo.setRenovacoes(2);
        assertEquals(2, emprestimo.getRenovacoes());
    }

    @Test
    void setDataDevolucao() {
        emprestimo.setDataDevolucao(new Data());
        assertEquals(0, new Data().compareData(emprestimo.getDataDevolucao()));
    }

    @Test
    void renovacaoEmprestimo() throws Exception{
        MasterDao.getFiladeReservaDao().save(new FilaDeReserva("12"));
        emprestimo.renovacaoEmprestimo("12","62909475085");
        assertEquals(1, emprestimo.getRenovacoes());
    }
}