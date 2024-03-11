package prog.pbl.Testes;

import prog.pbl.dao.FileManeger;
import prog.pbl.dao.MasterDao;
import prog.pbl.model.emprestimo.Emprestimo;
import prog.pbl.model.emprestimo.FilaDeReserva;
import prog.pbl.model.estoque.Livro;
import prog.pbl.model.usuarios.Administrador;
import prog.pbl.model.usuarios.Bibliotecario;
import prog.pbl.model.usuarios.Leitor;

public class PreencherDados {

    static public void main(String[] args) {
        try {
            FileManeger.generateCache();
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        try {

            MasterDao.getLeitorDao().clearAll();
            MasterDao.getAdministradorDao().clearAll();
            MasterDao.getBibliotecarioDao().clearAll();
            MasterDao.getLivroDao().clearAll();
            MasterDao.getEmprestimoDao().clearAll();
            MasterDao.getFiladeReservaDao().clearAll();
        } catch (Exception ignored) {

        }
        try {
            //administradores
            Administrador adm1 = new Administrador("111", "Paulo", "Administrador", "88052169500");
            Administrador adm2 = new Administrador("admin01","Pedro","Administrador","59834306024");
            Administrador adm3 = new Administrador("admin02","Ana Paula","Administrador","92734807050");
            Administrador adm4 = new Administrador("adminpass01","Olivia Carneiro","Administrador","28818001060");
            Administrador adm5 = new Administrador("PassWord","Tadeu mesquita","Administrador","04259326058");
            Administrador adm6 = new Administrador("012","Clovis vasconcelos","Administrador","44933238090");
            Administrador adm7 = new Administrador("adminPass","Matheus silva","Administrador","20116248009");
            Administrador adm8 = new Administrador("Admin123","Clara Oliviera","Administrador","04410191020");
            Administrador adm9 = new Administrador("senha123","Jessica Andradre","Administrador","00532428072");
            Administrador adm10 = new Administrador("123","Carlos Silva","Administrador","99996721086");



            //Bibliotecarios
            Bibliotecario lib1 = new Bibliotecario("Paulo", "111", "88052169500", "Bibliotecario");
            Bibliotecario lib2 = new Bibliotecario("Pedro","admin01","59834306024","Bibliotecario");
            Bibliotecario lib3 = new Bibliotecario("Ana Paula","admin02","92734807050","Bibliotecario");
            Bibliotecario lib4 = new Bibliotecario("Olivia Carneiro","adminpass01","28818001060","Bibliotecario");
            Bibliotecario lib5 = new Bibliotecario("Tadeu mesquita","PassWord","04259326058","Bibliotecario");
            Bibliotecario lib6 = new Bibliotecario("Clovis vasconcelos","012","44933238090","Bibliotecario");
            Bibliotecario lib7 = new Bibliotecario("Matheus silva","adminPass","20116248009","Bibliotecario");
            Bibliotecario lib8 = new Bibliotecario("Clara Oliviera","Admin123","04410191020","Bibliotecario");
            Bibliotecario lib9 = new Bibliotecario("Jessica Andradre","senha123","00532428072","Bibliotecario");
            Bibliotecario lib10 = new Bibliotecario("Carlos Silva","123","99996721086","Bibliotecario");


            //Leitores
            Leitor leitor1 = new Leitor("Paulo","111", "88052169500", "Feira de Santana", "(75) 9 9999-9999");
            Leitor leitor2 = new Leitor("Pedro","leitor01","59834306024","Feira de Sananta", "(75) 9 9878-9898");
            Leitor leitor3 = new Leitor("Ana Paula","leitor02","92734807050","Feira de Sananta", "(75) 9 9896-9698");
            Leitor leitor4 = new Leitor("Olivia Carneiro","leitorpass01","28818001060","Uefs", "(75) 9 9265-9898");
            Leitor leitor5 = new Leitor("Tadeu mesquita","PassWord","04259326058","Serrinha", "(75) 9 9654-7856");
            Leitor leitor6 = new Leitor("Clovis vasconcelos","012","44933238090","Conceição do Coite", "(75) 9 4002-8922");
            Leitor leitor7 = new Leitor("Matheus silva","leitorpass","20116248009","Salvador", "(75) 9 9999-9991");
            Leitor leitor8 = new Leitor("Clara Oliviera","Leitor123","04410191020","Rua O, 77", "(75) 9 6569-9845");
            Leitor leitor9 = new Leitor("Jessica Andradre","senha123","00532428072","Rua A, 25", "(11) 9 6578-9865");
            Leitor leitor10 = new Leitor("Carlos Silva","123","99996721086","Argentina", "+1 98569713");



            Leitor leitor = new Leitor("Maike", "111", "88052169500", "uefs", "(75) 9 92515720");
            Livro livro = new Livro("978-3-16-148410-0","João Silva","programação","Uefs","fonte seca", 2020, "Aprendendo Java",5);


            FilaDeReserva filaDeReserva = new FilaDeReserva(livro.getIsbn());
            filaDeReserva.addOnReservas(leitor);
            MasterDao.getAdministradorDao().save(new Administrador("1", "Maike de Oliveira Nascimento", "Administrador", "10058279580"));
            MasterDao.getLeitorDao().save(leitor);
            MasterDao.getBibliotecarioDao().save(new Bibliotecario("Maike", "111", "88052169500", "Bibliotecario"));
            MasterDao.getLivroDao().save(livro);
            MasterDao.getEmprestimoDao().save(new Emprestimo(leitor, livro));
            MasterDao.getFiladeReservaDao().save(filaDeReserva);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
