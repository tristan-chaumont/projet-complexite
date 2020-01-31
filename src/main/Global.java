package main;

/**
 * Classe globale qui regroupe toutes les informations qui sont identiques pour la méthode du graphe et la méthode du tableau
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
        ANGLE_BAS_DROITE,
        ANGLE_BAS_GAUCHE
    };
}
