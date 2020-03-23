package plateau;

import main.Cellule;
import main.Global.*;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Classe représentant un sommet.
 * @author Tristan
 */
public class Sommet extends Cellule {

    /**
     * Liste des sommets adjacents à celui-ci
     */
    private ArrayList<Sommet> adjacents;

    /**
     * Tableau des branches du Sommet. Vaut true pour chaque branche qui existe sur ce sommet, sinon false.
     */
    private boolean[] branches = new boolean[4];

    /**
     * Si le sommet fait partie du plus long cycle ou non.
     */
    private boolean visited;

    /**
     * Constructeur du sommet. Crée une nouvelle ArrayList de sommets adjacents vide. Attribue les valeurs au tableau branches en fonction du type du sommet.
     * @param abscisse
     *      Abscisse du sommet sur le plateau.
     * @param ordonnee
     *      Ordonnée du sommet sur le plateau.
     * @param type
     *      Type du sommet.
     */
    public Sommet(int abscisse, int ordonnee, Type type) {
        super(abscisse, ordonnee, type);
        adjacents = new ArrayList<>();
        visited = false;
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

    public ArrayList<Sommet> getAdjacents() {
        return adjacents;
    }

    public boolean[] getBranches() {
        return branches;
    }

    public void setVisited() {
        visited = true;
    }

    public boolean isVisited() {
        return visited;
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
