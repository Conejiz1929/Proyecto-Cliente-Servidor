
import javax.swing.*;
import java.awt.event.*;
import java.util.List;

public class EditarProductos extends JFrame {

    private Controlnventario controlInventario;
    private JList<String> listaProductos;
    private DefaultListModel<String> modeloLista;

    public EditarProductos() {
        controlInventario = new ControlInventario();
        setTitle("Editar Productos");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        modeloLista = new DefaultListModel<>();
        listaProductos = new JList<>(modeloLista);
        JScrollPane scrollPane = new JScrollPane(listaProductos);
        add(scrollPane);

        JButton btnAgregar = new JButton("Agregar Producto");
        btnAgregar.addActionListener(e -> agregarProducto());
        add(btnAgregar);

        JButton btnEliminar = new JButton("Eliminar Producto");
        btnEliminar.addActionListener(e -> eliminarProducto());
        add(btnEliminar);

        actualizarListaProductos();
        setVisible(true);
    }

    private void agregarProducto() {
        String codigo = JOptionPane.showInputDialog(this, "Ingrese el código del producto:");
        if (codigo == null || codigo.trim().isEmpty()) {
            return;
        }

        String nombre = JOptionPane.showInputDialog(this, "Ingrese el nombre del producto:");
        if (nombre == null || nombre.trim().isEmpty()) {
            return;
        }

        String categoriaStr = JOptionPane.showInputDialog(this, "Ingrese el ID de la categoría del producto:");
        if (categoriaStr == null || categoriaStr.trim().isEmpty()) {
            return;
        }

        String cantidadStr = JOptionPane.showInputDialog(this, "Ingrese la cantidad del producto:");
        String precioStr = JOptionPane.showInputDialog(this, "Ingrese el precio del producto:");

        try {
            int cantidad = Integer.parseInt(cantidadStr);
            double precio = Double.parseDouble(precioStr);
            int idCategoria = Integer.parseInt(categoriaStr);

            if (controlInventario.existeProducto(codigo)) {
                JOptionPane.showMessageDialog(this, "El código del producto ya existe.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            controlInventario.registrarProducto(codigo, nombre, cantidad, precio, idCategoria);
            actualizarListaProductos();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Cantidad, precio o categoría inválidos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminarProducto() {
        String codigo = JOptionPane.showInputDialog(this, "Ingrese el código del producto a eliminar:");
        if (codigo == null || codigo.trim().isEmpty()) {
            return;
        }

        controlInventario.eliminarProducto(codigo);
        actualizarListaProductos();
    }

    private void actualizarListaProductos() {
        modeloLista.clear();
        List<String> productos = controlInventario.obtenerListaProductos();
        for (String producto : productos) {
            modeloLista.addElement(producto);
        }
    }

    private static class ControlInventario {

        public ControlInventario() {

        }
    }
}
