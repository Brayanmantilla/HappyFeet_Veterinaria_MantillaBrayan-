package happyfeet.repository.DAO;

import happyfeet.model.entities.Servicio;
import happyfeet.repository.interfaces.IServiciosDAO;
import happyfeet.util.Conexion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ServiciosDAO implements IServiciosDAO {
    public ServiciosDAO(Connection connection) {
    }

    @Override
    public boolean insertar(Servicio servicio) {
        String sql = "INSERT INTO servicios (nombre, descripcion, precio_base) VALUES (?, ?, ?)";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, servicio.getNombreServicio());
            ps.setString(2, servicio.getDescripcionServicio());
            ps.setDouble(3, servicio.getPrecio());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al insertar servicio: " + e.getMessage());
        }
        return false;
    }

    @Override
    public Servicio obtenerPorId(int id) {
        String sql = "SELECT * FROM servicios WHERE id = ?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Servicio(
                            rs.getInt("id"),
                            rs.getString("nombre"),
                            rs.getString("descripcion"),
                            rs.getDouble("precio_base")
                    );
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener servicio por ID: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Servicio> listarTodas() {
        List<Servicio> lista = new ArrayList<>();
        String sql = "SELECT * FROM servicios";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(new Servicio(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("descripcion"),
                        rs.getDouble("precio_base")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Error al listar servicios: " + e.getMessage());
        }
        return lista;
    }

    @Override
    public boolean eliminar(int id) {
        String sql = "DELETE FROM servicios WHERE id = ?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al eliminar servicio: " + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean actualizar(Servicio servicio) {
        String sql = "UPDATE servicios SET nombre = ?, descripcion = ?, precio_base = ? WHERE id = ?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, servicio.getNombreServicio());
            ps.setString(2, servicio.getDescripcionServicio());
            ps.setDouble(3, servicio.getPrecio());
            ps.setInt(4, servicio.getIdServicio());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al actualizar servicio: " + e.getMessage());
        }
        return false;
    }
}