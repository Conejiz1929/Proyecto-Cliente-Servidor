package Login_Registro;

import Interfaz.PantallaAdministrador;
import Interfaz.PantallaColaborador;
import Interfaz.PantallaPrincipal;
import Interfaz.PantallaProveedor;
import controller.UsuarioController;
import modelo.Usuario;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.EmptyBorder;

public class Login extends JFrame {

    private UsuarioController usuarioController;

    private JTextField txtEmail;
    private JPasswordField txtPassword;
    private JComboBox<String> cbRoles;
    private Font comicSans;

    public Login(UsuarioController usuarioController) {
        this.usuarioController = usuarioController;

        // Cargar Comic Sans o usar SansSerif como fallback
        try {
            comicSans = new Font("Comic Sans MS", Font.PLAIN, 12);
        } catch (Exception e) {
            comicSans = new Font(Font.SANS_SERIF, Font.PLAIN, 12);
        }

        configurarVentana();
        initComponentes();
    }

    private void configurarVentana() {
        setTitle("Inicio de Sesión - Sistema Multirrol");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
    }

    private void initComponentes() {
        // Panel principal con diseño similar al menú principal
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Panel superior (título)
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBackground(new Color(255, 215, 0)); // Dorado
        JLabel lblTitulo = new JLabel("Inicio de Sesión", SwingConstants.CENTER);
        lblTitulo.setFont(comicSans.deriveFont(Font.BOLD, 28));
        lblTitulo.setForeground(Color.BLACK);
        lblTitulo.setBorder(new EmptyBorder(10, 10, 10, 10));
        titlePanel.add(lblTitulo, BorderLayout.CENTER);

        // Panel de formulario
        JPanel formPanel = new JPanel(new GridLayout(4, 1, 10, 10));
        formPanel.setBackground(Color.WHITE);

        txtEmail = new JTextField();
        txtPassword = new JPasswordField();
        cbRoles = new JComboBox<>(Usuario.ROLES.toArray(new String[0]));

        formPanel.add(crearCampo("Correo Electrónico:", txtEmail));
        formPanel.add(crearCampo("Contraseña:", txtPassword));
        formPanel.add(crearCampoRol("Rol:", cbRoles));

        // Panel de botones
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        buttonPanel.setBackground(Color.WHITE);

        JButton btnLogin = createStyledButton("Iniciar Sesión", new Color(46, 204, 113)); // Verde
        btnLogin.addActionListener(e -> iniciarSesion());

        JButton btnRegistro = createStyledButton("Registrarse", new Color(52, 152, 219)); // Azul
        btnRegistro.addActionListener(e -> abrirRegistro());

        buttonPanel.add(btnLogin);
        buttonPanel.add(btnRegistro);

        // Agregar todo al panel principal
        mainPanel.add(titlePanel, BorderLayout.NORTH);
        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private JPanel crearCampo(String label, JComponent field) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        JLabel lbl = new JLabel(label);
        lbl.setFont(comicSans.deriveFont(Font.PLAIN, 14));
        panel.add(lbl, BorderLayout.NORTH);
        panel.add(field, BorderLayout.CENTER);
        return panel;
    }

    private JPanel crearCampoRol(String label, JComboBox<String> comboBox) {
        return crearCampo(label, comboBox);
    }

    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(comicSans.deriveFont(Font.BOLD, 16));
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

    private void iniciarSesion() {
        String email = txtEmail.getText().trim();
        String password = new String(txtPassword.getPassword());
        String rol = (String) cbRoles.getSelectedItem();

        // Validación de campos vacíos
        if (email.isEmpty() || password.isEmpty() || rol == null) {
            JOptionPane.showMessageDialog(this,
                    "Por favor, completa todos los campos.",
                    "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            Usuario usuario = usuarioController.autenticar(email, password);

            if (usuario != null && usuario.getRol().equals(rol)) {
                abrirPantallaSegunRol(usuario);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this,
                        "Credenciales incorrectas o no tiene acceso a este rol.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Error al iniciar sesión: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void abrirPantallaSegunRol(Usuario usuario) {
        switch (usuario.getRol()) {
            case "ADMINISTRADOR":
                new PantallaAdministrador(usuario).setVisible(true);
                break;
            case "PROVEEDOR":
                new PantallaProveedor(usuario).setVisible(true);
                break;
            case "COLABORADOR":
                new PantallaColaborador(usuario).setVisible(true);
                break;
            default:
                new PantallaPrincipal(usuario, usuarioController).setVisible(true);
        }
    }

    private void abrirRegistro() {
        new Registro(usuarioController).setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UsuarioController usuarioController = new UsuarioController(); // Instancia del controlador
                Login login = new Login(usuarioController); // Crear la pantalla de login
                login.setVisible(true);
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
