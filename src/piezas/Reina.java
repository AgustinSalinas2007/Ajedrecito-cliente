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
public class Reina extends Pieza {
    public Reina(String color) {
        super(color);
    }

    @Override
    public boolean esMovimientoValido(int filaO, int colO, int filaD, int colD, Casilla[][] tablero) {
        return new Torre(color).esMovimientoValido(filaO, colO, filaD, colD, tablero) ||
               new Alfil(color).esMovimientoValido(filaO, colO, filaD, colD, tablero);
    }
}

