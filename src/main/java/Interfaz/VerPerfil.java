package Interfaz;

import controller.UsuarioController;
import modelo.Usuario;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.EmptyBorder;

/**
 * Clase para mostrar y editar el perfil del usuario con diseño mejorado
 */
public class VerPerfil extends JFrame {

    private Usuario usuario;
    private UsuarioController usuarioController;

    private JTextField txtNombre;
    private JTextField txtEmail;
    private JPasswordField txtPassword;
    private JComboBox<String> cbRol;

    public VerPerfil(Usuario usuario, UsuarioController usuarioController) {
        this.usuario = usuario;
        this.usuarioController = usuarioController;

        configurarVentana();
        initUI();
    }

    private void configurarVentana() {
        setTitle("Mi Perfil - " + usuario.getNombre());
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
    }

    private void initUI() {
        // Panel superior (encabezado)
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(255, 215, 0)); // Fondo dorado

        JLabel titleLabel = new JLabel("MI PERFIL", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 24));
        titleLabel.setForeground(Color.BLACK);
        titleLabel.setBorder(new EmptyBorder(10, 10, 10, 10));
        headerPanel.add(titleLabel, BorderLayout.CENTER);

        // Panel de formulario para mostrar y editar información
        JPanel formPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        formPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        formPanel.setBackground(Color.WHITE);

        txtNombre = new JTextField(usuario.getNombre());
        txtEmail = new JTextField(usuario.getEmail());
        txtPassword = new JPasswordField(usuario.getPassword());

        formPanel.add(createLabel("👤 Nombre:"));
        formPanel.add(txtNombre);
        formPanel.add(createLabel("✉️ Email:"));
        formPanel.add(txtEmail);
        formPanel.add(createLabel("🔑 Contraseña:"));
        formPanel.add(txtPassword);

        // Panel de botones
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBackground(Color.WHITE);

        JButton guardarButton = createStyledButton("Guardar Cambios", new Color(46, 204, 113)); // Verde
        guardarButton.addActionListener(e -> guardarCambios());

        JButton regresarButton = createStyledButton("Regresar", new Color(231, 76, 60)); // Rojo
        regresarButton.addActionListener(e -> dispose());

        buttonPanel.add(guardarButton);
        buttonPanel.add(regresarButton);

        // Agregar componentes a la ventana principal
        add(headerPanel, BorderLayout.NORTH);
        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
        label.setForeground(new Color(70, 70, 70));
        return label;
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

    private void guardarCambios() {
        String nuevoNombre = txtNombre.getText().trim();
        String nuevoEmail = txtEmail.getText().trim();
        String nuevaPassword = new String(txtPassword.getPassword());

        // Validar campos vacíos
        if (nuevoNombre.isEmpty() || nuevoEmail.isEmpty() || nuevaPassword.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Por favor, completa todos los campos.",
                    "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Actualizar los datos del usuario
        usuario.setNombre(nuevoNombre);
        usuario.setEmail(nuevoEmail);
        usuario.setPassword(nuevaPassword);

        boolean actualizado = usuarioController.actualizarUsuario(usuario);

        if (actualizado) {
            JOptionPane.showMessageDialog(this,
                    "Los cambios han sido guardados con éxito.",
                    "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this,
                    "Error al guardar los cambios. Inténtalo nuevamente.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            UsuarioController usuarioController = new UsuarioController();
            Usuario usuarioEjemplo = new Usuario("USR-001", "Juan Pérez", "juan.perez@example.com", "1234", "USUARIO");
            new VerPerfil(usuarioEjemplo, usuarioController).setVisible(true);
        });
    }
}
