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
public abstract class Pieza {
    protected String color; // "blanco" o "negro"

    public Pieza(String color) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }

    public abstract boolean esMovimientoValido(int filaO, int colO, int filaD, int colD, Casilla[][] tablero);
}

