package Interfaz;

import controller.ProductoController;
import modelo.Producto;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Clase para gestionar productos con diseño mejorado.
 */
public class EditarProductos extends JFrame {

    private ProductoController productoController;
    private JList<String> listaProductos;
    private DefaultListModel<String> modeloLista;
    private JButton btnEditar;

    public EditarProductos() {
        // Inicializar controlador
        this.productoController = new ProductoController();

        configurarVentana();
        initUI();
        actualizarListaProductos();
    }

    private void configurarVentana() {
        setTitle("Gestión de Productos");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
    }

    private void initUI() {
        // Panel superior (encabezado)
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(255, 215, 0)); // Fondo dorado

        JLabel titleLabel = new JLabel("Gestión de Productos", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 24));
        titleLabel.setForeground(Color.BLACK);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        headerPanel.add(titleLabel, BorderLayout.CENTER);

        // Panel de lista de productos
        modeloLista = new DefaultListModel<>();
        listaProductos = new JList<>(modeloLista);
        listaProductos.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
        listaProductos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPane = new JScrollPane(listaProductos);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        // Panel de botones
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.setBackground(Color.WHITE);

        JButton btnAgregar = createStyledButton("Agregar", new Color(46, 204, 113)); // Verde
        btnAgregar.addActionListener(e -> agregarProducto());

        btnEditar = createStyledButton("Editar", new Color(52, 152, 219)); // Azul
        btnEditar.setEnabled(false);
        btnEditar.addActionListener(e -> editarProducto());

        JButton btnEliminar = createStyledButton("Eliminar", new Color(231, 76, 60)); // Rojo
        btnEliminar.addActionListener(e -> eliminarProducto());

        JButton btnActualizar = createStyledButton("Actualizar", new Color(241, 196, 15)); // Amarillo
        btnActualizar.addActionListener(e -> actualizarListaProductos());

        buttonPanel.add(btnAgregar);
        buttonPanel.add(btnEditar);
        buttonPanel.add(btnEliminar);
        buttonPanel.add(btnActualizar);

        // Listener para selección en la lista
        listaProductos.addListSelectionListener(e -> {
            boolean haySeleccion = !listaProductos.isSelectionEmpty();
            btnEditar.setEnabled(haySeleccion);
        });

        // Agregar componentes al JFrame
        add(headerPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private JButton createStyledButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Efecto hover
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(color.darker());
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(color);
            }
        });

        return button;
    }

    private void agregarProducto() {
        JPanel panel = new JPanel(new GridLayout(5, 2, 5, 5));

        JTextField txtCodigo = new JTextField();
        JTextField txtNombre = new JTextField();
        JTextField txtCantidad = new JTextField();
        JTextField txtPrecio = new JTextField();
        JTextField txtCategoria = new JTextField();

        panel.add(new JLabel("Código:"));
        panel.add(txtCodigo);
        panel.add(new JLabel("Nombre:"));
        panel.add(txtNombre);
        panel.add(new JLabel("Cantidad:"));
        panel.add(txtCantidad);
        panel.add(new JLabel("Precio:"));
        panel.add(txtPrecio);
        panel.add(new JLabel("Categoría:"));
        panel.add(txtCategoria);

        int resultado = JOptionPane.showConfirmDialog(
                this,
                panel,
                "Nuevo Producto",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if (resultado == JOptionPane.OK_OPTION) {
            try {
                String codigo = txtCodigo.getText().trim();
                String nombre = txtNombre.getText().trim();
                int cantidad = Integer.parseInt(txtCantidad.getText().trim());
                double precio = Double.parseDouble(txtPrecio.getText().trim());
                String categoria = txtCategoria.getText().trim();

                if (productoController.existeProducto(codigo)) {
                    JOptionPane.showMessageDialog(
                            this,
                            "El código ya existe",
                            "Error",
                            JOptionPane.ERROR_MESSAGE
                    );
                    return;
                }

                if (productoController.registrarProducto(codigo, nombre, cantidad, precio, categoria)) {
                    JOptionPane.showMessageDialog(
                            this,
                            "Producto agregado exitosamente",
                            "Éxito",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                    actualizarListaProductos();
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(
                        this,
                        "Datos numéricos inválidos",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }

    private void editarProducto() {
        String seleccionado = listaProductos.getSelectedValue();
        if (seleccionado == null) {
            return;
        }

        // Extraer código del producto seleccionado
        String codigo = seleccionado.split(",")[0].split(":")[1].trim();

        try {
            Producto producto = productoController.buscarProducto(codigo);
            if (producto == null) {
                JOptionPane.showMessageDialog(
                        this,
                        "Producto no encontrado",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            JPanel panel = new JPanel(new GridLayout(5, 2, 5, 5));

            JTextField txtCodigo = new JTextField(producto.getCodigo());
            txtCodigo.setEditable(false);
            JTextField txtNombre = new JTextField(producto.getNombre());
            JTextField txtCantidad = new JTextField(String.valueOf(producto.getCantidad()));
            JTextField txtPrecio = new JTextField(String.valueOf(producto.getPrecio()));
            JTextField txtCategoria = new JTextField(producto.getCategoria());

            panel.add(new JLabel("Código:"));
            panel.add(txtCodigo);
            panel.add(new JLabel("Nombre:"));
            panel.add(txtNombre);
            panel.add(new JLabel("Cantidad:"));
            panel.add(txtCantidad);
            panel.add(new JLabel("Precio:"));
            panel.add(txtPrecio);
            panel.add(new JLabel("Categoría:"));
            panel.add(txtCategoria);

            int resultado = JOptionPane.showConfirmDialog(
                    this,
                    panel,
                    "Editar Producto",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.PLAIN_MESSAGE
            );

            if (resultado == JOptionPane.OK_OPTION) {
                producto.setNombre(txtNombre.getText().trim());
                producto.setCantidad(Integer.parseInt(txtCantidad.getText().trim()));
                producto.setPrecio(Double.parseDouble(txtPrecio.getText().trim()));
                producto.setCategoria(txtCategoria.getText().trim());

                if (productoController.actualizarProducto(producto)) {
                    JOptionPane.showMessageDialog(
                            this,
                            "Producto actualizado",
                            "Éxito",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                    actualizarListaProductos();
                }
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Datos numéricos inválidos",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void eliminarProducto() {
        String seleccionado = listaProductos.getSelectedValue();
        if (seleccionado == null) {
            JOptionPane.showMessageDialog(
                    this,
                    "Seleccione un producto",
                    "Advertencia",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        // Extraer código del producto seleccionado
        String codigo = seleccionado.split(",")[0].split(":")[1].trim();

        int confirmacion = JOptionPane.showConfirmDialog(
                this,
                "¿Eliminar este producto?",
                "Confirmar",
                JOptionPane.YES_NO_OPTION
        );

        if (confirmacion == JOptionPane.YES_OPTION) {
            if (productoController.eliminarProducto(codigo)) {
                JOptionPane.showMessageDialog(
                        this,
                        "Producto eliminado",
                        "Éxito",
                        JOptionPane.INFORMATION_MESSAGE
                );
                actualizarListaProductos();
            }
        }
    }

    private void actualizarListaProductos() {
        modeloLista.clear();
        List<Producto> productos = productoController.obtenerTodosProductos();
        for (Producto producto : productos) {
            modeloLista.addElement(String.format(
                    "Código: %s, Nombre: %s, Cantidad: %d, Precio: %.2f, Categoría: %s",
                    producto.getCodigo(),
                    producto.getNombre(),
                    producto.getCantidad(),
                    producto.getPrecio(),
                    producto.getCategoria()
            ));
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new EditarProductos().setVisible(true);
        });
    }
}