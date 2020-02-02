package main;

import graphe.Graphe;
import graphe.Sommet;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class Vue extends JFrame {

    private Graphe graphe;
    private JPanel panel;

    public Vue(Graphe graphe) throws IOException {
        this.graphe = graphe;
        GridLayout grid = new GridLayout(graphe.getHauteur(), graphe.getLargeur());
        panel = new JPanel(grid);
        panel.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
        addImagesAndBorder();
        setContentPane(panel);
        setTitle("Connect");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
        setMinimumSize(getPreferredSize());
        setVisible(true);
    }

    public void addImagesAndBorder() throws IOException {
        for (int i = 0; i < graphe.getHauteur(); i++) {
            for (int j = 0; j < graphe.getLargeur(); j++) {
                // JLabel dans lequel va être stocké le sprite
                JLabel label = new JLabel();

                // Récupération du sommet grâce à ses coordonnées dans le graphe pour le mettre dans le GridLayout
                Sommet sommet = graphe.getSommetDepuisCoordonnees(j, i);

                // On lit l'image en tant que BufferedImage
                BufferedImage sprite = getUrl(sommet);

                // On redimensionne la BufferedImage vers une autre BufferedImage qui a la taille du JLabel.
                Image resizableSprite = sprite.getScaledInstance(100, 100, Image.SCALE_SMOOTH);

                // On set l'ImageIcon avec l'Image redimensionnable.
                label.setIcon(new ImageIcon(resizableSprite));

                // On crée les bordures du JLabel
                label.setBorder(BorderFactory.createLineBorder(Color.BLACK));

                panel.add(label);
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
    public BufferedImage getUrl(Sommet sommet) throws IOException {
        if(sommet == null)
            return ImageIO.read(new File("sprites/blanc.png"));

        switch (sommet.getType()) {
            case CROIX:
                return ImageIO.read(new File(getCroixUrl(sommet)));
            case VERTICAL:
                return ImageIO.read(new File(sommet.isVisited() ? "sprites/vertical_plein.png" : "sprites/vertical_vide.png"));
            case HORIZONTAL:
                return ImageIO.read(new File(sommet.isVisited() ? "sprites/horizontal_plein.png" : "sprites/horizontal_vide.png"));
            case ANGLE_HAUT_DROITE:
                return ImageIO.read(new File(sommet.isVisited() ? "sprites/angle_haut_droite_plein.png" : "sprites/angle_haut_droite_vide.png"));
            case ANGLE_BAS_DROITE:
                return ImageIO.read(new File(sommet.isVisited() ? "sprites/angle_bas_droite_plein.png" : "sprites/angle_bas_droite_vide.png"));
            case ANGLE_HAUT_GAUCHE:
                return ImageIO.read(new File(sommet.isVisited() ? "sprites/angle_haut_gauche_plein.png" : "sprites/angle_haut_gauche_vide.png"));
            case ANGLE_BAS_GAUCHE:
                return ImageIO.read(new File(sommet.isVisited() ? "sprites/angle_bas_gauche_plein.png" : "sprites/angle_bas_gauche_vide.png"));
            default:
                return ImageIO.read(new File("sprites/blanc.png"));
        }
    }

    public String getCroixUrl(Sommet sommet) {
        if (!sommet.isVisited()) return "sprites/croix_vide.png";

        ArrayList<Sommet> sommetsVisites = (ArrayList<Sommet>) sommet.getAdjacents().stream().filter(Sommet::isVisited).collect(Collectors.toList());

        if(sommetsVisites.size() == 4) return "sprites/croix_pleine.png";

        int[] branches = new int[2];

        for (int i = 0; i < sommetsVisites.size(); i++) {
            int x = sommetsVisites.get(i).getX() - sommet.getX();
            int y = sommetsVisites.get(i).getY() - sommet.getY();

            if (x == -1 && y == 0) branches[i] = 3;
            else if (x == 1 && y == 0) branches[i] = 1;
            else if (x == 0 && y == -1) branches[i] = 0;
            else branches[i] = 2;
        }

        if((branches[0] == 0 && branches[1] == 2) || (branches[0] == 2 && branches[1] == 0)) return "sprites/vertical_plein.png";
        else if((branches[0] == 1 && branches[1] == 3) || (branches[0] == 3 && branches[1] == 1)) return "sprites/horizontal_plein/png";
        else if((branches[0] == 0 && branches[1] == 1) || (branches[0] == 1 && branches[1] == 0)) return "sprites/angle_haut_droite_plein.png";
        else if((branches[0] == 1 && branches[1] == 2) || (branches[0] == 2 && branches[1] == 1)) return "sprites/angle_bas_droite_plein.png";
        else if((branches[0] == 2 && branches[1] == 3) || (branches[0] == 3 && branches[1] == 2)) return "sprites/angle_bas_gauche_plein.png";
        else return "sprites/angle_haut_gauche_plein.png";
    }
}
