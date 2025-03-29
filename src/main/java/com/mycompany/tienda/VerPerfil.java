/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tienda;

/**
 *
 * @author José Sequeira
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;

class VerPerfil extends JFrame {

    public VerPerfil(Usuario usuario) {
        setTitle("Perfil de Usuario");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Nombre: " + usuario.getNombre()));
        panel.add(new JLabel("Email: " + usuario.getEmail()));

        // Botón para regresar
        JButton regresarButton = new JButton("Regresar");
        regresarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();  // Cierra la ventana actual
                // Aquí puedes agregar el código para mostrar la ventana anterior, si es necesario
            }
        });
        panel.add(regresarButton);

        add(panel);
    }
}
