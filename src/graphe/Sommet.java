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

    /**
     * Tableau des branches du Sommet. Vaut true pour chaque branche qui existe sur ce sommet, sinon false.
     */
    private boolean[] branches = new boolean[4];

    public Sommet(int abscisse, int ordonnee, Type type) {
        x = abscisse;
        y = ordonnee;
        this.type = type;
        adjacents = new ArrayList<>();
        attribuerBranches();
    }

    /**
     * Attribue pour chaque branche du sommet, sa valeur true ou false en fonction de s'il peut être relié ou non.
     */
    public void attribuerBranches() {
        switch (type) {
            case CROIX:
                for (int i = 0; i < 4; i++) {
                    branches[i] = true;
                }
                break;
            case HORIZONTAL:
                branches[1] = true;
                branches[3] = true;
                break;
            case VERTICAL:
                branches[0] = true;
                branches[2] = true;
                break;
            case ANGLE_BAS_GAUCHE:
                branches[2] = true;
                branches[3] = true;
                break;
            case ANGLE_BAS_DROITE:
                branches[1] = true;
                branches[2] = true;
                break;
            case ANGLE_HAUT_GAUCHE:
                branches[0] = true;
                branches[3] = true;
                break;
            case ANGLE_HAUT_DROITE:
                branches[0] = true;
                branches[1] = true;
            default:
                for (int i = 0; i < 4; i++) {
                    branches[i] = false;
                }
                break;
        }
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

    public boolean[] getBranches() {
        return branches;
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
