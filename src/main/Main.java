package main;

import graphe.Graphe;
import graphe.Sommet;

import javax.swing.*;

public class Main {

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
        graphe.addSommet(new Sommet(1, 0, Global.Type.ANGLE_BAS_DROITE));
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

        Vue vue = new Vue(graphe);
    }
}
