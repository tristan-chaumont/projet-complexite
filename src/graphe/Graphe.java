package graphe;

import java.util.ArrayList;

/**
 * Classe représentant un Graphe avec ses sommets ainsi que les méthodes afin de trouver un cycle.
 * @author Tristan
 */
public class Graphe {

    /**
     * Hauteur et largeur du plateau
     */
    private int hauteur, largeur;

    /**
     * Liste des sommets du graphe
     */
    public ArrayList<Sommet> sommets;

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

    public void addSommet(Sommet sommet) {
        sommets.add(sommet);
    }

    public Sommet getSommetDepuisCoordonnees(int x, int y) {
        return sommets.stream().filter(s -> s.getX() == x && s.getY() == y).findFirst().orElse(null);
    }

    public int getHauteur() {
        return hauteur;
    }

    public int getLargeur() {
        return largeur;
    }
}
