package Login_Registro;

import controller.UsuarioController;
import modelo.Administrador;
import modelo.Usuario;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.EmptyBorder;

public class Registro extends JFrame {

    private JTextField nombreField;
    private JTextField emailField;
    private JTextField idField;
    private JPasswordField passwordField;
    private JComboBox<String> rolField;
    private UsuarioController usuarioController;
    private Font comicSans;

    public Registro(UsuarioController usuarioController) {
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
        setTitle("Registro de Usuario");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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
        JLabel lblTitulo = new JLabel("Registro de Usuario", SwingConstants.CENTER);
        lblTitulo.setFont(comicSans.deriveFont(Font.BOLD, 28));
        lblTitulo.setForeground(Color.BLACK);
        lblTitulo.setBorder(new EmptyBorder(10, 10, 10, 10));
        titlePanel.add(lblTitulo, BorderLayout.CENTER);

        // Panel de formulario
        JPanel formPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        formPanel.setBackground(Color.WHITE);

        idField = new JTextField();
        nombreField = new JTextField();
        emailField = new JTextField();
        passwordField = new JPasswordField();
        rolField = new JComboBox<>(Usuario.ROLES.toArray(new String[0]));

        formPanel.add(createLabel("ID:"));
        formPanel.add(idField);
        formPanel.add(createLabel("Nombre:"));
        formPanel.add(nombreField);
        formPanel.add(createLabel("Email:"));
        formPanel.add(emailField);
        formPanel.add(createLabel("Contraseña:"));
        formPanel.add(passwordField);
        formPanel.add(createLabel("Rol:"));
        formPanel.add(rolField);

        // Panel de botones
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 15));
        buttonPanel.setBackground(Color.WHITE);

        JButton btnRegistrar = createStyledButton("Registrar", new Color(46, 204, 113)); // Verde
        btnRegistrar.addActionListener(e -> registrarUsuario());

        JButton btnCancelar = createStyledButton("Cancelar", new Color(231, 76, 60)); // Rojo
        btnCancelar.addActionListener(e -> dispose());

        JButton btnRegresar = createStyledButton("Regresar al Login", new Color(52, 152, 219)); // Azul
        btnRegresar.addActionListener(e -> regresarAlLogin());

        buttonPanel.add(btnRegistrar);
        buttonPanel.add(btnCancelar);
        buttonPanel.add(btnRegresar);

        // Agregar todo al panel principal
        mainPanel.add(titlePanel, BorderLayout.NORTH);
        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(comicSans.deriveFont(Font.PLAIN, 16));
        return label;
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

    private void registrarUsuario() {
        String id = idField.getText().trim();
        String nombre = nombreField.getText().trim();
        String email = emailField.getText().trim();
        String password = new String(passwordField.getPassword());
        String rol = (String) rolField.getSelectedItem();

        if (id.isEmpty() || nombre.isEmpty() || email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Todos los campos son obligatorios.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            Usuario usuario;
            if ("ADMINISTRADOR".equals(rol)) {
                usuario = new Administrador(id, nombre, email, password, rol);
            } else {
                usuario = new Usuario(id, nombre, email, password, rol);
            }

            if (usuarioController.registrarUsuario(usuario)) {
                JOptionPane.showMessageDialog(this,
                        "Usuario registrado exitosamente.",
                        "Éxito", JOptionPane.INFORMATION_MESSAGE);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this,
                        "Error al registrar el usuario.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this,
                    "Error: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void regresarAlLogin() {
        dispose(); // Cerrar la ventana actual
        Login login = new Login(usuarioController); // Volver al login
        login.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UsuarioController usuarioController = new UsuarioController(); // Instancia del controlador
                Registro registro = new Registro(usuarioController); // Crear la ventana de registro
                registro.setVisible(true);
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
