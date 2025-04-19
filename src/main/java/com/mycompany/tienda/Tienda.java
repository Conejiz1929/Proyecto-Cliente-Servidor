/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.tienda;

import Login_Registro.Login;
import Login_Registro.GestorUsuarios;

/**
 *
 * @author Conej
 */
public class Tienda {
    public static void main(String[] args) {
        // Crear un gestor de usuarios
        GestorUsuarios GestorUsuarios = new GestorUsuarios();

        // Ejecutar la ventana de Login
        javax.swing.SwingUtilities.invokeLater(() -> {
            try {
                new Login(GestorUsuarios).setVisible(true); // Abre la ventana de Login
            } catch (Exception e) {
                System.err.println("Error al iniciar la aplicación: " + e.getMessage());
                e.printStackTrace();
            }
        });
    }
}
