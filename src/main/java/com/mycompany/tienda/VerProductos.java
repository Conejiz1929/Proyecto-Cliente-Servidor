/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tienda;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author José Sequeira
 */
public class VerProductos extends JFrame {

    private ControlInventario controlInventario;
    private Carrito carrito;
    private JPanel panelProductos;
    private JTextField filtroCategoriaField;
    private JTextField filtroPrecioMinField;
    private JTextField filtroPrecioMaxField;
    private JCheckBox filtroDisponibilidadCheckBox;

    public VerProductos(ControlInventario controlInventario, Carrito carrito) {
        this.controlInventario = controlInventario;
        this.carrito = carrito;

        setTitle("Ver Productos");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Crear un panel para mostrar los productos
        panelProductos = new JPanel(new GridLayout(0, 1));
        JScrollPane scrollPane = new JScrollPane(panelProductos);

        // Crear un panel para los filtros
        JPanel panelFiltros = new JPanel(new GridLayout(2, 4, 10, 10));
        panelFiltros.setBorder(BorderFactory.createTitledBorder("Filtros"));

        // Filtro por categoría
        panelFiltros.add(new JLabel("Categoría:"));
        filtroCategoriaField = new JTextField();
        panelFiltros.add(filtroCategoriaField);

        // Filtro por precio mínimo
        panelFiltros.add(new JLabel("Precio Mínimo:"));
        filtroPrecioMinField = new JTextField();
        panelFiltros.add(filtroPrecioMinField);

        // Filtro por precio máximo
        panelFiltros.add(new JLabel("Precio Máximo:"));
        filtroPrecioMaxField = new JTextField();
        panelFiltros.add(filtroPrecioMaxField);

        // Filtro por disponibilidad
        panelFiltros.add(new JLabel("Solo disponibles:"));
        filtroDisponibilidadCheckBox = new JCheckBox();
        panelFiltros.add(filtroDisponibilidadCheckBox);

        // Botón para aplicar filtros
        JButton aplicarFiltrosButton = new JButton("Aplicar Filtros");
        aplicarFiltrosButton.addActionListener(e -> aplicarFiltros());
        panelFiltros.add(aplicarFiltrosButton);

        // Botón para regresar a la pantalla principal
        JButton regresarButton = new JButton("Regresar");
        regresarButton.addActionListener(e -> {
            dispose(); // Cierra la ventana actual

        });
        panelFiltros.add(regresarButton);

        // Añadir los componentes al JFrame
        add(panelFiltros, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        // Mostrar los productos al abrir la ventana
        actualizarListaProductos(controlInventario.obtenerProductos());
    }

    public void actualizarListaProductos(List<Producto> productos) {
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

    private void aplicarFiltros() {
        String categoria = filtroCategoriaField.getText().trim();
        String precioMinStr = filtroPrecioMinField.getText().trim();
        String precioMaxStr = filtroPrecioMaxField.getText().trim();
        boolean soloDisponibles = filtroDisponibilidadCheckBox.isSelected();

        double precioMin = precioMinStr.isEmpty() ? Double.MIN_VALUE : Double.parseDouble(precioMinStr);
        double precioMax = precioMaxStr.isEmpty() ? Double.MAX_VALUE : Double.parseDouble(precioMaxStr);

        List<Producto> productosFiltrados = controlInventario.obtenerProductos().stream()
                .filter(producto -> categoria.isEmpty() || producto.getCategoria().equalsIgnoreCase(categoria))
                .filter(producto -> producto.getPrecio() >= precioMin && producto.getPrecio() <= precioMax)
                .filter(producto -> !soloDisponibles || producto.getCantidad() > 0)
                .collect(Collectors.toList());

        actualizarListaProductos(productosFiltrados);
    }
}
