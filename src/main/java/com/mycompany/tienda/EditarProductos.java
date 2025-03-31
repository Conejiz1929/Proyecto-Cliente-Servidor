package com.mycompany.tienda;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

public class EditarProductos extends JFrame {

    private ControlInventario controlInventario;
    private JList<String> listaProductos;
    private DefaultListModel<String> modeloLista;

    public EditarProductos() {
        controlInventario = new ControlInventario();
        setTitle("Editar Productos");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        modeloLista = new DefaultListModel<>();
        listaProductos = new JList<>(modeloLista);
        JScrollPane scrollPane = new JScrollPane(listaProductos);
        add(scrollPane);

        JButton btnAgregar = new JButton("Agregar Producto");
        btnAgregar.addActionListener(e -> agregarProducto());
        add(btnAgregar);

        JButton btnEliminar = new JButton("Eliminar Producto");
        btnEliminar.addActionListener(e -> eliminarProducto());
        add(btnEliminar);

        // Añadir el botón "Guardar Cambios"
        JButton btnGuardarCambios = new JButton("Guardar Cambios");
        btnGuardarCambios.addActionListener(e -> guardarCambios());
        add(btnGuardarCambios);

        actualizarListaProductos();
        setVisible(true);
    }

    private void agregarProducto() {
        String codigo = JOptionPane.showInputDialog(this, "Ingrese el código del producto:");
        if (codigo == null || codigo.trim().isEmpty()) {
            return;
        }

        String nombre = JOptionPane.showInputDialog(this, "Ingrese el nombre del producto:");
        if (nombre == null || nombre.trim().isEmpty()) {
            return;
        }

        String categoriaStr = JOptionPane.showInputDialog(this, "Ingrese el ID de la categoría del producto:");
        if (categoriaStr == null || categoriaStr.trim().isEmpty()) {
            return;
        }

        String cantidadStr = JOptionPane.showInputDialog(this, "Ingrese la cantidad del producto:");
        String precioStr = JOptionPane.showInputDialog(this, "Ingrese el precio del producto:");

        try {
            int cantidad = Integer.parseInt(cantidadStr);
            double precio = Double.parseDouble(precioStr);
            String categoria = categoriaStr;

            if (controlInventario.existeProducto(codigo)) {
                JOptionPane.showMessageDialog(this, "El código del producto ya existe.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            controlInventario.registrarProducto(codigo, nombre, cantidad, precio, categoria);
            actualizarListaProductos();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Cantidad, precio o categoría inválidos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminarProducto() {
        String codigo = JOptionPane.showInputDialog(this, "Ingrese el código del producto a eliminar:");
        if (codigo == null || codigo.trim().isEmpty()) {
            return;
        }

        controlInventario.eliminarProducto(codigo);
        actualizarListaProductos();
    }

    private void actualizarListaProductos() {
        modeloLista.clear();
        List<String> productos = controlInventario.obtenerListaProductos();
        for (String producto : productos) {
            modeloLista.addElement(producto);
        }
    }

    // Método para guardar los cambios realizados
    private void guardarCambios() {
        List<Producto> productos = controlInventario.obtenerProductos();
        for (Producto producto : productos) {
            controlInventario.actualizarProducto(producto);
        }
        JOptionPane.showMessageDialog(this, "Cambios guardados exitosamente.");
    }

    private static class ControlInventario {

        private List<Producto> productos;

        public ControlInventario() {
            productos = new ArrayList<>();
        }

        public boolean existeProducto(String codigo) {
            return productos.stream().anyMatch(p -> p.getCodigo().equals(codigo));
        }

        public void registrarProducto(String codigo, String nombre, int cantidad, double precio, String categoria) {
            productos.add(new Producto(codigo, nombre, cantidad, precio, categoria));
        }

        public void eliminarProducto(String codigo) {
            productos.removeIf(p -> p.getCodigo().equals(codigo));
        }

        public List<String> obtenerListaProductos() {
            List<String> lista = new ArrayList<>();
            for (Producto producto : productos) {
                lista.add(producto.toString());
            }
            return lista;
        }

        public List<Producto> obtenerProductos() {
            return productos;
        }

        public void actualizarProducto(Producto producto) {
            String sql = "UPDATE Productos SET nombre = ?, precio = ?, stock = ?, id_categoria = ? WHERE id_producto = ?";
            try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, producto.getNombre());
                stmt.setDouble(2, producto.getPrecio());
                stmt.setInt(3, producto.getCantidad());
                stmt.setString(4, producto.getCategoria());
                stmt.setString(5, producto.getCodigo());
                stmt.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private static class Producto {

        private String codigo;
        private String nombre;
        private int cantidad;
        private double precio;
        private String categoria;

        public Producto(String codigo, String nombre, int cantidad, double precio, String categoria) {
            this.codigo = codigo;
            this.nombre = nombre;
            this.cantidad = cantidad;
            this.precio = precio;
            this.categoria = categoria;
        }

        public String getCodigo() {
            return codigo;
        }

        public void setCodigo(String codigo) {
            this.codigo = codigo;
        }

        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

        public int getCantidad() {
            return cantidad;
        }

        public void setCantidad(int cantidad) {
            this.cantidad = cantidad;
        }

        public double getPrecio() {
            return precio;
        }

        public void setPrecio(double precio) {
            this.precio = precio;
        }

        public String getCategoria() {
            return categoria;
        }

        public void setCategoria(String categoria) {
            this.categoria = categoria;
        }

        @Override
        public String toString() {
            return "Código: " + codigo + ", Nombre: " + nombre + ", Cantidad: " + cantidad + ", Precio: " + precio + ", ID Categoría: " + categoria;
        }
    }
}
