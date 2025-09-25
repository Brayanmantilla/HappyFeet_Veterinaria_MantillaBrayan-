package happyfeet.repository.interfaces;

import happyfeet.model.entities.Compra;

import java.time.LocalDate;
import java.util.List;

public interface ICompraDAO {

    boolean insertarCompra(Compra compra);
    Compra obtenerPorId(int id);
    List<Compra> listarPorPeriodo(LocalDate fechaInicio, LocalDate fechaFin);
}
