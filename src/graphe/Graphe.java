package graphe;

import main.Global.*;

import java.util.ArrayList;

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
        sommets = new ArrayList<>();
    }

    private boolean contientCycle(int[][] graphe) {
        return true;
    }

    /**
     * Ajoute un arc entre deux sommets.
     * On ajoute chaque sommet dans la liste de sommets adjacent de l'autre.
     * @param from
     *      D'où l'arc vient.
     * @param to
     *      Où il arrive.
     */
    public void addArc(Sommet from, Sommet to) {
        from.addAdjacent(to);
        to.addAdjacent(from);
    }

    public static void main(String[] args) {
        Graphe graphe = new Graphe(5, 5);
        /*
        Graphe :
            _ _
           |   |
           |   |
           |_ _|_ _
               |   |
               |_ _|_
                   |
                   |
         */
        graphe.sommets.add(new Sommet(1, 0, Type.ANGLE_BAS_DROITE));
        graphe.sommets.add(new Sommet(2, 0, Type.ANGLE_BAS_GAUCHE));
        graphe.addArc(graphe.sommets.get(0), graphe.sommets.get(1));

        graphe.sommets.add(new Sommet(1, 1, Type.VERTICAL));
        graphe.addArc(graphe.sommets.get(0), graphe.sommets.get(2));

        graphe.sommets.add(new Sommet(2, 1, Type.VERTICAL));
        graphe.addArc(graphe.sommets.get(1), graphe.sommets.get(3));

        graphe.sommets.add(new Sommet(1, 2, Type.ANGLE_HAUT_DROITE));
        graphe.addArc(graphe.sommets.get(2), graphe.sommets.get(4));

        graphe.sommets.add(new Sommet(2, 2, Type.CROIX));
        graphe.addArc(graphe.sommets.get(3), graphe.sommets.get(5));

        graphe.sommets.add(new Sommet(2, 3, Type.ANGLE_HAUT_DROITE));
        graphe.addArc(graphe.sommets.get(5), graphe.sommets.get(6));

        graphe.sommets.add(new Sommet(3, 2, Type.ANGLE_BAS_GAUCHE));
        graphe.addArc(graphe.sommets.get(5), graphe.sommets.get(7));

        graphe.sommets.add(new Sommet(3, 3, Type.CROIX));
        graphe.addArc(graphe.sommets.get(7), graphe.sommets.get(8));
        graphe.addArc(graphe.sommets.get(6), graphe.sommets.get(8));

        graphe.sommets.add(new Sommet(3, 4, Type.VERTICAL));
        graphe.addArc(graphe.sommets.get(8), graphe.sommets.get(9));

        StringBuilder builder = new StringBuilder();

        for(int i = 0; i < graphe.largeur; i++) {
            for(int j = 0; j < graphe.hauteur; j++) {
                final int finalI = i, finalJ = j;
                Sommet sommet = graphe.sommets.stream().filter(s -> s.getX() == finalJ && s.getY() == finalI).findFirst().orElse(null);
                builder.append(sommet == null ? "  " : "S ");
            }
            builder.append("\n");
        }

        System.out.println(builder.toString());
    }
}
