package com.mycompany.tienda;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class EditarProductos extends JFrame {

    private Controlnventario controlInventario;
    private JPanel panelProductos;

    public EditarProductos(Controlnventario controlInventario) {
        this.controlInventario = controlInventario;

        setTitle("Editar Productos del Inventario");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Crear un panel para mostrar los productos
        panelProductos = new JPanel(new GridLayout(0, 1));
        JScrollPane scrollPane = new JScrollPane(panelProductos);

        // Botón para agregar productos
        JButton agregarProductoButton = new JButton("Agregar Producto");
        agregarProductoButton.addActionListener(e -> agregarProducto());

        // Panel inferior con el botón
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelBotones.add(agregarProductoButton);

        // Añadir los componentes al JFrame
        add(scrollPane, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);

        // Mostrar los productos al abrir la ventana
        actualizarListaProductos();
    }

    private void actualizarListaProductos() {
        panelProductos.removeAll(); // Limpia el panel antes de actualizar
        List<Producto> productos = controlInventario.obtenerProductos(); // Obtener productos del inventario

        for (Producto producto : productos) {
            JPanel productoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            JLabel labelProducto = new JLabel(producto.toString());
            JButton editarButton = new JButton("Editar");
            JButton eliminarButton = new JButton("Eliminar");

            editarButton.addActionListener(e -> editarProducto(producto));
            eliminarButton.addActionListener(e -> eliminarProducto(producto));

            productoPanel.add(labelProducto);
            productoPanel.add(editarButton);
            productoPanel.add(eliminarButton);
            panelProductos.add(productoPanel);
        }

        panelProductos.revalidate(); // Actualiza el panel
        panelProductos.repaint(); // Redibuja el panel
    }

    private void agregarProducto() {
        String codigo = JOptionPane.showInputDialog(this, "Ingrese el código del producto:");
        String nombre = JOptionPane.showInputDialog(this, "Ingrese el nombre del producto:");
        String categoria = JOptionPane.showInputDialog(this,"Ingrese la categoria del producto");
        String cantidadStr = JOptionPane.showInputDialog(this, "Ingrese la cantidad del producto:");
        String precioStr = JOptionPane.showInputDialog(this, "Ingrese el precio del producto:");

        try {
            int cantidad = Integer.parseInt(cantidadStr);
            double precio = Double.parseDouble(precioStr);
            controlInventario.registrarProducto(codigo, nombre, cantidad, precio,categoria);
            JOptionPane.showMessageDialog(this, "Producto agregado exitosamente.");
            actualizarListaProductos(); // Actualiza la lista después de agregar
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Cantidad o precio inválidos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void editarProducto(Producto producto) {
        String nuevoNombre = JOptionPane.showInputDialog(this, "Nuevo nombre:", producto.getNombre());
        String nuevaCantidadStr = JOptionPane.showInputDialog(this, "Nueva cantidad:", producto.getCantidad());
        String nuevoPrecioStr = JOptionPane.showInputDialog(this, "Nuevo precio:", producto.getPrecio());

        try {
            int nuevaCantidad = Integer.parseInt(nuevaCantidadStr);
            double nuevoPrecio = Double.parseDouble(nuevoPrecioStr);
            producto.setNombre(nuevoNombre);
            producto.setCantidad(nuevaCantidad);
            producto.setPrecio(nuevoPrecio);
            JOptionPane.showMessageDialog(this, "Producto actualizado.");
            actualizarListaProductos(); // Actualiza la lista después de editar
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Cantidad o precio inválidos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminarProducto(Producto producto) {
        int confirm = JOptionPane.showConfirmDialog(this, "¿Está seguro de eliminar este producto?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            controlInventario.eliminarProducto(producto.getCodigo());
            JOptionPane.showMessageDialog(this, "Producto eliminado.");
            actualizarListaProductos(); // Actualiza la lista después de eliminar
        }
    }
}