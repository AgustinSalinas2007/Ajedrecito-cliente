/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.clienteajedrez.piezas;

import com.clienteajedrez.tablero.Casilla;

/**
 *
 * @author Agustín Salinas
 */
public class Alfil extends Pieza {
    public Alfil(String color) {
        super(color);
    }

    @Override
    public boolean esMovimientoValido(int filaO, int colO, int filaD, int colD, Casilla[][] tablero) {
        int df = filaD - filaO;
        int dc = colD - colO;
        if (Math.abs(df) != Math.abs(dc)) return false;

        int pasoFila = Integer.compare(df, 0);
        int pasoCol = Integer.compare(dc, 0);

        int f = filaO + pasoFila;
        int c = colO + pasoCol;

        while (f != filaD && c != colD) {
            if (tablero[f][c].getPieza() != null) return false;
            f += pasoFila;
            c += pasoCol;
        }

        Pieza destino = tablero[filaD][colD].getPieza();
        return destino == null || !destino.getColor().equals(color);
    }
}

