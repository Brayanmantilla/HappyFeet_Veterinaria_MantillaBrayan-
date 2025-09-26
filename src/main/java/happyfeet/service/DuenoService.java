package happyfeet.service;

import happyfeet.model.entities.Dueno;
import happyfeet.repository.DAO.DuenoDAO;

import java.sql.SQLException;
import java.util.List;

public class DuenoService {
    private final DuenoDAO duenoDAO;

    public DuenoService() throws SQLException {
        this.duenoDAO = new DuenoDAO();
    }

    // Registrar un nuevo dueño
    public boolean registrarDueno(Dueno dueno) throws Exception {
        // Aquí podrías validar que el email no esté vacío, etc.
        return duenoDAO.insertar(dueno);
    }

    // Listar todos los dueños
    public List<Dueno> listarDuenos() throws Exception {
        return duenoDAO.listarTodos();
    }

    // Buscar dueño por ID
    public Dueno buscarPorId(int id) throws Exception {
        return duenoDAO.obtenerPorId(id);
    }

    // Actualizar datos del dueño
    public boolean actualizarDueno(Dueno dueno) throws Exception {
        return duenoDAO.actualizar(dueno);
    }

    // Eliminar dueño
    public boolean eliminarDueno(int id) throws Exception {
        // ⚠️ Aquí podrías validar que no tenga mascotas registradas antes de eliminar
        return duenoDAO.eliminar(id);
    }
}
