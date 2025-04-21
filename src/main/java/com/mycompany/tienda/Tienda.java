/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.tienda;

import Login_Registro.Login;
import controller.UsuarioController;

/**
 *
 * @author Conej
 */
public class Tienda {

    public static void main(String[] args) {
        // Crear un controlador de usuarios
        UsuarioController usuarioController = new UsuarioController();

        // Ejecutar la ventana de Login
        javax.swing.SwingUtilities.invokeLater(() -> {
            try {
                new Login(usuarioController).setVisible(true); // Abre la ventana de Login
            } catch (Exception e) {
                System.err.println("Error al iniciar la aplicación: " + e.getMessage());
                e.printStackTrace();
            }
        });
    }
}
