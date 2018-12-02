package database;

public class Category {

    static final int SPECIAL = 1;
    static final int PHYSICAL = 2;
    static final int STATUS = 3;

    public static int getIndex(String category) {

        switch(category.toUpperCase()) {
            case "SPECIAL": return SPECIAL;
            case "PHYSICAL": return PHYSICAL;
            case "STATUS": return STATUS;
        }

        return -1;
    }
}
