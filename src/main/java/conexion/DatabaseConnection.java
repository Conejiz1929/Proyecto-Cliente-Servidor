/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package conexion;

/**
 *
 * @author José Sequeira
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.*;

public class DatabaseConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/ProyectoClienteServidor";
    private static final String USER = "root";
    private static final String PASSWORD = "Server";
    
    public static Connection conectar()
    {
        Connection con = null;
        try
        {
         con=DriverManager.getConnection(URL,USER,PASSWORD);
            System.out.println("Conexion exitosa");
        }catch(SQLException e)
        {
            e.printStackTrace();
        }
        return con;
        }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
