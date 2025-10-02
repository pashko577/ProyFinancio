package pe.edu.utp.financio.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import pe.edu.utp.financio.dao.impl.MovimientoDaoImpl;
import pe.edu.utp.financio.dao1.AporteDAO;
import pe.edu.utp.financio.dao1.MetaDAO;
import pe.edu.utp.financio.dao1.MovimientoDAO;
import pe.edu.utp.financio.modelo.Meta;
import pe.edu.utp.financio.modelo.Movimiento;
import pe.edu.utp.financio.modelo.Usuario;
import pe.edu.utp.financio.service.MovimientoService;

public class MovimientoServiceImpl implements MovimientoService {

    private MovimientoDAO movDAO;
    private MetaDAO metaDAO;
    private AporteDAO aporteDAO;

    // Constructor vacío
    public MovimientoServiceImpl() {
        this.movDAO = new MovimientoDaoImpl();
        // this.metaDAO = new MetaDaoImpl();
        // this.aporteDAO = new AporteDaoImpl();
    }

    // Constructor con DAOs (para inyección o tests)
    public MovimientoServiceImpl(MovimientoDAO movDAO, MetaDAO metaDAO, AporteDAO aporteDAO) {
        this.movDAO = movDAO;
        this.metaDAO = metaDAO;
        this.aporteDAO = aporteDAO;
    }

    // Registrar ingreso y repartir en metas
    @Override
    public int registrarIngresoYRepartir(int idUsuario, int idCategoria, BigDecimal monto,
                                         String categoria, String desc, Timestamp fecha) throws SQLException {

        int idAdmin = 1;      // ID del admin en la BD
        int creadoPor = idUsuario;  // quien crea el movimiento

        Movimiento mov = new Movimiento();
        mov.setIdUsuario(idAdmin);  // se registra a nombre del admin
        mov.setIdCategoria(idCategoria);
        mov.setMonto(monto);
        mov.setCategoria(categoria);
        mov.setDescripcion(desc);
        mov.setFecha(fecha);
        mov.setCreadoPor(creadoPor);

        int idMov = movDAO.registrarmovimiento(mov, idAdmin, creadoPor);

        // Repartir en metas activas
        if (metaDAO != null) {
            List<Meta> metas = metaDAO.listarActivasPorUsuario(idUsuario);
            for (Meta m : metas) {
                BigDecimal porcentaje = BigDecimal.valueOf(m.getPorcentaje());
                BigDecimal aporte = monto.multiply(porcentaje)
                        .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);

                // aporteDAO.registrarAporte(m.getIdMeta(), aporte);
                // metaDAO.actualizarAcumulado(m.getIdMeta(), aporte);
                // metaDAO.desactivarSiCumplida(m.getIdMeta());
            }
        }

        return idMov;
    }

    // ===================== LISTADOS =====================

    public List<Movimiento> listarPorUsuario(Usuario usuario) throws SQLException {
        boolean esAdmin = usuario.getRol().equalsIgnoreCase("ADMIN");
        return movDAO.listarPorUsuario(usuario.getId(), esAdmin);
    }

    public List<Movimiento> listarIngresosPorUsuario(Usuario usuario) throws SQLException {
        boolean esAdmin = usuario.getRol().equalsIgnoreCase("ADMIN");
        return movDAO.listarIngresosPorUsuario(usuario.getId(), esAdmin);
    }

    public List<Movimiento> listarGastosPorUsuario(Usuario usuario) throws SQLException {
        boolean esAdmin = usuario.getRol().equalsIgnoreCase("ADMIN");
        return movDAO.listarGastosPorUsuario(usuario.getId(), esAdmin);
    }


}
