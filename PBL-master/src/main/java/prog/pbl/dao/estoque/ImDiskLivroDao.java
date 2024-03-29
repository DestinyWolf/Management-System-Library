package prog.pbl.dao.estoque;

import prog.pbl.LibraryException.emprestimoException.EmprestimoException;
import prog.pbl.LibraryException.estoqueExceptions.LivroException;
import prog.pbl.dao.FileManeger;
import prog.pbl.dao.MasterDao;
import prog.pbl.model.estoque.Livro;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import static prog.pbl.util.Constantes.*;

public class ImDiskLivroDao implements LivroDao{
    private HashMap<String, Livro> livros;

    public ImDiskLivroDao() {
        try {
            this.livros = FileManeger.openLivro();
        } catch (Exception ignored) {

        }
    }

    @Override
    public Livro findById(String id) throws LivroException {
        if(this.livros.get(id) != null) {
            Livro livroEcontrado = livros.get(id);
            livroEcontrado.setQntDeBuscas(livroEcontrado.getQntDeBuscas()+1);
            this.Update(livroEcontrado, livros.get(id));
            return livroEcontrado;
        } else if (livros.isEmpty()) {
            throw new LivroException(findWhenNotHaveObj, null);
        } else {
            throw new LivroException(findLivro, null);
        }
    }

    @Override
    public void save(Livro obj) throws LivroException {
        Livro livro = new Livro(obj.getIsbn(), obj.getAutor(), obj.getCategoria(), obj.getEnderecoLivro(), obj.getEditora(), obj.getAnoDePublicacao(), obj.getNome(), obj.getQuantidade());

        if (this.livros.get(livro.getIsbn()) != null) {
            Livro livros = this.livros.get(livro.getIsbn());
            livros.setQuantidade(livros.getQuantidade()+obj.getQuantidade());
            this.livros.put(livro.getIsbn(), livros);
        } else {
            this.livros.put(livro.getIsbn(), livro);
        }
        FileManeger.saveLivro(this.livros);
    }



    @Override
    public void delete(Livro livro) throws LivroException {
        try {
            if (this.livros.containsKey(livro.getIsbn())) {
                this.livros.remove(livro.getIsbn());
                FileManeger.saveLivro(this.livros);
            } else if (!MasterDao.getEmprestimoDao().findByLivro(livro.getIsbn()).isEmpty()) {
                throw new LivroException(deleteLivroWithEmprestimo, livros.get(livro.getIsbn()));
            } else {
                throw new LivroException(deleteLivro, null);
            }
        } catch (EmprestimoException ee){
            throw new LivroException(deleteLivro, null);
        }
    }

    @Override
    public void Update(Livro livro, Livro old) throws LivroException{
        if (livros.get(old.getIsbn()) != null && livros.get(old.getIsbn()).getQuantidade() > 0) {
            Livro livros = this.livros.get(old.getIsbn());
            this.livros.remove(old);
            this.livros.put(livro.getIsbn(), livros);
            FileManeger.saveLivro(this.livros);

        } else if (livros.get(old.getIsbn()) == null) {
            throw new LivroException(updateWhenNotHaveObj, null);
        } else {
            throw new LivroException(updateLivro, null);
        }

    }

    @Override
    public List<Livro> findAll() {
        List<Livro> lista = new LinkedList<>();
        for(Livro livro : livros.values()){
            lista.add(livro);
        }
        return  lista;
    }

    @Override
    public void clearAll() throws LivroException {
        if(!this.livros.isEmpty())
            this.livros.clear();
        FileManeger.saveLivro(this.livros);
    }

    @Override
    public List<Livro> findByAutor(String autor) throws LivroException {
        List<Livro> livrosList = new LinkedList<>();

        for (Livro livro:
                livros.values()) {
            if (Objects.equals(livro.getAutor(), autor)) {
                livrosList.add(livro);
            }

        }
        if(livrosList.isEmpty()){
            throw new LivroException(autorNotExist, null);
        }
        else {
            return livrosList;
        }
    }

    @Override
    public List<Livro> findByCategoria(String categoria) throws LivroException{
        List<Livro> livrosList = new LinkedList<>();

        for (Livro livro:
                livros.values()) {
            if (Objects.equals(categoria, livro.getCategoria())) {
                livrosList.add(livro);
            }

        }
        if(livrosList.isEmpty()){
            throw new LivroException(categoriaNotExist, null);
        }
        else {
            return livrosList;
        }
    }

    @Override
    public List<Livro> findByNome(String nome)  throws LivroException {
        List<Livro> livrosList = new LinkedList<>();

        for (Livro livro:
                livros.values()) {
            if (Objects.equals(nome, livro.getNome())) {
                livrosList.add(livro);
            }

        }
        if(livrosList.isEmpty()){
            throw new LivroException(nomeNotExist, null);
        }
        else {
            return livrosList;
        }
    }

    @Override
    public void deleteOnlyOne(Livro obj) throws LivroException{
        if (livros.containsKey(obj.getIsbn())){
            Livro livros = this.livros.get(obj.getIsbn());
            if (livros.getQuantidade() == 0) {
                throw new LivroException(removeLivroWithZero, livros);
            }
            livros.setQuantidade(livros.getQuantidade()-1);
            this.livros.remove(obj.getIsbn());
            this.livros.put(livros.getIsbn(), livros);
            FileManeger.saveLivro(this.livros);
        } else if (livros.isEmpty()) {
            throw new LivroException(deleteWhenNotHaveObj, null);
        } else {
            throw new LivroException(deleteLivro, null);
        }
    }

    @Override
    public List<Livro> findLivrosMaisPesquisados() {
        Livro livroAtual = null;
        Livro[] livrosMaisPesquisados = new Livro[10];
        List<Livro> listaLivros = new LinkedList<>();
        for(int i = 0; i<10;i++) {
            if(livrosMaisPesquisados.length == 0) {
                for (Livro livro :
                        livros.values()) {
                    if (livroAtual == null) {
                        livroAtual = livro;
                    } else {
                        if (livroAtual.getQntDeBuscas() < livro.getQntDeBuscas()) {
                            livroAtual = livro;
                        }
                    }
                }
                livrosMaisPesquisados[i] = livroAtual;
                listaLivros.add(livroAtual);
            }
            else {
                for (Livro livro :
                        livros.values()) {
                    if (livroAtual == null) {
                        livroAtual = livro;
                    } else {
                        if (livroAtual.getQntDeBuscas() < livro.getQntDeBuscas() && livro.getQntDeBuscas() < livrosMaisPesquisados[i-1].getQntDeBuscas()) {
                            livroAtual = livro;
                        }
                    }
                }
                livrosMaisPesquisados[i] = livroAtual;
                listaLivros.add(livroAtual);
            }
        }

        return listaLivros;
    }
}
