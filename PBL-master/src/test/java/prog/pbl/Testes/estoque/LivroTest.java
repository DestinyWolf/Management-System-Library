package prog.pbl.Testes.estoque;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import prog.pbl.model.estoque.Livro;

import static org.junit.jupiter.api.Assertions.*;

class LivroTest {

    private Livro l;
    @BeforeEach
    void criar(){
        l = new Livro("12","Mikey","Diversao","endereco","Canaviais",2023,"Bolsonaro", 1);
    }

    @Test
    void getAnoDePublicacao() {
        assertEquals(2023,l.getAnoDePublicacao());
    }

    @Test
    void getIsbn() {
        assertEquals("12",l.getIsbn());
    }

    @Test
    void getAutor() {
        assertEquals("Mikey",l.getAutor());
    }

    @Test
    void getCategoria() {
        assertEquals("Diversao",l.getCategoria());
    }

    @Test
    void getEditora() {
        assertEquals("Canaviais",l.getEditora());
    }

    @Test
    void getEnderecoLivro() {
        assertEquals("endereco",l.getEnderecoLivro());
    }

    @Test
    void getNome() {
        assertEquals("Bolsonaro",l.getNome());
    }

    @Test
    void setAnoDePublicacao() {
        l.setAnoDePublicacao(2002);
        assertEquals(2002,l.getAnoDePublicacao());
    }

    @Test
    void setAutor() {
        l.setAutor("Pedro");
        assertEquals("Pedro",l.getAutor());
    }

    @Test
    void setCategoria() {
        l.setCategoria("Brincadeira");
        assertEquals("Brincadeira",l.getCategoria());
    }

    @Test
    void setEditora() {
        l.setEditora("Salvador");
        assertEquals("Salvador",l.getEditora());
    }

    @Test
    void setEnderecoLivro() {
        l.setEnderecoLivro("Minha casa");
        assertEquals("Minha casa",l.getEnderecoLivro());
    }

    @Test
    void setIsbn() {
        l.setIsbn("11");
        assertEquals("11",l.getIsbn());
    }

    @Test
    void setNome() {
        l.setNome("Carlos teste");
        assertEquals("Carlos teste",l.getNome());
    }
}