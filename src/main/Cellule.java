package main;

import main.Global.*;

public abstract class Cellule {

    protected int x, y;
    protected Type type;

    public Cellule(int abscisse, int ordonnee, Type type) {
        x = abscisse;
        y = ordonnee;
        this.type = type;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Type getType() {
        return type;
    }
}
