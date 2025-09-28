package happyfeet.repository.interfaces;

import happyfeet.model.entities.ClubMascota;
import java.sql.SQLException;
import java.util.List;

public interface IClubMascotaDAO {
    boolean insertar(ClubMascota club) throws SQLException;
    ClubMascota obtenerPorDueno(int duenoId) throws SQLException;
    boolean actualizar(ClubMascota club) throws SQLException;
    List<ClubMascota> listarTodos() throws SQLException;
}