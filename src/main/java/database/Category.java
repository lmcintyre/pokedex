package database;

import javafx.scene.image.Image;

public class Category {

    static final int SPECIAL = 1;
    static final int PHYSICAL = 2;
    static final int STATUS = 3;

    private String name;
    private Image icon;

    public Category() { }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Image getIcon() {
        return icon;
    }

    public void setIcon(Image icon) {
        this.icon = icon;
    }

    public Category(String name, Image icon) {
        this.name = name;
        this.icon = icon;
    }

    public static int getIndex(String category) {

        switch(category.toUpperCase()) {
            case "SPECIAL": return SPECIAL;
            case "PHYSICAL": return PHYSICAL;
            case "STATUS": return STATUS;
        }

        return -1;
    }
}
