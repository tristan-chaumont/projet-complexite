package main;

public abstract class Plateau {

    protected int hauteur, largeur;

    /**
     * Construit un plateau de taille largeur x hauteur
     * @param hauteur
     *      hauteur du plateau
     * @param largeur
     *      largeur du plateau
     */
    public Plateau(int hauteur, int largeur) {
        this.hauteur = hauteur;
        this.largeur = largeur;
    }

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
