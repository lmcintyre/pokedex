package database;

import java.io.*;

public class Pokemon {

    private final String imageFormat = "%3d.png";

    private int id;
    private String name;
    private String intName;
    private int type1;
    private int type2;
    private int ability1;
    private int ability2;
    private int abilityh;
    private String dexText;
    private String iconResourcePath;

    private String evolveText;

    public Pokemon() {}

    public Pokemon(int id, String name, String intName, int type1, int type2, int ability1, int ability2, int abilityh, String dexText) {
        this.id = id;
        this.name = name;
        this.intName = intName;
        this.type1 = type1;
        this.type2 = type2;
        this.ability1 = ability1;
        this.ability2 = ability2;
        this.abilityh = abilityh;
        this.dexText = dexText;
        this.iconResourcePath = String.format(imageFormat, id);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIntName() {
        return intName;
    }

    public void setIntName(String intName) {
        this.intName = intName;
    }

    public int getType1() {
        return type1;
    }

    public void setType1(int type1) {
        this.type1 = type1;
    }

    public int getType2() {
        return type2;
    }

    public void setType2(int type2) {
        this.type2 = type2;
    }

    public int getAbility1() {
        return ability1;
    }

    public void setAbility1(int ability1) {
        this.ability1 = ability1;
    }

    public int getAbility2() {
        return ability2;
    }

    public void setAbility2(int ability2) {
        this.ability2 = ability2;
    }

    public int getAbilityh() {
        return abilityh;
    }

    public void setAbilityh(int abilityh) {
        this.abilityh = abilityh;
    }

    public String getDexText() {
        return dexText;
    }

    public void setDexText(String dexText) {
        this.dexText = dexText;
    }

    public String getEvolveText() {
        return evolveText;
    }

    public void setEvolveText(String evolveText) {
        this.evolveText = evolveText;
    }

    public String getIconResourcePath() {
        return iconResourcePath;
    }

    public void setIconResourcePath() {
        this.iconResourcePath = String.format(imageFormat, id).replace(" ", "0");
    }

    public byte[] getBytesFromIcon() {

        InputStream is = Pokemon.class.getResourceAsStream("/img/poke/" + iconResourcePath);
        ByteArrayOutputStream bos = null;

        try {
            byte[] buffer = new byte[10240];
            bos = new ByteArrayOutputStream();
            for (int len; (len = is.read(buffer)) != -1;) {
                bos.write(buffer, 0, len);
            }
        } catch (IOException e2) {
            System.err.println(e2.getMessage());
        }
        return bos.toByteArray();
    }

    @Override
    public String toString() {
        return String.format("Id: %d | Name: %s | IntName: %s | Type1: %d | Type2: %d | Ability1: %d | Ability2: %d | AbilityH: %d | DexText: %s | Path: %s",
                            id, name, intName, type1, type2, ability1, ability2, abilityh, dexText, iconResourcePath);
    }


}
