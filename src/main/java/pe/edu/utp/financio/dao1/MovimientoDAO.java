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
    int registrarIngreso(Movimiento m) throws SQLException;
    int registrarGasto(Movimiento m) throws SQLException;
    List<Movimiento> listarPorUsuario(int idUsuario) throws SQLException;
}
