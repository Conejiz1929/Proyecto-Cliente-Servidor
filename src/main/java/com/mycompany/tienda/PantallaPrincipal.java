package com.mycompany.tienda;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author Conej
 */
class PantallaPrincipal extends JFrame {

    public PantallaPrincipal(Usuario usuarioAutenticado, GestorUsuarios gestorUsuarios) {
        // Inicializar componentes
        ControlInventario controlnventario = new ControlInventario();
        Inventario inventario = new Inventario();
        Carrito carrito = new Carrito();

        // Configuración de la ventana
        setTitle("Tienda Online - " + usuarioAutenticado.getNombre());
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel de botones
        JPanel panel = new JPanel(new GridLayout(5, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Crear botones
        JButton verProductosButton = new JButton("Ver Productos");
        JButton verCarritoButton = new JButton("Ver Carrito");
        JButton verPerfilButton = new JButton("Ver Perfil");

        // Listeners de botones
        verProductosButton.addActionListener(e -> {
            VerProductos verProductos = new VerProductos(controlnventario, carrito);
            verProductos.setVisible(true);
        });

        verCarritoButton.addActionListener(e -> {
            VerCarrito verCarrito = new VerCarrito(carrito);
            verCarrito.setVisible(true);
        });

        verPerfilButton.addActionListener(e -> {
            VerPerfil verPerfil = new VerPerfil(usuarioAutenticado);
            verPerfil.setVisible(true);
        });

        // Botón para regresar al login
        JButton regresarButton = new JButton("Regresar");
        regresarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();  // Cierra la ventana actual
                new Login(gestorUsuarios).setVisible(true);  // Muestra la ventana de login
            }
        });

        // Agregar botones al panel
        panel.add(verProductosButton);
        panel.add(verCarritoButton);
        panel.add(verPerfilButton);
        panel.add(regresarButton);

        add(panel);
    }
}
