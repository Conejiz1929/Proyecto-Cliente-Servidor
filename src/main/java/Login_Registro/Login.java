/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Login_Registro;

/**
 *
 * @author Conej
 */
import Interfaz.PantallaAdministrador;
import Interfaz.PantallaColaborador;
import Interfaz.PantallaPrincipal;
import Interfaz.PantallaProveedor;
import controller.UsuarioController;
import modelo.Usuario;
import javax.swing.*;
import java.awt.*;

public class Login extends JFrame {

    private UsuarioController usuarioController;

    private JTextField txtEmail;
    private JPasswordField txtPassword;
    private JComboBox<String> cbRoles;

    public Login(UsuarioController usuarioController) {
        this.usuarioController = usuarioController;
        configurarVentana();
        initComponentes();
    }

    private void configurarVentana() {
        setTitle("Inicio de Sesión - Sistema Multirrol");
        setSize(400, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
    }

    private void initComponentes() {
        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Panel de título
        JLabel lblTitulo = new JLabel("Inicio de Sesión", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        panelPrincipal.add(lblTitulo, BorderLayout.NORTH);

        // Panel de formulario
        JPanel panelForm = new JPanel(new GridLayout(4, 1, 10, 10));

        txtEmail = new JTextField("Correo Electrónico:");
        txtPassword = new JPasswordField("Contraseña:");
        cbRoles = new JComboBox<>(Usuario.ROLES.toArray(new String[0]));

        panelForm.add(txtEmail);
        panelForm.add(txtPassword);
        panelForm.add(crearPanelRol());

        // Panel de botones
        JPanel panelBotones = new JPanel(new GridLayout(1, 2, 10, 10));
        // Botón "Iniciar Sesión"
        JButton btnLogin = new JButton("Iniciar Sesión");
        btnLogin.setBackground(Color.decode("#4CAF50"));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.addActionListener(e -> iniciarSesion());

// Botón "Registrarse"
        JButton btnRegistro = new JButton("Registrarse");
        btnRegistro.setBackground(Color.decode("#2196F3"));
        btnRegistro.setForeground(Color.WHITE);
        btnRegistro.addActionListener(e -> {
            Registro registro = new Registro(usuarioController);
            registro.setVisible(true);
        });

        panelBotones.add(btnLogin);
        panelBotones.add(btnRegistro);

        panelPrincipal.add(panelForm, BorderLayout.CENTER);
        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);

        add(panelPrincipal);
    }

    private JPanel crearPanelRol() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JLabel("Rol:"), BorderLayout.NORTH);

        JPanel panelRol = new JPanel();
        cbRoles.setPreferredSize(new Dimension(200, 30));
        panelRol.add(cbRoles);

        panel.add(panelRol, BorderLayout.CENTER);
        return panel;
    }

    private void iniciarSesion() {
        String email = txtEmail.getText().trim();
        String password = new String(txtPassword.getPassword());
        String rol = (String) cbRoles.getSelectedItem();

        try {
            Usuario usuario = usuarioController.autenticar(email, password, rol);

            if (usuario != null) {
                abrirPantallaSegunRol(usuario);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this,
                        "Credenciales incorrectas o no tiene acceso a este rol",
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
                new PantallaPrincipal(usuario).setVisible(true);
        }
    }

    // Métodos auxiliares para crear componentes UI...
    private JTextField crearCampoTexto(String label) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JLabel(label), BorderLayout.NORTH);

        JTextField textField = new JTextField();
        textField.setPreferredSize(new Dimension(250, 30));
        panel.add(textField, BorderLayout.CENTER);

        return textField;
    }

    // ... (otros métodos auxiliares)
}
