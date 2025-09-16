/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package pe.edu.utp.financio.service;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;


/**
 *
 * @author User
 */
public interface MovimientoService {
     int registrarIngresoYRepartir(int idUsuario, int idCategoria, BigDecimal monto,String categoria, String desc,Timestamp fecha) throws SQLException;

}
