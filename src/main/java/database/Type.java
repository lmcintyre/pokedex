package database;

import javafx.scene.image.Image;

public class Type {

    enum Kind {
        PRIMARY, SECONDARY, MOVE
    }

    static final String[] types = {"BUG",
                                    "DARK",
                                    "DRAGON",
                                    "ELECTRIC",
                                    "FAIRY",
                                    "FIGHTING",
                                    "FIRE",
                                    "FLYING",
                                    "GHOST",
                                    "GRASS",
                                    "GROUND",
                                    "ICE",
                                    "NORMAL",
                                    "POISON",
                                    "PSYCHIC",
                                    "ROCK",
                                    "STEEL",
                                    "WATER"};

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Kind getKind() {
        return kind;
    }

    public void setKind(Kind kind) {
        this.kind = kind;
    }

    public Image getTypeIcon() {
        return typeIcon;
    }

    public void setTypeIcon(Image typeIcon) {
        this.typeIcon = typeIcon;
    }

    public Type() {}

    public Type(String typeName, Kind kind, Image typeIcon) {
        this.typeName = typeName;
        this.kind = kind;
        this.typeIcon = typeIcon;
    }

    private String typeName;
    private Kind kind;
    private Image typeIcon;

    public static int getIndex(String type) {

        for (int i = 0; i < types.length; i++)
            if (type.toUpperCase().equals(types[i]))
                return i + 1;

        return -1;
    }
}
