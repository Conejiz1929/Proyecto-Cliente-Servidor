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
import controller.PedidoController;
import javax.swing.*;

public class PantallaColaborador extends JFrame {

    private Usuario usuario;
    private PedidoController pedidoController;

    public PantallaColaborador(Usuario usuario) {
        this.usuario = usuario;
        this.pedidoController = new PedidoController();

        configurarVentana();
        initUI();
    }

    private void configurarVentana() {
        setTitle("Panel de Colaborador - " + usuario.getNombre());
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private void initUI() {
        JTabbedPane tabbedPane = new JTabbedPane();

        // Gestión de Pedidos
        JPanel panelPedidos = new JPanel();
        panelPedidos.add(new JLabel("Gestión de Pedidos"));
        // Tabla de pedidos con opción de cambiar estado

        // Soporte al Cliente
        JPanel panelSoporte = new JPanel();
        panelSoporte.add(new JLabel("Soporte al Cliente"));
        // Sistema de tickets o consultas

        // Tareas
        JPanel panelTareas = new JPanel();
        panelTareas.add(new JLabel("Mis Tareas Asignadas"));
        // Lista de tareas con prioridad

        tabbedPane.addTab("Pedidos", panelPedidos);
        tabbedPane.addTab("Soporte", panelSoporte);
        tabbedPane.addTab("Tareas", panelTareas);

        add(tabbedPane);
    }
}
