/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaz;

import tablero.Casilla;
import piezas.*;
import javax.swing.*;
import java.awt.*;

/**
 *
 * @author Agustín Salinas
 */
public class ProgramaAjedrez extends JFrame implements FinPartidaListener{
    private Casilla[][] casillas = new Casilla[8][8];
    private Pieza seleccionada = null;
    private int filaSel = -1, colSel = -1;
    private int turno = 1;

    public ProgramaAjedrez() {
        setTitle("LocalChess");
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
                casillas[fila][col] = casilla;
                add(casilla);
            }
        }
    }

    public void agregarPiezas(){
        // Negras
        casillas[0][0].setPieza(new Torre("negro"));
        casillas[0][1].setPieza(new Caballo("negro"));
        casillas[0][2].setPieza(new Alfil("negro"));
        casillas[0][3].setPieza(new Reina("negro"));
        casillas[0][4].setPieza(new Rey("negro"));
        casillas[0][5].setPieza(new Alfil("negro"));
        casillas[0][6].setPieza(new Caballo("negro"));
        casillas[0][7].setPieza(new Torre("negro"));

        // Blancas
        casillas[7][0].setPieza(new Torre("blanco"));
        casillas[7][1].setPieza(new Caballo("blanco"));
        casillas[7][2].setPieza(new Alfil("blanco"));
        casillas[7][3].setPieza(new Reina("blanco"));
        casillas[7][4].setPieza(new Rey("blanco"));
        casillas[7][5].setPieza(new Alfil("blanco"));
        casillas[7][6].setPieza(new Caballo("blanco"));
        casillas[7][7].setPieza(new Torre("blanco"));

        // Peones
        for (int i = 0; i < 8; i++) {
            casillas[1][i].setPieza(new Peon("negro"));
            casillas[6][i].setPieza(new Peon("blanco"));
        }
    }

    private void manejarClick(Casilla casilla) {
        int fila = casilla.getFila();
        int col = casilla.getColumna();
        Pieza pieza = casilla.getPieza();
        System.out.println(turno);
        String mueve = turno%2 != 0 ? "blanco" : "negro";

        if (pieza != null && (pieza.getColor().equals(mueve))) {
            System.out.println("Sustituido"+pieza.getColor());
            seleccionada = pieza;
            filaSel = fila;
            colSel = col;
        } else {
            if (seleccionada != null && seleccionada.esMovimientoValido(filaSel, colSel, fila, col, casillas)) {
                turno++;
                casillas[filaSel][colSel].setPieza(null);
                casilla.setPieza(seleccionada);
            }
            seleccionada = null;
            filaSel = colSel = -1;
        }
    }
    
    @Override
    public void reyCapturado(String colorReyCapturado) {
        String ganador = colorReyCapturado.equals("negro") ? "Negras" : "Blancas";

        int opcion = JOptionPane.showConfirmDialog(
            this,
            "¡Las piezas " + ganador + " ganan!\nReiniciar partida:",
            "Fin de la partida",
            JOptionPane.YES_NO_OPTION
        );

        if (opcion == JOptionPane.YES_OPTION) {
            reiniciarPartida();
        } else {
            System.exit(0);
        }
    }
    
    private void reiniciarPartida() {
        for (int fila = 0; fila < 8; fila++) {
            for (int col = 0; col < 8; col++) {
                if(casillas[fila][col].getPieza() != null){
                    casillas[fila][col].setPieza(null);
                }
            }
        }
        agregarPiezas();
        turno = 1;
    }

    public static void main(String[] args) {
        new ProgramaAjedrez();
    }
}

