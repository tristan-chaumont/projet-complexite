package main;

/**
 * Classe globale qui regroupe toutes les informations qui sont identiques pour la méthode du graphe et la méthode du tableau
 * @author Tristan
 */

public class Global {

    /**
     * Attributs :
     * type
     * 		type de la case
     * compte
     * 		booleen permettant de savoir si la case a ete comptee
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
