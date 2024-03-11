package prog.pbl.dao.emprestimo;

import prog.pbl.LibraryException.emprestimoException.ReservarException;
import prog.pbl.model.emprestimo.FilaDeReserva;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import static prog.pbl.util.Constantes.*;

public class ImMemoryFilaDeReservaDao implements FilaDeReservaDao{

    private HashMap<String, FilaDeReserva> filasDeReserva;

    public ImMemoryFilaDeReservaDao() {
        this.filasDeReserva = new HashMap<>();
    }


    @Override
    public FilaDeReserva findById(String id) throws ReservarException {
        if(filasDeReserva.containsKey(id))
            return filasDeReserva.get(id);
        else {
            throw new ReservarException(findReserva, null);
        }
    }

    @Override
    public void save(FilaDeReserva obj) throws ReservarException{
        if(this.filasDeReserva.containsKey(obj.getIsbn())) {
            throw new ReservarException(createReserva, null);
        }
        else {
            this.filasDeReserva.put(obj.getIsbn(), obj);
        }
    }

    @Override
    public void delete(FilaDeReserva filaDeReserva) throws ReservarException{
        if(this.filasDeReserva.containsKey(filaDeReserva.getIsbn())) {
            this.filasDeReserva.remove(filaDeReserva.getIsbn());
        } else {
            throw new ReservarException(deleteReserva, null);
        }

    }

    @Override
    public void Update(FilaDeReserva filaDeReserva, FilaDeReserva old) throws ReservarException{
        if (this.filasDeReserva.containsKey(old.getIsbn())) {
            this.filasDeReserva.remove(old.getIsbn());
            this.filasDeReserva.put(filaDeReserva.getIsbn(), filaDeReserva);
        } else {
            throw new ReservarException(updateReserva, old);
        }
    }

    @Override
    public List<FilaDeReserva> findAll() {
        List<FilaDeReserva> lista = new LinkedList<>();
        for(FilaDeReserva filaDeReserva : filasDeReserva.values()){
            lista.add(filaDeReserva);
        }
        return  lista;
    }

    @Override
    public void clearAll() throws ReservarException {
        if(!this.filasDeReserva.isEmpty())
            this.filasDeReserva.clear();
    }
}
