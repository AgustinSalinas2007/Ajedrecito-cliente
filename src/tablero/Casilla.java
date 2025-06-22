/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tablero;

/**
 *
 * @author Agustín Salinas
 */
import interfaz.FinPartidaListener;
import piezas.Pieza;
import javax.swing.*;
import java.awt.*;
import piezas.Rey;

public class Casilla extends JButton {
    private int fila, col;
    private Color color;
    private Pieza pieza;
    private FinPartidaListener listener;

    public Casilla(int fila, int col, Color colorFondo) {
        this.fila = fila;
        this.col = col;
        this.color = colorFondo;
        this.setBackground(colorFondo);
        this.setOpaque(true);
        this.setBorderPainted(false);
        this.setFont(new Font("Arial", Font.PLAIN, 32));
    }

    public int getFila() {
        return fila;
    }

    public int getColumna() {
        return col;
    }
    
    public Color getColor(){
        return color;
    }

    public Pieza getPieza() {
        return pieza;
    }

    public void setFinDePartidaListener(FinPartidaListener listener) {
        this.listener = listener;
    }

    public void setPieza(Pieza pieza) {
        boolean chauRey = this.pieza instanceof Rey && pieza != null;
        this.pieza = pieza;
        actualizarTexto();
        if (chauRey && listener != null) {
            listener.reyCapturado(this.pieza.getColor()); // avisar ganador
        }
    }

    public void actualizarTexto() {
        if (pieza != null) {
            String tipo = pieza.getClass().getSimpleName();
            boolean esBlanco = pieza.getColor().equals("blanco");
            String unicode = switch (tipo) {
                case "Peon" -> esBlanco ? "♙" : "♟";
                case "Torre" -> esBlanco ? "♖" : "♜";
                case "Caballo" -> esBlanco ? "♘" : "♞";
                case "Alfil" -> esBlanco ? "♗" : "♝";
                case "Reina" -> esBlanco ? "♕" : "♛";
                case "Rey" -> esBlanco ? "♔" : "♚";
                default -> "?";
            };
            setFont(new Font("Segoe UI Symbol", Font.PLAIN, 38));
            setText(unicode);
        } else {
            setText("");
        }
    }
}

