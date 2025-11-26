package com.clienteajedrez.interfaz;

import com.clienteajedrez.tablero.Casilla;
import com.clienteajedrez.piezas.*;
import javax.swing.*;
import java.awt.*;
import java.io.PrintWriter;

public class ProgramaAjedrez extends JFrame implements FinPartidaListener {
    private Casilla[][] tablero = new Casilla[8][8];
    private Pieza seleccionada = null;
    private int filaSel = -1, colSel = -1;
    private int turno = 1;

    private PrintWriter salida;  // conexión con el servidor
    private String colorJugador; // "blanco" o "negro"
    private boolean partidaActiva = true;

    public ProgramaAjedrez(PrintWriter salida, String colorJugador) {
        this.salida = salida;
        this.colorJugador = colorJugador;

        setTitle("Ajedrez Remoto - " + colorJugador.toUpperCase());
        setSize(600, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(8, 8));

        inicializarTablero();
        agregarPiezas();
        setVisible(true);
    }

    private void inicializarTablero() {
        boolean blanco = true;

        for (int fila = 0; fila < 8; fila++) {
            blanco = !blanco;
            for (int col = 0; col < 8; col++) {
                Color color = blanco ? new Color(240, 217, 181) : new Color(181, 136, 99);
                blanco = !blanco;

                Casilla casilla = new Casilla(fila, col, color);
                casilla.setFinDePartidaListener(this);
                casilla.addActionListener(e -> manejarClick(casilla));

                tablero[fila][col] = casilla;
            }
        }

        // 🔁 Si el jugador es negro, invertir visualmente el tablero
        if (colorJugador.equals("negro")) {
            for (int fila = 7; fila >= 0; fila--) {
                for (int col = 7; col >= 0; col--) {
                    add(tablero[fila][col]);
                }
            }
        } else {
            for (int fila = 0; fila < 8; fila++) {
                for (int col = 0; col < 8; col++) {
                    add(tablero[fila][col]);
                }
            }
        }
    }

    public void agregarPiezas() {
        // Piezas negras
        tablero[0][0].setPieza(new Torre("negro"));
        tablero[0][1].setPieza(new Caballo("negro"));
        tablero[0][2].setPieza(new Alfil("negro"));
        tablero[0][3].setPieza(new Reina("negro"));
        tablero[0][4].setPieza(new Rey("negro"));
        tablero[0][5].setPieza(new Alfil("negro"));
        tablero[0][6].setPieza(new Caballo("negro"));
        tablero[0][7].setPieza(new Torre("negro"));

        // Piezas blancas
        tablero[7][0].setPieza(new Torre("blanco"));
        tablero[7][1].setPieza(new Caballo("blanco"));
        tablero[7][2].setPieza(new Alfil("blanco"));
        tablero[7][3].setPieza(new Reina("blanco"));
        tablero[7][4].setPieza(new Rey("blanco"));
        tablero[7][5].setPieza(new Alfil("blanco"));
        tablero[7][6].setPieza(new Caballo("blanco"));
        tablero[7][7].setPieza(new Torre("blanco"));

        // Peones
        for (int i = 0; i < 8; i++) {
            tablero[1][i].setPieza(new Peon("negro"));
            tablero[6][i].setPieza(new Peon("blanco"));
        }
    }

    private void manejarClick(Casilla casilla) {
        if (!partidaActiva) return;

        int fila = casilla.getFila();
        int col = casilla.getColumna();
        Pieza pieza = casilla.getPieza();

        String mueve = turno % 2 != 0 ? "blanco" : "negro";

        if (pieza != null && pieza.getColor().equals(mueve) && pieza.getColor().equals(colorJugador)) {
            // Restaurar color
            if (filaSel != -1 && colSel != -1) {
                tablero[filaSel][colSel].setBackground(tablero[filaSel][colSel].getColor());
            }

            seleccionada = pieza;
            filaSel = fila;
            colSel = col;

            Color colorActual = casilla.getBackground();
            casilla.setBackground(colorActual.darker());

        } else {
            // Intenta mover
            if (seleccionada != null && seleccionada.esMovimientoValido(filaSel, colSel, fila, col, tablero)) {
                // Restaurar color
                tablero[filaSel][colSel].setBackground(tablero[filaSel][colSel].getColor());

                // Enviar movimiento al servidor
                salida.println("MOV:" + filaSel + "," + colSel + "," + fila + "," + col);

                tablero[filaSel][colSel].setPieza(null);
                casilla.setPieza(seleccionada);
                turno++;
            }

            seleccionada = null;
            filaSel = colSel = -1;
        }
    }


    public void aplicarMovimientoRemoto(String mensaje) {
        String[] p = mensaje.split(",");
        int filaO = Integer.parseInt(p[0]);
        int colO = Integer.parseInt(p[1]);
        int filaD = Integer.parseInt(p[2]);
        int colD = Integer.parseInt(p[3]);

        Casilla origen = tablero[filaO][colO];
        Casilla destino = tablero[filaD][colD];
        destino.setPieza(origen.getPieza());
        origen.setPieza(null);

        turno++;
    }

    @Override
    public void reyCapturado(String colorReyCapturado) {
        partidaActiva = false;
        String ganador = colorReyCapturado.equals("negro") ? "Blancas" : "Negras";
        int opcion = JOptionPane.showConfirmDialog(
            this,
            "¡Las piezas " + ganador + " ganan!\n¿Reiniciar partida?",
            "Fin de la partida",
            JOptionPane.YES_NO_OPTION
        );

        if (opcion == JOptionPane.YES_OPTION) {
            salida.println("REINICIO:SOLICITADO");
        }
    }

    public void reiniciarPartida() {
        for (int fila = 0; fila < 8; fila++) {
            for (int col = 0; col < 8; col++) {
                tablero[fila][col].setPieza(null);
            }
        }
        agregarPiezas();
        turno = 1;
        partidaActiva = true;
        repaint();
    }
}
