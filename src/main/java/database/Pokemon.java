package database;

import javafx.scene.image.Image;

import java.util.List;

public class Pokemon {

    private int dexNum;
    private String name;
    private String internalName;
    private List<Type> types;
    private List<Ability> abilities;
    private List<Move> moves;
    private Image icon;
    private List<Evolution> evolutions;

    public Pokemon() {}

    public int getDexNum() {
        return dexNum;
    }

    public void setDexNum(int dexNum) {
        this.dexNum = dexNum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInternalName() {
        return internalName;
    }

    public void setInternalName(String internalName) {
        this.internalName = internalName;
    }

    public List<Type> getTypes() {
        return types;
    }

    public void setTypes(List<Type> types) {
        this.types = types;
    }

    public List<Ability> getAbilities() {
        return abilities;
    }

    public void setAbilities(List<Ability> abilities) {
        this.abilities = abilities;
    }

    public List<Move> getMoves() {
        return moves;
    }

    public void setMoves(List<Move> moves) {
        this.moves = moves;
    }

    public Image getIcon() {
        return icon;
    }

    public void setIcon(Image icon) {
        this.icon = icon;
    }

    public List<Evolution> getEvolutions() {
        return evolutions;
    }

    public void setEvolutions(List<Evolution> evolutions) {
        this.evolutions = evolutions;
    }

    public Pokemon(int dexNum, String name, String internalName, List<Type> types, List<Ability> abilities, List<Move> moves, Image icon, List<Evolution> evolutions) {
        this.dexNum = dexNum;
        this.name = name;
        this.internalName = internalName;
        this.types = types;
        this.abilities = abilities;
        this.moves = moves;
        this.icon = icon;
        this.evolutions = evolutions;
    }
}
