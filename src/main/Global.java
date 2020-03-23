package main;

import plateau.Graphe;
import plateau.Sommet;

import java.io.*;

/**
 * Classe globale qui regroupe toutes les informations qui sont partagées par la méthode du graphe et la méthode du tableau
 * @author Tristan
 */

public class Global {

    /**
     * Type de la case du jeu Connect
     */
    public enum Type {
        CROIX,
        BLANC,
        VERTICAL,
        HORIZONTAL,
        ANGLE_HAUT_GAUCHE,
        ANGLE_HAUT_DROITE,
        ANGLE_BAS_GAUCHE,
        ANGLE_BAS_DROITE
    }

    /**
     * Génère un type aléatoire pour un sommet pour la génération de graphe aléatoire.
     * @return
     *      Un type pour le sommet aléatoire.
     */
    public static Type genererTypeAleatoire() {
        switch ((int) (Math.random() * 7)) {
            case 0:
                return Type.CROIX;
            case 1:
                return Type.BLANC;
            case 2:
                return Type.VERTICAL;
            case 3:
                return Type.HORIZONTAL;
            case 4:
                return Type.ANGLE_HAUT_GAUCHE;
            case 5:
                return Type.ANGLE_HAUT_DROITE;
            case 6:
                return Type.ANGLE_BAS_GAUCHE;
            case 7:
                return Type.ANGLE_BAS_DROITE;
        }
        return null;
    }

    /**
     * Génère un graphe fait à la main.
     * @return
     *      Le graphe en question.
     */
    /*public static Graphe genererGraphePrefait() {
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
        /*graphe.addSommet(new Sommet(1, 0, Global.Type.ANGLE_BAS_DROITE));
        graphe.addSommet(new Sommet(2, 0, Global.Type.ANGLE_BAS_GAUCHE));
        graphe.addArc(graphe.sommets.get(0), graphe.sommets.get(1));

        graphe.addSommet(new Sommet(1, 1, Global.Type.VERTICAL));
        graphe.addArc(graphe.sommets.get(0), graphe.sommets.get(2));

        graphe.addSommet(new Sommet(2, 1, Global.Type.VERTICAL));
        graphe.addArc(graphe.sommets.get(1), graphe.sommets.get(3));

        graphe.addSommet(new Sommet(1, 2, Global.Type.ANGLE_HAUT_DROITE));
        graphe.addArc(graphe.sommets.get(2), graphe.sommets.get(4));

        graphe.addSommet(new Sommet(2, 2, Global.Type.CROIX));
        graphe.addArc(graphe.sommets.get(3), graphe.sommets.get(5));

        graphe.addSommet(new Sommet(2, 3, Global.Type.ANGLE_HAUT_DROITE));
        graphe.addArc(graphe.sommets.get(5), graphe.sommets.get(6));

        graphe.addSommet(new Sommet(3, 2, Global.Type.ANGLE_BAS_GAUCHE));
        graphe.addArc(graphe.sommets.get(5), graphe.sommets.get(7));

        graphe.addSommet(new Sommet(3, 3, Global.Type.CROIX));
        graphe.addArc(graphe.sommets.get(7), graphe.sommets.get(8));
        graphe.addArc(graphe.sommets.get(6), graphe.sommets.get(8));

        graphe.addSommet(new Sommet(3, 4, Global.Type.VERTICAL));
        graphe.addArc(graphe.sommets.get(8), graphe.sommets.get(9));

        return graphe;
    }*/

    //region GENERER GRAPHE ALEATOIRE

    /**
     * Génère un graphe aléatoire contenant au moins un cycle.
     * @return
     *      Le graphe aléatoire généré.
     */
    /*public static Graphe genererGrapheAleatoire() {
        Graphe g;
        do {
            //g = new Graphe(50, 50);
            g = new Graphe((int) (Math.random() * 5) + 15, (int) (Math.random() * 5) + 15);
            for (int i = 0; i < g.getHauteur(); i++) {
                for (int j = 0; j < g.getLargeur(); j++) {
                    Sommet sommet = new Sommet(j, i, Global.genererTypeAleatoire());
                    g.relierSommetsAdjacents(sommet);
                    g.addSommet(sommet);
                }
            }
            System.out.println("Ne contient pas de cycle.");
        } while(!g.contientCycle());
        return g;
    }*/

    //endregion

    //region CREER PLATEAU PARFAIT

    public static Graphe genererGraphePrefait(String fileName) {
        Graphe plateau = null;
        try {
            FileReader fileReader = new FileReader("plateauxPrefaits/" + fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = "";
            int count = 0;
            line = bufferedReader.readLine();
            plateau = new Graphe(Integer.parseInt(line.split(" ")[0]), Integer.parseInt(line.split(" ")[1]));
            while ((line = bufferedReader.readLine()) != null) {
                String[] splitLine = line.split(" ");
                for (int i = 0; i < splitLine.length; i++) {
                    Sommet sommet = Sommet.getSommet(count, i, splitLine[i]);
                    plateau.relierSommetsAdjacents(sommet);
                    plateau.addSommet(sommet);
                }
                count++;
            }
            bufferedReader.close();
            fileReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return plateau;
    }

    //endregion
}