package happyfeet.repository.DAO;

import happyfeet.model.entities.Dueno;
import happyfeet.model.entities.Mascota;
import happyfeet.model.entities.Raza;
import happyfeet.model.enums.sexoMascota;
import happyfeet.repository.interfaces.IMascotaDAO;
import happyfeet.util.Conexion;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MascotaDAO implements IMascotaDAO {
    private final Connection connection;

    // CONSTRUCTOR 1: Para uso directo (obtiene su propia conexión)
    public MascotaDAO() throws SQLException {
        this.connection = Conexion.getConexion();
    }

    // CONSTRUCTOR 2 (¡NECESARIO!): Para ser usado por otros DAOs (HistorialMedicoDAO)
    public MascotaDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean insertar(Mascota mascota) throws SQLException {
        String sql = "INSERT INTO mascotas (nombre, raza_id, fecha_nacimiento, sexo, url_foto, dueno_id) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, mascota.getNombreMascota());
            ps.setInt(2, mascota.getRaza().getIdRaza());
            ps.setDate(3, mascota.getFechaNacimiento() != null ? Date.valueOf(mascota.getFechaNacimiento()) : null);
            ps.setString(4, mascota.getSexo().name());
            ps.setString(5, mascota.getUrlFoto());
            ps.setInt(6, mascota.getIdDueno().getIdDueno());

            int rows = ps.executeUpdate();
            if (rows > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        mascota.setIdMascota(rs.getInt(1));
                    }
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public Mascota obtenerPorId(int id) throws SQLException {
        // Asegúrate que tu columna se llama 'id' o 'id_mascota'
        String sql = "SELECT * FROM mascotas WHERE id = ?";
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
    public List<Mascota> listarTodos() throws SQLException {
        List<Mascota> lista = new ArrayList<>();
        String sql = """
            SELECT 
                m.id,
                m.nombre,
                m.fecha_nacimiento,
                m.sexo,
                m.url_foto,

                r. id,
                r.nombre,

                e.id,
                e.nombre,

                d.id,
                d.nombre_completo,
                d.documento_identidad,
                d.telefono,
                d.email

            FROM mascotas m
            JOIN razas r    ON m.raza_id = r.id
            JOIN especies e ON r.especie_id = e.id
            JOIN duenos d   ON m.dueno_id= d.id
            ORDER BY m.id;
        """;
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(mapResultSet(rs));
            }
        }
        return lista;
    }

    @Override
    public boolean actualizar(Mascota mascota) throws SQLException {
        String sql = "UPDATE mascotas SET nombre=?, raza_id=?, fecha_nacimiento=?, sexo=?, url_foto=?, dueno_id=? WHERE id=?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, mascota.getNombreMascota());
            ps.setInt(2, mascota.getRaza().getIdRaza());
            ps.setDate(3, mascota.getFechaNacimiento() != null ? Date.valueOf(mascota.getFechaNacimiento()) : null);
            ps.setString(4, mascota.getSexo().name());
            ps.setString(5, mascota.getUrlFoto());
            ps.setInt(6, mascota.getIdDueno().getIdDueno());
            ps.setInt(7, mascota.getIdMascota());

            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public boolean eliminar(int id) throws SQLException {
        String sql = "DELETE FROM mascotas WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public List<Mascota> buscarPorNombre(String nombre) throws SQLException {
        List<Mascota> lista = new ArrayList<>();
        String sql = "SELECT * FROM mascotas WHERE nombre LIKE ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, "%" + nombre + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapResultSet(rs));
                }
            }
        }
        return lista;
    }

    @Override
    public List<Mascota> listarPorDueno(int idDueno) throws SQLException {
        List<Mascota> lista = new ArrayList<>();
        String sql = "SELECT * FROM mascotas WHERE dueno_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, idDueno);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapResultSet(rs));
                }
            }
        }
        return lista;
    }

    // =================== MÉTODO AUXILIAR (Simplificado) ===================
    private Mascota mapResultSet(ResultSet rs) throws SQLException {
        // SOLUCIÓN TEMPORAL: Crear objetos Raza y Dueno solo con el ID,
        // asumiendo que tus clases Raza y Dueno tienen el constructor 'new Raza(int id)'.
        Raza raza = new Raza(rs.getInt("id"));
        Dueno dueno = new Dueno(rs.getInt("id"));

        LocalDate fecha = rs.getDate("fecha_nacimiento") != null ? rs.getDate("fecha_nacimiento").toLocalDate() : null;

        return new Mascota(
                rs.getInt("id"),
                rs.getString("nombre"),
                raza,
                fecha,
                sexoMascota.valueOf(rs.getString("sexo")),
                rs.getString("url_foto"),
                dueno
        );
    }
}
