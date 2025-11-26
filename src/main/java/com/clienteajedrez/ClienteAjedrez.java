/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.clienteajedrez;

/**
 *
 * @author Agustín Salinas
 */
import java.io.*;
import java.net.*;
import javax.swing.*;
import com.clienteajedrez.interfaz.ProgramaAjedrez;

public class ClienteAjedrez {
    public static void main(String[] args) {
        try {
            String ip = JOptionPane.showInputDialog("IP del servidor:", "127.0.0.1");
            Socket socket = new Socket(ip, 5000);
            System.out.println("Conectado al servidor.");

            BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter salida = new PrintWriter(socket.getOutputStream(), true);

            String color = entrada.readLine().split(":")[1];
            System.out.println("Color asignado: " + color);

            ProgramaAjedrez programa = new ProgramaAjedrez(salida, color);

            new Thread(() -> {
                try {
                    String msg;
                    while ((msg = entrada.readLine()) != null) {
                        if (msg.startsWith("MOV:")) {
                            programa.aplicarMovimientoRemoto(msg.substring(4));
                        } else if (msg.equals("REINICIO:ACEPTADO")) {
                            programa.reiniciarPartida();
                        } else if (msg.equals("REINICIO:SOLICITADO")) {
                            int opcion = JOptionPane.showConfirmDialog(
                                programa,
                                "El otro jugador quiere reiniciar la partida. ¿Aceptar?",
                                "Solicitud de reinicio",
                                JOptionPane.YES_NO_OPTION
                            );
                            if (opcion == JOptionPane.YES_OPTION) {
                                salida.println("REINICIO:ACEPTADO");
                            }
                        }
                    }
                } catch (IOException e) {
                    System.out.println("Desconectado del servidor.");
                }
            }).start();

        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "No se pudo conectar con el servidor.");
        }
    }
}

