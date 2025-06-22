/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package piezas;

import tablero.Casilla;

/**
 *
 * @author Agustín Salinas
 */
public class Torre extends Pieza {
    public Torre(String color) {
        super(color);
    }

    @Override
    public boolean esMovimientoValido(int filaO, int colO, int filaD, int colD, Casilla[][] tablero) {
        if (filaO != filaD && colO != colD) return false;

        int pasoFila = Integer.compare(filaD, filaO);
        int pasoCol = Integer.compare(colD, colO);

        int f = filaO + pasoFila;
        int c = colO + pasoCol;

        while (f != filaD || c != colD) {
            if (tablero[f][c].getPieza() != null) return false;
            f += pasoFila;
            c += pasoCol;
        }

        Pieza destino = tablero[filaD][colD].getPieza();
        return destino == null || !destino.getColor().equals(color);
    }
}

