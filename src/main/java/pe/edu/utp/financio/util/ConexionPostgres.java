/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.utp.financio.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author User
 */
public class ConexionPostgres {

    private static final String URL = "jdbc:postgresql://localhost:5432/financio";
    private static final String USER = "postgres";
    private static final String PASS = "root";

    public static Connection getConnection() {
        try {
            Class.forName("org.postgresql.Driver");
            return DriverManager.getConnection(URL, USER, PASS);

        } catch (Exception e) {
            System.out.println("Error en la conexión: " + e.getMessage());
            return null;
        }
    }
}

