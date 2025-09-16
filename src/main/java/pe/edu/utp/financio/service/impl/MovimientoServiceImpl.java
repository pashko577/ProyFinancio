/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.utp.financio.service.impl;



import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import pe.edu.utp.financio.dao1.AporteDAO;
import pe.edu.utp.financio.dao1.MovimientoDAO;
import pe.edu.utp.financio.service.MovimientoService;
import pe.edu.utp.financio.dao1.MetaDAO;
import pe.edu.utp.financio.modelo.Meta;
import pe.edu.utp.financio.modelo.Movimiento;

public class MovimientoServiceImpl implements MovimientoService {
    private final MovimientoDAO movDAO;
    private final MetaDAO metaDAO;
    private final AporteDAO aporteDAO;

    public MovimientoServiceImpl(MovimientoDAO movDAO, MetaDAO metaDAO, AporteDAO aporteDAO) {
        this.movDAO = movDAO;
        this.metaDAO = metaDAO;
        this.aporteDAO = aporteDAO;
    }

    @Override
    public int registrarIngresoYRepartir(int idUsuario, int idCategoria, BigDecimal monto,String categoria, String desc, Timestamp fecha) throws SQLException {
           Movimiento mov = new Movimiento();
    // Si tu modelo tiene setId(...) puedes dejarlo en 0 o no setearlo
    mov.setIdUsuario(idUsuario);
    mov.setIdCategoria(idCategoria);
    mov.setMonto(monto);
    mov.setCategoria(categoria);
    mov.setDescripcion(desc);
    mov.setFecha(fecha); // si Movimiento usa LocalDateTime

    // 2) Registrar el movimiento (el DAO debe devolver el id generado)
    int idMov = movDAO.registrarmovimiento(mov);

    // 3) Repartir a metas activas usando BigDecimal para evitar errores de tipo
    List<Meta> metas = metaDAO.listarActivasPorUsuario(idUsuario);
    for (Meta m : metas) {
        // Suponiendo que m.getPorcentaje() devuelve double; si devuelve BigDecimal, ajusta.
        BigDecimal porcentaje = BigDecimal.valueOf(m.getPorcentaje()); // ej: 10 -> 10
        BigDecimal aporte = monto.multiply(porcentaje)
                                 .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);

        // 4) Llamadas a los DAOs: asumo que aceptan BigDecimal. Si aceptaran double, usar aporte.doubleValue()
        aporteDAO.registrarAporte(m.getIdMeta(), aporte);          // <-- ajustar firma si es necesario
        metaDAO.actualizarAcumulado(m.getIdMeta(), aporte);       // <-- ajustar firma si es necesario
        metaDAO.desactivarSiCumplida(m.getIdMeta());
    }

    return idMov;
    }
}


