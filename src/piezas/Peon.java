/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package piezas;

import tablero.Casilla;

/**
 *
 * @author Agustín Salinas
 */
public class Peon extends Pieza {
    public Peon (String color) {
        super(color);
    }
    
    @Override
    public boolean esMovimientoValido(int filaO, int colO, int filaD, int colD, Casilla[][] tablero) {
        int dir = color.equals("blanco") ? -1 : 1;
        int filaInicio = color.equals("blanco") ? 6 : 1;

        Casilla destino = tablero[filaD][colD];

        // Movimiento hacia adelante
        if (colO == colD && destino.getPieza() == null) {
            if (filaD == filaO + dir) return true;
            if (filaO == filaInicio && filaD == filaO + 2 * dir && tablero[filaO + dir][colO].getPieza() == null)
                return true;
        }

        // Captura en diagonal
        if (Math.abs(colD - colO) == 1 && filaD == filaO + dir) {
            Pieza p = destino.getPieza();
            return p != null && !p.getColor().equals(this.color);
        }

        return false;
    }
}
