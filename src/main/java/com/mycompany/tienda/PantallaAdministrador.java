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

public class PantallaAdministrador extends JFrame {

    public PantallaAdministrador(GestorUsuarios gestorUsuarios) {
        setTitle("Administrador - Reportes e Inventarios");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(3, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JButton generarReporteButton = new JButton("Generar Reporte de Ventas");
        JButton controlarInventarioButton = new JButton("Controlar Inventario");

        generarReporteButton.addActionListener(e -> {
            // Lógica para generar reporte
            JOptionPane.showMessageDialog(this, "Reporte de ventas generado.");
        });

        controlarInventarioButton.addActionListener(e -> {
            // Lógica para controlar inventario
            JOptionPane.showMessageDialog(this, "Control de inventario.");
        });

        panel.add(generarReporteButton);
        panel.add(controlarInventarioButton);

        // Botón para regresar al login
        JButton regresarButton = new JButton("Regresar");
        regresarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();  // Cierra la ventana actual
                new Login(gestorUsuarios).setVisible(true);  // Muestra la ventana de login
            }
        });
        panel.add(regresarButton);

        add(panel);
    }
}
