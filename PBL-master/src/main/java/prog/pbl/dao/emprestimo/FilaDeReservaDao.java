package prog.pbl.dao.emprestimo;

import prog.pbl.LibraryException.emprestimoException.ReservarException;
import prog.pbl.dao.Dao;
import prog.pbl.model.emprestimo.FilaDeReserva;

/**Interface CRUD da fila de reserva*/
public interface FilaDeReservaDao extends Dao<FilaDeReserva, ReservarException> {
}
