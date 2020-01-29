package tableau;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Vue extends JFrame {
	
	private static int taille = 10;
	private static Case[][] cases;
	
	public Vue() {
		JPanel main = new JPanel();
		getContentPane().add(main);
		setSize(500,500);
	}
	
	public void paint(Graphics g) {
        super.paint(g);  // fixes the immediate problem.
        g.drawLine(50, 50, 450, 50);
        g.drawLine(50, 50, 50, 450);
        g.drawLine(450, 450, 450, 50);
        g.drawLine(450, 450, 50, 450);
        
        int tailleXY = 400 / (taille);
        
        for(int i = 1; i < taille; i++) {
        	g.drawLine(50+(tailleXY*i), 50, 50+(tailleXY*i), 450);
        }
        
        for(int i = 1; i < taille; i++) {
        	g.drawLine(50, 50+(tailleXY*i), 450, 50+(tailleXY*i));
        }
    }
	
	public static void main(String args[]) {
		Vue vue = new Vue();
		Plateau plateau = new Plateau(taille);
		plateau.genererPlateau();
		cases = plateau.getCases();
		vue.setVisible(true);
	}

}
