package tableau;

public class Case {

    private enum Type {
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
}
