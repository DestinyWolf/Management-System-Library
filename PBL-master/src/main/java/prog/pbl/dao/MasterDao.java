package prog.pbl.dao;

import prog.pbl.dao.emprestimo.EmprestimoDao;
import prog.pbl.dao.emprestimo.FilaDeReservaDao;
import prog.pbl.dao.emprestimo.ImDiskEmprestimoDao;
import prog.pbl.dao.emprestimo.ImDiskFilaDeReservaDao;
import prog.pbl.dao.estoque.ImDiskLivroDao;
import prog.pbl.dao.estoque.LivroDao;
import prog.pbl.dao.usuarios.*;


public class MasterDao{

    private static LeitorDao leitorDao;
    private static LivroDao livroDao;
    private static BibliotecarioDao bibliotecarioDao;
    private static FilaDeReservaDao filadeReservaDao;
    private static EmprestimoDao emprestimoDao;
    private static AdministradorDao administradorDao;

    public static LeitorDao getLeitorDao(){
        if(leitorDao == null){
            leitorDao = new ImDiskLeitorDao();
        }
        return leitorDao;
    }

    public static LivroDao getLivroDao(){
        if(livroDao == null) {
            livroDao = new ImDiskLivroDao();
        }
        return livroDao;
    }

    public static BibliotecarioDao getBibliotecarioDao() {
        if(bibliotecarioDao == null) {
            bibliotecarioDao = new ImDiskBibliotecarioDao();
        }
        return bibliotecarioDao;
    }

    public static FilaDeReservaDao getFiladeReservaDao() {
        if (filadeReservaDao == null) {
            filadeReservaDao = new ImDiskFilaDeReservaDao();
        }
        return filadeReservaDao;
    }

    public static EmprestimoDao getEmprestimoDao() {
        if (emprestimoDao == null) {
            emprestimoDao = new ImDiskEmprestimoDao();
        }
        return emprestimoDao;
    }

    public static AdministradorDao getAdministradorDao() {
        if (administradorDao == null) {
            administradorDao = new ImDiskAdministradorDao();
        }

        return administradorDao;
    }

}

