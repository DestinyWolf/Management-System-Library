package prog.pbl.Testes;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import prog.pbl.LibraryException.estoqueExceptions.LivroException;
import prog.pbl.dao.estoque.ImMemoryLivroDao;
import prog.pbl.model.estoque.Livro;
import prog.pbl.model.relatorio.Relatorios;

class RelatoriosTest {

    private Relatorios rela;
    private Livro livro;
    private ImMemoryLivroDao dao;

    @BeforeEach
    void criar() throws LivroException {
        rela = new Relatorios();
        livro = new Livro("12","Mikey","Diversao","endereco","Canaviais",2023,"Bolsonaro", 1);
        dao = new ImMemoryLivroDao();
        dao.save(livro);

    }


    //dando erro embaixo
    @Test
    void getQntLivros() {
        rela.getQntLivros();
    }

    @Test
    void getQntLivrosAtrasados() {
    }

    @Test
    void getQntLivroEmprestados() {
    }

    @Test
    void getIsbnLivrosMaisBuscados() {
    }

    public void ss(){
        System.out.println("testando");
    }
}