/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package pe.edu.utp.financio.dao1;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import org.bson.types.ObjectId;
import pe.edu.utp.financio.modelo.Meta;

/**
 *
 * @author User
 */
public interface MetaDAO {
     int registrar(Meta meta) throws SQLException;
    List<Meta> listarActivasPorUsuario(int idUsuario) throws SQLException;
    void actualizarAcumulado(String idMeta, BigDecimal monto) throws SQLException;
    void desactivarSiCumplida(String idMeta) throws SQLException;
}

