/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Usuarios_Programa;

/**
 *
 * @author José Sequeira
 */
import java.io.*;
import java.net.*;

public class ClienteApp {

    public static void main(String[] args) {
        String host = "localhost";
        int puerto = 12345;

        try (Socket socket = new Socket(host, puerto); BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream())); PrintWriter salida = new PrintWriter(socket.getOutputStream(), true); BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in))) {

            System.out.println(entrada.readLine()); // Mensaje de bienvenida

            // Enviar credenciales
            System.out.print("Email: ");
            String email = teclado.readLine();
            salida.println(email);

            System.out.print("Password: ");
            String password = teclado.readLine();
            salida.println(password);

            // Leer respuesta del servidor
            System.out.println(entrada.readLine());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
