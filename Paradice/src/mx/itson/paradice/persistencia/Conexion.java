/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.itson.paradice.persistencia;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Clase para establcer la conexion con la base de datos 
 * @author AbelEsquer
 */
public class Conexion {
    
    /**
     * Establece la conexion con la base de datos 
     * @return regresa los datos en la base de datos 
     */
    public static Connection obtener() {

        Connection connection = null;

        try {

            connection = DriverManager.getConnection("jdbc:mysql://localhost/paradicedb?user=root&password=admin");
        } catch (Exception e) {
            System.err.print("Error: " + e.getMessage());
        }
        return connection;
    }
    
}
