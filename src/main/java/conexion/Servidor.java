package conexion;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author José Sequeira
 */
import conexion.DatabaseConnection;
import java.io.*;
import java.net.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Servidor {

    public static void main(String[] args) {
        int puerto = 12345;

        try (ServerSocket serverSocket = new ServerSocket(puerto)) {
            System.out.println("Servidor iniciado en el puerto " + puerto);

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("Nueva conexión aceptada: " + socket.getInetAddress());
                new Thread(new ManejadorCliente(socket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class ManejadorCliente implements Runnable {

    private Socket socket;

    public ManejadorCliente(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream())); PrintWriter salida = new PrintWriter(socket.getOutputStream(), true)) {

            salida.println("Bienvenido al servidor. Por favor, autentíquese.");

            // Leer credenciales del cliente
            String email = entrada.readLine();
            String password = entrada.readLine();

            // Validar credenciales en la base de datos
            if (autenticarUsuario(email, password)) {
                salida.println("Autenticación exitosa.");
                // Aquí puedes manejar las solicitudes del cliente
            } else {
                salida.println("Autenticación fallida.");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean autenticarUsuario(String email, String password) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM usuarios WHERE email = ? AND password = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, email);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
