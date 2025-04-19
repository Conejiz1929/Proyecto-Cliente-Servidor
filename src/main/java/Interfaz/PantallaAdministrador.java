/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Interfaz;

/**
 *
 * @author José Sequeira
 */
import controller.ProductoController;
import controller.UsuarioController;
import javax.swing.*;
import java.awt.*;
import modelo.Usuario;

public class PantallaAdministrador extends JFrame {

    private Usuario usuario;
    private UsuarioController usuarioController;
    private ProductoController productoController;

    public PantallaAdministrador(Usuario usuario) {
        this.usuario = usuario;
        this.usuarioController = new UsuarioController();
        this.productoController = new ProductoController();

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
        JTabbedPane tabbedPane = new JTabbedPane();

        // Dashboard
        JPanel panelDashboard = crearPanelDashboard();

        // Gestión de Usuarios
        JPanel panelUsuarios = new JPanel();
        panelUsuarios.add(new JLabel("Gestión de Usuarios"));
        // Tabla de usuarios con opciones CRUD

        // Gestión de Productos
        JPanel panelProductos = new JPanel();
        panelProductos.add(new JLabel("Gestión de Productos"));
        // Tabla de productos con opciones CRUD

        // Reportes
        JPanel panelReportes = new JPanel();
        panelReportes.add(new JLabel("Reportes del Sistema"));
        // Gráficos y estadísticas

        tabbedPane.addTab("Dashboard", panelDashboard);
        tabbedPane.addTab("Usuarios", panelUsuarios);
        tabbedPane.addTab("Productos", panelProductos);
        tabbedPane.addTab("Reportes", panelReportes);

        add(tabbedPane);
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
}
