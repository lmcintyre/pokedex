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
