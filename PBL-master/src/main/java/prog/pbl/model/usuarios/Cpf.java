package prog.pbl.model.usuarios;
import java.io.Serializable;

import static prog.pbl.util.Constantes.cpfInvalido;

public class Cpf implements Serializable{
    private String cpf;

    /**
     * <p>construtor da classe <b>cpf</b></p>
     */
    public Cpf(){
        this.cpf = "";
    }

    /**
     *<p>Metodo responsavel por retornar o cpf</p>
     * @return <b>String</b> - <i>cpf</i>
     */
    public String getCpf() {
        return cpf;
    }

    /**
     * <p>Metodo responsavel por settar o <b>cpf</b> e realizar a <b>validação</b></p>
     * @param cpf <b>String</b>
     * @throws Exception <i>Caso o cpf seja invalido, retorna uma exceção de cpf invalido</i>
     */
    public void setCpf(String cpf) throws Exception{
        int digt1 = 0;
        int digt2 = 0;
        String[] vetor = cpf.split("");
        for(int i = 0; i < vetor.length-2; i++) {
            digt1 += (Integer.parseInt(vetor[i]) * (vetor.length -1 - i));
        }
        if (digt1%11 <= 1){
            digt1 = 0;
        } else{
            digt1 = 11 - digt1%11;
        }


        for(int i = 0; i < vetor.length-1; i++) {
            digt2 += (Integer.parseInt(vetor[i]) * (vetor.length - i));

        }

        if (digt2%11 <= 1){
            digt2 = 0;
        } else {
            digt2 = 11 - digt2%11;
        }

        if(digt1 == Integer.parseInt(vetor[9]) && digt2 == Integer.parseInt(vetor[10]))
            this.cpf = cpf;
        else {
            throw new Exception(cpfInvalido);
        }
    }

    @Override
    public String toString() {
        return "Cpf{" +
                "cpf='" + cpf + '\'' +
                '}';
    }

}
