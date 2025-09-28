package happyfeet.repository.DAO;

import happyfeet.model.entities.Dueno;
import happyfeet.repository.interfaces.IDuenoDAO;
import happyfeet.util.Conexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DuenoDAO implements IDuenoDAO {

    private final Connection connection;

    public DuenoDAO() throws SQLException {
        this.connection = Conexion.getConexion();
    }

    @Override
    public boolean insertar(Dueno dueno) throws SQLException {
        String sql = "INSERT INTO duenos (nombre_completo, documento_identidad, direccion, telefono, email) VALUES (?,?,?,?,?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, dueno.getNombreDueno());
            stmt.setString(2, dueno.getDoumentoDueno());
            stmt.setString(3, dueno.getDireccionDueno());
            stmt.setString(4, dueno.getTelefonoDueno());
            stmt.setString(5, dueno.getEmailDueno());
            return stmt.executeUpdate() > 0;
        }
    }

    @Override
    public boolean actualizar(Dueno dueno) throws SQLException {
        String sql = "UPDATE duenos SET nombre_completo=?, documento_identidad=?, direccion=?, telefono=?, email=? WHERE id=?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, dueno.getNombreDueno());
            stmt.setString(2, dueno.getDoumentoDueno());
            stmt.setString(3, dueno.getDireccionDueno());
            stmt.setString(4, dueno.getTelefonoDueno());
            stmt.setString(5, dueno.getEmailDueno());
            stmt.setInt(6, dueno.getIdDueno());
            return stmt.executeUpdate() > 0;
        }
    }

    @Override
    public boolean eliminar(int id) throws SQLException {
        String sql = "DELETE FROM duenos WHERE id=?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        }
    }

    @Override
    public Dueno obtenerPorId(int id) throws SQLException {
        String sql = "SELECT * FROM duenos WHERE id=?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapResultSet(rs);
            }
        }
        return null;
    }

    @Override
    public List<Dueno> listarTodos() throws SQLException {
        List<Dueno> duenos = new ArrayList<>();
        String sql = "SELECT * FROM duenos";
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                duenos.add(mapResultSet(rs));
            }
        }
        return duenos;
    }

    @Override
    public Dueno buscarPorDocumento(String documento) throws SQLException {
        String sql = "SELECT * FROM duenos WHERE documento_identidad=?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, documento);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapResultSet(rs);
            }
        }
        return null;
    }

    private Dueno mapResultSet(ResultSet rs) throws SQLException {
        return new Dueno(
                rs.getInt("id"),
                rs.getString("nombre_completo"),
                rs.getString("documento_identidad"),
                rs.getString("direccion"),
                rs.getString("telefono"),
                rs.getString("email")
        );
    }
}