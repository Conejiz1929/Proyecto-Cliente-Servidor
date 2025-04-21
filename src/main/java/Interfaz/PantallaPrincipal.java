package Interfaz;

import Login_Registro.Login;
import Producto.Carrito;
import controller.PedidoController;
import modelo.Usuario;
import controller.ProductoController;
import controller.UsuarioController;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.EmptyBorder;

/**
 * Pantalla principal para la tienda en línea. Incluye secciones para productos,
 * carrito de compras y perfil del usuario. Ahora también incluye un botón para
 * regresar al login.
 *
 * Autor: Conej
 */
public class PantallaPrincipal extends JFrame {

    private Usuario usuario;
    private ProductoController productoController;
    private PedidoController pedidoController;
    private Carrito carrito;
    private Font comicSans;
    private UsuarioController usuarioController;

    public PantallaPrincipal(Usuario usuario, UsuarioController usuarioController) {
        this.usuario = usuario;
        this.usuarioController = usuarioController; // Guardar instancia del controlador
        this.productoController = new ProductoController();
        this.pedidoController = new PedidoController();
        this.carrito = new Carrito();

        // Cargar Comic Sans o usar SansSerif como fallback
        try {
            comicSans = new Font("Comic Sans MS", Font.PLAIN, 12);
        } catch (Exception e) {
            comicSans = new Font(Font.SANS_SERIF, Font.PLAIN, 12);
        }

        configurarVentana();
        initUI();
    }

    private void configurarVentana() {
        setTitle("Tienda Online - " + usuario.getNombre());
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private void initUI() {
        // Panel principal con BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Panel para los botones en la parte superior
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 15));
        buttonPanel.setBackground(new Color(255, 215, 0)); // Dorado
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Botones con Comic Sans y estilo mejorado
        JButton btnProductos = createStyledButton("PRODUCTOS", new Color(46, 204, 113));
        JButton btnCarrito = createStyledButton("CARRITO", new Color(52, 152, 219));
        JButton btnPerfil = createStyledButton("MI PERFIL", new Color(155, 89, 182));
        JButton btnCerrarSesion = createStyledButton("CERRAR SESIÓN", new Color(231, 76, 60)); // Botón de cerrar sesión

        buttonPanel.add(btnProductos);
        buttonPanel.add(btnCarrito);
        buttonPanel.add(btnPerfil);
        buttonPanel.add(btnCerrarSesion); // Añadir botón de cerrar sesión

        // Panel de bienvenida
        JPanel welcomePanel = new JPanel(new BorderLayout());
        welcomePanel.setBackground(Color.WHITE);

        JLabel welcomeLabel = new JLabel("¡BIENVENIDO, " + usuario.getNombre().toUpperCase() + "!", SwingConstants.CENTER);
        welcomeLabel.setFont(comicSans.deriveFont(Font.BOLD, 28));
        welcomeLabel.setForeground(Color.BLACK);
        welcomeLabel.setBorder(new EmptyBorder(20, 0, 20, 0));

        welcomePanel.add(welcomeLabel, BorderLayout.CENTER);

        // Panel de contenido central
        JPanel centerPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        centerPanel.setBackground(Color.WHITE);
        centerPanel.setBorder(new EmptyBorder(30, 100, 30, 100));

        // Paneles de características
        centerPanel.add(createFeaturePanel("📦", "EXPLORA PRODUCTOS", "Descubre todo lo que tenemos para ofrecerte"));
        centerPanel.add(createFeaturePanel("🛒", "TU CARRITO", "Revisa y gestiona tus productos seleccionados"));
        centerPanel.add(createFeaturePanel("👤", "TU PERFIL", "Actualiza tu información personal"));

        // Panel de contenido principal
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.add(welcomePanel, BorderLayout.NORTH);
        contentPanel.add(centerPanel, BorderLayout.CENTER);
        contentPanel.setBackground(Color.WHITE);

        // Acciones de los botones
        btnProductos.addActionListener(e -> {
            VerProductos verProductos = new VerProductos(productoController, carrito);
            verProductos.setVisible(true);
        });

        btnCarrito.addActionListener(e -> {
            VerCarrito verCarrito = new VerCarrito(carrito);
            verCarrito.setVisible(true);
        });

        btnPerfil.addActionListener(e -> {
            VerPerfil verPerfil = new VerPerfil(usuario,usuarioController);
            verPerfil.setVisible(true);
        });

        btnCerrarSesion.addActionListener(e -> {
            // Acción para cerrar sesión
            int confirm = JOptionPane.showConfirmDialog(this,
                    "¿Estás seguro de que deseas cerrar sesión?",
                    "Confirmar Cierre de Sesión",
                    JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                dispose(); // Cierra la ventana actual
                Login login = new Login(usuarioController); // Abrir login con el controlador
                login.setVisible(true);
            }
        });

        // Añadir componentes al panel principal
        mainPanel.add(buttonPanel, BorderLayout.NORTH);
        mainPanel.add(contentPanel, BorderLayout.CENTER);

        add(mainPanel);
    }

    private JPanel createFeaturePanel(String icon, String title, String description) {
        JPanel panel = new JPanel(new BorderLayout(10, 5));
        panel.setBackground(new Color(240, 240, 240));
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 2),
                new EmptyBorder(20, 20, 20, 20)
        ));

        JLabel iconLabel = new JLabel(icon, SwingConstants.CENTER);
        iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 36));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(comicSans.deriveFont(Font.BOLD, 20));
        titleLabel.setForeground(Color.BLACK);

        JLabel descLabel = new JLabel(description);
        descLabel.setFont(comicSans.deriveFont(Font.PLAIN, 16));
        descLabel.setForeground(Color.BLACK);

        JPanel textPanel = new JPanel(new GridLayout(2, 1, 0, 5));
        textPanel.setBackground(new Color(240, 240, 240));
        textPanel.add(titleLabel);
        textPanel.add(descLabel);

        panel.add(iconLabel, BorderLayout.WEST);
        panel.add(textPanel, BorderLayout.CENTER);

        return panel;
    }

    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(comicSans.deriveFont(Font.BOLD, 18));
        button.setBackground(bgColor);
        button.setForeground(Color.BLACK); // Texto en negro
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.BLACK, 2),
                BorderFactory.createEmptyBorder(10, 30, 10, 30)
        ));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Efecto hover
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor.brighter());
                button.setForeground(Color.WHITE);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor);
                button.setForeground(Color.BLACK);
            }
        });

        return button;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UsuarioController usuarioController = new UsuarioController(); // Instancia del controlador
                Usuario usuarioEjemplo = new Usuario(
                        "USR-001",
                        "Juan Pérez",
                        "juan@example.com",
                        "password123",
                        "CLIENTE"
                );

                PantallaPrincipal pantalla = new PantallaPrincipal(usuarioEjemplo, usuarioController);
                pantalla.setVisible(true);

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null,
                        "Error: " + e.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        });
    }
}
