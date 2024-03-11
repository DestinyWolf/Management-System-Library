package prog.pbl.dao.usuarios;

import prog.pbl.LibraryException.usersexcepitions.AdministradorException;
import prog.pbl.dao.Dao;
import prog.pbl.model.usuarios.Administrador;

public interface AdministradorDao extends Dao<Administrador, AdministradorException> {

    /**
     * <p>Metodo responsavel por <b>retornar um usuario pelo login</b></p>
     * @param id <b>String</b>
     * @param senha <b>String</b>
     * @return <b>Administrador</b> - <i>O administrador a qual aquela senha e aquele id pertencem</i>>
     * @throws AdministradorException <i>caso não seja encontrado nenhum administrador</i>
     */
    public Administrador findLogin(String id, String senha) throws AdministradorException;

}
