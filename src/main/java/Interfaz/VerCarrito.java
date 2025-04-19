/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Interfaz;

import modelo.Producto;
import Producto.Carrito;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author José Sequeira
 */
public class VerCarrito extends JFrame {

    public VerCarrito(Carrito carrito) {
        setTitle("Carrito de Compras");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(0, 1));
        JScrollPane scrollPane = new JScrollPane(panel);

        for (Producto producto : carrito.obtenerProductos()) {
            JPanel productoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            JLabel labelProducto = new JLabel(producto.toString());
            JButton botonEliminar = new JButton("Eliminar");

            botonEliminar.addActionListener(e -> {
                carrito.eliminarProducto(producto);
                panel.remove(productoPanel);
                panel.revalidate();
                panel.repaint();
            });

            productoPanel.add(labelProducto);
            productoPanel.add(botonEliminar);
            panel.add(productoPanel);
        }

        JLabel total = new JLabel("Total: $" + String.format("%.2f", carrito.calcularTotal()));
        panel.add(total);

        JButton realizarPedidoButton = new JButton("Realizar Pedido");
        realizarPedidoButton.addActionListener(e -> {
            // Lógica para realizar pedido
            JOptionPane.showMessageDialog(this, "Pedido realizado y factura enviada.");
        });
        panel.add(realizarPedidoButton);

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

        add(scrollPane);
    }
}
