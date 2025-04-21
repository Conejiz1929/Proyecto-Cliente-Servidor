package Interfaz;

import Producto.Carrito;
import modelo.Producto;
import controller.ProductoController;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Ventana para mostrar el catálogo de productos y permitir su gestión. Autor:
 * José Sequeira
 */
public class VerProductos extends JFrame {

    private ProductoController productoController;
    private JPanel panelProductos;
    private JTextField filtroField;
    private JComboBox<String> comboFiltro;
    private Carrito carrito; // Instancia del carrito

    public VerProductos(ProductoController controller, Carrito carrito) {
        this.productoController = controller;
        this.carrito = carrito;

        configurarVentana();
        initComponentes();
        cargarProductos(); // Cargar productos iniciales
    }

    private void configurarVentana() {
        setTitle("Catálogo de Productos");
        setSize(900, 650);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
    }

    private void initComponentes() {
        // Panel superior (título y filtros)
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBackground(new Color(255, 215, 0)); // Fondo dorado

        JLabel lblTitulo = new JLabel("Catálogo de Productos", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Comic Sans MS", Font.BOLD, 24));
        lblTitulo.setForeground(Color.BLACK);
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        titlePanel.add(lblTitulo, BorderLayout.NORTH);

        JPanel panelFiltros = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panelFiltros.setBackground(new Color(255, 215, 0)); // Mismo fondo que el título

        comboFiltro = new JComboBox<>(new String[]{"Todos", "Código", "Nombre", "Categoría"});
        filtroField = new JTextField(20);
        JButton btnFiltrar = createStyledButton("Filtrar", new Color(70, 130, 180)); // Azul

        btnFiltrar.addActionListener(e -> aplicarFiltros());

        panelFiltros.add(new JLabel("Filtrar por:"));
        panelFiltros.add(comboFiltro);
        panelFiltros.add(filtroField);
        panelFiltros.add(btnFiltrar);

        titlePanel.add(panelFiltros, BorderLayout.CENTER);

        // Panel principal de productos
        panelProductos = new JPanel();
        panelProductos.setLayout(new BoxLayout(panelProductos, BoxLayout.Y_AXIS));
        panelProductos.setBackground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(panelProductos);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        // Botón para regresar
        JButton btnRegresar = createStyledButton("Regresar", new Color(231, 76, 60)); // Rojo
        btnRegresar.addActionListener(e -> dispose());

        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        footerPanel.setBackground(Color.WHITE);
        footerPanel.add(btnRegresar);

        // Organización de la ventana
        add(titlePanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(footerPanel, BorderLayout.SOUTH);
    }

    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Comic Sans MS", Font.BOLD, 16));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.BLACK, 2),
                BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Efecto hover
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor.darker());
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor);
            }
        });

        return button;
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
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));
        panel.setBackground(Color.WHITE);
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 150));

        // Panel de información
        JPanel infoPanel = new JPanel(new GridLayout(0, 1, 5, 5));
        infoPanel.setBackground(Color.WHITE);

        JLabel codigoLabel = new JLabel("Código: " + producto.getCodigo());
        codigoLabel.setFont(new Font("Arial", Font.PLAIN, 14));

        JLabel nombreLabel = new JLabel("<html><b>" + producto.getNombre() + "</b></html>");
        nombreLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 16));
        nombreLabel.setForeground(new Color(70, 70, 70));

        JLabel precioLabel = new JLabel(String.format("Precio: $%.2f", producto.getPrecio()));
        precioLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        precioLabel.setForeground(new Color(0, 102, 204));

        JLabel stockLabel = new JLabel("Stock: " + producto.getCantidad());
        stockLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        stockLabel.setForeground(producto.getCantidad() > 0 ? new Color(0, 120, 0) : Color.RED);

        infoPanel.add(codigoLabel);
        infoPanel.add(nombreLabel);
        infoPanel.add(precioLabel);
        infoPanel.add(stockLabel);

        // Panel de botones
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(Color.WHITE);

        JButton btnCarrito = createStyledButton("Agregar al Carrito", new Color(46, 204, 113)); // Verde

        btnCarrito.addActionListener(e -> {
            carrito.agregarProducto(producto);
            JOptionPane.showMessageDialog(this,
                    "Producto agregado al carrito: " + producto.getNombre(),
                    "Éxito", JOptionPane.INFORMATION_MESSAGE);
        });

        buttonPanel.add(btnCarrito);

        panel.add(infoPanel, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

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
                ProductoController controller = new ProductoController();
                Carrito carrito = new Carrito();
                new VerProductos(controller, carrito).setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
