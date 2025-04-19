/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Login_Registro;

/**
 *
 * @author Conej
 */
import controller.UsuarioController;
import dao.UsuarioDAO;
import modelo.Administrador;
import modelo.Usuario;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

class Registro extends JFrame {

    private JTextField nombreField;
    private JTextField emailField;
    private JTextField idField;
    private JComboBox rolField;
    private JPasswordField passwordField;
    private JButton registrarButton;
    private GestorUsuarios gestorUsuarios;
    private JCheckBox esAdministrador;

    public Registro(UsuarioController gestorUsuarios) {

        setTitle("Registro de Usuario");
        setSize(300, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(null);

        JLabel nombreLabel = new JLabel("Nombre:");
        nombreLabel.setBounds(10, 20, 80, 25);
        panel.add(nombreLabel);

        nombreField = new JTextField(20);
        nombreField.setBounds(100, 20, 160, 25);
        panel.add(nombreField);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setBounds(10, 50, 80, 25);
        panel.add(emailLabel);

        emailField = new JTextField(20);
        emailField.setBounds(100, 50, 160, 25);
        panel.add(emailField);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(10, 80, 80, 25);
        panel.add(passwordLabel);

        passwordField = new JPasswordField(20);
        passwordField.setBounds(100, 80, 160, 25);
        panel.add(passwordField);

        esAdministrador = new JCheckBox("Registrar como Administrador");
        esAdministrador.setBounds(10, 110, 250, 25);
        panel.add(esAdministrador);

        registrarButton = new JButton("Registrar");
        registrarButton.setBounds(10, 140, 250, 25);
        panel.add(registrarButton);

        registrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = idField.getText();
                String nombre = nombreField.getText();
                String email = emailField.getText();
                String password = new String(passwordField.getPassword());
                String rol = (String) rolField.getSelectedItem(); // Obtener el rol seleccionado del JComboBox

                if (nombre.isEmpty() || email.isEmpty() || password.isEmpty() || rol == null) {
                    JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios.");
                    return;
                }

                if (esAdministrador.isSelected()) {
                    Administrador nuevoAdmin = new Administrador(id, nombre, email, password, rol); // Crear nuevo administrador
                    try {
                        UsuarioDAO.registrarAdministrador(nuevoAdmin);
                    } catch (SQLException ex) {
                        Logger.getLogger(Registro.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    Usuario nuevoUsuario = new Usuario(id, nombre, email, password, rol); // Crear nuevo usuario
                    try {
                        UsuarioDAO.registrarUsuario(nuevoUsuario);
                    } catch (SQLException ex) {
                        Logger.getLogger(Registro.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

                JOptionPane.showMessageDialog(null, "Usuario registrado exitosamente!");
                dispose();
            }
        });

        add(panel);
    }
}
