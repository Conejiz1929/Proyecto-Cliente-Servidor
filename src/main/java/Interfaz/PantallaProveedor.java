package Interfaz;

import modelo.Usuario;
import controller.ProductoController;
import modelo.Producto;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Clase para mostrar el panel del proveedor con diseño mejorado. Autor: José
 * Sequeira
 */
public class PantallaProveedor extends JFrame {

    private Usuario usuario;
    private ProductoController productoController;
    private DefaultListModel<String> listaProductosModel;
    private JList<String> listaProductos;

    public PantallaProveedor(Usuario usuario) {
        this.usuario = usuario;
        this.productoController = new ProductoController();

        configurarVentana();
        initUI();
    }

    private void configurarVentana() {
        setTitle("Panel de Proveedor - " + usuario.getNombre());
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
    }

    private void initUI() {
        // Encabezado dorado
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(255, 215, 0)); // Fondo dorado

        JLabel titleLabel = new JLabel("Panel de Proveedor", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 28));
        titleLabel.setForeground(Color.BLACK);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        headerPanel.add(titleLabel, BorderLayout.CENTER);

        JLabel usuarioLabel = new JLabel("Usuario: " + usuario.getNombre(), SwingConstants.CENTER);
        usuarioLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 16));
        usuarioLabel.setForeground(Color.BLACK);
        headerPanel.add(usuarioLabel, BorderLayout.SOUTH);

        // Pestañas principales
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Comic Sans MS", Font.PLAIN, 16));

        // Pestaña "Mis Productos"
        JPanel panelProductos = crearPanelProductos();

        // Pestaña "Estadísticas"
        JPanel panelStats = crearPanelEstadisticas();

        // Pestaña "Pedidos"
        JPanel panelPedidos = crearPanelPedidos();

        // Agregar pestañas al tabbedPane
        tabbedPane.addTab("Mis Productos", panelProductos);
        tabbedPane.addTab("Estadísticas", panelStats);
        tabbedPane.addTab("Pedidos", panelPedidos);

        // Agregar componentes a la ventana principal
        add(headerPanel, BorderLayout.NORTH);
        add(tabbedPane, BorderLayout.CENTER);
    }

    private JPanel crearPanelProductos() {
        JPanel panelProductos = new JPanel(new BorderLayout());
        panelProductos.setBackground(Color.WHITE);

        JLabel productosLabel = new JLabel("Mis Productos Suministrados", SwingConstants.CENTER);
        productosLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
        panelProductos.add(productosLabel, BorderLayout.NORTH);

        // Lista de productos
        listaProductosModel = new DefaultListModel<>();
        listaProductos = new JList<>(listaProductosModel);
        listaProductos.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
        JScrollPane scrollProductos = new JScrollPane(listaProductos);
        panelProductos.add(scrollProductos, BorderLayout.CENTER);

        // Botones para gestionar productos
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.setBackground(Color.WHITE);

        JButton btnActualizar = createStyledButton("Actualizar Lista", new Color(46, 204, 113)); // Verde
        btnActualizar.addActionListener(e -> cargarListaProductos());

        JButton btnAgregar = createStyledButton("Agregar Producto", new Color(52, 152, 219)); // Azul
        btnAgregar.addActionListener(e -> agregarProducto());

        JButton btnEditar = createStyledButton("Editar Producto", new Color(241, 196, 15)); // Amarillo
        btnEditar.addActionListener(e -> editarProducto());

        buttonPanel.add(btnActualizar);
        buttonPanel.add(btnAgregar);
        buttonPanel.add(btnEditar);

        panelProductos.add(buttonPanel, BorderLayout.SOUTH);

        cargarListaProductos();
        return panelProductos;
    }

    private JPanel crearPanelEstadisticas() {
        JPanel panelStats = new JPanel(new BorderLayout());
        panelStats.setBackground(Color.WHITE);

        JLabel statsLabel = new JLabel("Estadísticas de Ventas", SwingConstants.CENTER);
        statsLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
        panelStats.add(statsLabel, BorderLayout.NORTH);

        // Aquí puedes agregar gráficos de estadísticas
        JTextArea statsArea = new JTextArea("Gráficos de ventas por producto...");
        statsArea.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
        statsArea.setEditable(false);
        JScrollPane scrollStats = new JScrollPane(statsArea);
        panelStats.add(scrollStats, BorderLayout.CENTER);

        return panelStats;
    }

    private JPanel crearPanelPedidos() {
        JPanel panelPedidos = new JPanel(new BorderLayout());
        panelPedidos.setBackground(Color.WHITE);

        JLabel pedidosLabel = new JLabel("Pedidos", SwingConstants.CENTER);
        pedidosLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
        panelPedidos.add(pedidosLabel, BorderLayout.NORTH);

        // Aquí puedes agregar una lista o tabla con los pedidos
        JTextArea pedidosArea = new JTextArea("Lista de pedidos que incluyen tus productos...");
        pedidosArea.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
        pedidosArea.setEditable(false);
        JScrollPane scrollPedidos = new JScrollPane(pedidosArea);
        panelPedidos.add(scrollPedidos, BorderLayout.CENTER);

        return panelPedidos;
    }

    private void cargarListaProductos() {
        listaProductosModel.clear();
        List<Producto> productos = productoController.obtenerTodosProductos();
        for (Producto producto : productos) {
            listaProductosModel.addElement(String.format(
                    "Código: %s | Nombre: %s | Cantidad: %d | Precio: %.2f | Categoría: %s",
                    producto.getCodigo(),
                    producto.getNombre(),
                    producto.getCantidad(),
                    producto.getPrecio(),
                    producto.getCategoria()
            ));
        }
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

        int resultado = JOptionPane.showConfirmDialog(this, panel, "Agregar Producto", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (resultado == JOptionPane.OK_OPTION) {
            try {
                String codigo = txtCodigo.getText().trim();
                String nombre = txtNombre.getText().trim();
                int cantidad = Integer.parseInt(txtCantidad.getText().trim());
                double precio = Double.parseDouble(txtPrecio.getText().trim());
                String categoria = txtCategoria.getText().trim();

                if (productoController.existeProducto(codigo)) {
                    JOptionPane.showMessageDialog(this, "El código ya existe.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (productoController.registrarProducto(codigo, nombre, cantidad, precio, categoria)) {
                    JOptionPane.showMessageDialog(this, "Producto agregado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    cargarListaProductos();
                } else {
                    JOptionPane.showMessageDialog(this, "Error al agregar el producto.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Datos numéricos inválidos.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void editarProducto() {
        String seleccionado = listaProductos.getSelectedValue();
        if (seleccionado == null) {
            JOptionPane.showMessageDialog(this, "Seleccione un producto para editar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Extraer código del producto seleccionado
        String codigo = seleccionado.split("\\|")[0].split(":")[1].trim();

        Producto producto = productoController.buscarProducto(codigo);
        if (producto == null) {
            JOptionPane.showMessageDialog(this, "Producto no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Crear formulario para editar el producto
        JPanel panel = new JPanel(new GridLayout(5, 2, 5, 5));
        JTextField txtNombre = new JTextField(producto.getNombre());
        JTextField txtCantidad = new JTextField(String.valueOf(producto.getCantidad()));
        JTextField txtPrecio = new JTextField(String.valueOf(producto.getPrecio()));
        JTextField txtCategoria = new JTextField(producto.getCategoria());

        panel.add(new JLabel("Nombre:"));
        panel.add(txtNombre);
        panel.add(new JLabel("Cantidad:"));
        panel.add(txtCantidad);
        panel.add(new JLabel("Precio:"));
        panel.add(txtPrecio);
        panel.add(new JLabel("Categoría:"));
        panel.add(txtCategoria);

        int resultado = JOptionPane.showConfirmDialog(this, panel, "Editar Producto", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (resultado == JOptionPane.OK_OPTION) {
            try {
                producto.setNombre(txtNombre.getText().trim());
                producto.setCantidad(Integer.parseInt(txtCantidad.getText().trim()));
                producto.setPrecio(Double.parseDouble(txtPrecio.getText().trim()));
                producto.setCategoria(txtCategoria.getText().trim());

                if (productoController.actualizarProducto(producto)) {
                    JOptionPane.showMessageDialog(this, "Producto actualizado con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    cargarListaProductos();
                } else {
                    JOptionPane.showMessageDialog(this, "Error al actualizar el producto.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Datos numéricos inválidos.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Usuario usuarioEjemplo = new Usuario("USR-002", "Proveedor Ejemplo", "proveedor@example.com", "securepassword123", "PROVEEDOR");
            new PantallaProveedor(usuarioEjemplo).setVisible(true);
        });
    }
}
