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
public class Rey extends Pieza {
    
    public Rey(String color) {
        super(color);
    }

    @Override
    public boolean esMovimientoValido(int filaO, int colO, int filaD, int colD, Casilla[][] tablero) {
        int df = Math.abs(filaD - filaO);
        int dc = Math.abs(colD - colO);

        if (df <= 1 && dc <= 1) {
            Pieza destino = tablero[filaD][colD].getPieza();
            return destino == null || !destino.getColor().equals(color);
        }
        
        // ENROQUE CORTO
        if (df == 0 && dc == 2 && colD > colO) {
            for (int c = colO + 1; c < 7; c++) {
                if (tablero[filaO][c].getPieza() != null) return false;
            }
            Pieza torre = tablero[filaO][7].getPieza();
            if (torre instanceof Torre && torre.getColor().equals(this.color)){
                tablero[filaO][7].setPieza(null);
                tablero[filaO][5].setPieza(torre);
                return true;
            }
        }

        // ENROQUE LARGO
        if (df == 0 && dc == 2 && colD < colO) {
            for (int c = colO - 1; c > 0; c--) {
                if (tablero[filaO][c].getPieza() != null) return false;
            }
            Pieza torre = tablero[filaO][0].getPieza();
            if (torre instanceof Torre && torre.getColor().equals(this.color)){
                tablero[filaO][0].setPieza(null);
                tablero[filaO][3].setPieza(torre);
                return true;
            }
        }

        return false;
    }
}

