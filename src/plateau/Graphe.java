package plateau;

import main.Cellule;
import static main.Global.*;

import main.ConnectException;
import main.Plateau;
import tableau.Tableau;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Collectors;

/**
 * Classe représentant un Graphe avec ses sommets ainsi que les méthodes afin de trouver un cycle.
 * @author Tristan
 */
public class Graphe extends Plateau {

    /**
     * Liste des sommets du graphe
     */
    public ArrayList<Sommet> sommets;

    /**
     * Liste des sommets appartenant au potentiel cycle actuel.
     */
    public ArrayList<Sommet> pre;

    /**
     * Circuits actuellement trouvés.
     */
    public ArrayList<ArrayList<Sommet>> circuits;

    public Graphe(int hauteur, int largeur) {
        super(hauteur, largeur);
        sommets = new ArrayList<>();
        pre = new ArrayList<>();
        circuits = new ArrayList<>();
    }

    /**
     * Si le circuit est imparfait, on passe tous les sommets de ce circuit à "visited".
     * Cela évite de repasser dessus par la suite.
     * @param sommet
     *      Sommet à partir duquel on veut set tous les voisins à "visité".
     */
    public void setAllVisited(Sommet sommet) {
        if (!sommet.isVisited()) {
            sommet.setVisited();
            for (Sommet s : sommet.getAdjacents()) {
                if (!s.isVisited()) {
                    setAllVisited(s);
                }
            }
        }
    }

    /**
     * Méthode helper qui détecte s'il existe un sous-graphe qui peut être atteint depuis le Sommet "sommet".
     * @param sommet
     *      Tableau des sommets déjà visités.
     * @param parent
     *      Parent du sommet courant.
     * @return
     *      true s'il existe un cycle, false sinon.
     */
    private boolean contientCycleUtil(Sommet sommet, Sommet parent) {
        // Si le sommet est une CROIX et qu'elle n'a pas 4 voisins, ce n'est pas un circuit parfait. On retourne false.
        // Ou si le sommet n'a pas 2 voisins, ce n'est pas non plus un circuit parfait. On retourne false.
        if (((sommet.getType().equals(Type.CROIX) && sommet.getAdjacents().size() < 4) || (!sommet.getType().equals(Type.CROIX) && sommet.getAdjacents().size() < 2))
                && !sommet.getType().equals(Type.BLANC)) {
            setAllVisited(sommet);
            pre = new ArrayList<>();
            return false;
        }

        // Marque le sommet courant comme visité
        sommet.setVisited();

        // Si le sommet est une CROIX alors, pour chacun de ses sommets adjacents, on essaie de trouver un cycle.
        // On ajoute le sommet adjacent dans la liste des prédécesseurs s'il n'a pas été visité (pour éviter les doublons).
        // On exécute l'algo de recherche d'un circuit à partir du sommet adjacent.
        // Si on ne trouve pas de circuit, c'est que le circuit n'est pas parfait.
        if (sommet.getType().equals(Type.CROIX)) {
            ArrayList<Sommet> nonVisites = (ArrayList<Sommet>) sommet.getAdjacents().stream().filter(som -> !som.isVisited()).collect(Collectors.toList());
            for (Sommet adjacent: nonVisites) {
                if (!adjacent.isVisited()) {
                    pre.add(adjacent);
                    if (!contientCycleUtil(adjacent, sommet)) {
                        return false;
                    }
                }
            }
            return true;
        }

        // Pour tous les sommets adjacents au sommet courant
        for (Sommet s : sommet.getAdjacents()) {
            // Si le noeud adjacent n'a pas été visité, alors on réexécute la méthode sur ce nouveau sommet
            if (!s.isVisited()) {
                // Récurrence, on récupère la valeur du cycle
                boolean value = contientCycleUtil(s, sommet);

                if (value) {
                    // On a déjà trouvé le cycle, donc on arrête l'algorithme.
                    pre.add(s);
                    return true;
                }
            // Si le sommet adjacent a été visité et que ce n'est pas un parent du sommet actuel, alors il y a un cycle.
            } else if (!s.equals(parent) && parent != null) {
                // On l'ajoute dans la liste des prédecesseurs.
                pre.add(s);
                return true;
            }
        }
        return false;
    }

    /**
     * Retourne le plus long cycle du plateau s'il y en a un, sinon retourne une liste vide.
     * @return
     *      true s'il y a un cycle, false sinon.
     */
    public ArrayList<Sommet> contientCycle() {
        // Utilise l'algo DFS (Depth First Search) pour détecter les cycles.
        for (Sommet sommet : sommets) {
            // On n'exécute pas la méthode helper si le chemin a déjà été visité.
            if (!sommet.isVisited())
                if (contientCycleUtil(sommet, null)) {
                    circuits.add(pre);
                }
            pre = new ArrayList<>();
        }

        return circuits.stream().max(Comparator.comparingInt(ArrayList::size)).orElseGet(ArrayList::new);
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
                sommet = new Sommet(x, y, Type.ANGLE_HAUT_DROITE);
                break;
            case "AHG":
                sommet = new Sommet(x, y, Type.ANGLE_HAUT_GAUCHE);
                break;
            case "ABG":
                sommet = new Sommet(x, y, Type.ANGLE_BAS_GAUCHE);
                break;
            case "ABD":
                sommet = new Sommet(x, y, Type.ANGLE_BAS_DROITE);
                break;
            case "H":
                sommet = new Sommet(x, y, Type.HORIZONTAL);
                break;
            case "V":
                sommet = new Sommet(x, y, Type.VERTICAL);
                break;
            case "C":
                sommet = new Sommet(x, y, Type.CROIX);
                break;
            default:
                sommet = new Sommet(x, y, Type.BLANC);
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

    public ArrayList<ArrayList<Sommet>> getCircuits() {
        return circuits;
    }

    public void resetAll() {
        pre = new ArrayList<>();
        circuits = new ArrayList<>();
        for (Sommet sommet: sommets) {
            sommet.setUnvisited();
        }
    }
}
