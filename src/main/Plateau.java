package main;

import plateau.Sommet;

public abstract class Plateau {

    protected static int hauteur;
	protected static int largeur;

    /**
     * Construit un plateau de taille largeur x hauteur
     * @param hauteur
     *      hauteur du plateau
     * @param largeur
     *      largeur du plateau
     */
    public Plateau(int largeur, int hauteur) {
        this.hauteur = hauteur;
        this.largeur = largeur;
    }

    public abstract Cellule getCellule(int x, int y , String type);

    public int getHauteur() {
        return hauteur;
    }

    public int getLargeur() {
        return largeur;
    }

    public void setLargeur(int largeur) {
        this.largeur = largeur;
    }

    public void setHauteur(int hauteur) {
        this.hauteur = hauteur;
    }
}
