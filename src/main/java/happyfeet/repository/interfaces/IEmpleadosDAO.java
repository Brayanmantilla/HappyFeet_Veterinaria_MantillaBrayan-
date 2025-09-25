package happyfeet.repository.interfaces;

import happyfeet.model.entities.Empleado;

import java.util.List;

public interface IEmpleadosDAO {
    boolean insertar(Empleado empleado);
    Empleado obtenerPorId(int id);
    List<Empleado> listarTodos();
    boolean actualizar(Empleado empleado);
    boolean eliminar(int id);
}
