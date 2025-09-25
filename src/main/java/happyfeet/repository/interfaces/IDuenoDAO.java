package happyfeet.repository.interfaces;

import happyfeet.model.entities.Dueno;

import java.util.List;

public interface IDuenoDAO {
    public boolean insertar(Dueno dueno) throws Exception;
    Dueno obtenerPorId(int idDueno) throws Exception;
    List<Dueno> listarTodos() throws Exception;
    public boolean actualizar(Dueno dueno) throws Exception;
    public boolean eliminar(int id) throws Exception;
    Dueno buscarPorDocumento(String documento) throws Exception;
}
