package Interfaz;

import Login_Registro.Login;
import controller.UsuarioController;
import javax.swing.*;
import java.awt.*;
import modelo.Usuario;

public class PantallaAdministrador extends JFrame {

    private Usuario usuario;
    private UsuarioController usuarioController;
    private JPanel contentPanel;

    public PantallaAdministrador(Usuario usuario) {
        this.usuario = usuario;
        this.usuarioController = new UsuarioController();

        configurarVentana();
        initUI();
    }

    private void configurarVentana() {
        setTitle("Panel de Administración - " + usuario.getNombre());
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private void initUI() {
        setLayout(new BorderLayout());

        // Panel de botones de navegación
        JPanel navigationPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
        navigationPanel.setBackground(new Color(255, 215, 0)); // Fondo dorado

        JButton btnDashboard = createStyledButton("Dashboard");
        JButton btnUsuarios = createStyledButton("Usuarios");
        JButton btnProductos = createStyledButton("Productos");
        JButton btnReportes = createStyledButton("Reportes");
        JButton btnCerrarSesion = createStyledButton("Cerrar Sesión");

        navigationPanel.add(btnDashboard);
        navigationPanel.add(btnUsuarios);
        navigationPanel.add(btnProductos);
        navigationPanel.add(btnReportes);
        navigationPanel.add(btnCerrarSesion);

        // Panel de contenido dinámico
        contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(Color.WHITE);

        // Acción de los botones
        btnDashboard.addActionListener(e -> mostrarDashboard());
        btnUsuarios.addActionListener(e -> mostrarUsuarios());
        btnProductos.addActionListener(e -> mostrarProductos());
        btnReportes.addActionListener(e -> mostrarReportes());
        btnCerrarSesion.addActionListener(e -> cerrarSesion());

        // Mostrar Dashboard por defecto
        mostrarDashboard();

        add(navigationPanel, BorderLayout.NORTH);
        add(contentPanel, BorderLayout.CENTER);
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Comic Sans MS", Font.BOLD, 16));
        button.setBackground(Color.WHITE);
        button.setForeground(Color.BLACK); // Texto en negro
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.BLACK, 2),
                BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Efecto hover
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(220, 220, 220));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(Color.WHITE);
            }
        });

        return button;
    }

    private JButton createCardButton(String text, Color bgColor, String iconPath) {
        JButton button = new JButton(text);
        button.setFont(new Font("Comic Sans MS", Font.BOLD, 18));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.BLACK, 2),
                BorderFactory.createEmptyBorder(15, 20, 15, 20)
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

        // Agregar icono si se proporciona una ruta
        if (iconPath != null) {
            ImageIcon icon = new ImageIcon(iconPath);
            button.setIcon(icon);
            button.setHorizontalTextPosition(SwingConstants.CENTER);
            button.setVerticalTextPosition(SwingConstants.BOTTOM);
        }

        return button;
    }

    private void mostrarDashboard() {
        contentPanel.removeAll();
        contentPanel.add(crearPanelDashboard(), BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private void mostrarUsuarios() {
        contentPanel.removeAll();
        contentPanel.add(crearPanelUsuarios(), BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private void mostrarProductos() {
        contentPanel.removeAll();
        contentPanel.add(crearPanelProductos(), BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private void mostrarReportes() {
        contentPanel.removeAll();
        JPanel panelReportes = new JPanel();
        panelReportes.add(new JLabel("Reportes del Sistema"));
        contentPanel.add(panelReportes, BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private void cerrarSesion() {
        int confirm = JOptionPane.showConfirmDialog(this,
                "¿Estás seguro de que deseas cerrar sesión?",
                "Confirmar Cierre de Sesión",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            dispose();
            Login login = new Login(usuarioController);
            login.setVisible(true);
        }
    }

    private JPanel crearPanelDashboard() {
        JPanel panel = new JPanel(new GridLayout(2, 2, 10, 10));

        // Tarjetas de métricas
        panel.add(crearMetrica("Total Usuarios", "125", Color.BLUE));
        panel.add(crearMetrica("Productos Activos", "342", Color.GREEN));
        panel.add(crearMetrica("Ventas Hoy", "$1,245", Color.ORANGE));
        panel.add(crearMetrica("Pedidos Pendientes", "12", Color.RED));

        return panel;
    }

    private JPanel crearMetrica(String titulo, String valor, Color color) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createLineBorder(color, 2));

        JLabel lblTitulo = new JLabel(titulo, SwingConstants.CENTER);
        JLabel lblValor = new JLabel(valor, SwingConstants.CENTER);
        lblValor.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblValor.setForeground(color);

        panel.add(lblTitulo, BorderLayout.NORTH);
        panel.add(lblValor, BorderLayout.CENTER);

        return panel;
    }

    private JPanel crearPanelUsuarios() {
        JPanel panel = new JPanel(new GridBagLayout());
        JButton btnVerUsuarios = createCardButton("Ver Usuarios Registrados", new Color(52, 152, 219), null);

        btnVerUsuarios.addActionListener(e -> {
            // Llama a la ventana para mostrar usuarios
            SwingUtilities.invokeLater(() -> new VerUsuarios().setVisible(true));
        });

        panel.add(btnVerUsuarios);
        return panel;
    }

    private JPanel crearPanelProductos() {
        JPanel panel = new JPanel(new GridBagLayout());
        JButton btnGestionarProductos = createCardButton("Gestionar Productos", new Color(46, 204, 113), null);

        btnGestionarProductos.addActionListener(e -> {
            // Llama a la ventana de edición de productos
            SwingUtilities.invokeLater(() -> new EditarProductos().setVisible(true));
        });

        panel.add(btnGestionarProductos);
        return panel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Usuario usuarioEjemplo = new Usuario(
                    "ADM-001",
                    "Administrador",
                    "admin@example.com",
                    "admin123",
                    "ADMINISTRADOR"
            );
            new PantallaAdministrador(usuarioEjemplo).setVisible(true);
        });
    }
}
