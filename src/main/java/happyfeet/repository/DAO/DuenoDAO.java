package happyfeet.repository.DAO;

import happyfeet.model.entities.Dueno;
import happyfeet.repository.interfaces.IDuenoDAO;
import happyfeet.util.Conexion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DuenoDAO implements IDuenoDAO {

    private final Connection connection;

    // Inicializa la conexión automáticamente
    public DuenoDAO() throws SQLException {
        this.connection = Conexion.getConexion();
    }

    @Override
    public boolean insertar(Dueno dueno) throws Exception {
        String sql = "INSERT INTO duenos " +
                "(nombre_completo, documento_identidad, direccion, telefono, email) " +
                "VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, dueno.getNombreDueno());
            ps.setString(2, dueno.getDoumentoDueno());
            ps.setString(3, dueno.getDireccionDueno());
            ps.setString(4, dueno.getTelefonoDueno());
            ps.setString(5, dueno.getEmailDueno());
            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public Dueno obtenerPorId(int id) throws Exception {
        String sql = "SELECT * FROM duenos WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSet(rs);
                }
            }
        }
        return null;
    }

    @Override
    public List<Dueno> listarTodos() throws Exception {
        List<Dueno> duenos = new ArrayList<>();
        String sql = "SELECT * FROM duenos";
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                duenos.add(mapResultSet(rs));
            }
        }
        return duenos;
    }

    @Override
    public boolean actualizar(Dueno dueno) throws Exception {
        String sql = "UPDATE duenos SET nombre_completo=?, documento_identidad=?, " +
                "direccion=?, telefono=?, email=? WHERE id=?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, dueno.getNombreDueno());
            ps.setString(2, dueno.getDoumentoDueno());
            ps.setString(3, dueno.getDireccionDueno());
            ps.setString(4, dueno.getTelefonoDueno());
            ps.setString(5, dueno.getEmailDueno());
            ps.setInt(6, dueno.getIdDueno());
            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public boolean eliminar(int id) throws Exception {
        String sql = "DELETE FROM duenos WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public Dueno buscarPorDocumento(String documento) throws Exception {
        String sql = "SELECT * FROM duenos WHERE documento_identidad = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, documento);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSet(rs);
                }
            }
        }
        return null;
    }

    private Dueno mapResultSet(ResultSet rs) throws SQLException {
        return new Dueno(
                rs.getInt("id"),
                rs.getString("telefono"),
                rs.getString("email"),
                rs.getString("documento_identidad"),
                rs.getString("nombre_completo"),
                rs.getString("direccion")
        );
    }
}