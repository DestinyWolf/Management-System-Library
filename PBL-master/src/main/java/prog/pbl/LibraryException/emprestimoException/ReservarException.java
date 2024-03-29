package prog.pbl.LibraryException.emprestimoException;

import prog.pbl.model.emprestimo.FilaDeReserva;

public class ReservarException extends Exception{

    private FilaDeReserva filaDeReserva;

    /**
     * contrutor das exceções para a reserva
     * @param excessao
     * @param filaDeReserva
     */
    public ReservarException(String excessao, FilaDeReserva filaDeReserva) {
        super(excessao);
        this.filaDeReserva = filaDeReserva;
    }
}