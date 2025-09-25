package happyfeet.repository.interfaces;

import happyfeet.model.entities.Factura;

import java.time.LocalDate;
import java.util.List;

public interface IFacturaDAO {
    boolean insertarFactura(Factura factura);
    Factura obtenerPorId(int id);
    List<Factura> listarPorPeriodo(LocalDate fechaInicio, LocalDate fechaFin);
    List<Factura> listarPorDueno(int duenoId);
}
