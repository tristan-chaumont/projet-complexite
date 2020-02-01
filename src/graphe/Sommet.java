package graphe;

import main.Global.*;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Classe représentant un sommet ainsi que ses successeurs et prédecesseurs
 * @author Tristan
 */
public class Sommet {

    /**
     * Coordonnées du sommet dans le graphe
     */
    private int x, y;

    /**
     * Liste des sommets adjacents à celui-ci
     */
    private ArrayList<Sommet> adjacents;

    /**
     * Type du sommet (parmi les types du jeu Connect)
     */
    private Type type;

    public Sommet(int abscisse, int ordonnee, Type type) {
        x = abscisse;
        y = ordonnee;
        this.type = type;
        adjacents = new ArrayList<>();
    }

    public Sommet(Type type) {
        x = 0;
        y = 0;
        this.type = type;
        adjacents = new ArrayList<>();
    }

    public void addAdjacent(Sommet adjacent) {
        adjacents.add(adjacent);
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

    public ArrayList<Sommet> getAdjacents() {
        return adjacents;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sommet sommet = (Sommet) o;
        return x == sommet.x &&
                y == sommet.y &&
                Objects.equals(adjacents, sommet.adjacents);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, adjacents);
    }
}
