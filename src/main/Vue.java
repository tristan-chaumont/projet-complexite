package main;

import graphe.Graphe;
import graphe.Sommet;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Vue extends JFrame {

    private Graphe graphe;

    public Vue(Graphe graphe) {
        this.graphe = graphe;
        JPanel panel = new JPanel();
        add(panel);
        setMinimumSize(new Dimension(500, 500));
        setSize(new Dimension(graphe.getLargeur() * 100, graphe.getHauteur() * 100));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        // On dessine les sprites
        for (int i = 0; i < graphe.getHauteur(); i++) {
            for (int j = 0; j < graphe.getLargeur(); j++) {
                Sommet sommet = graphe.getSommetDepuisCoordonnees(j, i);
                try {
                    // On dessine les images
                    g.drawImage(getUrl(sommet),
                            j * (getWidth() / graphe.getLargeur()),
                            i * (getHeight() / graphe.getHauteur()),
                            getWidth() / graphe.getLargeur(),
                            getHeight() / graphe.getHauteur(),
                            null);

                    // On dessine les séparations
                    g.drawRect(j * (getWidth() / graphe.getLargeur()),
                            i * (getHeight() / graphe.getHauteur()),
                            getWidth() / graphe.getLargeur(),
                            getHeight() / graphe.getHauteur());
                } catch (IOException e) {
                    System.out.println("Image introuvable : mauvais chemin ?");
                }
            }
        }
    }

    /**
     * Crée une image à partir de l'url du sprite et du Type du sommet.
     * @param sommet
     *      Sommet dont il faut trouver l'url.
     * @return
     *      L'image créée.
     * @throws IOException
     *      Si le fichier est introuvable.
     */
    public Image getUrl(Sommet sommet) throws IOException {
        if(sommet == null)
            return ImageIO.read(new File("sprites/blanc.png"));

        switch (sommet.getType()) {
            case CROIX:
                return ImageIO.read(new File("sprites/croix_vide.png"));
            case VERTICAL:
                return ImageIO.read(new File("sprites/vertical_vide.png"));
            case HORIZONTAL:
                return ImageIO.read(new File("sprites/horizontal_vide.png"));
            case ANGLE_HAUT_DROITE:
                return ImageIO.read(new File("sprites/angle_haut_droite_vide.png"));
            case ANGLE_BAS_DROITE:
                return ImageIO.read(new File("sprites/angle_bas_droite_vide.png"));
            case ANGLE_HAUT_GAUCHE:
                return ImageIO.read(new File("sprites/angle_haut_gauche_vide.png"));
            case ANGLE_BAS_GAUCHE:
                return ImageIO.read(new File("sprites/angle_bas_gauche_vide.png"));
            default:
                return ImageIO.read(new File("sprites/blanc.png"));
        }
    }
}
