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

public class PantallaAdministrador extends JFrame {

    private Controlnventario controlInventario;

    public PantallaAdministrador(GestorUsuarios gestorUsuarios, Controlnventario controlInventario) {
        this.controlInventario = controlInventario;

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
            VerProductos verProductos = new VerProductos(controlInventario, new Carrito());
            EditarProductos editarProductos = new EditarProductos(controlInventario, verProductos);
            editarProductos.setVisible(true);
        });

        panel.add(generarReporteButton);
        panel.add(controlarInventarioButton);

        // Botón para regresar al login
        JButton regresarButton = new JButton("Regresar");
        regresarButton.addActionListener(e -> {
            dispose();  // Cierra la ventana actual
            new Login(gestorUsuarios).setVisible(true);  // Muestra la ventana de login
        });
        panel.add(regresarButton);

        add(panel);
    }
}
