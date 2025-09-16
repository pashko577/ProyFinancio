/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package pe.edu.utp.financio.dao1;

import java.math.BigDecimal;
import java.sql.SQLException;

/**
 *
 * @author User
 */
public interface AporteDAO {
        int registrarAporte(int idMeta, BigDecimal monto) throws SQLException;

}
