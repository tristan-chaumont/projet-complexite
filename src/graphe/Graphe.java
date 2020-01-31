package graphe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class Graphe {

    /**
     * Hauteur et largeur du plateau
     */
    private int hauteur, largeur;

    /**
     * Liste des sommets du graphe
     */
    private ArrayList<Sommet> sommets;

    public Graphe(int h, int l) {
        hauteur = h;
        largeur = l;
    }

    private int[][] construireGraphe() {
        int[][] g = new int[largeur][hauteur];
        do {
            for(int i = 0; i < largeur - 1; i++) {
                for(int j = 0; j < hauteur - 1; j++) {
                    g[i][j] = (int) Math.round(Math.random());
                }
            }
        } while(!contientCycle(g));
        return g;
    }

    private boolean contientCycle(int[][] graphe) {
        return true;
    }

    public void addArc(Sommet from, Sommet to) {
        from.addAdjacent(to);
        to.addAdjacent(from);
    }

    public static void main(String[] args) {

    }
}
