/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.utp.financio.service.impl;



import java.sql.SQLException;
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
    public int registrarIngresoYRepartir(int idUsuario, int idCategoria, double monto, String desc) throws SQLException {
        Movimiento mov = new Movimiento(0, idUsuario, idCategoria, monto, desc, LocalDateTime.now());
        int idMov = movDAO.registrarIngreso(mov);

        List<Meta> metas = metaDAO.listarActivasPorUsuario(idUsuario);
        for (Meta m : metas) {
            double aporte = monto * (m.getPorcentaje() / 100.0);
            aporteDAO.registrarAporte(m.getIdMeta(), aporte);
            metaDAO.actualizarAcumulado(m.getIdMeta(), aporte);
            metaDAO.desactivarSiCumplida(m.getIdMeta());
        }

        return idMov;
    }
}


