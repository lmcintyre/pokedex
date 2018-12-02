package database;

public class Ability {

    private String abilityName;
    private String abilityInternalName;
    private String abilityText;
    private boolean isHidden;

    public Ability() {}

    public Ability(String abilityName, String abilityInternalName, String abilityText, boolean isHidden) {
        this.abilityName = abilityName;
        this.abilityInternalName = abilityInternalName;
        this.abilityText = abilityText;
        this.isHidden = isHidden;
    }

    public String getAbilityName() {
        return abilityName;
    }

    public void setAbilityName(String abilityName) {
        this.abilityName = abilityName;
    }

    public String getAbilityInternalName() {
        return abilityInternalName;
    }

    public void setAbilityInternalName(String abilityInternalName) {
        this.abilityInternalName = abilityInternalName;
    }

    public String getAbilityText() {
        return abilityText;
    }

    public void setAbilityText(String abilityText) {
        this.abilityText = abilityText;
    }

    public boolean isHidden() {
        return isHidden;
    }

    public void setHidden(boolean hidden) {
        isHidden = hidden;
    }
}
