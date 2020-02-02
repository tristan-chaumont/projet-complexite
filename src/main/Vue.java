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
        setMinimumSize(new Dimension(graphe.getLargeur() * 100, graphe.getHauteur() * 100));
        addImagesAndBorder();
        setContentPane(panel);
        setTitle("Connect");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
        //setMinimumSize(getPreferredSize());
        setVisible(true);
    }

    public void addImagesAndBorder() throws IOException {
        for (int i = 0; i < graphe.getHauteur(); i++) {
            for (int j = 0; j < graphe.getLargeur(); j++) {
                Sommet sommet = graphe.getSommetDepuisCoordonnees(j, i);
                BufferedImage sprite = getUrl(sommet);

                // On crée un label contenant le sprite. Ce label peut être resizable.
                JLabel label = new JLabel() {
                    @Override
                    public void paintComponent(Graphics g) {
                        super.paintComponent(g);
                        g.drawImage(sprite, 0, 0, this.getWidth(), this.getHeight(), null);
                        repaint();
                        revalidate();
                    }
                };

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
                return ImageIO.read(new File(graphe.getPre().contains(sommet) ? "sprites/vertical_plein.png" : "sprites/vertical_vide.png"));
            case HORIZONTAL:
                return ImageIO.read(new File(graphe.getPre().contains(sommet) ? "sprites/horizontal_plein.png" : "sprites/horizontal_vide.png"));
            case ANGLE_HAUT_DROITE:
                return ImageIO.read(new File(graphe.getPre().contains(sommet) ? "sprites/angle_haut_droite_plein.png" : "sprites/angle_haut_droite_vide.png"));
            case ANGLE_BAS_DROITE:
                return ImageIO.read(new File(graphe.getPre().contains(sommet) ? "sprites/angle_bas_droite_plein.png" : "sprites/angle_bas_droite_vide.png"));
            case ANGLE_HAUT_GAUCHE:
                return ImageIO.read(new File(graphe.getPre().contains(sommet) ? "sprites/angle_haut_gauche_plein.png" : "sprites/angle_haut_gauche_vide.png"));
            case ANGLE_BAS_GAUCHE:
                return ImageIO.read(new File(graphe.getPre().contains(sommet) ? "sprites/angle_bas_gauche_plein.png" : "sprites/angle_bas_gauche_vide.png"));
            default:
                return ImageIO.read(new File("sprites/blanc.png"));
        }
    }

    public String getCroixUrl(Sommet sommet) {
        if (!graphe.getPre().contains(sommet)) return "sprites/croix_vide.png";

        ArrayList<Sommet> sommetsAdjInCycle = (ArrayList<Sommet>) sommet.getAdjacents().stream().filter(s -> graphe.getPre().contains(s)).collect(Collectors.toList());

        if(sommetsAdjInCycle.size() == 4) return "sprites/croix_pleine.png";

        int[] branches = new int[2];

        for (int i = 0; i < sommetsAdjInCycle.size(); i++) {
            int x = sommetsAdjInCycle.get(i).getX() - sommet.getX();
            int y = sommetsAdjInCycle.get(i).getY() - sommet.getY();

            if (x == -1 && y == 0) branches[i] = 3;
            else if (x == 1 && y == 0) branches[i] = 1;
            else if (x == 0 && y == -1) branches[i] = 0;
            else branches[i] = 2;
        }

        if ((branches[0] == 0 && branches[1] == 2) || (branches[0] == 2 && branches[1] == 0)) return "sprites/croix_vertical_plein.png";
        else if ((branches[0] == 1 && branches[1] == 3) || (branches[0] == 3 && branches[1] == 1)) return "sprites/croix_horizontal_plein/png";
        else if ((branches[0] == 0 && branches[1] == 1) || (branches[0] == 1 && branches[1] == 0)) return "sprites/croix_angle_haut_droite_plein.png";
        else if ((branches[0] == 1 && branches[1] == 2) || (branches[0] == 2 && branches[1] == 1)) return "sprites/croix_angle_bas_droite_plein.png";
        else if ((branches[0] == 2 && branches[1] == 3) || (branches[0] == 3 && branches[1] == 2)) return "sprites/croix_angle_bas_gauche_plein.png";
        else if ((branches[0] == 0 && branches[1] == 3) || (branches[0] == 3 && branches[1] == 0)) return "sprites/croix_angle_haut_gauche_plein.png";
        else return "sprites/croix_vide.png";
    }
}
