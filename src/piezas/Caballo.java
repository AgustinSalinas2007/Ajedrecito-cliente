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
public class Caballo extends Pieza {
    public Caballo(String color) {
        super(color);
    }

    @Override
    public boolean esMovimientoValido(int filaO, int colO, int filaD, int colD, Casilla[][] tablero) {
        int df = Math.abs(filaD - filaO);
        int dc = Math.abs(colD - colO);

        if ((df == 2 && dc == 1) || (df == 1 && dc == 2)) {
            Pieza destino = tablero[filaD][colD].getPieza();
            return destino == null || !destino.getColor().equals(color);
        }

        return false;
    }
}

