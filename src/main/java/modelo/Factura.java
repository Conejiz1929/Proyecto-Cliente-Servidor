/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author José Sequeira
 */


import modelo.Producto;
import Usuarios_Programa.Cliente;
import java.util.Date;
import java.util.List;

public class Factura {
    private String id;
    private Cliente cliente;
    private List<Producto> productos;
    private double total;
    private Date fecha;

    public Factura(String id, Cliente cliente, List<Producto> productos, double total, Date fecha) {
        this.id = id;
        this.cliente = cliente;
        this.productos = productos;
        this.total = total;
        this.fecha = fecha;
    }

    // Métodos para generar y enviar factura
    public void generarFactura() {
        // Lógica para generar la factura
    }

    public void enviarFacturaPorCorreo() {
        // Lógica para enviar la factura por correo
    }

    // Getters y setters
    // ...
}
