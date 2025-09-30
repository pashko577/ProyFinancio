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
import pe.edu.utp.financio.dao.impl.MovimientoDaoImpl;
import pe.edu.utp.financio.dao1.AporteDAO;
import pe.edu.utp.financio.dao1.MovimientoDAO;
import pe.edu.utp.financio.service.MovimientoService;
import pe.edu.utp.financio.dao1.MetaDAO;
import pe.edu.utp.financio.modelo.Meta;
import pe.edu.utp.financio.modelo.Movimiento;

public class MovimientoServiceImpl implements MovimientoService {

    private MovimientoDAO movDAO;
    private MetaDAO metaDAO;
    private AporteDAO aporteDAO;

    // ✅ Constructor vacío, pero inicializa los DAO
    public MovimientoServiceImpl() {
        this.movDAO = new MovimientoDaoImpl();
        /*this.metaDAO = new MetaDaoImpl();*/
 /*this.aporteDAO = new AporteDaoImpl();*/
    }

    // ✅ Constructor con parámetros (útil si quieres inyectar DAOs en tests)
    public MovimientoServiceImpl(MovimientoDAO movDAO, MetaDAO metaDAO, AporteDAO aporteDAO) {
        this.movDAO = movDAO;
        this.metaDAO = metaDAO;
        this.aporteDAO = aporteDAO;
    }

    @Override
    public int registrarIngresoYRepartir(int idUsuario, int idCategoria, BigDecimal monto,
            String categoria, String desc, Timestamp fecha) throws SQLException {
        Movimiento mov = new Movimiento();
        mov.setIdUsuario(idUsuario);
        mov.setIdCategoria(idCategoria);
        mov.setMonto(monto);
        mov.setCategoria(categoria);
        mov.setDescripcion(desc);
        mov.setFecha(fecha);

        // Registrar movimiento
        int idMov = movDAO.registrarmovimiento(mov);

        // Repartir en metas activas
        List<Meta> metas = metaDAO.listarActivasPorUsuario(idUsuario);
        for (Meta m : metas) {
            BigDecimal porcentaje = BigDecimal.valueOf(m.getPorcentaje());
            BigDecimal aporte = monto.multiply(porcentaje)
                    .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);

//            aporteDAO.registrarAporte(m.getIdMeta(), aporte);
//            metaDAO.actualizarAcumulado(m.getIdMeta(), aporte);
//            metaDAO.desactivarSiCumplida(m.getIdMeta());
        }

        return idMov;
    }

    @Override
    public List<Movimiento> listarPorUsuario(int idUsuario) throws SQLException {
        return movDAO.listarPorUsuario(idUsuario);
    }

    @Override
    public List<Movimiento> listarIngresosPorUsuario(int idUsuario) throws SQLException {
        return movDAO.listarIngresosPorUsuario(idUsuario);
    }

    @Override
    public List<Movimiento> listarGastosPorUsuario(int idUsuario) throws SQLException {
        return movDAO.listarGastosPorUsuario(idUsuario);

    }
}
