package happyfeet.service;

import happyfeet.model.entities.Empleado;
import happyfeet.repository.DAO.EmpleadosDAO;
import happyfeet.repository.interfaces.IEmpleadosDAO;

import java.util.List;

public class EmpleadoService {
    private final IEmpleadosDAO empleadosDAO;

    // Puedes inyectar la implementación o instanciarla directamente
    public EmpleadoService() {
        this.empleadosDAO = new EmpleadosDAO();
    }

    // Constructor alternativo para inyección manual si se necesita
    public EmpleadoService(IEmpleadosDAO empleadosDAO) {
        this.empleadosDAO = empleadosDAO;
    }

    /** Crear nuevo empleado */
    public boolean crearEmpleado(Empleado empleado) {
        return empleadosDAO.insertar(empleado);
    }

    /** Buscar empleado por ID */
    public Empleado obtenerEmpleadoPorId(int id) {
        return empleadosDAO.obtenerPorId(id);
    }

    /** Listar todos los empleados */
    public List<Empleado> listarEmpleados() {
        return empleadosDAO.listarTodos();
    }

    /** Actualizar datos de un empleado */
    public boolean actualizarEmpleado(Empleado empleado) {
        return empleadosDAO.actualizar(empleado);
    }

    /** Eliminar empleado por ID */
    public boolean eliminarEmpleado(int id) {
        return empleadosDAO.eliminar(id);
    }
}
