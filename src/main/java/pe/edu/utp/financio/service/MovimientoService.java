/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package pe.edu.utp.financio.service;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import pe.edu.utp.financio.modelo.Movimiento;
import pe.edu.utp.financio.modelo.Usuario;

/**
 *
 * @author User
 */
public interface MovimientoService {

    int registrarIngresoYRepartir(int idUsuario, int idCategoria, BigDecimal monto, String categoria, String desc, Timestamp fecha) throws SQLException;

// 👇 Nuevo método para que tu vista pueda listar movimientos
    List<Movimiento> listarPorUsuario(Usuario usuario) throws SQLException;

    List<Movimiento> listarIngresosPorUsuario(Usuario usuario) throws SQLException;

    List<Movimiento> listarGastosPorUsuario(Usuario usuario) throws SQLException;

}
