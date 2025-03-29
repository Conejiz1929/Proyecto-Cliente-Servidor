/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tienda;

/**
 *
 * @author Conej
 */
import javax.swing.*;
import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;

public class Login extends JFrame {

    private JTextField emailField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton registerButton;
    private GestorUsuarios gestorUsuarios;

    public Login(GestorUsuarios gestorUsuarios) {
        this.gestorUsuarios = gestorUsuarios;
        setTitle("Inicio de Sesion");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        JPanel panel = new JPanel();
        add(panel);
        placeComponents(panel);
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = emailField.getText();
                String password = new String(passwordField.getPassword());

                if (gestorUsuarios.autenticarUsuario(email, password)) {
                    Usuario usuarioAutenticado = gestorUsuarios.obtenerUsuario(email);

                    JOptionPane.showMessageDialog(null, "Inicio de sesion exitoso!");

                    if (usuarioAutenticado instanceof Administrador) {
                        new PantallaAdministrador(gestorUsuarios).setVisible(true);
                    } else {
                        new PantallaPrincipal(usuarioAutenticado, gestorUsuarios).setVisible(true);
                    }
                    dispose();  // Cierra la ventana de Login
                } else {
                    JOptionPane.showMessageDialog(null, "Email o contraseña incorrectos.");
                }
            }
        });
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Registro registro = new Registro(gestorUsuarios);
                registro.setVisible(true);
            }
        });
    }

    private void placeComponents(JPanel panel) {
        panel.setLayout(null);
        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setBounds(10, 20, 80, 25);
        panel.add(emailLabel);
        emailField = new JTextField(20);
        emailField.setBounds(100, 20, 160, 25);
        panel.add(emailField);
        JLabel passwordLabel = new JLabel("Contraseña:");
        passwordLabel.setBounds(10, 50, 80, 25);
        panel.add(passwordLabel);
        passwordField = new JPasswordField(20);
        passwordField.setBounds(100, 50, 160, 25);
        panel.add(passwordField);
        loginButton = new JButton("Inicio de Sesion");
        loginButton.setBounds(10, 80, 250, 25);
        panel.add(loginButton);
        registerButton = new JButton("Registro");
        registerButton.setBounds(10, 110, 250, 25);
        panel.add(registerButton);
    }

    public static void main(String[] args) {
        GestorUsuarios gestorUsuarios = new GestorUsuarios();  // Crear gestorUsuarios
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Login(gestorUsuarios).setVisible(true);  // Pasar gestorUsuarios al Login
            }
        });
    }
}
