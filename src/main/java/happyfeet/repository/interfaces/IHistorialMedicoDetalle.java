package happyfeet.repository.interfaces;

import happyfeet.model.entities.HistorialMedicoDetalle;

import java.sql.SQLException;
import java.util.List;

public interface IHistorialMedicoDetalle {
    boolean insertarDetalle(HistorialMedicoDetalle detalle) throws Exception;
    List<HistorialMedicoDetalle> listarPorHistorial(int idHistorial) throws Exception;
}
