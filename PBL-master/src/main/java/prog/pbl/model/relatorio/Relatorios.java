package prog.pbl.model.relatorio;

import prog.pbl.dao.MasterDao;
import prog.pbl.model.emprestimo.Emprestimo;
import prog.pbl.model.estoque.Livro;
import prog.pbl.util.Data;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class Relatorios implements Serializable {
    private Integer qntLivrosEmprestados;
    private Integer qntLivrosAtrasados;
    private Integer qntLivros;
    private List<Livro> isbnLivrosMaisBuscados;

    /**
     * construtor da classe relatorio
     */
    public Relatorios() {
        try {
            this.qntLivros = MasterDao.getLivroDao().findAll().size();
            this.qntLivrosAtrasados = 0;
            this.qntLivrosEmprestados = MasterDao.getEmprestimoDao().findEmprestimosAtivos().size();
            this.isbnLivrosMaisBuscados = new LinkedList<Livro>();

        } catch (Exception ignored) {

        }
    }

    /**
     * metodo que retonrar a quantidade de livros
     * @return a quantidade de livros
     */
    public String getQntLivros(){
        if (qntLivros != null) {
            return this.qntLivros.toString();
        }
        else {
            return "0";
        }
    }

    /**
     * Metodo responsavel por retornar a quantidade total de atrasos
     * @return a quantidade de livros atrasados
     */
    public String getQntLivrosAtrasados(){
        try {
            List<Emprestimo> emprestimoList = new LinkedList<>();
            Data data = new Data();
            for (Emprestimo emprestimo : MasterDao.getEmprestimoDao().findEmprestimosAtivos()) {
                if(emprestimo.getPrazoFinal().getDia() < data.getDia()){
                    this.qntLivrosAtrasados ++;
                }
                else if(emprestimo.getPrazoFinal().getDia() > data.getDia() && emprestimo.getPrazoFinal().getMes() < data.getMes());
            }
            return this.qntLivrosAtrasados.toString();
        } catch (Exception e) {
            return ("0");
        }
    }

    /**
     * metodo responsavel por retonar a quantidade de livros que foram emprestados
     * @return a quantidade de livros emprestados
     */
    public String getQntLivroEmprestados(){
        return qntLivrosEmprestados.toString();
    }

    /**
     * a lista com os livros mais buscados
     * @return List de livro, com os livros mais buscados
     */
    public List<Livro> getIsbnLivrosMaisBuscados() {

        return isbnLivrosMaisBuscados = MasterDao.getLivroDao().findLivrosMaisPesquisados();
    }
}
