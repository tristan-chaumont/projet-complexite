package graphe;

import java.util.ArrayList;

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


}
