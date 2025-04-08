/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package interfaz;

import java.awt.Image;
import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author José Sequeira
 */
public class registroAnimales extends javax.swing.JFrame {

    private File imagenSeleccionada;

    public registroAnimales() {
        initComponents();
    }

    private void initComponents() {
        setTitle("Registro de Animal");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 600);
        setLayout(null);

        JLabel lblIdentificacion = new JLabel("Identificacion:");
        lblIdentificacion.setBounds(30, 30, 100, 20);
        add(lblIdentificacion);

        JTextField txtIdentificacion = new JTextField();
        txtIdentificacion.setBounds(150, 30, 200, 25);
        add(txtIdentificacion);

        JLabel lblRaza = new JLabel("Raza:");
        lblRaza.setBounds(30, 70, 100, 20);
        add(lblRaza);

        JTextField txtRaza = new JTextField();
        txtRaza.setBounds(150, 70, 200, 25);
        add(txtRaza);

        JLabel lblSexo = new JLabel("Sexo:");
        lblSexo.setBounds(30, 110, 100, 20);
        add(lblSexo);

        JComboBox<String> cmbSexo = new JComboBox<>(new String[]{"Macho", "Hembra"});
        cmbSexo.setBounds(150, 110, 200, 25);
        add(cmbSexo);

        JLabel lblFechaNacimiento = new JLabel("Fecha Nac. (YYYY-MM-DD):");
        lblFechaNacimiento.setBounds(30, 150, 180, 20);
        add(lblFechaNacimiento);

        JTextField txtFechaNac = new JTextField();
        txtFechaNac.setBounds(220, 150, 130, 25);
        add(txtFechaNac);

        JLabel lblPeso = new JLabel("Peso Nac:");
        lblPeso.setBounds(30, 190, 100, 20);
        add(lblPeso);

        JTextField txtPeso = new JTextField();
        txtPeso.setBounds(150, 190, 200, 25);
        add(txtPeso);

        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setBounds(30, 230, 100, 20);
        add(lblNombre);

        JTextField txtNombre = new JTextField();
        txtNombre.setBounds(150, 230, 200, 25);
        add(txtNombre);

        JLabel lblColor = new JLabel("Color Pelaje:");
        lblColor.setBounds(30, 270, 100, 20);
        add(lblColor);

        JTextField txtColor = new JTextField();
        txtColor.setBounds(150, 270, 200, 25);
        add(txtColor);

        JLabel lblNotas = new JLabel("Notas:");
        lblNotas.setBounds(30, 310, 100, 20);
        add(lblNotas);

        JTextArea txtNotas = new JTextArea();
        JScrollPane scrollNotas = new JScrollPane(txtNotas);
        scrollNotas.setBounds(150, 310, 200, 60);
        add(scrollNotas);

        JCheckBox chkEsComprado = new JCheckBox("Es Comprado");
        chkEsComprado.setBounds(150, 380, 200, 25);
        add(chkEsComprado);

        JLabel lblFechaCompra = new JLabel("Fecha Compra:");
        lblFechaCompra.setBounds(30, 420, 100, 20);
        add(lblFechaCompra);

        JTextField txtFechaCompra = new JTextField();
        txtFechaCompra.setBounds(150, 420, 200, 25);
        add(txtFechaCompra);

        JLabel lblHatoOrigen = new JLabel("Hato Origen:");
        lblHatoOrigen.setBounds(30, 460, 100, 20);
        add(lblHatoOrigen);

        JTextField txtHato = new JTextField();
        txtHato.setBounds(150, 460, 200, 25);
        add(txtHato);

        JButton btnFoto = new JButton("Seleccionar Foto");
        btnFoto.setBounds(150, 500, 150, 25);
        add(btnFoto);

        btnFoto.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            chooser.setFileFilter(new FileNameExtensionFilter("Imágenes", "jpg", "png", "jpeg"));
            int result = chooser.showOpenDialog(null);
            if (result == JFileChooser.APPROVE_OPTION) {
                imagenSeleccionada = chooser.getSelectedFile();
            }
        });

        JButton btnGuardar = new JButton("Guardar");
        btnGuardar.setBounds(320, 500, 100, 25);
        add(btnGuardar);

        btnGuardar.addActionListener(e -> {
            try {
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/rancho_ganadero", "root", "");
                String query = "INSERT INTO animal (identificacion, raza, sexo, fecha_nacimiento, peso_nacimiento, nombre, color_pelaje, notas, es_comprado, fecha_compra, hato_origen, fotografia) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

                PreparedStatement ps = conn.prepareStatement(query);
                ps.setString(1, txtIdentificacion.getText());
                ps.setString(2, txtRaza.getText());
                ps.setString(3, cmbSexo.getSelectedItem().toString());
                ps.setString(4, txtFechaNac.getText());
                ps.setDouble(5, Double.parseDouble(txtPeso.getText()));
                ps.setString(6, txtNombre.getText());
                ps.setString(7, txtColor.getText());
                ps.setString(8, txtNotas.getText());
                ps.setBoolean(9, chkEsComprado.isSelected());
                ps.setString(10, txtFechaCompra.getText());
                ps.setString(11, txtHato.getText());

                if (imagenSeleccionada != null) {
                    FileInputStream fis = new FileInputStream(imagenSeleccionada);
                    ps.setBinaryStream(12, fis, (int) imagenSeleccionada.length());
                } else {
                    ps.setNull(12, java.sql.Types.BLOB);
                }

                ps.executeUpdate();
                JOptionPane.showMessageDialog(null, "Animal registrado con éxito");

                conn.close();
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
            }
        });

        setVisible(true);
    }
}
