/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Usuarios_Programa;

/**
 *
 * @author Conej
 */
public class Cliente {

    private String id;
    private String nombre;
    private String email;
    private String password;
    private boolean esFrecuente;
    private int puntosLealtad;

    public Cliente(String id, String nombre, String email, String password, boolean esFrecuente) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.password = password;
        this.esFrecuente = esFrecuente;
        this.puntosLealtad = 0;
    }

    // Métodos para manejar puntos de lealtad
    public int getPuntosLealtad() {
        return puntosLealtad;
    }

    public void agregarPuntosLealtad(int puntos) {
        this.puntosLealtad += puntos;
    }

    public void canjearPuntosLealtad(int puntos) {
        if (this.puntosLealtad >= puntos) {
            this.puntosLealtad -= puntos;
        }
    }

    // Getters y setters
    // ...
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEsFrecuente() {
        return esFrecuente;
    }

    public void setEsFrecuente(boolean esFrecuente) {
        this.esFrecuente = esFrecuente;
    }
}
