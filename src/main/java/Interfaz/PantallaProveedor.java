/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Interfaz;

/**
 *
 * @author José Sequeira
 */


import modelo.Usuario;
import controller.ProductoController;

import javax.swing.*;
import java.awt.*;

public class PantallaProveedor extends JFrame {
    private Usuario usuario;
    private ProductoController productoController;

    public PantallaProveedor(Usuario usuario) {
        this.usuario = usuario;
        this.productoController = new ProductoController();
        
        configurarVentana();
        initUI();
    }

    private void configurarVentana() {
        setTitle("Panel de Proveedor - " + usuario.getNombre());
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private void initUI() {
        JTabbedPane tabbedPane = new JTabbedPane();
        
        // Mis Productos
        JPanel panelProductos = new JPanel();
        panelProductos.add(new JLabel("Mis Productos Suministrados"));
        // Lista de productos que suministra con opción de actualizar stock
        
        // Estadísticas
        JPanel panelStats = new JPanel();
        panelStats.add(new JLabel("Estadísticas de Ventas"));
        // Gráficos de ventas por producto
        
        // Pedidos
        JPanel panelPedidos = new JPanel();
        panelPedidos.add(new JLabel("Pedidos a Despachar"));
        // Lista de pedidos que incluyen sus productos
        
        tabbedPane.addTab("Mis Productos", panelProductos);
        tabbedPane.addTab("Estadísticas", panelStats);
        tabbedPane.addTab("Pedidos", panelPedidos);
        
        add(tabbedPane);
    }
}
