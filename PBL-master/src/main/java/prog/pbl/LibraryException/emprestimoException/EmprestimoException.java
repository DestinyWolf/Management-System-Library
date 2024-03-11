package prog.pbl.LibraryException.emprestimoException;

import prog.pbl.model.emprestimo.Emprestimo;

public class EmprestimoException extends Exception{

    private Emprestimo emprestimo;

    /**
     * construtor das exceções de emprestimo
     * @param excessao
     * @param emprestimo
     */
    public EmprestimoException(String excessao, Emprestimo emprestimo) {
        super(excessao);
        this.emprestimo = emprestimo;
    }
}
