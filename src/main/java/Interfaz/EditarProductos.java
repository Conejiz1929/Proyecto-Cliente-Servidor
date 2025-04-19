package Interfaz;


import controller.ProductoController;
import javax.swing.*;
import java.awt.*;
import java.util.List;
import modelo.Producto;

public class EditarProductos extends JFrame {
    private ProductoController productoController;
    private JList<String> listaProductos;
    private DefaultListModel<String> modeloLista;
    private JButton btnEditar;

    public EditarProductos() {
        // Inicializar controlador
        this.productoController = new ProductoController();
        
        // Configuración básica del JFrame
        setTitle("Gestión de Productos");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Panel principal con BorderLayout
        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Panel de lista de productos
        modeloLista = new DefaultListModel<>();
        listaProductos = new JList<>(modeloLista);
        listaProductos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(listaProductos);
        panelPrincipal.add(scrollPane, BorderLayout.CENTER);

        // Panel de botones
        JPanel panelBotones = new JPanel(new GridLayout(1, 4, 5, 5));
        
        JButton btnAgregar = new JButton("Agregar");
        btnAgregar.addActionListener(e -> agregarProducto());
        
        btnEditar = new JButton("Editar");
        btnEditar.setEnabled(false);
        btnEditar.addActionListener(e -> editarProducto());
        
        JButton btnEliminar = new JButton("Eliminar");
        btnEliminar.addActionListener(e -> eliminarProducto());
        
        JButton btnActualizar = new JButton("Actualizar");
        btnActualizar.addActionListener(e -> actualizarListaProductos());
        
        panelBotones.add(btnAgregar);
        panelBotones.add(btnEditar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnActualizar);
        
        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);

        // Listener para selección en la lista
        listaProductos.addListSelectionListener(e -> {
            boolean haySeleccion = !listaProductos.isSelectionEmpty();
            btnEditar.setEnabled(haySeleccion);
        });

