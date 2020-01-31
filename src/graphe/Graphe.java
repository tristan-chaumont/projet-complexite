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

    /**
     * Méthode helper qui détecte s'il existe un sous-graphe qui peut être atteint depuis le Sommet "sommet".
     * @param sommet
     *      Sommet du quel on part pour détecter un cycle.
     * @param visited
     *      Tableau des sommets déjà visités.
     * @param parent
     *      Parent du sommet courant.
     * @return
     */
    private boolean contientCycleUtil(Sommet sommet, boolean[] visited, Sommet parent) {
        // Marque le sommet courant comme visité
        visited[sommets.indexOf(sommet)] = true;

        // Pour tous les sommets adjacents au sommet courant
        for (Sommet s : sommet.getAdjacents()) {
            // Si le noeud adjacent n'a pas été visité, alors on réexécute la méthode sur ce nouveau sommet
            if (!visited[sommets.indexOf(s)]) {
                if (contientCycleUtil(s, visited, sommet))
                    return true;

            // Si le sommet adjacent a été visité et que ce n'est pas un parent du sommet actuel, alors il y a un cycle.
            } else if (!s.equals(parent))
                return true;
        }

        return false;
    }

    /**
     * Détecte s'il y a un cycle.
     * @return
     *      true s'il y a un cycle, false sinon.
     */
    private boolean contientCycle() {
        // Tableau des sommets visités. Initialement tous à false.
        boolean[] visited = new boolean[sommets.size()];

        // Utilise l'algo DFS (Depth First Search) pour détecter les cycles.
        for(int i = 0; i < visited.length; i++) {
            // On n'exécute pas la méthode helper si le chemin a déjà été visité.
            if (!visited[i])
                if (contientCycleUtil(sommets.get(i), visited, null))
                    return true;
        }

        return false;
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

        if(graphe.contientCycle())
            System.out.println("Le graphe contient au moins un cycle.");
        else
            System.out.println("Le graphe ne contient pas de cycle.");
    }
}
