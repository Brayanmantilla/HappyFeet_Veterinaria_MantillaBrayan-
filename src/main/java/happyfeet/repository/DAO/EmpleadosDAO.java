package happyfeet.repository.DAO;

import happyfeet.model.entities.Empleado;
import happyfeet.repository.interfaces.IEmpleadosDAO;
import happyfeet.util.Conexion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmpleadosDAO implements IEmpleadosDAO {
    @Override
    public boolean insertar(Empleado empleado) {
        String sql = "INSERT INTO empleados (nombre_completo, documento_identidad, telefono, email, cargo, salario) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, empleado.getNombreEmpleado());
            ps.setString(2, empleado.getDocumentoEmpleado());
            ps.setString(3, empleado.getTelefonoEmpleado());
            ps.setString(4, empleado.getEmailEmpleado());
            ps.setString(5, empleado.getCargo());
            ps.setDouble(6, empleado.getSalario());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al insertar empleado: " + e.getMessage());
        }
        return false;
    }

    @Override
    public Empleado obtenerPorId(int id) {
        String sql = "SELECT * FROM empleados WHERE id = ?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Empleado(
                            rs.getInt("id"),
                            rs.getString("nombre_completo"),
                            rs.getString("documento_identidad"),
                            rs.getString("telefono"),
                            rs.getString("email"),
                            rs.getString("cargo"),
                            rs.getDouble("salario")
                    );
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener empleado por ID: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Empleado> listarTodos() {
        List<Empleado> empleados = new ArrayList<>();
        String sql = "SELECT * FROM empleados";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                empleados.add(new Empleado(
                        rs.getInt("id"),
                        rs.getString("nombre_completo"),
                        rs.getString("documento_identidad"),
                        rs.getString("telefono"),
                        rs.getString("email"),
                        rs.getString("cargo"),
                        rs.getDouble("salario")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Error al listar empleados: " + e.getMessage());
        }
        return empleados;
    }

    @Override
    public boolean actualizar(Empleado empleado) {
        String sql = "UPDATE empleados SET nombre_completo = ?, documento_identidad = ?, telefono = ?, email = ?, cargo = ?, salario = ? " +
                "WHERE id = ?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, empleado.getNombreEmpleado());
            ps.setString(2, empleado.getDocumentoEmpleado());
            ps.setString(3, empleado.getTelefonoEmpleado());
            ps.setString(4, empleado.getEmailEmpleado());
            ps.setString(5, empleado.getCargo());
            ps.setDouble(6, empleado.getSalario());
            ps.setInt(7, empleado.getIdEmpleado());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al actualizar empleado: " + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean eliminar(int id) {
        String sql = "DELETE FROM empleados WHERE id = ?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al eliminar empleado: " + e.getMessage());
        }
        return false;
    }
}
