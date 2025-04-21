package modelo;

import modelo.Usuario;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.List;

public class Factura {

    private String id;
    private Usuario usuario;
    private List<Producto> productos;
    private double total;
    private Date fecha;

    public Factura(String id, Usuario usuario, List<Producto> productos, double total, Date fecha) {
        this.id = id;
        this.usuario = usuario;
        this.productos = productos;
        this.total = total;
        this.fecha = fecha;
    }

    public String generarContenidoFactura() {
        StringBuilder contenido = new StringBuilder();
        contenido.append("Factura ID: ").append(id).append("\n");
        contenido.append("Usuario: ").append(usuario.getNombre()).append(" (").append(usuario.getEmail()).append(")\n");
        contenido.append("Fecha: ").append(fecha).append("\n\n");
        contenido.append("Productos:\n");

        for (Producto producto : productos) {
            contenido.append("- ").append(producto.getNombre())
                    .append(" | Precio: $").append(String.format("%.2f", producto.getPrecio())).append("\n");
        }

        contenido.append("\nTotal: $").append(String.format("%.2f", total)).append("\n");
        return contenido.toString();
    }

    public void generarFacturaComoTxt() {
        String contenido = generarContenidoFactura();
        String nombreArchivo = "Factura_" + id + ".txt";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nombreArchivo))) {
            writer.write(contenido);
            System.out.println("Factura generada como archivo: " + nombreArchivo);
        } catch (IOException e) {
            System.err.println("Error al generar el archivo de la factura: " + e.getMessage());
        }
    }

    // Getters y setters
    public String getId() {
        return id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public List<Producto> getProductos() {
        return productos;
    }

    public double getTotal() {
        return total;
    }

    public Date getFecha() {
        return fecha;
    }
}
