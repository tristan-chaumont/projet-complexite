package tableau;

public class Case {

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

    private Type type;

    public Case(Type t) {
        type = t;
    }
    
    public String toString() {
    	String res = "";
    	if(this.type == Type.CROIX) {
    		res = "+";
    	}else if(this.type == Type.BLANC) {
    		res = " ";
    	}else if(this.type == Type.VERTICAL) {
    		res = "|";
    	}else if(this.type == Type.HORIZONTAL) {
    		res = "-";
    	}else if(this.type == Type.ANGLE_HAUT_GAUCHE) {
    		res = "|¯";
    	}else if(this.type == Type.ANGLE_HAUT_DROITE) {
    		res = "¯|";
    	}else if(this.type == Type.ANGLE_BAS_GAUCHE) {
    		res = "|_";
    	}else if(this.type == Type.ANGLE_BAS_DROITE) {
    		res = "_|";
    	}
    	
    	return res;
    }
    
    /*************************/
   /****GETTERS & SETTERS****/
  /*************************/
    
	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}
}
