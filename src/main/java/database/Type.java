package database;

public class Type {

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

    public static int getIndex(String type) {

        for (int i = 0; i < types.length; i++)
            if (type.toUpperCase().equals(types[i]))
                return i + 1;

        return -1;
    }
}
