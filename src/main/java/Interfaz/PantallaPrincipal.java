package Interfaz;

import controller.PedidoController;
import modelo.Usuario;
import controller.ProductoController;
import javax.swing.*;
import java.awt.*;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author Conej
 */
public class PantallaPrincipal extends JFrame {

    private Usuario usuario;
    private ProductoController productoController;
    private PedidoController pedidoController;

    public PantallaPrincipal(Usuario usuario) {
        this.usuario = usuario;
        this.productoController = new ProductoController();
        this.pedidoController = new PedidoController();

        configurarVentana();
        initUI();
    }

    private void configurarVentana() {
        setTitle("Tienda Online - Cliente: " + usuario.getNombre());
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private void initUI() {
        JTabbedPane tabbedPane = new JTabbedPane();

        // Pestaña de Productos
        JPanel panelProductos = new JPanel(new BorderLayout());
        panelProductos.add(new JLabel("Catálogo de Productos", SwingConstants.CENTER), BorderLayout.NORTH);
        // Aquí iría la lista de productos con capacidad de filtrado

        // Pestaña de Carrito
        JPanel panelCarrito = new JPanel();
        panelCarrito.add(new JLabel("Mi Carrito de Compras"));
        // Mostrar items del carrito y total

        // Pestaña de Pedidos
        JPanel panelPedidos = new JPanel();
        panelPedidos.add(new JLabel("Mis Pedidos Anteriores"));
        // Historial de pedidos

        // Pestaña de Perfil
        JPanel panelPerfil = new JPanel();
        panelPerfil.add(new JLabel("Mi Perfil"));
        // Información del usuario y opciones

        tabbedPane.addTab("Productos", panelProductos);
        tabbedPane.addTab("Carrito", panelCarrito);
        tabbedPane.addTab("Mis Pedidos", panelPedidos);
        tabbedPane.addTab("Mi Perfil", panelPerfil);

        add(tabbedPane);
    }
}
