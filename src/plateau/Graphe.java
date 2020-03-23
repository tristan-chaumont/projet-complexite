package plateau;

import main.Cellule;
import main.Global;
import main.Plateau;
import tableau.Tableau;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Classe représentant un Graphe avec ses sommets ainsi que les méthodes afin de trouver un cycle.
 * @author Tristan
 */
public class Graphe extends Plateau {

    /**
     * Liste des sommets du graphe
     */
    public ArrayList<Sommet> sommets;

    public ArrayList<Sommet> pre;

    public Sommet finCycle;

    public Graphe(int hauteur, int largeur) {
        super(hauteur, largeur);
        sommets = new ArrayList<>();
        pre = new ArrayList<>();
        finCycle = null;
    }

    public Graphe() {
        super(0, 0);
        sommets = new ArrayList<>();
        pre = new ArrayList<>();
        finCycle = null;
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
     *      true s'il existe un cycle, false sinon.
     */
    private int contientCycleUtil(Sommet sommet, boolean[] visited, Sommet parent) {
        // Marque le sommet courant comme visité
        visited[sommets.indexOf(sommet)] = true;

        // Pour tous les sommets adjacents au sommet courant
        for (Sommet s : sommet.getAdjacents()) {
            // Si le noeud adjacent n'a pas été visité, alors on réexécute la méthode sur ce nouveau sommet
            if (!visited[sommets.indexOf(s)]) {
                int value = contientCycleUtil(s, visited, sommet);
                if (sommet == finCycle) {
                    pre.add(s);
                    return 2;
                } else if (value == 1) {
                    pre.add(s);
                    return 1;
                } else if (value == 2)
                    return 2;

            // Si le sommet adjacent a été visité et que ce n'est pas un parent du sommet actuel, alors il y a un cycle.
            } else if (!s.equals(parent)) {
                finCycle = s;
                pre.add(s);
                return 1;
            }
        }
        return 0;
    }

    /**
     * Détecte s'il y a un cycle.
     * @return
     *      true s'il y a un cycle, false sinon.
     */
    public boolean contientCycle() {
        // Tableau des sommets visités. Initialement tous à false.
        boolean[] visited = new boolean[sommets.size()];

        // Utilise l'algo DFS (Depth First Search) pour détecter les cycles.
        for (int i = 0; i < visited.length; i++) {
            // On n'exécute pas la méthode helper si le chemin a déjà été visité.
            if (!visited[i])
                if (contientCycleUtil(sommets.get(i), visited, null) == 2)
                    return true;
        }

        return false;
    }

    /**
     * Vérifie si le sommet courant peut être relié aux sommets qui lui sont adjacents. Si oui, il se relie à eux.
     * @param sommet
     *      Sommet qui doit être relié.
     */
    public void relierSommetsAdjacents(Sommet sommet) {
        int x = sommet.getX();
        int y = sommet.getY();

        for (int i = 0; i < 4; i++) {
            if (sommet.getBranches()[i]) {
                Sommet s = null;
                switch (i) {
                    case 0:
                        if (y > 0) s = getSommetDepuisCoordonnees(x, y - 1);
                        break;
                    case 1:
                        if (x < largeur) s = getSommetDepuisCoordonnees(x + 1, y);
                        break;
                    case 2:
                        if (y < hauteur) s = getSommetDepuisCoordonnees(x, y + 1);
                        break;
                    default:
                        if (x > 0) s = getSommetDepuisCoordonnees(x - 1, y);
                        break;
                }

                if(isReliable(sommet, i, s))
                    addArc(sommet, s);
            }
        }
    }

    /**
     * Vérifie si 2 sommets sont reliables.
     * @param from
     *      Sommet depuis lequel on veut relier.
     * @param branche
     *      Dans le cas d'une croix. 0 pour la branche du haut, 1 pour la branche de droite, 2 pour la branche du bas et 3 pour la branche de gauche.
     * @param to
     *      Sommet sur lequel on veut relier.
     * @return
     *      true si il est possible d'effectuer la liaison, false sinon.
     */
    private boolean isReliable(Sommet from, int branche, Sommet to) {
        if (from.getAdjacents().contains(to) || to == null)
            return false;

        return from.getBranches()[branche] && to.getBranches()[branche > 1 ? branche - 2 : branche + 2];
    }

    @Override
    public Cellule getCellule(int x, int y, String type) {
        Sommet sommet;
        switch (type) {
            case "AHD":
                sommet = new Sommet(y, x, Global.Type.ANGLE_HAUT_DROITE);
                break;
            case "AHG":
                sommet = new Sommet(y, x, Global.Type.ANGLE_HAUT_GAUCHE);
                break;
            case "ABG":
                sommet = new Sommet(y, x, Global.Type.ANGLE_BAS_GAUCHE);
                break;
            case "ABD":
                sommet = new Sommet(y, x, Global.Type.ANGLE_BAS_DROITE);
                break;
            case "H":
                sommet = new Sommet(y, x, Global.Type.HORIZONTAL);
                break;
            case "V":
                sommet = new Sommet(y, x, Global.Type.VERTICAL);
                break;
            case "C":
                sommet = new Sommet(y, x, Global.Type.CROIX);
                break;
            default:
                sommet = new Sommet(y, x, Global.Type.BLANC);
                break;
        }
        return sommet;
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

    public ArrayList<Sommet> getPre() {
        return pre;
    }
}
