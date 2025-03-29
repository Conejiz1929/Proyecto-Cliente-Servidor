/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.tienda;

/**
 *
 * @author Conej
 */


public class Tienda {
     public static void main(String[] args) {
        // Crear un gestor de usuarios
        GestorUsuarios gestorUsuarios = new GestorUsuarios();

        // Ejecutar la ventana de Login
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Login(gestorUsuarios).setVisible(true);  // Abre la ventana de Login
            }
        });
    }
}
