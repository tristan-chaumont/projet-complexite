package graphe;

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

    public Sommet(int abscisse, int ordonnee) {
        x = abscisse;
        y = ordonnee;
    }

    public Sommet() {
        x = 0;
        y = 0;
    }

    public void addAdjacent(Sommet adjacent) {
        adjacents.add(adjacent);
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
