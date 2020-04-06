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
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

public class Vue extends JFrame {

    private Plateau plateau;
    private JPanel panel;
    private GridLayout grid;
    
    private ArrayList<Case> cycleMax;
    private ArrayList<Sommet> circuitMax;
    private ArrayList<ArrayList<Case>> cycles;
    private ArrayList<ArrayList<Sommet>> circuits;

    public Vue(Plateau plateau) throws IOException, ConnectException {
        this.plateau = plateau;
        long debut = System.currentTimeMillis(), fin = 0;
        if(plateau instanceof Tableau) {
        	Tableau.backtrack(null, 0, 0, false);
        	cycleMax = ((Tableau) plateau).getMeilleurCycle();
        	
        	Tableau.verifieListes(cycleMax);
        	
        	cycles = ((Tableau) plateau).getListeCycle();
        	cycles.remove(cycleMax);
        	
            fin = System.currentTimeMillis();
        }else {
            Graphe graphe = (Graphe) plateau;
        	graphe.contientCycle();
            fin = System.currentTimeMillis();

            System.out.println("Nombre de circuits trouvé : " + graphe.getCircuits().size());
        	System.out.println("Taille du circuit le plus long : " + graphe.getCircuits().stream().mapToInt(ArrayList::size).max().orElse(0));
            graphe.getCircuits().forEach(s -> System.out.println(String.format("Taille du circuit %d : %d", graphe.getCircuits().indexOf(s) + 1, s.size())));

            // SET CIRCUIT MAX
            Optional<ArrayList<Sommet>> optionalCircuit = graphe.getCircuits().stream().max(Comparator.comparingInt(ArrayList::size));
            circuitMax = optionalCircuit.orElseGet(ArrayList::new);
            circuits = graphe.getCircuits();
            circuits.remove(circuitMax);
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
        System.out.println("Le programme a mis " +((fin - debut)) +"ms pour éxécuter cette tâche");
    }

    public void setPlateauSize() {
        int width, height;
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

    public void addImagesAndBorder() throws IOException, ConnectException {
        for (int i = 0; i < plateau.getHauteur(); i++) {
            for (int j = 0; j < plateau.getLargeur(); j++) {

                Object caseConnect;

                if (plateau instanceof Graphe)
                    caseConnect = ((Graphe) plateau).getSommetDepuisCoordonnees(j, i);
                else
                    caseConnect = ((Tableau) plateau).getCase(i,j);

                BufferedImage sprite = caseConnect instanceof Sommet ? getUrl((Sommet) caseConnect) : getUrl((Case) caseConnect);

                // On crÃ©e un label contenant le sprite. Ce label peut Ãªtre resizable.
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
     * CrÃ©e une image Ã  partir de l'url du sprite et du Type du sommet.
     * @param sommet
     *      Sommet dont il faut trouver l'url.
     * @return
     *      L'image crÃ©Ã©e.
     * @throws IOException
     *      Si le fichier est introuvable.
     */
    public BufferedImage getUrl(Sommet sommet) throws IOException, ConnectException {
        if(sommet == null)
            return ImageIO.read(new File("sprites/blanc.png"));

        switch (sommet.type) {
            case CROIX:
                return ImageIO.read(new File(circuitMax.contains(sommet) ? "sprites/croix_max.png" : contientSommet(circuits, sommet) ? "sprites/croix_plein.png" : "sprites/croix_vide.png"));
            case VERTICAL:
                return ImageIO.read(new File(circuitMax.contains(sommet) ? "sprites/vertical_max.png" : contientSommet(circuits, sommet) ? "sprites/vertical_plein.png" : "sprites/vertical_vide.png"));
            case HORIZONTAL:
                return ImageIO.read(new File(circuitMax.contains(sommet) ? "sprites/horizontal_max.png" : contientSommet(circuits, sommet) ? "sprites/horizontal_plein.png" : "sprites/horizontal_vide.png"));
            case ANGLE_HAUT_DROITE:
                return ImageIO.read(new File(circuitMax.contains(sommet) ? "sprites/angle_haut_droite_max.png" : contientSommet(circuits, sommet) ? "sprites/angle_haut_droite_plein.png" : "sprites/angle_haut_droite_vide.png"));
            case ANGLE_BAS_DROITE:
                return ImageIO.read(new File(circuitMax.contains(sommet) ? "sprites/angle_bas_droite_max.png" : contientSommet(circuits, sommet) ? "sprites/angle_bas_droite_plein.png" : "sprites/angle_bas_droite_vide.png"));
            case ANGLE_HAUT_GAUCHE:
                return ImageIO.read(new File(circuitMax.contains(sommet) ? "sprites/angle_haut_gauche_max.png" : contientSommet(circuits, sommet) ? "sprites/angle_haut_gauche_plein.png" : "sprites/angle_haut_gauche_vide.png"));
            case ANGLE_BAS_GAUCHE:
                return ImageIO.read(new File(circuitMax.contains(sommet) ? "sprites/angle_bas_gauche_max.png" : contientSommet(circuits, sommet) ? "sprites/angle_bas_gauche_plein.png" : "sprites/angle_bas_gauche_vide.png"));
            default:
                return ImageIO.read(new File("sprites/blanc.png"));
        }
    }

    public boolean contientSommet(ArrayList<ArrayList<Sommet>> circuits, Sommet sommet) {
        for (ArrayList<Sommet> circuit: circuits) {
            if (circuit.contains(sommet)) return true;
        }
        return false;
    }
    
    public BufferedImage getUrl(Case c) throws IOException {
        if(c == null)
            return ImageIO.read(new File("sprites/blanc.png"));

        Tableau plat = (Tableau) plateau;

        switch (c.getType()) {
            case CROIX:
                return ImageIO.read(new File(cycleMax.contains(c) ? "sprites/croix_max.png" : Tableau.contientCase(cycles,c) ? "sprites/croix_plein.png" : "sprites/croix_vide.png"));
            case VERTICAL:
                return ImageIO.read(new File(cycleMax.contains(c) ? "sprites/vertical_max.png" : Tableau.contientCase(cycles,c) ? "sprites/vertical_plein.png" : "sprites/vertical_vide.png"));
            case HORIZONTAL:
                return ImageIO.read(new File(cycleMax.contains(c) ? "sprites/horizontal_max.png" : Tableau.contientCase(cycles,c) ? "sprites/horizontal_plein.png" : "sprites/horizontal_vide.png"));
            case ANGLE_HAUT_DROITE:
                return ImageIO.read(new File(cycleMax.contains(c) ? "sprites/angle_haut_droite_max.png" : Tableau.contientCase(cycles,c) ? "sprites/angle_haut_droite_plein.png" : "sprites/angle_haut_droite_vide.png"));
            case ANGLE_BAS_DROITE:
                return ImageIO.read(new File(cycleMax.contains(c) ? "sprites/angle_bas_droite_max.png" : Tableau.contientCase(cycles,c) ? "sprites/angle_bas_droite_plein.png" : "sprites/angle_bas_droite_vide.png"));
            case ANGLE_HAUT_GAUCHE:
                return ImageIO.read(new File(cycleMax.contains(c) ? "sprites/angle_haut_gauche_max.png" : Tableau.contientCase(cycles,c) ? "sprites/angle_haut_gauche_plein.png" : "sprites/angle_haut_gauche_vide.png"));
            case ANGLE_BAS_GAUCHE:
                return ImageIO.read(new File(cycleMax.contains(c) ? "sprites/angle_bas_gauche_max.png" : Tableau.contientCase(cycles,c) ? "sprites/angle_bas_gauche_plein.png" : "sprites/angle_bas_gauche_vide.png"));
            default:
                return ImageIO.read(new File("sprites/blanc.png"));
        }
    }

    // ON GARDE MAIS C'EST INUTILE POUR LE MOMENT.
    /*public String getCroixUrl(Sommet sommet) {
        Graphe plateau = (Graphe) this.plateau;
        if (!plateau.getPre().contains(sommet)) return "sprites/croix_vide.png";

        ArrayList<Sommet> sommetsAdjInCycle = (ArrayList<Sommet>) sommet.getAdjacents().stream().filter(s -> plateau.getPre().contains(s)).collect(Collectors.toList());

        if(sommetsAdjInCycle.size() == 4) return "sprites/croix_max.png";

        int[] branches = new int[sommetsAdjInCycle.size()];

        for (int i = 0; i < sommetsAdjInCycle.size(); i++) {
            int x = sommetsAdjInCycle.get(i).x - sommet.x;
            int y = sommetsAdjInCycle.get(i).y - sommet.y;

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
    }*/
}
