package Interfaz;

import modelo.Usuario;
import controller.PedidoController;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase para mostrar el panel del colaborador con diseño mejorado. Autor: José
 * Sequeira
 */
public class PantallaColaborador extends JFrame {

    private Usuario usuario;
    private PedidoController pedidoController;
    private DefaultListModel<String> listaTareasModel;
    private JList<String> listaTareas;
    private DefaultListModel<String> listaTicketsModel;
    private JList<String> listaTickets;

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
        setLayout(new BorderLayout());
    }

    private void initUI() {
        // Encabezado dorado
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(255, 215, 0)); // Fondo dorado

        JLabel titleLabel = new JLabel("Panel de Colaborador", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 28));
        titleLabel.setForeground(Color.BLACK);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        headerPanel.add(titleLabel, BorderLayout.CENTER);

        JLabel usuarioLabel = new JLabel("Usuario: " + usuario.getNombre(), SwingConstants.CENTER);
        usuarioLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 16));
        usuarioLabel.setForeground(Color.BLACK);
        headerPanel.add(usuarioLabel, BorderLayout.SOUTH);

        // Pestañas principales
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Comic Sans MS", Font.PLAIN, 16));

        // Pestaña "Gestión de Pedidos"
        JPanel panelPedidos = crearPanelPedidos();

        // Pestaña "Soporte al Cliente"
        JPanel panelSoporte = crearPanelSoporte();

        // Pestaña "Tareas Asignadas"
        JPanel panelTareas = crearPanelTareas();

        // Agregar pestañas al tabbedPane
        tabbedPane.addTab("Pedidos", panelPedidos);
        tabbedPane.addTab("Soporte", panelSoporte);
        tabbedPane.addTab("Tareas", panelTareas);

        // Agregar componentes a la ventana principal
        add(headerPanel, BorderLayout.NORTH);
        add(tabbedPane, BorderLayout.CENTER);
    }

    private JPanel crearPanelPedidos() {
        JPanel panelPedidos = new JPanel(new BorderLayout());
        panelPedidos.setBackground(Color.WHITE);

        JLabel pedidosLabel = new JLabel("Gestión de Pedidos", SwingConstants.CENTER);
        pedidosLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
        panelPedidos.add(pedidosLabel, BorderLayout.NORTH);

        // Área para mostrar la tabla de pedidos
        JTextArea pedidosArea = new JTextArea("Aquí se mostrará la tabla de pedidos con opciones de gestión...");
        pedidosArea.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
        pedidosArea.setEditable(false);
        JScrollPane scrollPedidos = new JScrollPane(pedidosArea);
        panelPedidos.add(scrollPedidos, BorderLayout.CENTER);

        return panelPedidos;
    }

    private JPanel crearPanelSoporte() {
        JPanel panelSoporte = new JPanel(new BorderLayout());
        panelSoporte.setBackground(Color.WHITE);

        JLabel soporteLabel = new JLabel("Soporte al Cliente", SwingConstants.CENTER);
        soporteLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
        panelSoporte.add(soporteLabel, BorderLayout.NORTH);

        // Lista de tickets de soporte
        listaTicketsModel = new DefaultListModel<>();
        listaTickets = new JList<>(listaTicketsModel);
        listaTickets.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
        JScrollPane scrollTickets = new JScrollPane(listaTickets);
        panelSoporte.add(scrollTickets, BorderLayout.CENTER);

        // Botones para gestionar tickets
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.setBackground(Color.WHITE);

        JButton btnAtenderTicket = createStyledButton("Atender Ticket", new Color(46, 204, 113)); // Verde
        btnAtenderTicket.addActionListener(e -> atenderTicket());

        JButton btnCerrarTicket = createStyledButton("Cerrar Ticket", new Color(231, 76, 60)); // Rojo
        btnCerrarTicket.addActionListener(e -> cerrarTicket());

        buttonPanel.add(btnAtenderTicket);
        buttonPanel.add(btnCerrarTicket);
        panelSoporte.add(buttonPanel, BorderLayout.SOUTH);

        cargarTicketsSoporte();
        return panelSoporte;
    }

    private JPanel crearPanelTareas() {
        JPanel panelTareas = new JPanel(new BorderLayout());
        panelTareas.setBackground(Color.WHITE);

        JLabel tareasLabel = new JLabel("Mis Tareas Asignadas", SwingConstants.CENTER);
        tareasLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
        panelTareas.add(tareasLabel, BorderLayout.NORTH);

        // Lista de tareas asignadas
        listaTareasModel = new DefaultListModel<>();
        listaTareas = new JList<>(listaTareasModel);
        listaTareas.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
        JScrollPane scrollTareas = new JScrollPane(listaTareas);
        panelTareas.add(scrollTareas, BorderLayout.CENTER);

        // Botón para marcar tarea como hecha
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.setBackground(Color.WHITE);

        JButton btnTareaHecha = createStyledButton("Tarea Hecha", new Color(46, 204, 113)); // Verde
        btnTareaHecha.addActionListener(e -> tareaHecha());

        buttonPanel.add(btnTareaHecha);
        panelTareas.add(buttonPanel, BorderLayout.SOUTH);

        cargarTareasAsignadas();
        return panelTareas;
    }

    private void cargarTareasAsignadas() {
        listaTareasModel.clear();
        List<String> tareas = obtenerTareasEjemplo(); // Simula obtener tareas asignadas
        for (String tarea : tareas) {
            listaTareasModel.addElement(tarea);
        }
    }

    private List<String> obtenerTareasEjemplo() {
        // Tareas de ejemplo
        List<String> tareas = new ArrayList<>();
        tareas.add("Revisar inventario de productos");
        tareas.add("Atender ticket de soporte #12345");
        tareas.add("Actualizar estado del pedido #67890");
        tareas.add("Generar reporte semanal de ventas");
        tareas.add("Revisar contrato con proveedor ABC");
        return tareas;
    }

    private void tareaHecha() {
        String seleccionada = listaTareas.getSelectedValue();
        if (seleccionada == null) {
            JOptionPane.showMessageDialog(this, "Seleccione una tarea para marcar como hecha.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirmacion = JOptionPane.showConfirmDialog(this, "¿Marcar esta tarea como hecha?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirmacion == JOptionPane.YES_OPTION) {
            listaTareasModel.removeElement(seleccionada);
            JOptionPane.showMessageDialog(this, "Tarea marcada como hecha.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void cargarTicketsSoporte() {
        listaTicketsModel.clear();
        List<String> tickets = obtenerTicketsEjemplo(); // Simula obtener tickets
        for (String ticket : tickets) {
            listaTicketsModel.addElement(ticket);
        }
    }

    private List<String> obtenerTicketsEjemplo() {
        // Tickets de soporte de ejemplo
        List<String> tickets = new ArrayList<>();
        tickets.add("Ticket #001 - Consulta sobre facturación");
        tickets.add("Ticket #002 - Problema con la entrega del pedido #789");
        tickets.add("Ticket #003 - Solicitud de cambio de producto");
        tickets.add("Ticket #004 - Consulta técnica sobre un producto");
        return tickets;
    }

    private void atenderTicket() {
        String seleccionado = listaTickets.getSelectedValue();
        if (seleccionado == null) {
            JOptionPane.showMessageDialog(this, "Seleccione un ticket para atender.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        JOptionPane.showMessageDialog(this, "Atendiendo: " + seleccionado, "Atención de Ticket", JOptionPane.INFORMATION_MESSAGE);
    }

    private void cerrarTicket() {
        String seleccionado = listaTickets.getSelectedValue();
        if (seleccionado == null) {
            JOptionPane.showMessageDialog(this, "Seleccione un ticket para cerrar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirmacion = JOptionPane.showConfirmDialog(this, "¿Cerrar este ticket?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirmacion == JOptionPane.YES_OPTION) {
            listaTicketsModel.removeElement(seleccionado);
            JOptionPane.showMessageDialog(this, "Ticket cerrado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        }
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Usuario usuarioEjemplo = new Usuario("USR-003", "Colaborador Ejemplo", "colaborador@example.com", "securepassword123", "COLABORADOR");
            new PantallaColaborador(usuarioEjemplo).setVisible(true);
        });
    }
}
