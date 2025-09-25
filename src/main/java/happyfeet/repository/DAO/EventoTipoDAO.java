package happyfeet.repository.DAO;

import happyfeet.model.entities.EventoTipo;
import happyfeet.repository.interfaces.IEventoTipoDAO;
import happyfeet.util.Conexion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EventoTipoDAO implements IEventoTipoDAO {
    private final Connection connection;

    // CONSTRUCTOR 1 (¡NECESARIO!): Para ser usado por otros DAOs (como HistorialMedicoDAO)
    public EventoTipoDAO(Connection connection) {
        this.connection = connection;
    }

    // CONSTRUCTOR 2: Para uso directo, obtiene su propia conexión
    public EventoTipoDAO() throws SQLException {
        this.connection = Conexion.getConexion();
    }

    // Listar todos los tipos de evento
    @Override
    public List<EventoTipo> listarTodos() throws SQLException { // AÑADIR throws SQLException
        List<EventoTipo> tipos = new ArrayList<>();
        String sql = "SELECT * FROM evento_tipos ORDER BY id";

        // Usamos la conexión del constructor, no abrimos una nueva
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                tipos.add(new EventoTipo(
                        rs.getInt("id"),
                        rs.getString("nombre")
                ));
            }
        }
        return tipos;
    }

    // Obtener un tipo de evento por ID
    @Override
    public EventoTipo obtenerPorId(int id) throws SQLException { // AÑADIR throws SQLException
        String sql = "SELECT * FROM evento_tipos WHERE id = ?";

        // Usamos la conexión del constructor, no abrimos una nueva
        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new EventoTipo(
                            rs.getInt("id"),
                            rs.getString("nombre")
                    );
                }
            }
        }
        return null;
    }
}
