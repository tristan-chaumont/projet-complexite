package main;

import main.Global.*;

public abstract class Cellule {

    int x, y;
    Type type;

    public Cellule(int abscisse, int ordonnee, Type type) {
        x = abscisse;
        y = ordonnee;
        this.type = type;
    }
}
