package main;

import plateau.Graphe;
import plateau.Sommet;
import tableau.Case;
import tableau.Tableau;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class Vue extends JFrame {

    private Plateau plateau;
    private JPanel panel;
    private GridLayout grid;
    
    private ArrayList<Case> cycle;

    public Vue(Plateau plateau) throws IOException {
    	long debut = System.currentTimeMillis();
        this.plateau = plateau;
        if(plateau instanceof Tableau) {
        	cycle = ((Tableau) plateau).getMeilleurCycle(((Tableau)plateau).getCycles());
        }
        grid = new GridLayout(plateau.getHauteur(), plateau.getLargeur());
        panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
        panel = new JPanel(grid);
        setPlateauSize();
        addImagesAndBorder();
        setContentPane(panel);
        setTitle("Connect");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
        //setMinimumSize(getPreferredSize());
        setVisible(true);
        long fin = System.currentTimeMillis();
        System.out.println("Le programme a mis " +((fin - debut)) +"ms pour �x�cuter cette t�che");
    }

    public void setPlateauSize() {
        int width = 0, height = 0;
    	if(plateau.getLargeur() * 50 > 1920 && plateau.getHauteur() * 50 > 1080) {
            GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
            width = gd.getDisplayMode().getWidth();
            height = gd.getDisplayMode().getHeight();
        } else {
            width = plateau.getLargeur() * 50;
            height = plateau.getHauteur() * 50;
        }
        
        setMinimumSize(new Dimension(width, height));
    }

    public void addImagesAndBorder() throws IOException {
        if (plateau instanceof Graphe)
            addImagesAndBorderGraphe();
        else
            addImagesAndBorderTableau();
    }

    private void addImagesAndBorderGraphe() throws IOException {
        for (int i = 0; i < plateau.getHauteur(); i++) {
            for (int j = 0; j < plateau.getLargeur(); j++) {
                Sommet sommet = ((Graphe) plateau).getSommetDepuisCoordonnees(j, i);
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
                label.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0, 75)));

                panel.add(label);
            }
        }
    }

    private void addImagesAndBorderTableau() throws IOException {
    	for (int i = 0; i < plateau.getHauteur(); i++) {
            for (int j = 0; j < plateau.getLargeur(); j++) {
            	Case c = ((Tableau) plateau).getCase(i,j);
                BufferedImage sprite = getUrl(c);

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
                label.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0, 75)));

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

        Graphe plateau = (Graphe) this.plateau;

        switch (sommet.getType()) {
            case CROIX:
                return ImageIO.read(new File(getCroixUrl(sommet)));
            case VERTICAL:
                return ImageIO.read(new File(plateau.getPre().contains(sommet) ? "sprites/vertical_plein.png" : "sprites/vertical_vide.png"));
            case HORIZONTAL:
                return ImageIO.read(new File(plateau.getPre().contains(sommet) ? "sprites/horizontal_plein.png" : "sprites/horizontal_vide.png"));
            case ANGLE_HAUT_DROITE:
                return ImageIO.read(new File(plateau.getPre().contains(sommet) ? "sprites/angle_haut_droite_plein.png" : "sprites/angle_haut_droite_vide.png"));
            case ANGLE_BAS_DROITE:
                return ImageIO.read(new File(plateau.getPre().contains(sommet) ? "sprites/angle_bas_droite_plein.png" : "sprites/angle_bas_droite_vide.png"));
            case ANGLE_HAUT_GAUCHE:
                return ImageIO.read(new File(plateau.getPre().contains(sommet) ? "sprites/angle_haut_gauche_plein.png" : "sprites/angle_haut_gauche_vide.png"));
            case ANGLE_BAS_GAUCHE:
                return ImageIO.read(new File(plateau.getPre().contains(sommet) ? "sprites/angle_bas_gauche_plein.png" : "sprites/angle_bas_gauche_vide.png"));
            default:
                return ImageIO.read(new File("sprites/blanc.png"));
        }
    }
    
    public BufferedImage getUrl(Case c) throws IOException {
        if(c == null)
            return ImageIO.read(new File("sprites/blanc.png"));

        Tableau plateau = (Tableau) this.plateau;

        switch (c.getType()) {
            case CROIX:
                return ImageIO.read(new File(cycle.contains(c) ? "sprites/croix_pleine.png" : "sprites/croix_vide.png"));
            case VERTICAL:
                return ImageIO.read(new File(cycle.contains(c) ? "sprites/vertical_plein.png" : "sprites/vertical_vide.png"));
            case HORIZONTAL:
                return ImageIO.read(new File(cycle.contains(c) ? "sprites/horizontal_plein.png" : "sprites/horizontal_vide.png"));
            case ANGLE_HAUT_DROITE:
                return ImageIO.read(new File(cycle.contains(c) ? "sprites/angle_haut_droite_plein.png" : "sprites/angle_haut_droite_vide.png"));
            case ANGLE_BAS_DROITE:
                return ImageIO.read(new File(cycle.contains(c) ? "sprites/angle_bas_droite_plein.png" : "sprites/angle_bas_droite_vide.png"));
            case ANGLE_HAUT_GAUCHE:
                return ImageIO.read(new File(cycle.contains(c) ? "sprites/angle_haut_gauche_plein.png" : "sprites/angle_haut_gauche_vide.png"));
            case ANGLE_BAS_GAUCHE:
                return ImageIO.read(new File(cycle.contains(c) ? "sprites/angle_bas_gauche_plein.png" : "sprites/angle_bas_gauche_vide.png"));
            default:
                return ImageIO.read(new File("sprites/blanc.png"));
        }
    }

    public String getCroixUrl(Sommet sommet) {
        Graphe plateau = (Graphe) this.plateau;
        if (!plateau.getPre().contains(sommet)) return "sprites/croix_vide.png";

        ArrayList<Sommet> sommetsAdjInCycle = (ArrayList<Sommet>) sommet.getAdjacents().stream().filter(s -> plateau.getPre().contains(s)).collect(Collectors.toList());

        if(sommetsAdjInCycle.size() == 4) return "sprites/croix_pleine.png";

        int[] branches = new int[sommetsAdjInCycle.size()];

        for (int i = 0; i < sommetsAdjInCycle.size(); i++) {
            int x = sommetsAdjInCycle.get(i).getX() - sommet.getX();
            int y = sommetsAdjInCycle.get(i).getY() - sommet.getY();

            if (x == -1 && y == 0) branches[i] = 3;
            else if (x == 1 && y == 0) branches[i] = 1;
            else if (x == 0 && y == -1) branches[i] = 0;
            else branches[i] = 2;
        }

        if (branches.length == 2) {
            if ((branches[0] == 0 && branches[1] == 2) || (branches[0] == 2 && branches[1] == 0))
                return "sprites/croix_vertical_plein.png";
            else if ((branches[0] == 1 && branches[1] == 3) || (branches[0] == 3 && branches[1] == 1))
                return "sprites/croix_horizontal_plein.png";
            else if ((branches[0] == 0 && branches[1] == 1) || (branches[0] == 1 && branches[1] == 0))
                return "sprites/croix_angle_haut_droite_plein.png";
            else if ((branches[0] == 1 && branches[1] == 2) || (branches[0] == 2 && branches[1] == 1))
                return "sprites/croix_angle_bas_droite_plein.png";
            else if ((branches[0] == 2 && branches[1] == 3) || (branches[0] == 3 && branches[1] == 2))
                return "sprites/croix_angle_bas_gauche_plein.png";
            else if ((branches[0] == 0 && branches[1] == 3) || (branches[0] == 3 && branches[1] == 0))
                return "sprites/croix_angle_haut_gauche_plein.png";
        }
        return "sprites/croix_vide.png";
    }
}