        add(panelPrincipal);
        actualizarListaProductos();
    }

    private void agregarProducto() {
        JPanel panel = new JPanel(new GridLayout(5, 2, 5, 5));
        
        JTextField txtCodigo = new JTextField();
        JTextField txtNombre = new JTextField();
        JTextField txtCantidad = new JTextField();
        JTextField txtPrecio = new JTextField();
        JTextField txtCategoria = new JTextField();
        
        panel.add(new JLabel("Código:"));
        panel.add(txtCodigo);
        panel.add(new JLabel("Nombre:"));
        panel.add(txtNombre);
        panel.add(new JLabel("Cantidad:"));
        panel.add(txtCantidad);
        panel.add(new JLabel("Precio:"));
        panel.add(txtPrecio);
        panel.add(new JLabel("ID Categoría:"));
        panel.add(txtCategoria);
        
        int resultado = JOptionPane.showConfirmDialog(
            this, 
            panel, 
            "Nuevo Producto", 
            JOptionPane.OK_CANCEL_OPTION, 
            JOptionPane.PLAIN_MESSAGE
        );
        
        if (resultado == JOptionPane.OK_OPTION) {
            try {
                String codigo = txtCodigo.getText().trim();
                String nombre = txtNombre.getText().trim();
                int cantidad = Integer.parseInt(txtCantidad.getText().trim());
                double precio = Double.parseDouble(txtPrecio.getText().trim());
                int categoria = Integer.parseInt(txtCategoria.getText().trim());
                
                if (productoController.existeProducto(codigo)) {
                    JOptionPane.showMessageDialog(
                        this, 
                        "El código ya existe", 
                        "Error", 
                        JOptionPane.ERROR_MESSAGE
                    );
                    return;
                }
                
                if (productoController.registrarProducto(codigo, nombre, cantidad, precio, categoria)) {
                    JOptionPane.showMessageDialog(
                        this, 
                        "Producto agregado exitosamente", 
                        "Éxito", 
                        JOptionPane.INFORMATION_MESSAGE
                    );
                    actualizarListaProductos();
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(
                    this, 
                    "Datos numéricos inválidos", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }

    private void editarProducto() {
        String seleccionado = listaProductos.getSelectedValue();
        if (seleccionado == null) return;
        
        // Extraer código del producto seleccionado
        String codigo = seleccionado.split(",")[0].split(":")[1].trim();
        
        try {
            Producto producto = productoController.buscarProducto(codigo);
            if (producto == null) {
                JOptionPane.showMessageDialog(
                    this, 
                    "Producto no encontrado", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE
                );
                return;
            }
            
            JPanel panel = new JPanel(new GridLayout(5, 2, 5, 5));
            
            JTextField txtCodigo = new JTextField(producto.getCodigo());
            txtCodigo.setEditable(false);
            JTextField txtNombre = new JTextField(producto.getNombre());
            JTextField txtCantidad = new JTextField(String.valueOf(producto.getCantidad()));
            JTextField txtPrecio = new JTextField(String.valueOf(producto.getPrecio()));
            JTextField txtCategoria = new JTextField(producto.getCategoria());
            
            panel.add(new JLabel("Código:"));
            panel.add(txtCodigo);
            panel.add(new JLabel("Nombre:"));
            panel.add(txtNombre);
            panel.add(new JLabel("Cantidad:"));
            panel.add(txtCantidad);
            panel.add(new JLabel("Precio:"));
            panel.add(txtPrecio);
            panel.add(new JLabel("Categoría:"));
            panel.add(txtCategoria);
            
            int resultado = JOptionPane.showConfirmDialog(
                this, 
                panel, 
                "Editar Producto", 
                JOptionPane.OK_CANCEL_OPTION, 
                JOptionPane.PLAIN_MESSAGE
            );
            
            if (resultado == JOptionPane.OK_OPTION) {
                producto.setNombre(txtNombre.getText().trim());
                producto.setCantidad(Integer.parseInt(txtCantidad.getText().trim()));
                producto.setPrecio(Double.parseDouble(txtPrecio.getText().trim()));
                producto.setCategoria(txtCategoria.getText().trim());
                
                if (productoController.actualizarProducto(producto)) {
                    JOptionPane.showMessageDialog(
                        this, 
                        "Producto actualizado", 
                        "Éxito", 
                        JOptionPane.INFORMATION_MESSAGE
                    );
                    actualizarListaProductos();
                }
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(
                this, 
                "Datos numéricos inválidos", 
                "Error", 
                JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void eliminarProducto() {
        String seleccionado = listaProductos.getSelectedValue();
        if (seleccionado == null) {
            JOptionPane.showMessageDialog(
                this, 
                "Seleccione un producto", 
                "Advertencia", 
                JOptionPane.WARNING_MESSAGE
            );
            return;
        }
        
        // Extraer código del producto seleccionado
        String codigo = seleccionado.split(",")[0].split(":")[1].trim();
        
        int confirmacion = JOptionPane.showConfirmDialog(
            this, 
            "¿Eliminar este producto?", 
            "Confirmar", 
            JOptionPane.YES_NO_OPTION
        );
        
        if (confirmacion == JOptionPane.YES_OPTION) {
            if (productoController.eliminarProducto(codigo)) {
                JOptionPane.showMessageDialog(
                    this, 
                    "Producto eliminado", 
                    "Éxito", 
                    JOptionPane.INFORMATION_MESSAGE
                );
                actualizarListaProductos();
            }
        }
    }

    private void actualizarListaProductos() {
        modeloLista.clear();
        List<Producto> productos = productoController.obtenerTodosProductos();
        for (Producto producto : productos) {
            modeloLista.addElement(String.format(
                "Código: %s, Nombre: %s, Cantidad: %d, Precio: %.2f, Categoría: %s",
                producto.getCodigo(),
                producto.getNombre(),
                producto.getCantidad(),
                producto.getPrecio(),
                producto.getCategoria()
            ));
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            new EditarProductos().setVisible(true);
        });
    }
}