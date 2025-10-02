/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package pe.edu.utp.financio.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author User
 */
public class PruebaPostgreSQL {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5432/financio";
        String user = "postgres";
        String pass = "Castro";

        try (Connection cn = DriverManager.getConnection(url, user, pass)) {
            System.out.println("✅ Conectado a PostgreSQL");

            PreparedStatement ps = cn.prepareStatement("SELECT * FROM usuarios");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                System.out.println("Usuario: " + rs.getString("nombre"));
            }
        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }

}
