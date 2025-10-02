/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package pe.edu.utp.financio.dao1;

import java.sql.SQLException;
import java.util.List;
import pe.edu.utp.financio.modelo.Movimiento;

/**
 *
 * @author User
 */
public interface MovimientoDAO {
    int registrarmovimiento(Movimiento m, int idAdmin, int creadoPor) throws SQLException;
    List<Movimiento> listarIngresosPorUsuario(int idUsuario, boolean esAdmin) throws SQLException;
    List<Movimiento> listarGastosPorUsuario(int idUsuario, boolean esAdmin) throws SQLException;
    int eliminarMovimiento(int idUsuario) throws Exception;
    List<Movimiento> listarPorUsuario(int idUsuario, boolean esAdmin) throws SQLException;
    
}
