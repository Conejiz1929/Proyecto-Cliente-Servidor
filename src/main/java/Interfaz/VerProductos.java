/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Interfaz;

import modelo.Producto;
import Producto.ControlInventario;
import Producto.Carrito;
import controller.ProductoController;
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

    private ProductoController productoController;
    private JPanel panelProductos;
    private JTextField filtroField;
    private JComboBox<String> comboFiltro;

    public VerProductos(ProductoController controller) {
        this.productoController = controller;
        
        // Configuración básica de la ventana
        setTitle("Catálogo de Productos");
        setSize(900, 650);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(240, 240, 240));
        
        // Panel superior con filtros
        JPanel panelFiltros = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panelFiltros.setBackground(new Color(240, 240, 240));
        
        comboFiltro = new JComboBox<>(new String[]{"Todos", "Código", "Nombre", "Categoría"});
        filtroField = new JTextField(20);
        JButton btnFiltrar = new JButton("Filtrar");
        btnFiltrar.setBackground(new Color(70, 130, 180));
        btnFiltrar.setForeground(Color.WHITE);
        
        btnFiltrar.addActionListener(e -> aplicarFiltros());
        
        panelFiltros.add(new JLabel("Filtrar por:"));
        panelFiltros.add(comboFiltro);
        panelFiltros.add(filtroField);
        panelFiltros.add(btnFiltrar);
        
        // Panel principal de productos
        panelProductos = new JPanel();
        panelProductos.setLayout(new BoxLayout(panelProductos, BoxLayout.Y_AXIS));
        panelProductos.setBackground(Color.WHITE);
        
        JScrollPane scrollPane = new JScrollPane(panelProductos);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        
        // Organización de la ventana
        add(panelFiltros, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        
        // Cargar productos iniciales
        cargarProductos();
    }

    private void cargarProductos() {
        try {
            List<Producto> productos = productoController.obtenerTodosProductos();
            mostrarProductos(productos);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Error al cargar productos: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void mostrarProductos(List<Producto> productos) {
        panelProductos.removeAll();
        
        if (productos.isEmpty()) {
            JLabel lblEmpty = new JLabel("No se encontraron productos");
            lblEmpty.setHorizontalAlignment(SwingConstants.CENTER);
            lblEmpty.setFont(new Font("Arial", Font.ITALIC, 14));
            panelProductos.add(lblEmpty);
        } else {
            for (Producto producto : productos) {
                panelProductos.add(crearPanelProducto(producto));
                panelProductos.add(Box.createRigidArea(new Dimension(0, 10)));
            }
        }
        
        panelProductos.revalidate();
        panelProductos.repaint();
    }

    private JPanel crearPanelProducto(Producto producto) {
        JPanel panel = new JPanel(new BorderLayout(10, 5));
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));
        panel.setBackground(Color.WHITE);
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 120));

        // Panel de información
        JPanel infoPanel = new JPanel(new GridLayout(0, 1, 5, 5));
        infoPanel.setBackground(Color.WHITE);
        
        JLabel codigoLabel = new JLabel("Código: " + producto.getCodigo());
        JLabel nombreLabel = new JLabel(producto.getNombre());
        nombreLabel.setFont(new Font("Arial", Font.BOLD, 14));
        
        JLabel precioLabel = new JLabel(String.format("Precio: $%.2f", producto.getPrecio()));
        JLabel stockLabel = new JLabel("Stock: " + producto.getCantidad());
        stockLabel.setForeground(producto.getCantidad() > 0 ? new Color(0, 120, 0) : Color.RED);
        
        infoPanel.add(codigoLabel);
        infoPanel.add(nombreLabel);
        infoPanel.add(precioLabel);
        infoPanel.add(stockLabel);

        // Panel de botones
        JPanel buttonPanel = new JPanel(new GridLayout(2, 1, 5, 5));
        
        JButton btnEditar = new JButton("Editar");
        btnEditar.setBackground(new Color(70, 130, 180));
        btnEditar.setForeground(Color.WHITE);
        
        JButton btnEliminar = new JButton("Eliminar");
        btnEliminar.setBackground(new Color(220, 80, 60));
        btnEliminar.setForeground(Color.WHITE);
        
        buttonPanel.add(btnEditar);
        buttonPanel.add(btnEliminar);

        panel.add(infoPanel, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.EAST);

        return panel;
    }

    private void aplicarFiltros() {
        try {
            String filtro = filtroField.getText().trim();
            String tipoFiltro = (String) comboFiltro.getSelectedItem();
            
            List<Producto> productosFiltrados = productoController.obtenerTodosProductos();
            
            if (!filtro.isEmpty()) {
                switch (tipoFiltro) {
                    case "Código":
                        productosFiltrados.removeIf(p -> !p.getCodigo().contains(filtro));
                        break;
                    case "Nombre":
                        productosFiltrados.removeIf(p -> !p.getNombre().toLowerCase().contains(filtro.toLowerCase()));
                        break;
                    case "Categoría":
                        productosFiltrados.removeIf(p -> !p.getCategoria().toLowerCase().contains(filtro.toLowerCase()));
                        break;
                }
            }
            
            mostrarProductos(productosFiltrados);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Error al filtrar productos: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                // Ejemplo de uso
                ProductoController controller = new ProductoController();
                new VerProductos(controller).setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
