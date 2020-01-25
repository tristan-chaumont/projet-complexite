package tableau;

public class Plateau {

    private Case[][] cases;

    public Plateau(Case[][] c) {
        cases = c;
    }
    
    public String getMeilleur() {
    	for(int i = 0; i < cases.length; i++) {
    		for(int j = 0; j < cases.length; j++) {
    			if(cases[i][j].getType() == Case.Type.ANGLE_HAUT_GAUCHE) {
    				if(cases[i][j].getType() == Case.Type.ANGLE_HAUT_DROITE || cases[i][j].getType() == Case.Type.HORIZONTAL) {
    					//OK
    				}
    			}
    		}
    	}
    	return "";
    }
    
    public String toString() {
    	String res = "";
    	for(int i = 0; i < cases.length; i++) {
    		for(int j = 0; j < cases.length; j++) {
    			res += cases[i][j].toString();
    		}
    		res += "\n";
    	}
    	
    	return res;
    }
}
