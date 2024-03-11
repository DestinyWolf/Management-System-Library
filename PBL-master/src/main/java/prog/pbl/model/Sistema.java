package prog.pbl.model;

import prog.pbl.LibraryException.emprestimoException.EmprestimoException;
import prog.pbl.dao.MasterDao;
import prog.pbl.model.emprestimo.Emprestimo;
import prog.pbl.model.usuarios.Administrador;
import prog.pbl.model.usuarios.Bibliotecario;
import prog.pbl.model.usuarios.Leitor;
import prog.pbl.util.Data;

public class Sistema {

    private static Leitor sessaoAtualLeitor = null;
    private static Bibliotecario sessaoAtualBibliotecario = null;
    private static Administrador sessaoAtualAdministrador = null;


    /**
     * <p>Metodo responsavel por, toda vez que o sistema iniciar, calcular as multas</p>
     * @return <b>boolean</b> - <i><b>true</b>, se tudo ocoreu certo, <b>false</b>, caso ocorra algum erro</i>*/
    public static boolean atualizaMultas() {
        Data dataAtual = new Data();
        try {
            for (Emprestimo emprestimo : MasterDao.getEmprestimoDao().findEmprestimosAtivos()
            ){

                if (emprestimo.getPrazoFinal().compareData(dataAtual) == 2) {
                    emprestimo.getLeitor().setDiasRestantesMulta(emprestimo.getLeitor().getDiasRestantesMulta()+ 2);
                }
            }
            for (Leitor leitor: MasterDao.getLeitorDao().findAll()
            ) {
                if (MasterDao.getEmprestimoDao().findEmprestimosAtivosPorUsuario(leitor.getId()) == null) {
                    if (leitor.getDiasRestantesMulta() >= 1) {
                        Emprestimo ultimoEmprestimo = null;
                        for (Emprestimo emprestimo: MasterDao.getEmprestimoDao().findByUser(leitor.getId())
                        ) {

                            if (ultimoEmprestimo == null) {
                                ultimoEmprestimo = emprestimo;
                            }
                            else if (emprestimo.getDataDevolucao().compareData(ultimoEmprestimo.getDataDevolucao()) == 1) {
                                ultimoEmprestimo = emprestimo;
                            }
                        }
                        Integer qntDiasQuePassaram = 30*(ultimoEmprestimo.getDataDevolucao().getMes() - dataAtual.getMes());
                        qntDiasQuePassaram += (ultimoEmprestimo.getDataDevolucao().getDia() - dataAtual.getDia());
                        qntDiasQuePassaram += 365*(ultimoEmprestimo.getDataDevolucao().getAno() - dataAtual.getAno());


                        leitor.setDiasRestantesMulta(leitor.getDiasRestantesMulta() - qntDiasQuePassaram);
                    }
                }
            }
            return true;

        } catch (EmprestimoException e) {
            return false;
        }

    }

    /**
     * <p>metodo responsavel por retornar o leitor que esta logado no atual momento</p>
     * @return <b>Leitor</b> - <i>o leitor atual logado</i>
     */
    public static Leitor getSessaoAtualLeitor() {
        return sessaoAtualLeitor;
    }

    /**
     * <p>metodo responsavel por retornar o administrador que esta logado no atual momento</p>
     * @return <b>Administrador</b> - <i>o administrador atual logado</i>
     */
    public static Administrador getSessaoAtualAdministrador() {
        return sessaoAtualAdministrador;
    }

    /**
     * <p>metodo responsavel por retornar o bibliotecario que esta logado no atual momento</p>
     * @return <b>Bibliotecario</b> - <i>o bibliotecario atual logado</i>
     */
    public static Bibliotecario getSessaoAtualBibliotecario() {
        return sessaoAtualBibliotecario;
    }

    /**
     * <p>Metodo responsavel por settar o administrador passado por parametro como administrador atual logado</p>
     * @param sessaoAtualAdministrador <b>Administrador</b>
     */
    public static void setSessaoAtualAdministrador(Administrador sessaoAtualAdministrador) {
        Sistema.sessaoAtualAdministrador = sessaoAtualAdministrador;
    }

    /**
     * <p>Metodo responsavel por settar o bibliotecario passado por parametro como bibliotecario atual logado</p>
     * @param sessaoAtualBibliotecario <b>Bibliotecario</b>
     */
    public static void setSessaoAtualBibliotecario(Bibliotecario sessaoAtualBibliotecario) {
        Sistema.sessaoAtualBibliotecario = sessaoAtualBibliotecario;
    }

    /**
     * <p>Metodo responsavel por settar o leitor passado por parametro como leitor atual logado</p>
     * @param sessaoAtualLeitor
     */
    public static void setSessaoAtualLeitor(Leitor sessaoAtualLeitor) {
        Sistema.sessaoAtualLeitor = sessaoAtualLeitor;
    }

    public static Integer currentLogMember() {
        if (sessaoAtualAdministrador != null) {
            return 0;
        } else if (sessaoAtualBibliotecario != null){
            return 1;
        } else if (sessaoAtualLeitor != null) {
            return 2;
        }
        return 3;
    }
}
