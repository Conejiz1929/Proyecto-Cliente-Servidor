package Interfaz;

import controller.UsuarioController;
import modelo.Usuario;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Clase para mostrar los usuarios registrados con diseño mejorado
 */
public class VerUsuarios extends JFrame {

    private UsuarioController usuarioController;
    private JList<String> listaUsuarios;
    private DefaultListModel<String> modeloLista;

    public VerUsuarios() {
        this.usuarioController = new UsuarioController();

        configurarVentana();
        initUI();
        cargarUsuarios();
    }

    private void configurarVentana() {
        setTitle("Usuarios Registrados");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
    }

    private void initUI() {
        // Panel superior (encabezado)
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(255, 215, 0)); // Fondo dorado

        JLabel titleLabel = new JLabel("Usuarios Registrados", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 24));
        titleLabel.setForeground(Color.BLACK);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        headerPanel.add(titleLabel, BorderLayout.CENTER);

        // Panel de lista de usuarios
        modeloLista = new DefaultListModel<>();
        listaUsuarios = new JList<>(modeloLista);
        listaUsuarios.setFont(new Font("Comic Sans MS", Font.PLAIN, 16));
        listaUsuarios.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPane = new JScrollPane(listaUsuarios);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        // Panel de botones
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.setBackground(Color.WHITE);

        JButton btnActualizar = createStyledButton("Actualizar Lista", new Color(46, 204, 113)); // Verde
        btnActualizar.addActionListener(e -> cargarUsuarios());

        JButton btnRegresar = createStyledButton("Regresar", new Color(231, 76, 60)); // Rojo
        btnRegresar.addActionListener(e -> dispose());

        buttonPanel.add(btnActualizar);
        buttonPanel.add(btnRegresar);

        // Agregar componentes a la ventana principal
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

    private void cargarUsuarios() {
        modeloLista.clear();
        try {
            List<Usuario> usuarios = usuarioController.obtenerTodosLosUsuarios();
            if (usuarios.isEmpty()) {
                modeloLista.addElement("No hay usuarios registrados.");
            } else {
                for (Usuario usuario : usuarios) {
                    modeloLista.addElement("ID: " + usuario.getId() + " - Nombre: " + usuario.getNombre());
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error al cargar la lista de usuarios: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new VerUsuarios().setVisible(true);
        });
    }
}
