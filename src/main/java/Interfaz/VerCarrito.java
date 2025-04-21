package Interfaz;

import modelo.Producto;
import Producto.Carrito;
import javax.swing.*;
import java.awt.*;
import java.util.List;
import javax.swing.border.*;

/**
 * Clase para mostrar el carrito de compras con diseño mejorado
 */
public class VerCarrito extends JFrame {

    private JLabel totalLabel;
    private JPanel panel;
    private Carrito carrito;
    private Font fontPrincipal = new Font("Comic Sans MS", Font.PLAIN, 14);
    private Font fontTitulo = new Font("Comic Sans MS", Font.BOLD, 18);

    public VerCarrito(Carrito carrito) {
        this.carrito = carrito;
        configurarVentana();
        initUI();
    }

    private void configurarVentana() {
        setTitle("🛒 Mi Carrito de Compras");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
    }

    private void initUI() {
        // Panel superior (encabezado)
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(255, 215, 0)); // Fondo dorado

        JLabel titulo = new JLabel("Mi Carrito de Compras", SwingConstants.CENTER);
        titulo.setFont(fontTitulo);
        titulo.setForeground(Color.BLACK);
        titulo.setBorder(new EmptyBorder(10, 10, 10, 10));
        headerPanel.add(titulo, BorderLayout.CENTER);

        // Panel de productos con scroll
        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(Color.WHITE);

        // Panel para el total
        JPanel totalPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        totalPanel.setBackground(new Color(230, 240, 250));
        totalPanel.setBorder(new CompoundBorder(
                new MatteBorder(1, 0, 0, 0, new Color(200, 200, 200)),
                new EmptyBorder(10, 15, 10, 15)
        ));

        totalLabel = new JLabel("Total: $0.00");
        totalLabel.setFont(fontTitulo);
        totalLabel.setForeground(new Color(40, 40, 40));
        totalPanel.add(totalLabel);

        // Panel de botones
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
        buttonPanel.setBackground(Color.WHITE);

        JButton vaciarBtn = createButton("Vaciar Carrito", new Color(231, 76, 60)); // Rojo
        vaciarBtn.addActionListener(e -> {
            carrito.vaciarCarrito();
            actualizarListaProductos();
        });

        JButton pedidoBtn = createButton("Realizar Pedido", new Color(46, 204, 113)); // Verde
        pedidoBtn.addActionListener(e -> realizarPedido());

        JButton regresarBtn = createButton("Regresar", new Color(52, 152, 219)); // Azul
        regresarBtn.addActionListener(e -> dispose());

        buttonPanel.add(vaciarBtn);
        buttonPanel.add(pedidoBtn);
        buttonPanel.add(regresarBtn);

        // Agregar componentes
        add(headerPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(totalPanel, BorderLayout.SOUTH);
        add(buttonPanel, BorderLayout.PAGE_END);

        actualizarListaProductos();
    }

    private JButton createButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setFont(fontPrincipal);
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(new CompoundBorder(
                new LineBorder(color.darker(), 1),
                new EmptyBorder(8, 20, 8, 20)
        ));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

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

    private void actualizarListaProductos() {
        panel.removeAll();

        if (carrito.obtenerProductos().isEmpty()) {
            JLabel emptyLabel = new JLabel("Tu carrito está vacío", SwingConstants.CENTER);
            emptyLabel.setFont(fontPrincipal);
            emptyLabel.setForeground(new Color(150, 150, 150));
            emptyLabel.setBorder(new EmptyBorder(50, 0, 50, 0));
            panel.add(emptyLabel);
        } else {
            for (Producto producto : carrito.obtenerProductos()) {
                panel.add(createProductoPanel(producto));
                panel.add(Box.createRigidArea(new Dimension(0, 5)));
            }
        }

        totalLabel.setText("Total: $" + String.format("%.2f", carrito.calcularTotal()));
        panel.revalidate();
        panel.repaint();
    }

    private JPanel createProductoPanel(Producto producto) {
        JPanel productoPanel = new JPanel(new BorderLayout());
        productoPanel.setBackground(Color.WHITE);
        productoPanel.setBorder(new CompoundBorder(
                new MatteBorder(0, 0, 1, 0, new Color(230, 230, 230)),
                new EmptyBorder(10, 15, 10, 15)
        ));

        // Información del producto
        JPanel infoPanel = new JPanel(new GridLayout(0, 1, 2, 2));
        infoPanel.setBackground(Color.WHITE);

        JLabel nombreLabel = new JLabel(producto.getNombre());
        nombreLabel.setFont(fontPrincipal.deriveFont(Font.BOLD));

        JLabel detallesLabel = new JLabel(String.format("Precio: $%.2f | Cantidad: 1", producto.getPrecio()));
        detallesLabel.setFont(fontPrincipal);
        detallesLabel.setForeground(new Color(100, 100, 100));

        infoPanel.add(nombreLabel);
        infoPanel.add(detallesLabel);

        // Botón eliminar
        JButton eliminarBtn = createButton("Eliminar", new Color(231, 76, 60));
        eliminarBtn.setBackground(new Color(245, 245, 245));
        eliminarBtn.setForeground(new Color(231, 76, 60));
        eliminarBtn.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        eliminarBtn.addActionListener(e -> {
            carrito.eliminarProducto(producto);
            actualizarListaProductos();
        });

        productoPanel.add(infoPanel, BorderLayout.CENTER);
        productoPanel.add(eliminarBtn, BorderLayout.EAST);

        return productoPanel;
    }

    private void realizarPedido() {
        if (carrito.obtenerProductos().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Tu carrito está vacío. Agrega productos antes de realizar un pedido.",
                    "Carrito Vacío",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
                "<html><div style='text-align: center;'>"
                + "<p style='font-weight:bold; font-size:14px;'>¿Confirmar pedido?</p>"
                + "<p>Total: <b>$" + String.format("%.2f", carrito.calcularTotal()) + "</b></p>"
                + "<p>" + carrito.obtenerProductos().size() + " productos en el carrito</p>"
                + "</div></html>",
                "Confirmar Pedido",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);

        if (confirm == JOptionPane.YES_OPTION) {
            // Aquí iría la lógica para procesar el pedido
            JOptionPane.showMessageDialog(this,
                    "<html><div style='text-align: center;'>"
                    + "<p style='color:green; font-weight:bold;'>¡Pedido realizado con éxito!</p>"
                    + "<p>Recibirás un correo con los detalles.</p>"
                    + "</div></html>",
                    "Pedido Completado",
                    JOptionPane.INFORMATION_MESSAGE);

            carrito.vaciarCarrito();
            actualizarListaProductos();
        }
    }
}
