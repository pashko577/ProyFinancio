/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package pe.edu.utp.financio.service;

import java.sql.SQLException;

/**
 *
 * @author User
 */
public interface MovimientoService {
     int registrarIngresoYRepartir(int idUsuario, int idCategoria, double monto, String desc) throws SQLException;

}
