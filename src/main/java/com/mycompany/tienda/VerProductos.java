/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tienda;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author José Sequeira
 */
public class VerProductos extends JFrame {

    private Inventario inventario;
    private Carrito carrito;
    private JPanel panelProductos;

    public VerProductos(Inventario inventario, Carrito carrito) {
        this.inventario = inventario;
        this.carrito = carrito;
        setTitle("Productos Disponibles");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        panelProductos = new JPanel(new GridLayout(0, 1));
        JScrollPane scrollPane = new JScrollPane(panelProductos);

        // Panel de filtros
        JPanel panelFiltros = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JComboBox<String> categoriaComboBox = new JComboBox<>(new String[]{"Todos", "Electrónica", "Accesorios"});
        JTextField precioMinField = new JTextField(5);
        JTextField precioMaxField = new JTextField(5);
        JCheckBox disponibilidadCheckBox = new JCheckBox("Solo disponibles");
        JButton aplicarFiltrosButton = new JButton("Aplicar Filtros");

        panelFiltros.add(new JLabel("Categoría:"));
        panelFiltros.add(categoriaComboBox);
        panelFiltros.add(new JLabel("Precio Mín:"));
        panelFiltros.add(precioMinField);
        panelFiltros.add(new JLabel("Precio Máx:"));
        panelFiltros.add(precioMaxField);
        panelFiltros.add(disponibilidadCheckBox);
        panelFiltros.add(aplicarFiltrosButton);

        aplicarFiltrosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String categoria = (String) categoriaComboBox.getSelectedItem();
                String precioMinStr = precioMinField.getText();
                String precioMaxStr = precioMaxField.getText();
                boolean soloDisponibles = disponibilidadCheckBox.isSelected();

                double precioMin = precioMinStr.isEmpty() ? 0 : Double.parseDouble(precioMinStr);
                double precioMax = precioMaxStr.isEmpty() ? Double.MAX_VALUE : Double.parseDouble(precioMaxStr);

                List<Producto> productosFiltrados = inventario.obtenerProductos();

                if (categoria != null && !categoria.equals("Todos")) {
                    productosFiltrados = inventario.filtrarPorCategoria(categoria);
                } else {
                    productosFiltrados = inventario.obtenerProductos();
                }

                productosFiltrados = productosFiltrados.stream()
                        .filter(p -> p.getPrecio() >= precioMin && p.getPrecio() <= precioMax)
                        .collect(Collectors.toList());

                if (soloDisponibles) {
                    productosFiltrados = productosFiltrados.stream()
                            .filter(p -> p.getCantidad() > 0)
                            .collect(Collectors.toList());
                }

                actualizarListaProductos(productosFiltrados);
            }
        });

        // Botón para regresar
        JButton regresarButton = new JButton("Regresar");
        regresarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();  // Cierra la ventana actual
                // Aquí puedes agregar el código para mostrar la ventana anterior, si es necesario
            }
        });

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelBotones.add(regresarButton);

        add(panelFiltros, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);

        actualizarListaProductos(inventario.obtenerProductos());
    }

    private void actualizarListaProductos(List<Producto> productos) {
        panelProductos.removeAll();
        for (Producto producto : productos) {
            JPanel productoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            JLabel labelProducto = new JLabel(producto.toString());
            JButton botonAgregar = new JButton("Agregar al Carrito");

            botonAgregar.addActionListener(e -> {
                carrito.agregarProducto(producto);
                JOptionPane.showMessageDialog(this,
                        producto.getNombre() + " agregado al carrito",
                        "Carrito",
                        JOptionPane.INFORMATION_MESSAGE);
            });

            productoPanel.add(labelProducto);
            productoPanel.add(botonAgregar);
            panelProductos.add(productoPanel);
        }
        panelProductos.revalidate();
        panelProductos.repaint();
    }
}
